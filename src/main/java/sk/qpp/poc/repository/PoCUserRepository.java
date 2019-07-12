package sk.qpp.poc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import sk.qpp.poc.documents.PoCUser;

@Repository
@RepositoryRestResource
public interface PoCUserRepository extends PagingAndSortingRepository<PoCUser, Long> {
}
