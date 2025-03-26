package com.example.demo.service;

import com.example.demo.domain.URLS;
import com.example.demo.domain.URLSDataTransfer;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class URLSService {

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
       connection.connect();
       Map<String, List<String>> test = new HashMap<>();
       int responseCode = connection.getResponseCode();
       if (responseCode == HttpURLConnection.HTTP_OK) {
           test = connection.getHeaderFields();

       }

       for (Map.Entry<String, List<String>> entry : test.entrySet()) {
           System.out.println(entry.getKey() + ": " + entry.getValue());
       }
      //transfer.setHeaders(test);
       return transfer;

   }
}
