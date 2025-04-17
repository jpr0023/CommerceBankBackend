package com.example.demo.service;

import com.example.demo.domain.RecentSearches;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RecentSearchesRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class RecentSearchesService {

    private final RecentSearchesRepo recentSearchesRepo;
    private final CustomerRepository customerRepository;

    public List<RecentSearches> getRecentSearches(@PathVariable String token) {
        Long customerId = customerRepository.findByToken(token).getCustomer_id();
        return recentSearchesRepo.getRecentSearchesByCustomerId(customerId);
    }





}
