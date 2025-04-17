package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @Column (nullable = true)
    private String token;


    @Column (nullable = false)
    private String username;

    @Column (nullable = false, length = 36)
    private String password;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    public List<SavedSearches> savedUrls;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    public List<RecentSearches> recentSearches;
}
