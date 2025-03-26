package com.example.demo.domain;

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
    List<ResponseHeaders> headers;

}
