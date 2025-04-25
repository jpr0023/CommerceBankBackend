package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.domain.ResponseHeaders;
import com.example.demo.domain.URLS;
import com.example.demo.DTOs.URLSDataTransfer;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SearchService {
    private final URLSRepo urlsRepo;

    public URLSDataTransfer analyze(int url_id) throws IOException {
        URLSDataTransfer transferData = new URLSDataTransfer();
        URLS foundUrl = urlsRepo.findById(url_id).orElse(null);
        transferData.setUrl(foundUrl);

        if (foundUrl == null) {
            throw new IOException(url_id + " not found");
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(foundUrl.getUrlValue()).openConnection();
        connection.setRequestMethod("GET");

        // Initialize
        Map<String, List<String>> headerFields = new HashMap<>();


//     Grabbing Response Time

        long startTime = System.currentTimeMillis();

        int responseCode = connection.getResponseCode();

        long elapsedTime = System.currentTimeMillis() - startTime;
        transferData.setResponseCode(responseCode);
        transferData.setResponseTime(elapsedTime + "ms");
        if (responseCode == HttpURLConnection.HTTP_OK) {
            headerFields = connection.getHeaderFields();
        }

        List<ResponseHeaders> headers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if (entry.getKey() != null){
                for (String value: entry.getValue()){
                    if (entry.getKey().equals("Content-Type")){
                        transferData.setContentType(value);
                    }

                    else if (entry.getKey().equals("Server")){
                        transferData.setServer(value);
                    }

                    else{
                        headers.add(new ResponseHeaders(entry.getKey(),value));
                    }
                }
            }
        }
        transferData.setHeaders(headers);
        connection.disconnect();
        return transferData;
    }
}
