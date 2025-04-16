package com.example.demo.repository;

import com.example.demo.domain.Customer;
import com.example.demo.domain.SavedUrl;
import com.example.demo.domain.URLS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedUrlRepo extends JpaRepository<SavedUrl, Long> {

    boolean existsByCustomerAndUrl(Customer customer, URLS url);


}