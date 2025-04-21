package com.example.demo.service;

import com.example.demo.DTOs.URLSDataTransfer;
import com.example.demo.domain.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RecentSearchesRepo;
import com.example.demo.repository.SavedSearchesRepo;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class URLSService {

    private final CustomerRepository customerRepository;
    private final SavedSearchesRepo savedUrlRepo;
    private URLSRepo urlsRepo;
    private final RecentSearchesRepo recentSearchesRepo;
    private final SearchService searchService;


   public URLSDataTransfer grabInfo(String website, String token) throws IOException {
       URLS foundUrl = urlsRepo.findByurlValue(website);

       if (foundUrl == null) {
           URLS url = new URLS();
           url.setUrlValue(website);
           foundUrl = urlsRepo.save(url);
       }
       URLSDataTransfer transfer = searchService.analayze(foundUrl.getId());

       Customer customer = customerRepository.findByToken(token);


       RecentSearches recentSearches = recentSearchesRepo.getRecentSearchesByCustomerAndURl(customer.getCustomer_id(), foundUrl.getId());


       if (recentSearches == null) {
       recentSearches = new RecentSearches();
       recentSearches.setCustomer(customer);
       recentSearches.setUrl(foundUrl);
       customer.getRecentSearches().add(recentSearches);
        }
       recentSearches.setLastUpdated(new Date());
       recentSearchesRepo.save(recentSearches);
       System.out.println(transfer);
       return transfer;

   }

   public void save(int urlId, String token) throws IOException {
       // Find the Users Account First

       Customer savedCustomer = customerRepository.findByToken(token);

       if(savedCustomer != null) {
           // Find the Url for the urlID
           URLS foundUrl = urlsRepo.findByid(urlId);


            // Make a new Saved Url Object
           if (!savedUrlRepo.existsByCustomerAndUrl(savedCustomer,foundUrl)) {
               SavedSearches savedUrl = new SavedSearches();

               savedUrl.setUrl(foundUrl);
               savedUrl.setCustomer(savedCustomer);
               savedUrlRepo.save(savedUrl);
               // Add that to the list of Saved Urls for User

               savedCustomer.savedUrls.add(savedUrl);
           }
       }

   }

   public URLS getURL(int urlId) throws IOException {
       return urlsRepo.findById(urlId).orElse(null);
   }
}
