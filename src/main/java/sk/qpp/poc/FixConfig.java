package sk.qpp.poc;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import sk.qpp.poc.documents.relationSample.Book;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdClass;
import sk.qpp.poc.documents.relationSample.Publisher;
import sk.qpp.poc.repository.BookPublisherRepository;
import sk.qpp.poc.repository.BookRepository;
import sk.qpp.poc.repository.PublisherRepository;

import java.io.Serializable;

// see https://jira.spring.io/browse/DATAJPA-770
// source "copied" from https://github.com/dariotortola/jira-datajpa-770/blob/master/datajpa-770/datajpa-770/src/main/java/jira/FixConfig.java
@Configuration
@AllArgsConstructor
public class FixConfig implements RepositoryRestConfigurer {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.withEntityLookup().forRepository(BookPublisherRepository.class)
                .withIdMapping((BookPublisher ed) -> {
                    BookPublisherIdClass pk = new BookPublisherIdClass();
                    pk.setBook(ed.getBook());
                    pk.setPublisher(ed.getPublisher());
                    return pk;
                })
                .withLookup((r, id) -> r.findById(id));
    }

    @Bean
    public BackendIdConverter employeeDepartmentIdConverter() {
        return new BackendIdConverter() {

            @Override
            public boolean supports(Class<?> delimiter) {
                return BookPublisher.class.equals(delimiter);
            }

            @Override
            public String toRequestId(Serializable id, Class<?> entityType) {
                BookPublisherIdClass pk = (BookPublisherIdClass) id;
                return String.format("%s_%s", pk.getBook().getId(), pk.getPublisher().getId());
            }

            @Override
            public Serializable fromRequestId(String id, Class<?> entityType) {
                if (id == null) {
                    return null;
                }
                String[] parts = id.split("_");
                BookPublisherIdClass pk = new BookPublisherIdClass();
                // TODO consult this "hackish" approach to load entities and do copy them into pojo with someone!
                final Book book = bookRepository.getOne(Integer.valueOf(parts[0]));
                pk.setBook(book.toBuilder().build());
                final Publisher publisher = publisherRepository.getOne(Integer.valueOf(parts[1]));
                pk.setPublisher(publisher.toBuilder().build());
                return pk;
            }
        };
    }
}

