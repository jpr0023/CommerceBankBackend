package com.example.demo.repository;

import com.example.demo.domain.Customer;
import com.example.demo.domain.SavedSearches;
import com.example.demo.domain.URLS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedSearchesRepo extends JpaRepository<SavedSearches, Long> {

    boolean existsByCustomerAndUrl(Customer customer, URLS url);


    @Query("Select ss from SavedSearches ss WHERE ss.customer.customer_id = ?1 ORDER BY ss.id LIMIT ?2")
    List<SavedSearches> getSavedSearchesByCustomer(Long customer_id, int limit);

    @Query("Select ss from SavedSearches ss WHERE ss.customer.customer_id = ?1 AND ss.url.id = ?2")
    SavedSearches getSavedSearch(Long customer_id, int url_id);
}