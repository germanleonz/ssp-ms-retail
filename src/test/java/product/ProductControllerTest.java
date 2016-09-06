package product;


import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.Product;
import common.util.RequestHandler;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class ProductControllerTest extends RequestHandler {

    static {
        REQUEST_URI = "%s" + API_VERSION + "/products/";
    }

    @Test
    @FlywayTest
    public void testCreate() {
        Long expectedProductId = Long.valueOf(3L);
        Long storeId = Long.valueOf(1L);

        Product product = new Product();
        product.setStoreId(storeId);
        product.setName("Test product");
        product.setDescription("My product's description");
        product.setSku("abcdefg");
        product.setPrice(new BigDecimal("123.45"));

        ResponseEntity<String> createProductResponse = handleRequest(Long.toString(storeId), HttpStatus.CREATED, HttpMethod.POST, product);

        try {
            ResourceCreated<Long> creationResponse = mapper.readValue(createProductResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            Long actualProductId = creationResponse.getId();
            assertEquals("Product Ids do not match", expectedProductId, actualProductId);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreatePreconditionFailed() {
        Long storeId = Long.valueOf(1L);

        Product product = new Product();
        product.setStoreId(storeId);
        product.setName("Test product");
        product.setDescription("My product's description");
        product.setSku("abcdefg");
        product.setPrice(new BigDecimal("123.45"));

        handleRequest(Long.toString(storeId), HttpStatus.CREATED, HttpMethod.POST, product);
    }

    @Test
    @FlywayTest
    public void testCreateNoSuchElementException() {
        Long nonExistentStoreId = Long.MAX_VALUE;

        Product product = new Product();
        product.setStoreId(nonExistentStoreId);
        product.setName("Test product");
        product.setDescription("My product's description");
        product.setSku("abcdefg");
        product.setPrice(new BigDecimal("123.45"));

        handleRequest(Long.toString(nonExistentStoreId), HttpStatus.NOT_FOUND, HttpMethod.POST, product);
    }

    @Test
    @FlywayTest
    public void testFindAll() {
        Long storeId = Long.valueOf(1L);

        ResponseEntity<String> response = handleRequest(Long.toString(storeId), HttpStatus.OK, HttpMethod.GET);
        try {
            List<Product> products = mapper.readValue(response.getBody(), new TypeReference<List<Product>>() {});
            assertNotNull("Product list should not be null", products);
            assertEquals("Number of products found does not match", products.size(), 2);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindAllByName() {
        Long storeId = Long.valueOf(1L);
        String productName = "Product";

        ResponseEntity<String> findAllResponse = handleRequest(storeId + "?name=" + productName, HttpStatus.OK, HttpMethod.GET);
        try {
            List<Product> products = mapper.readValue(findAllResponse.getBody(), new TypeReference<List<Product>>() {});
            assertNotNull("Product list should not be null", products);
            assertEquals("Number of products found does not match", products.size(), 1);
            assertEquals("Product names do not match", productName, products.get(0).getName());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindById() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(1L);

        ResponseEntity<String> findProductResponse = handleRequest(storeId + "/" + productId, HttpStatus.OK, HttpMethod.GET);
        try {
            Product product = mapper.readValue(findProductResponse.getBody(), new TypeReference<Product>() {});
            assertNotNull("Product should not be null", product);
            assertEquals("Product Ids does not match", productId, product.getProductId());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindByIdNoSuchElementException() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;

        handleRequest(storeId + "/" + nonExistentProductId, HttpStatus.NOT_FOUND, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testDelete() {
        Long storeId = Long.valueOf(1L);
        Long productToDeleteId = Long.valueOf(2L);
        String urlId = storeId + "/" + productToDeleteId;

        handleRequest(urlId, HttpStatus.NO_CONTENT, HttpMethod.DELETE);
        handleRequest(urlId, HttpStatus.NOT_FOUND, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testDeleteNoSuchElementException() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;

        handleRequest(storeId + "/" + nonExistentProductId, HttpStatus.NOT_FOUND, HttpMethod.DELETE);
    }
}