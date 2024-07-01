package com.hnpl.wum.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long movieSeq;

    private String genre;

    private String poster;

    private String movieName;

    private String movieYear;

    private String director;

    private String actor;

    private String plot;

    private String rating;

    private String keyword;
}
