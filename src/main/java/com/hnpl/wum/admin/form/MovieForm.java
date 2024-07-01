package com.hnpl.wum.admin.form;

import com.hnpl.wum.admin.dto.MovieDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieForm {

    private Long movieSeq;

    @NotBlank(message = "장르를 입력하세요")
    private String genre;

    @NotBlank(message = "영화제목을 입력하세요")
    private String movieName;

    @NotBlank(message = "개봉일을 입력하세요")
    private String movieYear;

    @NotBlank(message = "감독을 입력하세요")
    private String director;

    @NotBlank(message = "출연배우를 입력하세요")
    private String actor;

    @NotBlank(message = "줄거리를 입력하세요")
    private String plot;

    @NotBlank(message = "관람등급을 입력하세요")
    private String rating;

    private String poster;

    @NotBlank(message = "키워드를 입력하세요")
    private String keyword;

    private List<MovieDto> moviePoster = new ArrayList<>();
}
