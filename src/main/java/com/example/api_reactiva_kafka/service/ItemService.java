package com.example.api_reactiva_kafka.service;

import com.example.api_reactiva_kafka.model.Item;
import com.example.api_reactiva_kafka.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for managing Item operations.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Get all items.
     * @return Flux of items.
     */
    public Flux<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Get an item by ID.
     * @param id Item ID.
     * @return Mono of the item.
     */
    public Mono<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    /**
     * Create a new item.
     * @param item Item to create.
     * @return Mono of the created item.
     */
    public Mono<Item> createItem(Item item) {
        return itemRepository.save(item);
    }

    /**
     * Update an existing item.
     * @param id Item ID.
     * @param item Item details to update.
     * @return Mono of the updated item.
     */
    public Mono<Item> updateItem(String id, Item item) {
        return itemRepository.findById(id)
                .flatMap(existingItem -> {
                    existingItem.setName(item.getName());
                    existingItem.setPrice(item.getPrice());
                    return itemRepository.save(existingItem);
                });
    }

    /**
     * Delete an item by ID.
     * @param id Item ID.
     * @return Mono of void.
     */
    public Mono<Void> deleteItem(String id) {
        return itemRepository.deleteById(id);
    }
}
