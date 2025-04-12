package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentSearches {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String urlName;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    @ToString.Exclude
    private Customer customer;


    @ManyToOne
    @JoinColumn(name="url_id")
    private URLS url;



}
