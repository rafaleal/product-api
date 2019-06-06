package com.rakuten.productapi.service;

import com.rakuten.productapi.domain.Money;
import com.rakuten.productapi.domain.Product;
import com.rakuten.productapi.dto.ProductDTO;
import com.rakuten.productapi.dto.mapper.ProductMapper;
import com.rakuten.productapi.exception.ProductBadRequestException;
import com.rakuten.productapi.exception.ProductNotFoundException;
import com.rakuten.productapi.repository.ProductMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    ProductMongoRepository productMongoRepository;

    @Autowired
    ProductMapper productMapper;

    ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        productService = new ProductServiceImpl(productMongoRepository, exchangeService, productMapper);
    }

    @Nested
    @DisplayName("Given the product was already saved in DB")
    class SavedProduct {

        Product product;
        UUID uuid;

        @BeforeEach
        void setup() {
            uuid = UUID.randomUUID();
            product = new Product();
            product.setId(uuid);
            product.setImageUrl(Collections.singletonList("img"));
            product.setTitle("Title");
            product.setDescription("Description");
            product.setCreatedAt(LocalDate.now());
            product.setCategories(Collections.singletonList("categoryX"));
            productMongoRepository.save(product);
        }

        @Nested
        @DisplayName("When gets the product by UUID")
        class GetProduct {
            @Test
            @DisplayName("Then it should return the same product")
            void testGetProduct() throws ProductNotFoundException {
                Product returnedProduct = productService.getProduct(product.getId());
                assertEquals(returnedProduct, product);
            }
        }

        @Nested
        @DisplayName("When deletes the product by UUID")
        class DeleteProduct {
            @Test
            @DisplayName("Then it should delete it")
            void testDeleteProduct() throws ProductNotFoundException {
                productService.deleteProduct(product.getId());
                assertThrows(ProductNotFoundException.class, () -> productService.getProduct(product.getId()));
            }
        }

        @Nested
        @DisplayName("When assigning new Category to the Product")
        class AssignCategory {
            @Test
            @DisplayName("Then it will be updated successfully")
            void testAddCategory() throws ProductNotFoundException {

                String category = "anything";

                Product product = productService.addCategoryToProduct(uuid, category);
                assertTrue(product.getCategories().contains(category));

                Product checkProduct = productService.getProduct(uuid);
                assertTrue(checkProduct.getCategories().contains(category));
            }
        }

        @Nested
        @DisplayName("When removing existing Category from Product")
        class RemoveExistingCategory {

            @BeforeEach
            void setup() throws ProductNotFoundException {
                String category = "anything";

                productService.addCategoryToProduct(uuid, category);
            }

            @Test
            @DisplayName("Then it will be removed successfully")
            void testRemoveCategory() throws Exception {
                String category = "anything";
                productService.removeCategoryFromProduct(uuid, category);
                Product product = productService.getProduct(uuid);
                assertFalse(product.getCategories().contains(category));
            }
        }

        @Nested
        @DisplayName("When removing non-existing Category from Product")
        class RemoveNonExistingCategory {

            @Test
            @DisplayName("Then it will throw a ProductBadRequestException")
            void testRemoveNonExistingCategory() {
                String category = "anything";
                assertThrows(ProductBadRequestException.class, () -> productService.removeCategoryFromProduct(uuid, category));
            }
        }

    }

    @Nested
    @DisplayName("Given the product wasn't saved in DB")
    class NotSavedProduct {

        @Nested
        @DisplayName("When gets the product by UUID")
        class GetProduct {

            @Test
            @DisplayName("Then it should throw a ProductNotFoundException")
            void testGetMissingProduct() {
                UUID uuid = UUID.randomUUID();

                assertThrows(ProductNotFoundException.class, () -> productService.getProduct(uuid));
            }

        }

        @Nested
        @DisplayName("When saves the product")
        class CreateProduct {

            ProductDTO productDTO;

            @BeforeEach
            void setup() {
                productDTO = new ProductDTO();
                productDTO.setTitle("Anything");
                productDTO.setDescription("Anything");
                productDTO.setMoney(new Money(BigDecimal.TEN, Currency.getInstance("BRL")));
                productDTO.setImageUrl(Collections.singletonList("img"));
                productDTO.setCategories(Collections.singletonList("category"));
            }

            @Test
            @DisplayName("Then it must save and return expected saved product")
            void testCreateProduct() throws ProductBadRequestException {

                Product product = productService.createProduct(productDTO);

                assertAll("Verify all conditions for a new product created",
                        () -> assertNotNull(product),
                        () -> assertEquals(Currency.getInstance("EUR"), product.getMoney().getCurrency()),
                        () -> assertNotEquals(BigDecimal.TEN, product.getMoney().getAmount()),
                        () -> assertEquals(productDTO.getCategories(), product.getCategories()),
                        () -> assertEquals(productDTO.getImageUrl(), product.getImageUrl()),
                        () -> assertEquals(productDTO.getTitle(), product.getTitle()),
                        () -> assertEquals(productDTO.getDescription(), product.getDescription())
                );
            }


            @Nested
            @DisplayName(" and the Money amount is less than or equal ZERO")
            class ZeroAmount {
                @Test
                @DisplayName("Then it should throw an ProductBadRequestException")
                void testZeroAmount() {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setTitle("Anything");
                    productDTO.setDescription("Anything");
                    productDTO.setMoney(new Money(BigDecimal.ZERO, Currency.getInstance("BRL")));
                    productDTO.setImageUrl(Collections.singletonList("img"));
                    productDTO.setCategories(Collections.singletonList("category"));

                    assertThrows(ProductBadRequestException.class, () -> productService.createProduct(productDTO));
                }
            }
        }

        @Nested
        @DisplayName("When deletes the product")
        class DeleteProduct {
            @Test
            @DisplayName("Then it should throw a ProductNotFoundException")
            void testDeleteProduct() {
                UUID uuid = UUID.randomUUID();
                assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(uuid));
            }
        }

        @Nested
        @DisplayName("When assigning new Category to a non-existing Product")
        class AssignCategory {
            @Test
            @DisplayName("Then it will be updated successfully")
            void testAddCategory() {

                String category = "anything";

                assertThrows(ProductNotFoundException.class, () -> productService.addCategoryToProduct(UUID.randomUUID(), category));

            }
        }

        @Nested
        @DisplayName("When removing a Category from a non-existing Product")
        class RemoveCategory {
            @Test
            @DisplayName("Then it should throw a ProductNotFoundException")
            void testRemoveCategory() {
                String category = "anything";

                assertThrows(ProductNotFoundException.class, () -> productService.removeCategoryFromProduct(UUID.randomUUID(), category));
            }
        }
    }

}