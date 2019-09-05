package sk.qpp.poc.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.qpp.poc.documents.relationSample.Book;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdClass;

import java.util.Optional;

@RepositoryRestResource
public interface BookPublisherRepository extends JpaRepository<BookPublisher, BookPublisherIdClass> {
    public Optional<BookPublisher> findById(BookPublisherIdClass id);
}
