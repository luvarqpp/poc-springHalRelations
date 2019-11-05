package sk.qpp.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdEmbeddable;

import java.util.Optional;

@RepositoryRestResource
public interface BookPublisherRepository extends JpaRepository<BookPublisher, BookPublisherIdEmbeddable> {
    public Optional<BookPublisher> findById(BookPublisherIdEmbeddable id);
}
