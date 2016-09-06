package common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public abstract class RequestHandler extends AbstractIntegrationTest {

    protected static final String API_VERSION = RestConstants.VERSION_ONE;

    protected static String REQUEST_URI;

    private static final String OBJECT_MAPPER_BEAN = "objectMapper";

    private static final String WRONG_STATUS_MESSAGE = "HTTP Status code was incorrect";

    private final TestRestTemplate template = new TestRestTemplate();

    @Autowired
    @Qualifier(OBJECT_MAPPER_BEAN)
    protected ObjectMapper mapper;

    protected <T> ResponseEntity<String> handleRequest(String tId, HttpStatus expectedHttpStatus, HttpMethod httpMethod, T content) {
        ResponseEntity<String> response = null;
        try {
            response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath()) + tId,
                    (content != null) ? mapper.writeValueAsString(content) : null,
                    httpMethod
            );
            assertEquals(WRONG_STATUS_MESSAGE, expectedHttpStatus, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return response;
    }

    protected <T> ResponseEntity<String> handleRequest(String tId, HttpStatus expectedHttpStatus, HttpMethod httpMethod) {
        return handleRequest(tId, expectedHttpStatus, httpMethod, null);
    }
}
