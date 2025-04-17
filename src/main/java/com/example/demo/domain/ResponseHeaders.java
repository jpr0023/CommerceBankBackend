package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ResponseHeaders {
    String key;
    String value;
}
