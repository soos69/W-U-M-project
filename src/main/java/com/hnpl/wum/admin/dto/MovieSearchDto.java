package com.hnpl.wum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieSearchDto {

    //검색어
    private String searchText = "";

    //장르선택
    private String genre;

    //연도선택
    private String movieYear;
}
