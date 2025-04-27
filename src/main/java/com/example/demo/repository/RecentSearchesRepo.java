package com.example.demo.repository;

import com.example.demo.domain.RecentSearches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentSearchesRepo extends JpaRepository<RecentSearches, Long> {

    @Query ("SELECT rs FROM RecentSearches rs WHERE rs.customer.customer_id = ?1 ORDER BY rs.lastUpdated DESC LIMIT 5")
    List<RecentSearches> getRecentSearchesByCustomerId(Long customer_id);

    @Query ("Select rs FROM RecentSearches rs Where rs.customer.customer_id = ?1 AND rs.url.id = ?2")
    RecentSearches getRecentSearchesByCustomerAndURl(Long customer_id, int url_id);
}
