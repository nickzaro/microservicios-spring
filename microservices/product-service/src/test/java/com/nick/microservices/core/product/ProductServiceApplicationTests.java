package com.nick.microservices.core.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceApplicationTests {

	@Autowired
	private WebTestClient client;
	private final int PRODUCT_ID_NOT_FOUND =13;
	@Test
	public void getProductById(){
		int productId =1;
		client.get()
				.uri("/product/" + productId)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.productId").isEqualTo(productId);
	}

	@Test
	public void getProductNotFound(){
		client.get()
				.uri("/product/"+PRODUCT_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				//.expectStatus().isNotFound() devuelve 500
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product/"+PRODUCT_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("No product found for productId: " + PRODUCT_ID_NOT_FOUND);
	}

}
