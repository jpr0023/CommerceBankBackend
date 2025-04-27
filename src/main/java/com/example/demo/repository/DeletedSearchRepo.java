package com.example.demo.repository;

import com.example.demo.domain.DeletedSearches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeletedSearchRepo extends JpaRepository<DeletedSearches, Long> {

    @Query("Select ds from DeletedSearches ds WHERE ds.customer.customer_id = ?1 ORDER BY ds.id DESC LIMIT 5")
    List<DeletedSearches> getDeletedSearchesBy (Long customer_id);


    @Query("SELECT ds from DeletedSearches ds WHERE ds.customer.customer_id = ?1 AND ds.url.id = ?2")
    Optional<DeletedSearches> ifExists(Long customer_id, int url_id);

}
