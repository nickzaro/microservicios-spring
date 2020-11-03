package com.nick.microservices.composite.product;

import com.nick.microservices.api.core.product.Product;
import com.nick.microservices.api.core.recommendation.Recommendation;
import com.nick.microservices.api.core.review.Review;
import com.nick.microservices.composite.product.services.ProductCompositeIntegration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.when;


@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCompositeServiceTests {
    @Autowired
    private WebTestClient client;

    private final int PRODUCT_ID_OK =1;

    @MockBean
    private ProductCompositeIntegration compositeIntegration;

    @Before
    public void setup(){
        when(compositeIntegration.getProduct(PRODUCT_ID_OK))
                .thenReturn(new Product(PRODUCT_ID_OK,"name",1,"mock-address"));

        when(compositeIntegration.getRecommendations(PRODUCT_ID_OK))
                .thenReturn(Collections.singletonList(new Recommendation(PRODUCT_ID_OK,1,"author",1,"content","mock-address")));
        when(compositeIntegration.getReviews(PRODUCT_ID_OK))
                .thenReturn(Collections.singletonList(new Review(PRODUCT_ID_OK,1,"author","subject","content","mock-address")));

    }

    @Test
    public void getProductById(){

        client.get()
                .uri("/product-composite/"+PRODUCT_ID_OK)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
                .jsonPath("$.recommendations.length()").isEqualTo(1)
                .jsonPath("$.reviews.length()").isEqualTo(1);
    }

}
