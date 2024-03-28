package com.uala.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheClearService {
    @CacheEvict(value = "userCache", key = "#id")
    public void evictUserCache(String id) {
        log.info("Evicting user with id {} from cache", id);
    }
}
