package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

    @Id
    private int id;

    @Column
    private String userName;

    @Column
    private String password;

//    @ManyToMany
//    @JoinTable(
//            name="user_urls",
//            joinColumns = @JoinColumn(name="user_id"),
//            inverseJoinColumns = @JoinColumn(name="urls_id")
//    )
//    private List<URLS> urls;





}
