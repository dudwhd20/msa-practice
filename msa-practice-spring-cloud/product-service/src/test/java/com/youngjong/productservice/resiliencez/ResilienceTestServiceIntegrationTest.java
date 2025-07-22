package com.youngjong.productservice.resiliencez;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ResilienceTestServiceIntegrationTest {

    @Autowired
    private ResilienceTestService resilienceTestService;

    @Test
    void retry_5회후_circuitBreaker_동작_검증() {
        String result = null;
        for (int i = 0; i < 6; i++) {
            try {
                System.out.println("[TEST] 호출 " + (i + 1) + "회차: retry 및 circuitbreaker 동작 확인");
                resilienceTestService.explodeWithStyle("test-user");
            } catch (Exception e) {
                System.out.println("[TEST] 예외 발생: " + e.getMessage());
            }
        }
        // 6번째 호출(5회 retry 후 circuit open)에서 fallback이 circuit breaker로 바뀌는지 확인
        try {
            System.out.println("[TEST] 7번째 호출: circuit breaker fallback 동작 확인");
            result = resilienceTestService.explodeWithStyle("test-user");
        } catch (Exception e) {
            System.out.println("[TEST] 예외 발생: " + e.getMessage());
        }
        System.out.println("[TEST] 최종 결과: " + result);
        assertThat(result).isEqualTo("CircuitBreaker4j says: circuit open!");
    }
}
