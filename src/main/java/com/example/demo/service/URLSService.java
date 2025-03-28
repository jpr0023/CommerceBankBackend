package com.example.demo.service;

import com.example.demo.domain.ResponseHeaders;
import com.example.demo.domain.URLS;
import com.example.demo.domain.URLSDataTransfer;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

       System.out.println("All headers:");
       for (Map.Entry<String, List<String>> entry : test.entrySet()) {
           System.out.println(entry.getKey() + ": " + entry.getValue());
       }

       transfer.setHeaders(headers);

       connection.disconnect();
       return transfer;

   }
}
