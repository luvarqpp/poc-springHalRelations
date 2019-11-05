package sk.qpp.poc.documents.relationSample.tags;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sk.qpp.poc.misc.AbstractHttpHelper;

import static org.hamcrest.Matchers.*;

class BookTypedTagAssociationRepositoryTest extends AbstractHttpHelper {
    /**
     * Check if returned data contains projection of TypedTag type. It means that association
     * of a {@link sk.qpp.poc.documents.relationSample.Book} with a {@link TypedTag} contain whole {@link TypedTag}.
     * There should be also links for {@link TypedTag} and {@link sk.qpp.poc.documents.relationSample.Book}.
     */
    @Test
    public void projectionTest() {
        this.webClient.get().uri("/api/bookTypedTagAssociations?projection=eagerLoadedTypedTag")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$._embedded.bookTypedTagAssociations[?(@.typedTag.value=='my favourite')].typedTag.type")
                .value(contains(equalTo("FREE_TAG")))
                .consumeWith(x ->
                        // To see result, have a look at file bookTypedTagAssociations_eagerLoadedTypedTag.json
                        System.out.println(new String(x.getResponseBody()))
                )
                .jsonPath("$._embedded.bookTypedTagAssociations[*]._links.typedTag.href")
                .value(not(emptyString()))
                .jsonPath("$._embedded.bookTypedTagAssociations[*]._links.book.href")
                // expecting links to associated books, like "http://localhost:40515/api/bookTypedTagAssociations/1_1/book"
                .value(allOf(
                        not(emptyIterable()),
                        everyItem(not(emptyString())),
                        everyItem(matchesPattern("^http://localhost.*book$"))
                ))
        ;
    }
}