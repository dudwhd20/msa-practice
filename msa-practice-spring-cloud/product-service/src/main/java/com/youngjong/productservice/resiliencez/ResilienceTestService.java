package com.youngjong.productservice.resiliencez;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResilienceTestService {

    @Retry(name = "retry4j", fallbackMethod = "onRetryFail")
    @CircuitBreaker(name = "cb4j", fallbackMethod = "onCircuitBreak")
//    @Bulkhead(name = "bulkhead4j", fallbackMethod = "onBulkheadCrash")
    public String explodeWithStyle(String caller) {
        log.info("💣 Boom! Call from [{}] triggered unstable service", caller);
        throw new RuntimeException("Simulated explosion for test 🎆");
    }

    @Retry(name = "retry4j", fallbackMethod = "onRetryFail")
    public String retryOnlyTest(String caller) {
        log.info("🔥 [RetryTest] Attempted call by [{}]", caller);
        throw new RuntimeException("Retry test failure");
    }

    public String onRetryFail(String caller, Throwable e) {
        log.warn("🛑 Retry4j fallback engaged for [{}]: {}", caller, e.getMessage());
        return "Retry4j says: try again later!";
    }

    public String onCircuitBreak(String caller, Throwable e) {
        log.warn("⚡ CircuitBreaker4j fallback engaged for [{}]: {}", caller, e.getMessage());
        return "CircuitBreaker4j says: circuit open!";
    }

    public String onBulkheadCrash(String caller, Throwable e) {
        log.warn("🚪 Bulkhead4j fallback engaged for [{}]: {}", caller, e.getMessage());
        return "Bulkhead4j says: too crowded, come back later!";
    }
}
