package sk.qpp.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sk.qpp.poc.documents.relationSample.Book;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Integer> {
}
