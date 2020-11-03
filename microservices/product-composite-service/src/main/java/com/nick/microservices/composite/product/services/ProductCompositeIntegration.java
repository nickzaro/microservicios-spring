package com.nick.microservices.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nick.microservices.api.core.product.Product;
import com.nick.microservices.api.core.product.ProductService;
import com.nick.microservices.api.core.recommendation.Recommendation;
import com.nick.microservices.api.core.recommendation.RecommendationService;
import com.nick.microservices.api.core.review.Review;
import com.nick.microservices.api.core.review.ReviewService;
import com.nick.microservices.util.exceptions.InvalidInputException;
import com.nick.microservices.util.exceptions.NotFoundException;
import com.nick.microservices.util.http.HttpErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);
    private ObjectMapper mapper;

    private final RestTemplate restTemplate;
    private final String productServiceURL;
    private final String reviewServiceURL;
    private final String recommendationServiceURL;

    @Autowired
    public ProductCompositeIntegration(RestTemplate restTemplate,
                                       ObjectMapper mapper,
                                       @Value("${app.product-service.host}") String productServiceHost,
                                       @Value("${app.product-service.port}") String productServicePort,
                                       @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                                       @Value("${app.recommendation-service.port}") String recommendationServicePort,
                                       @Value("${app.review-service.host}") String reviewServiceHost,
                                       @Value("${app.review-service.port}") String reviewServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.productServiceURL = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        this.recommendationServiceURL = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation/";
        this.reviewServiceURL = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review/";

    }

    // usado para recibir peticiones en "/product/{productId}"
    @Override
    public Product getProduct(int productId) {
        try {
            String url = productServiceURL + productId;
            LOG.debug("Will call getProduct API on URL: {}", url);
            // realizo una comunicacion con el microservicio product-service
            Product product = restTemplate.getForObject(url, Product.class);
            LOG.debug("Found a product with id: {}", product.getProductId());
            return product;
        } catch (HttpClientErrorException ex){
            switch (ex.getStatusCode()){
                case NOT_FOUND:
                    throw  new NotFoundException(getErrorMessage(ex));
                case UNPROCESSABLE_ENTITY:
                    throw  new InvalidInputException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
            }
        }
        return null;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {

        try{
            String url = recommendationServiceURL+productId;
            LOG.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Recommendation>>(){}).getBody();

            LOG.debug("Found {} recommendations for a product with id: {}",recommendations.size(),productId);
            return recommendations;
        } catch ( Exception ex){
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}",ex.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    public List<Review> getReviews(int productId) {
        try{
            String url = reviewServiceURL+productId;
            LOG.debug("Will call getReviews API on URL: {}", url);
            List<Review> reviews = restTemplate.exchange(url, GET, null,
                    new ParameterizedTypeReference<List<Review>>(){}).getBody();

            LOG.debug("Found {} reviews for a product with id: {}",reviews.size(),productId);
            return reviews;
        } catch ( Exception ex){
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}",ex.getMessage());
            return new ArrayList<>();
        }
    }

    private String getErrorMessage(HttpClientErrorException ex){
        try{
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch(IOException ioex){
            return ioex.getMessage();
        }
    }
}
