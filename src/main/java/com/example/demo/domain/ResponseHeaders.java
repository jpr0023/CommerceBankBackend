package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ResponseHeaders {
    String key;
    List<String> value;
}
