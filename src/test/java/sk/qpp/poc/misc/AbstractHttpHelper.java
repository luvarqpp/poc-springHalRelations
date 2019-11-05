package sk.qpp.poc.misc;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

// when using restdocs, this would be handy:
// @ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
// @AutoConfigureRestDocs(outputDir = "target/snippets", uriScheme = "https", uriHost = "poc42.example.com", uriPort = 443)

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AbstractHttpHelper {
    /**
     * Modified (mutated) {@link #webClientBase} instance, which can in real app have pre-set these things:
     * <ul>
     *     <li>X-Forwarded-Host -> simulating that we are going to our testing (publicly available) site. Documentation is than generated with correct sample commands with correct url, etc</li>
     *     <li>X-Forwarded-Port -> simulating test site</li>
     *     <li>X-Forwarded-prefix -> simulating test site</li>
     *     <li>X-Forwarded-proto -> simulating test site</li>
     *     <li>Authorization -> adding "Bearer" JWT authorization token for master user to make http request authorized</li>
     * </ul>
     *
     * @see #setUp() initialization of mutated webClient is done here
     */
    protected WebTestClient webClient;
    @LocalServerPort
    protected int port;
    /**
     * Using {@link Autowired} annotation in favour of using constructor with these parameters, because in that way,
     * all extenders of this abstract class have to call given constructor and additionally they all have to use
     * annotation like: <pre>@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)</pre>.
     * <p>
     * Non-modified web client wired here. It will be mutated in {@link #setUp()}
     * method and stored in {@link #webClient} field. Given field should be used for actual testing of project.
     */
    @Autowired
    private WebTestClient webClientBase;

    /**
     * Remove projections information from link. Link with projection info is like "api/testCase/3{?projection}". This
     * method strips anything after last '{' character.
     *
     * @param link Link returned from rest api
     * @return link with removed suffix in form '{?projection}}}'
     */
    protected static String stripProjectionsFromLink(String link) {
        return link.substring(0, link.lastIndexOf('{'));
    }

    /**
     * Possible parameter is of RestDocumentationContextProvider type.
     */
    @BeforeEach
    public void setUp() {
        final WebTestClient.Builder wcBuilder = this.webClientBase.mutate()
                // here can be introduced restdocs thing, default headers and so on
                .responseTimeout(Duration.ofSeconds(1));
        this.webClient = wcBuilder.build();
        log.info(
                "webClient for test has been set-up. SUT (service under test) is accessible at {}.",
                this.webClient.get().uri("api").exchange().returnResult(String.class).getUrl()
        );
        log.info("Testing server should listen on port {}.", port);
    }

    /**
     * "Dereference" resource link to its canonical form.
     *
     * @param restResourceLink link to resource (for example author of testcase with id=3: https://qdevel.qpp.sk:433/qaron_backend-jib/api/testCases/3/author )
     * @return self link (canonical link of resource) of given resource. For example https://qdevel.qpp.sk:433/qaron_backend-jib/api/qaronUsers/4
     */
    protected String getSelfHrefLink(String restResourceLink) {
        // TODO apply projection only for self link if possible.
        final String uriStripped = restResourceLink;
        log.debug("Going to \"dereference\" resource link {}, with base uri calculated as {}.", restResourceLink, uriStripped);
        var resource = this.webClient.get().uri(uriStripped)
                .accept(MediaTypes.HAL_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(String.class)
                .returnResult().getResponseBody();
        log.debug("Loaded resource content: {}. Going to extract json path \"$._links.self.href\" and return it.", resource);
        String selfLink = JsonPath.read(resource, "$._links.self.href");
        return selfLink;
    }
}
