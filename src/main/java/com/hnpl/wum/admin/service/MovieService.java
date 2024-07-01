package com.hnpl.wum.admin.service;

import com.hnpl.wum.admin.dto.MovieSearchDto;
import com.hnpl.wum.admin.dto.ScoreDto;
import com.hnpl.wum.admin.form.MovieForm;
import com.hnpl.wum.admin.dto.MovieDto;
import com.hnpl.wum.admin.dto.MovieMainDto;
import com.hnpl.wum.admin.mapper.MovieMapper;
import com.hnpl.wum.myPage.dto.UserScoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;

    private final MovieImgService movieImgService;

    public Long insertMovie(MovieForm movieForm, MultipartFile movieImgFile) throws  Exception {

        MovieDto movieDto = makeMovie(movieForm);

        movieImgService.saveMoviePoster(movieDto, movieImgFile);

        System.out.println("movieDto : " + movieDto);
        System.out.println("movieImgFile: " + movieImgFile);

        return movieDto.getMovieSeq();
    }


    public List<MovieDto> listAllMovie() {
        return movieMapper.listAllMovie();
    }

    public List<MovieDto> movieListPage(Map map) {
        return movieMapper.movieListPage(map);
    }

    public List<MovieDto> searchMovies(Map map) {
        return movieMapper.mainSearchQuery(map);
    }

    public int countAllMovie(Map map) {
        return movieMapper.countAllMovie(map);
    }

    public List<MovieMainDto> mainSelect(Map map) {
        return movieMapper.mainSelect(map);
    }

    public MovieForm getMovieDtl(Long movieSeq) {

        //영화검색
        MovieDto movieDto = movieMapper.movieDetail(movieSeq);

        if (movieDto == null) {
            throw new NullPointerException("영화가 존재하지 않습니다.");
        }

        //movieDto -> movieForm으로 변환
        MovieForm movieForm = makeMovieForm(movieDto);

        //이미지 가져오기
        movieForm.setMoviePoster(movieMapper.moviePoster(movieSeq));

        return movieForm;
    }

    public MovieDto makeMovie(MovieForm movieForm) {
        MovieDto movieDto = new MovieDto();

        movieDto.setMovieSeq(movieForm.getMovieSeq());
        movieDto.setGenre(movieForm.getGenre());
        movieDto.setPoster(movieForm.getPoster());
        movieDto.setMovieName(movieForm.getMovieName());
        movieDto.setMovieYear(movieForm.getMovieYear());
        movieDto.setDirector(movieForm.getDirector());
        movieDto.setActor(movieForm.getActor());
        movieDto.setPlot(movieForm.getPlot());
        movieDto.setRating(movieForm.getRating());
        movieDto.setKeyword(movieForm.getKeyword());

        return movieDto;
    }

    public MovieForm makeMovieForm(MovieDto movieDto) {
        MovieForm movieForm = new MovieForm();

        movieForm.setMovieSeq(movieDto.getMovieSeq());
        movieForm.setGenre(movieDto.getGenre());
        movieForm.setPoster(movieDto.getPoster());
        movieForm.setMovieName(movieDto.getMovieName());
        movieForm.setMovieYear(movieDto.getMovieYear());
        movieForm.setDirector(movieDto.getDirector());
        movieForm.setActor(movieDto.getActor());
        movieForm.setPlot(movieDto.getPlot());
        movieForm.setRating(movieDto.getRating());
        movieForm.setKeyword(movieDto.getKeyword());

        return movieForm;
    }

    public MovieForm editMovie(Long movieSeq) {

        //영화검색
        MovieDto movieDto = movieMapper.editMovie(movieSeq);

        if (movieDto == null) {
            throw new NullPointerException("영화가 존재하지 않습니다.");
        }

        //movieDto -> movieForm으로 변환
        MovieForm movieForm = makeMovieForm(movieDto);

        //이미지 가져오기
        movieForm.setMoviePoster(movieMapper.moviePoster(movieSeq));

        return movieForm;
    }

    public Long updateMovie(MovieForm movieForm, MultipartFile movieImgFile) throws Exception{
        MovieDto movieDto = makeMovie(movieForm);

        int result = movieMapper.updateMovie(movieDto);

        Long movieSeq = movieForm.getMovieSeq();

        movieImgService.updateMoviePoster(movieSeq, movieImgFile);

        return movieDto.getMovieSeq();
    }

    public void deleteMovie(List<Long> movieSeqList) {
        for (Long movieSeq : movieSeqList) {
            movieMapper.deleteMovie(movieSeq);
        }
    }

    public Long userCheck(Long userSeq, Long movieSeq) {
        Map<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("movieSeq", movieSeq);
        return movieMapper.checkWish(map);
    }

    public int wish(Long userSeq, Long movieSeq) {
        Map<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("movieSeq", movieSeq);
        Long result = movieMapper.checkWish(map);
        if(result==null){
            return movieMapper.insertWish(map);
        } else {
            return movieMapper.cancelWish(map);
        }
    }

    public boolean submitReview(Map map) {
        int result = movieMapper.insertReview(map);
        return result > 0;
    }

    public List<ScoreDto> showReview(Long movieSeq){
        return movieMapper.showReview(movieSeq);
    }
}
