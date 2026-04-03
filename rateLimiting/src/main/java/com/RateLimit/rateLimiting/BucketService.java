package com.RateLimit.rateLimiting;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BucketService {

    private final ConcurrentHashMap<String, Bucket>  cache =
            new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key){
        return cache.computeIfAbsent(key, this::newBucket);
    }

    private Bucket newBucket(String key){
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(30)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
