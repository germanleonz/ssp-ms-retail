package order;

import com.tenx.ms.retail.RetailServiceApp;
import common.util.RequestHandler;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class OrderControllerTest extends RequestHandler{

    static {
        requestUri= "%s" + API_VERSION + "/orders/";
    }

    @Value("classpath:order/success/valid_order.json")
    private File validOrder;

    @Value("classpath:order/failure/order_non_existent_store.json")
    private File orderNonExistentStore;

    @Value("classpath:order/failure/order_non_existent_product.json")
    private File orderNonExistentProduct;

    @Value("classpath:order/failure/order_insufficient_stock.json")
    private File orderInsufficientStock;

    @Value("classpath:order/failure/order_precondition_failed.json")
    private File orderPreconditionFailed;

    @Test
    @FlywayTest
    public void testCreate() {
        Long storeId = Long.valueOf(1L);
        try {
            String order = FileUtils.readFileToString(validOrder);
            handleRequest(Long.toString(storeId), HttpStatus.CREATED, HttpMethod.POST, order);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateWithNonExistentStore() {
        Long nonExistentStoreId = Long.valueOf(8L);
        try {
            String order = FileUtils.readFileToString(orderNonExistentStore);
            handleRequest(Long.toString(nonExistentStoreId), HttpStatus.NOT_FOUND, HttpMethod.POST, order);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateWithNonExistentProduct() {
        Long storeId = Long.valueOf(1L);

        try {
            String order = FileUtils.readFileToString(orderNonExistentProduct);
            handleRequest(Long.toString(storeId), HttpStatus.NOT_FOUND, HttpMethod.POST, order);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateInsufficientStock() {
        Long storeId = Long.valueOf(1L);

        try {
            String order = FileUtils.readFileToString(orderInsufficientStock);
            handleRequest(Long.toString(storeId), HttpStatus.PRECONDITION_FAILED, HttpMethod.POST, order);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreatePreconditionFailed() {
        Long storeId = Long.valueOf(1L);

        try {
            String order = FileUtils.readFileToString(orderPreconditionFailed);
            handleRequest(Long.toString(storeId), HttpStatus.PRECONDITION_FAILED, HttpMethod.POST, order);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }

    }
}
