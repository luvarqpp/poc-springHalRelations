package sk.qpp.poc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import sk.qpp.poc.documents.relationSample.BookPublisher;
import sk.qpp.poc.documents.relationSample.BookPublisherIdClass;
import sk.qpp.poc.repository.BookPublisherRepository;

import java.io.Serializable;

// see https://jira.spring.io/browse/DATAJPA-770
// source "copied" from https://github.com/dariotortola/jira-datajpa-770/blob/master/datajpa-770/datajpa-770/src/main/java/jira/FixConfig.java
@Configuration
public class FixConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.withEntityLookup().forRepository(BookPublisherRepository.class)
                .withIdMapping((BookPublisher ed) -> {
                    BookPublisherIdClass pk = new BookPublisherIdClass();
                    pk.setBook(ed.getBook().getId());
                    pk.setPublisher(ed.getPublisher().getId());
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
                return String.format("%s_%s", pk.getBook(), pk.getPublisher());
            }

            @Override
            public Serializable fromRequestId(String id, Class<?> entityType) {
                if (id == null) {
                    return null;
                }
                String[] parts = id.split("_");
                BookPublisherIdClass pk = new BookPublisherIdClass();
                pk.setBook(Integer.valueOf(parts[0]));
                pk.setPublisher(Integer.valueOf(parts[1]));
                return pk;
            }
        };
    }
}

