package stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.stock.rest.dto.Stock;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class StockControllerTest extends AbstractIntegrationTest {
    private static final String API_VERSION = RestConstants.VERSION_ONE;

    private static final String REQUEST_URI = "%s" + API_VERSION + "/stock/";

    private static final String WRONG_STATUS_MESSAGE = "HTTP Status code was incorrect";

    private static final String OBJECT_MAPPER_BEAN = "objectMapper";

    private final TestRestTemplate template = new TestRestTemplate();

    @Autowired
    @Qualifier(OBJECT_MAPPER_BEAN)
    private ObjectMapper mapper;

    @Test
    @FlywayTest
    public void testUpsert() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(2L);

        Stock stock = new Stock();
        stock.setCount(100);

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId + "/" + productId,
                    mapper.writeValueAsString(stock),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.CREATED, response.getStatusCode());

            Stock createdStock = getStock(storeId, productId, HttpStatus.OK);
            assertEquals("Store Ids do not match", storeId, createdStock.getStoreId());
            assertEquals("Product Ids do not match", storeId, createdStock.getStoreId());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testUpsertPreconditionFailed() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(1L);

        Stock stock = new Stock();
        stock.setCount(0);

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId + "/" + productId,
                    mapper.writeValueAsString(stock),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindById() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(1L);
        getStock(storeId, productId, HttpStatus.OK);
    }

    @Test
    @FlywayTest
    public void testFindByIdStoreNotFound() {
        Long nonExistentStoreId = Long.valueOf(1L);
        Long productId = Long.MAX_VALUE;
        getStock(nonExistentStoreId, productId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testFindByIdProductNotFound() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;
        getStock(storeId, nonExistentProductId, HttpStatus.NOT_FOUND);
    }

    private Stock getStock(Long storeId, Long productId, HttpStatus expectedHttpStatus) {
        Stock stock = null;
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + storeId + "/" + productId,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", expectedHttpStatus, response.getStatusCode());
            stock = mapper.readValue(response.getBody(), new TypeReference<Stock>() {});
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return stock;
    }
}
