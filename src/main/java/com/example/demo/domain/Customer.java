package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false, length = 36)
    private String password;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    public List<SavedUrl> savedUrls;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    public List<RecentSearches> recentSearches;
}
