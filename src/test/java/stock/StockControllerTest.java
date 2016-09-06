package stock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import common.util.RequestHandler;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class StockControllerTest extends RequestHandler {

    static {
        REQUEST_URI = "%s" + API_VERSION + "/stock/";
    }

    @Test
    @FlywayTest
    public void testUpsert() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(2L);

        Stock stock = new Stock();
        stock.setCount(100);

        handleRequest(storeId + "/" + productId, HttpStatus.CREATED, HttpMethod.POST, stock);

        ResponseEntity<String> upsertStockResponse = handleRequest(storeId + "/" + productId, HttpStatus.OK, HttpMethod.GET);
        try {
            Stock stockAfterCreation = mapper.readValue(upsertStockResponse.getBody(), new TypeReference<Stock>() {});
            assertEquals("Store Ids do not match", storeId, stockAfterCreation.getStoreId());
            assertEquals("Product Ids do not match", storeId, stockAfterCreation.getStoreId());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testUpsertPreconditionFailed() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(1L);

        Stock stock = new Stock();
        stock.setCount(0);

        handleRequest(storeId + "/" + productId, HttpStatus.PRECONDITION_FAILED, HttpMethod.POST, stock);
    }

    @Test
    @FlywayTest
    public void testFindById() {
        Long storeId = Long.valueOf(1L);
        Long productId = Long.valueOf(1L);
        handleRequest(storeId + "/" + productId, HttpStatus.OK, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testFindByIdStoreNotFound() {
        Long nonExistentStoreId = Long.valueOf(1L);
        Long productId = Long.MAX_VALUE;
        handleRequest(nonExistentStoreId + "/" + productId, HttpStatus.NOT_FOUND, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testFindByIdProductNotFound() {
        Long storeId = Long.valueOf(1L);
        Long nonExistentProductId = Long.MAX_VALUE;
        handleRequest(storeId + "/" + nonExistentProductId, HttpStatus.NOT_FOUND, HttpMethod.GET);
    }
}
