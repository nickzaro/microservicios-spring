package com.nick.microservices.api.composite;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(description = "REST API for composite product information")
public interface ProductCompositeService {

    @ApiOperation(value = "${api.product-composite.get-composite-product.description}",
    notes = "${api.product-composite.get-composite-product.notes}")

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code=404, message = "Not Found"),
            @ApiResponse(code=422, message = "Unprocessable entity"),
    }
    )
    @GetMapping(value = "/product-composite/{productId}", produces = "application/json")
    ProductAggregate getProduct(@PathVariable int productId);
}
