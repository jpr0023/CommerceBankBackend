package com.example.demo.DTOs;

import com.example.demo.domain.ResponseHeaders;
import com.example.demo.domain.URLS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URLSDataTransfer {
    URLS url;
    int responseCode;
    String responseTime;
    String responseSize;
    String Server;
    String CipherSuite;
    List<ResponseHeaders> headers;
    List<CertificateInfoDTO> certificateInfo;

}
