package com.newer.springboot.logback;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author SONG
 */
public interface LogRepository extends MongoRepository<MyLog, String> {
}
