package com.hnpl.wum;

import com.hnpl.wum.admin.dto.MovieDto;
import com.hnpl.wum.admin.form.MovieForm;
import com.hnpl.wum.admin.service.MovieImgService;
import com.hnpl.wum.admin.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    @DisplayName("영화추가테스트")
    public void createMovieTest() {
        MovieForm movie = new MovieForm();
        movie.setGenre("장르");
        movie.setPoster("포스터는 어쩌지");
        movie.setMovieName("영화제목");
        movie.setMovieYear("2024-05-23");
        movie.setDirector("감독");
        movie.setActor("배우");
        movie.setPlot("줄거리");
        movie.setRating("전체관람가");
        movie.setKeyword("키워드");

//        Long result = movieService.insertMovie(movie);
//        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("영화전체목록")
    void listAllMovieTest() {
        List<MovieDto> movieList = movieService.listAllMovie();
        System.out.println(movieList);
        assertThat(movieList).isNotEmpty();
    }
}
