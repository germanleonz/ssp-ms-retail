package store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class StoreControllerTest extends AbstractIntegrationTest {

    private static final String API_VERSION = RestConstants.VERSION_ONE;

    private static final String REQUEST_URI = "%s" + API_VERSION + "/stores";

    private static final String WRONG_STATUS_MESSAGE = "HTTP Status code was incorrect";

    private static final String OBJECT_MAPPER_BEAN = "objectMapper";

    private final TestRestTemplate template = new TestRestTemplate();

    @Autowired
    @Qualifier(OBJECT_MAPPER_BEAN)
    private ObjectMapper mapper;

    @Test
    @FlywayTest
    public void testCreate() {
        Long expectedStoreId = Long.valueOf(4L);

        Store store = new Store();
        store.setName("Test store");

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    mapper.writeValueAsString(store),
                    HttpMethod.POST
            );
            assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> creationResponse = mapper.readValue(response.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            Long actualStoreId = creationResponse.getId();
            assertEquals("Store Ids do not match", expectedStoreId, actualStoreId);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreatePreconditionFailed() {
        Store store = new Store();

        try {
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()),
                mapper.writeValueAsString(store),
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
        Store store = getStore(storeId, HttpStatus.OK);
        assertNotNull("Store should not be null", store);
        assertEquals("Store Ids do not match", storeId, store.getStoreId());
    }

    @Test
    @FlywayTest
    public void testFindAll() {
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()),
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Store> stores = mapper.readValue(response.getBody(), new TypeReference<List<Store>>() {});
            assertNotNull("Store list should not be null", stores);
            assertEquals("Number of stores found does not match", stores.size(), 3);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindAllByName() {
        String storeNameAsParam = "Store";

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + "?name=" + storeNameAsParam,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Store> stores = mapper.readValue(response.getBody(), new TypeReference<List<Store>>() {});
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
        getStore(nonExistentStoreId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testDelete() {
        Long storeToDeleteId = Long.valueOf(1L);

        ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()) + "/" + storeToDeleteId,
                null,
                HttpMethod.DELETE
        );
        assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.NO_CONTENT, response.getStatusCode());

        getStore(storeToDeleteId, HttpStatus.NOT_FOUND);
    }

    @Test
    @FlywayTest
    public void testDeleteNoSuchElementException() {
        Long nonExistentStoreId = Long.MAX_VALUE;

        ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath()) + "/" + nonExistentStoreId,
                null,
                HttpMethod.DELETE
        );
        assertEquals(WRONG_STATUS_MESSAGE, HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Store getStore(Long storeId, HttpStatus expectedHttpStatus) {
        Store store = null;
        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + "/" + storeId,
                    null,
                    HttpMethod.GET
            );
            assertEquals("HTTP Status code incorrect", expectedHttpStatus, response.getStatusCode());
            store = mapper.readValue(response.getBody(), new TypeReference<Store>() {});
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return store;
    }
}