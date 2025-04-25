package com.example.api_reactiva_kafka.repository;

import com.example.api_reactiva_kafka.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Reactive repository for Item entities.
 */
public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
}
