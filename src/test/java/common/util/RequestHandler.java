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


import static org.junit.Assert.assertEquals;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class RequestHandler extends AbstractIntegrationTest {

    protected static final String API_VERSION = RestConstants.VERSION_ONE;

    protected static String requestUri;

    private static final String OBJECT_MAPPER_BEAN = "objectMapper";

    private static final String WRONG_STATUS_MESSAGE = "HTTP Status code was incorrect";

    private final TestRestTemplate template = new TestRestTemplate();

    @Autowired
    @Qualifier(OBJECT_MAPPER_BEAN)
    protected ObjectMapper mapper;

    protected ResponseEntity<String> handleRequest(String tId, HttpStatus expectedHttpStatus, HttpMethod httpMethod, String content) {
        ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(requestUri, basePath()) + tId,
                content,
                httpMethod
        );
        assertEquals(WRONG_STATUS_MESSAGE, expectedHttpStatus, response.getStatusCode());

        return response;
    }

    protected ResponseEntity<String> handleRequest(String tId, HttpStatus expectedHttpStatus, HttpMethod httpMethod) {
        return handleRequest(tId, expectedHttpStatus, httpMethod, null);
    }
}
