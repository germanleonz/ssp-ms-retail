package product;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class ProductControllerTest extends AbstractIntegrationTest {

    private static final String API_VERSION = RestConstants.VERSION_ONE;

    private static final String REQUEST_URI = "%s" + API_VERSION + "/products/";

    private static final String WRONG_STATUS_MESSAGE = "HTTP Status code was incorrect";

    private static final String OBJECT_MAPPER_BEAN = "objectMapper";

    private final TestRestTemplate template = new TestRestTemplate();

    @Autowired
    @Qualifier(OBJECT_MAPPER_BEAN)
    private ObjectMapper mapper;

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

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId,
                    mapper.writeValueAsString(product),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> creationResponse = mapper.readValue(response.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            Long actualProductId = creationResponse.getId();
            assertEquals("Product Ids do not match", expectedProductId, actualProductId);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreatePreconditionFailed() {
        Long expectedProductId = Long.valueOf(3L);
        Long storeId = Long.valueOf(1L);

        Product product = new Product();
        product.setStoreId(storeId);
        product.setName("Test product");
        product.setDescription("My product's description");
        product.setSku("abcdefg");
        product.setPrice(new BigDecimal("123.45"));

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId,
                    mapper.writeValueAsString(product),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> creationResponse = mapper.readValue(response.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            Long actualProductId = creationResponse.getId();
            assertEquals("Product Ids do not match", expectedProductId, actualProductId);
        } catch (IOException e) {
            fail(e.getMessage());
        }
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

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + nonExistentStoreId,
                    mapper.writeValueAsString(product),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.NOT_FOUND, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindAll() {
        Long storeId = Long.valueOf(1L);

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Product> products = mapper.readValue(response.getBody(), new TypeReference<List<Product>>() {});
            assertNotNull("Product list should not be null", products);
            assertEquals("Number of products found does not match", products.size(), 2);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindAllByName() {
        Long storeId = Long.valueOf(1L);
        String productName = "Product";

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId + "?name=" + productName,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Product> products = mapper.readValue(response.getBody(), new TypeReference<List<Product>>() {});
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
        Product product = getProduct(storeId, productId, HttpStatus.OK);
        assertNotNull("Product should not be null", product);
        assertEquals("Product Ids does not match", productId, product.getProductId());
    }

    @Test
    @FlywayTest
    public void testFindByIdNoSuchElementException() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;
        getProduct(storeId, nonExistentProductId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testDelete() {
        Long storeId = Long.valueOf(1L);
        Long productToDeleteId = Long.valueOf(2L);

        ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()) + storeId + "/" + productToDeleteId,
                null,
                HttpMethod.DELETE
        );
        assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.NO_CONTENT, response.getStatusCode());

        getProduct(storeId, productToDeleteId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testDeleteNoSuchElementException() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;

        ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()) + storeId + "/" + nonExistentProductId,
                null,
                HttpMethod.DELETE
        );
        assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Product getProduct(Long storeId, Long productId, HttpStatus expectedHttpStatus) {
        Product product = null;
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId + "/" + productId,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", expectedHttpStatus, response.getStatusCode());
            product = mapper.readValue(response.getBody(), new TypeReference<Product>() {});
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return product;
    }
}
