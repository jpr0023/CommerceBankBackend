package com.example.demo.service;

import com.example.demo.DTOs.CertificateInfoDTO;
import org.springframework.stereotype.Service;
import com.example.demo.domain.ResponseHeaders;
import com.example.demo.domain.URLS;
import com.example.demo.DTOs.URLSDataTransfer;
import com.example.demo.repository.URLSRepo;
import lombok.AllArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
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
        String url;
        if (!foundUrl.getUrlValue().startsWith("https://")) {
            url = "https://" + foundUrl.getUrlValue();
        }
        else{
            url = foundUrl.getUrlValue();
        }

        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        // Initialize
        Map<String, List<String>> headerFields = new HashMap<>();


//     Grabbing Response Time
        long startTime = System.currentTimeMillis();
        int responseCode = connection.getResponseCode();
        long elapsedTime = System.currentTimeMillis() - startTime;


        if (responseCode == HttpURLConnection.HTTP_OK) {
            headerFields = connection.getHeaderFields();
        }
        String CipherSuite = connection.getCipherSuite();


        List<CertificateInfoDTO> certificates = new ArrayList<>();
        Certificate[] certs = connection.getServerCertificates();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Certificate cert : certs) {
            if (cert instanceof X509Certificate x509) {
                CertificateInfoDTO certDTO = new CertificateInfoDTO(
                        x509.getSubjectDN().getName(),
                        x509.getIssuerDN().getName(),
                        sdf.format(x509.getNotBefore()),
                        sdf.format(x509.getNotAfter()),
                        x509.getSerialNumber().toString(),
                        x509.getSigAlgName()
                );
                certificates.add(certDTO);
            }
        }


        // Grabbing Response Headers
        List<ResponseHeaders> headers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            if (entry.getKey() != null){
                for (String value: entry.getValue()){
                    if (entry.getKey().equals("Server")){
                        transferData.setServer(value);
                    }
                    else{
                        headers.add(new ResponseHeaders(entry.getKey(),value));
                    }
                }
            }
        }
        // Setting all the things for transferring back
        transferData.setCertificateInfo(certificates);
        transferData.setHeaders(headers);
        transferData.setCipherSuite(CipherSuite);
        transferData.setResponseCode(responseCode);
        transferData.setResponseTime(elapsedTime + "ms");

        connection.disconnect();
        return transferData;
    }
}
