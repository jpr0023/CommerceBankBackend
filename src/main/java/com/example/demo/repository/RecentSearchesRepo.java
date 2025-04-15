package com.example.demo.repository;

import com.example.demo.domain.Customer;
import com.example.demo.domain.RecentSearches;
import com.example.demo.domain.URLS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentSearchesRepo extends JpaRepository<RecentSearches, Long> {


}
