package com.example.demo.service;

import com.example.demo.DTOs.URLSDataTransfer;
import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SavedSearchesService {

    private final CustomerRepository customerRepository;
    private final SavedSearchesRepo savedSearchesRepo;
    private final RecentSearchesRepo recentSearchesRepo;
    private final URLSRepo urlsRepo;
    private final SearchService searchService;
    private final DeletedSearchRepo deletedSearchRepo;

    public List<SavedSearches> getSavedSearches(String token, int limit) {
        Long customerId = customerRepository.findByToken(token).getCustomer_id();

        return savedSearchesRepo.getSavedSearchesByCustomer(customerId, limit);
    }


    public URLSDataTransfer rescan(String token, int id) throws IOException {
        URLSDataTransfer urlsDataTransfer = searchService.analyze(id);
        Customer customer = customerRepository.findByToken(token);
        RecentSearches recentSearches = recentSearchesRepo.getRecentSearchesByCustomerAndURl(customer.getCustomer_id(), id);
        if (recentSearches == null) {
            recentSearches = new RecentSearches();
            recentSearches.setCustomer(customer);
            recentSearches.setUrl(urlsRepo.findByid(id));
            customer.getRecentSearches().add(recentSearches);
        }
        recentSearches.setLastUpdated(new Date());
        recentSearchesRepo.save(recentSearches);
        return urlsDataTransfer;
    }

    public void rename(long id, String name){
        SavedSearches savedSearch = savedSearchesRepo.getReferenceById(id);
        savedSearch.setUrlName(name);
        savedSearchesRepo.save(savedSearch);
    }

    public void deleteSavedSearch(long id) {
        SavedSearches match = savedSearchesRepo.getReferenceById(id);
        Customer customer = match.getCustomer();
        DeletedSearches deletedSearch = deletedSearchRepo.ifExists(match.getCustomer().getCustomer_id(), match.getUrl().getId()).orElse(null);
        if (deletedSearch == null) {
            deletedSearch = new DeletedSearches();
            deletedSearch.setCustomer(match.getCustomer());
            deletedSearch.setUrl(match.getUrl());

            if (match.getUrlName() != null) {
                deletedSearch.setUrlName(match.getUrlName());
            }

            deletedSearchRepo.save(deletedSearch);
        }

        customer.getDeletedSearches().add(deletedSearch);

        savedSearchesRepo.delete(match);
    }



}
