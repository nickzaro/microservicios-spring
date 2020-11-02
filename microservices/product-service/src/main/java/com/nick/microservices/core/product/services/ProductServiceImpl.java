package com.nick.microservices.core.product.services;

import com.nick.microservices.api.core.product.Product;
import com.nick.microservices.api.core.product.ProductService;
import com.nick.microservices.util.exceptions.InvalidInputException;
import com.nick.microservices.util.exceptions.NotFoundException;
import com.nick.microservices.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl  implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil){
        this.serviceUtil = serviceUtil;
    }

    @Override
    @GetMapping(value = "/product/{productId}")
    public Product getProduct(@PathVariable(value = "productId", required = true) int productId) {
        LOG.debug("/product return the found product for productId={}", productId);

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        if (productId == 13) throw new NotFoundException("No product found for productId: " + productId);
        return new Product(productId, "name-"+productId, 123,
                serviceUtil.getServiceAddress());
    }
}
