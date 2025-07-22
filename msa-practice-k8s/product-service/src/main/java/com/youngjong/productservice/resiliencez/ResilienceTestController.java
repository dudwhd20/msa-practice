package com.youngjong.productservice.resiliencez;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/resiliencez")
@RequiredArgsConstructor
public class ResilienceTestController {

    private final ResilienceTestService service;

    @GetMapping("/retry")
    public String retry(@RequestParam(defaultValue = "anonymous") String caller) {
        return service.retryOnlyTest(caller);
    }

    @GetMapping("/explode")
    public String explode(@RequestParam(defaultValue = "anonymous") String caller) {
        return service.explodeWithStyle(caller);
    }


}
