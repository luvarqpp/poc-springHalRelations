package sk.qpp.poc.documents.relationSample.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

// note that excerpt is used wherever given entity is presented. It is also in search results and so on. see whole part of https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts
@RepositoryRestResource //(excerptProjection = BookTypedTagAssociationExtendedProjection.class)
public interface BookTypedTagAssociationRepository extends JpaRepository<BookTypedTagAssociation, BookTypedTagAssociationIdClass> {
    Optional<BookTypedTagAssociation> findById(BookTypedTagAssociationIdClass id);
    Optional<BookTypedTagAssociation> findByTypedTag(TypedTag typedTag);
    // TODO is something even possible?
    Optional<BookTypedTagAssociation> findByTypedTagContaining(String typedTagPartialValue);
}
