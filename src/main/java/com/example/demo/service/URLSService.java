package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SavedUrlRepo;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@AllArgsConstructor
public class URLSService {

    private final CustomerRepository customerRepository;
    private final SavedUrlRepo savedUrlRepo;
    private URLSRepo urlsRepo;

   public URLSDataTransfer grabInfo(String website) throws IOException {
       URLS foundUrl = urlsRepo.findByurl(website);
       System.out.println(website);

       if (foundUrl == null) {
           URLS url = new URLS();
           url.setUrl(website);
           foundUrl = urlsRepo.save(url);
       }

       URLSDataTransfer transfer = new URLSDataTransfer();

       transfer.setUrl(foundUrl);


       HttpURLConnection connection = (HttpURLConnection) new URL(foundUrl.getUrl()).openConnection();
       connection.setRequestMethod("GET");

       // Initialize
       Map<String, List<String>> test = new HashMap<>();


//     Grabbing Response Time

       long startTime = System.currentTimeMillis();

       int responseCode = connection.getResponseCode();

       long elapsedTime = System.currentTimeMillis() - startTime;
       transfer.setResponseCode(responseCode);
       transfer.setResponseTime(elapsedTime + "ms");
       if (responseCode == HttpURLConnection.HTTP_OK) {
           test = connection.getHeaderFields();
       }

        List<ResponseHeaders> headers = new ArrayList<>();
       for (Map.Entry<String, List<String>> entry : test.entrySet()) {
           if (entry.getKey() != null){
                for (String value: entry.getValue()){
                    if (entry.getKey().equals("Content-Type")){
                        transfer.setContentType(value);
                    }

                    else if (entry.getKey().equals("Server")){
                        transfer.setServer(value);
                    }

                    else{
                        headers.add(new ResponseHeaders(entry.getKey(),value));
                    }
                }
           }
       }

       transfer.setHeaders(headers);

       connection.disconnect();
       return transfer;

   }

   public void save(int urlId, String username) throws IOException {
       // Find the Users Account First

       Customer savedCustomer = customerRepository.findByUsername(username).orElse(null);

       if(savedCustomer != null) {
           // Find the Url for the urlID
           URLS foundUrl = urlsRepo.findByid(urlId);


            // Make a new Saved Url Object
           if (!savedUrlRepo.existsByCustomerAndUrl(savedCustomer,foundUrl)) {
               SavedUrl savedUrl = new SavedUrl();

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
