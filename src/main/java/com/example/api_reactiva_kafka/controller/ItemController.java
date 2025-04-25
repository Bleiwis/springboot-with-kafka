package com.example.api_reactiva_kafka.controller;

import com.example.api_reactiva_kafka.kafka.KafkaProducer;
import com.example.api_reactiva_kafka.model.Item;
import com.example.api_reactiva_kafka.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for managing CRUD operations for Items.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * Get all items.
     * @return Flux of items.
     */
    @GetMapping
    public Flux<Item> getAllItems() {
        return itemService.getAllItems();
    }

    /**
     * Get an item by ID.
     * @param id Item ID.
     * @return Mono of the item.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Item>> getItemById(@PathVariable String id) {
        return itemService.getItemById(id)
                .map(item -> ResponseEntity.ok(item))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Create a new item.
     * @param item Item to create.
     * @return Mono of the created item.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    /**
     * Update an existing item.
     * @param id Item ID.
     * @param item Item details to update.
     * @return Mono of the updated item.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id, @RequestBody Item item) {
        return itemService.updateItem(id, item)
                .map(updatedItem -> ResponseEntity.ok(updatedItem))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Delete an item by ID.
     * @param id Item ID.
     * @return Mono of ResponseEntity.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return itemService.deleteItem(id)
                .map(r -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Send a message to Kafka.
     * @param message The message to send.
     */
    @PostMapping("/kafka/send")
    public void sendMessageToKafka(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
    }

    /**
     * Update the inventory of a product and send an event to Kafka.
     * @param id The ID of the product.
     * @param newQuantity The new quantity of the product.
     * @return Mono of ResponseEntity.
     */
    @PatchMapping("/{id}/inventory")
    public Mono<ResponseEntity<Item>> updateInventory(@PathVariable String id, @RequestParam int newQuantity) {
        return itemService.getItemById(id)
                .flatMap(item -> {
                    int oldQuantity = (int) item.getPrice(); // Convert double to int for quantity.
                    item.setPrice(newQuantity);
                    return itemService.updateItem(id, item)
                            .doOnSuccess(updatedItem -> {
                                // Create an event to send to Kafka
                                Map<String, Object> event = new HashMap<>();
                                event.put("productId", updatedItem.getId());
                                event.put("productName", updatedItem.getName());
                                event.put("oldQuantity", oldQuantity);
                                event.put("newQuantity", newQuantity);
                                event.put("updatedAt", LocalDateTime.now());

                                // Send the event to Kafka
                                kafkaProducer.sendMessage(event.toString());
                            });
                })
                .map(updatedItem -> ResponseEntity.ok(updatedItem))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
