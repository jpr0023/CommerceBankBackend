package com.example.demo.DTOs;

import com.example.demo.domain.RecentSearches;
import com.example.demo.domain.SavedSearches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchesDTO {

    List<RecentSearches> recentSearches;
    List<SavedSearches> savedSearches;
}
