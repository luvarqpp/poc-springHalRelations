package sk.qpp.poc.documents.relationSample.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.qpp.poc.documents.relationSample.tags.projections.TypedTagWithBooksCount;

import java.util.Optional;

@RepositoryRestResource //(excerptProjection = TypedTagWithBooksCount.class)
public interface TypedTagRepository extends JpaRepository<TypedTag, Integer> {
    Optional<TypedTag> findById(Integer id);

    Optional<TypedTag> findByValueContaining(String tagValue);
}
