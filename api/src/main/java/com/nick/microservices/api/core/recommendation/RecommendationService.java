package com.nick.microservices.api.core.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RecommendationService {

    @GetMapping(value = "/recommendation/{productId}", produces = "application/json")
    List<Recommendation> getRecommendations(@PathVariable(value = "productId", required = true) int productId);
}
