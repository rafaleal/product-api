package com.rakuten.productapi.controller;

import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.rakuten.productapi.constant.Endpoints.*;

@RefreshScope
@RestController
@RequestMapping(API_V1 + API_PRODUCTS)
@Api(value = "ProductsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Posts the product with title, description, price, currency and categories")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Product.class)})
    public ResponseEntity<Product> postProduct(@RequestBody ProductDTO product) {
        Product returnedProduct = this.productService.createProduct(product);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_PRODUCTS_ID)
                .buildAndExpand(returnedProduct.getId())
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(returnedProduct, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = API_PRODUCTS_ID)
    @ApiOperation("Deletes the product")
    public ResponseEntity deleteProduct(@PathVariable UUID uuid) {
        this.productService.deleteProduct(uuid);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = API_PRODUCTS_ID)
    @ApiOperation("Update categories in respective product")
    public ResponseEntity<Product> patchProduct(@PathVariable UUID uuid, @RequestBody List<String> categories) {

        return new ResponseEntity<>(this.productService.updateProduct(uuid, categories), HttpStatus.OK);
    }



}
