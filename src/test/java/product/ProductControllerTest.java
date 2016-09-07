package product;


import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.Product;
import common.util.RequestHandler;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class ProductControllerTest extends RequestHandler {

    static {
        requestUri = "%s" + API_VERSION + "/products/";
    }

    @Value("classpath:product/success/valid_product.json")
    private File validProduct;

    @Value("classpath:product/failure/invalid_product.json")
    private File invalidProduct;

    @Value("classpath:product/failure/product_non_existent_store.json")
    private File productNonExistentStore;

    @Test
    @FlywayTest
    public void testCreate() {
        Long expectedProductId = Long.valueOf(3L);
        Long storeId = Long.valueOf(1L);

        try {
            String productString = FileUtils.readFileToString(validProduct);
            ResponseEntity<String> createProductResponse = handleRequest(Long.toString(storeId), HttpStatus.CREATED, HttpMethod.POST, productString);

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

        try {
            String productString = FileUtils.readFileToString(invalidProduct);
            handleRequest(Long.toString(storeId), HttpStatus.PRECONDITION_FAILED, HttpMethod.POST, productString);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateNoSuchElementException() {
        Long nonExistentStoreId = Long.MAX_VALUE;

        try {
            String productString = FileUtils.readFileToString(productNonExistentStore);
            handleRequest(Long.toString(nonExistentStoreId), HttpStatus.NOT_FOUND, HttpMethod.POST, productString);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
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
        Long productToDeleteId = Long.valueOf(1L);
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