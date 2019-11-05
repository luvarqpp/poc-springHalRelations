package sk.qpp.poc.misc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

/**
 * Testing actuator endpoints. Some of them are used in buildscripts or another management things, so we need them.
 * <p>
 * When adding new test, try to describe where is given thing used.
 */
@Tag("integrationTest")
@Slf4j
public class ActuatorHttpTest extends AbstractHttpHelper {
    /**
     * Test ensures that /actuator/health returns correct answer after application has started up. It has to answer
     * without any authentication.
     * <p>
     * This endpoint is used in Jenkinsfile to detect "successful" start of application.
     */
    @Test
    public void checkHealthActuator() {
        this.webClient.get().uri("/actuator/health")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody().json("{status:\"UP\"}");
    }
}
