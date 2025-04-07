package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    private Long customer_id;

    @Column(unique = true)
    private String userId;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false, length = 36)
    private String password;

//        Make Joiner Lists for Recent Searches and Saved List
//    Will need to make a new class for recent searches for either data or just to have another id that goes up in value
//
}
