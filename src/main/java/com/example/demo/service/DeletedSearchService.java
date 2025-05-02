package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.domain.DeletedSearches;
import com.example.demo.domain.SavedSearches;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.DeletedSearchRepo;
import com.example.demo.repository.SavedSearchesRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletedSearchService {

    private final DeletedSearchRepo deletedSearchRepo;
    private final SavedSearchesRepo savedSearchesRepo;
    private final CustomerRepository customerRepository;


    public void recover(String deleteId, String token) {
        try {
            int deletedSearchId = Integer.parseInt(deleteId);
            System.out.println("Recovering delete ID: " + deletedSearchId);

            Customer customer = customerRepository.findByToken(token);
            if (customer == null) {
                System.out.println("No customer found with token: " + token);
                return;
            }
            System.out.println("Found customer ID: " + customer.getCustomer_id());

            Optional<DeletedSearches> optionalDeletedSearch = deletedSearchRepo.ifExists(customer.getCustomer_id(), deletedSearchId);
            if (optionalDeletedSearch.isEmpty()) {
                System.out.println("No deleted search found with ID: " + deletedSearchId + " for customer ID: " + customer.getCustomer_id());
                return;
            }
            DeletedSearches deletedSearch = optionalDeletedSearch.get();
//            System.out.println("Found deleted search: " + deletedSearch);

            SavedSearches savedSearch = new SavedSearches();
            savedSearch.setUrlName(deletedSearch.getUrlName());
            savedSearch.setCustomer(deletedSearch.getCustomer());
            savedSearch.setUrl(deletedSearch.getUrl());

            deletedSearchRepo.deleteById(deletedSearch.getId());
            customer.getSavedUrls().add(savedSearch);
            savedSearchesRepo.save(savedSearch);
            System.out.println("Successfully recovered and saved search!");

        } catch (Exception e) {
            System.out.println("Error during recovery: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<DeletedSearches> getDeletedSearches() {
        return deletedSearchRepo.findAll();
    }


}
