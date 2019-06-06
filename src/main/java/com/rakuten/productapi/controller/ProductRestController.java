package com.rakuten.productapi.controller;

import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.exception.ProductBadRequestException;
import com.rakuten.productapi.exception.ProductNotFoundException;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static com.rakuten.productapi.constant.Endpoints.*;

@RefreshScope
@RestController
@RequestMapping(API_V1 + API_PRODUCTS)
@Api(value = "ProductsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRestController {

    private Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Posts the product with title, description, price, currency and categories")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Product.class)})
    public ResponseEntity<Product> postProduct(@Valid @RequestBody ProductDTO product) throws ProductBadRequestException {
        Product returnedProduct = this.productService.createProduct(product);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_V1 + API_PRODUCTS_ID)
                .buildAndExpand(returnedProduct.getId())
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(returnedProduct, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = API_PRODUCTS_ID)
    @ApiOperation("Deletes the product by UUID")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "NO CONTENT"), @ApiResponse(code = 404, message = "NOT FOUND")})
    public ResponseEntity deleteProduct(@PathVariable UUID uuid) throws ProductNotFoundException {
        this.productService.deleteProduct(uuid);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = API_PRODUCTS_ID + API_ASSIGN_CATEGORIES)
    @ApiOperation("Assigns new category in respective product")
    public ResponseEntity<Product> putProduct(@PathVariable UUID uuid, @RequestBody String category) throws ProductNotFoundException {

        return new ResponseEntity<>(this.productService.addCategoryToProduct(uuid, category), HttpStatus.OK);
    }

    @PatchMapping(value = API_PRODUCTS_ID + API_REMOVE_CATEGORIES)
    @ApiOperation("Removes category in respective product")
    public ResponseEntity<Product> patchCategoryFromProduct(@PathVariable UUID uuid, @RequestBody String category) throws Exception {
        return new ResponseEntity<>(this.productService.removeCategoryFromProduct(uuid, category), HttpStatus.OK);
    }

}
