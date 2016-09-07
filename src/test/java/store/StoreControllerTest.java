package store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class StoreControllerTest extends RequestHandler {

    static {
        requestUri = "%s" + API_VERSION + "/stores/";
    }

    @Value("classpath:store/success/valid_store.json")
    private File validStore;

    @Value("classpath:store/failure/invalid_store.json")
    private File invalidStore;

    @Test
    @FlywayTest
    public void testCreate() {
        Long expectedStoreId = Long.valueOf(4L);

        try {
            String storeString = FileUtils.readFileToString(validStore);
            ResponseEntity<String> creationResponse = handleRequest("", HttpStatus.CREATED, HttpMethod.POST, storeString);
            ResourceCreated<Long> responseBody = mapper.readValue(creationResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            assertEquals("Store Ids do not match", expectedStoreId, responseBody.getId());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreatePreconditionFailed() {
        try {
            String storeString = FileUtils.readFileToString(invalidStore);
            handleRequest("", HttpStatus.PRECONDITION_FAILED, HttpMethod.POST, storeString);
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindById() {
        Long storeId = Long.valueOf(1L);
        ResponseEntity<String> findResponse = handleRequest(Long.toString(storeId), HttpStatus.OK, HttpMethod.GET);
        try {
            Store store = mapper.readValue(findResponse.getBody(), new TypeReference<Store>() {});
            assertNotNull("Store should not be null", store);
            assertEquals("Store Ids do not match", storeId, store.getStoreId());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testFindAll() {
        ResponseEntity<String> findAllResponse = handleRequest("", HttpStatus.OK, HttpMethod.GET);
        try {
            List<Store> stores = mapper.readValue(findAllResponse.getBody(), new TypeReference<List<Store>>() {});
            assertNotNull("Store list should not be null", stores);
            assertEquals("Number of stores found does not match", stores.size(), 3);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByName() {
        String storeNameAsParam = "Store";

        ResponseEntity<String> findAllResponse = handleRequest("?name=" + storeNameAsParam, HttpStatus.OK, HttpMethod.GET);

        try {
            List<Store> stores = mapper.readValue(findAllResponse.getBody(), new TypeReference<List<Store>>() {});
            assertNotNull("Store list should not be null", stores);
            assertEquals("Number of stores found does not match", stores.size(), 1);
            assertEquals("Store name does not match", storeNameAsParam, stores.get(0).getName());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testFindByIdNoSuchElementException() {
        Long nonExistentStoreId = Long.MAX_VALUE;
        handleRequest(Long.toString(nonExistentStoreId), HttpStatus.NOT_FOUND, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testDelete() {
        Long storeToDeleteId = Long.valueOf(1L);
        handleRequest(Long.toString(storeToDeleteId), HttpStatus.NO_CONTENT, HttpMethod.DELETE);
        handleRequest(Long.toString(storeToDeleteId), HttpStatus.NOT_FOUND, HttpMethod.GET);
    }

    @Test
    @FlywayTest
    public void testDeleteNoSuchElementException() {
        Long nonExistentStoreId = Long.MAX_VALUE;
        handleRequest(Long.toString(nonExistentStoreId), HttpStatus.NOT_FOUND, HttpMethod.DELETE);
    }
}