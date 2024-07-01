package com.hnpl.wum.admin.controller;

import com.hnpl.wum.admin.dto.MovieDto;
import com.hnpl.wum.admin.dto.ScoreDto;
import com.hnpl.wum.admin.form.MovieForm;
import com.hnpl.wum.admin.service.MovieService;
import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.admin.dto.MovieSearchDto;
import com.hnpl.wum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    private final UserService userService;

    @PostMapping("/search/searchText")
    public String searchMovies(@ModelAttribute("movieSearchDto") MovieSearchDto movieSearchDto,
                               Model model) {
        return searchMovies(1, movieSearchDto, model);
    }


    @GetMapping("/search/searchText/{page}")
    public String searchMovies(@PathVariable(value= "page", required = false)Integer page,
                               @ModelAttribute("movieSearchDto") MovieSearchDto movieSearchDto,
                               Model model) {
        //페이지 크기
        int pg = 24;
        if (page==null) page =1;
        Map map = new HashMap();

        map.put("page", page * pg - pg);
        map.put("pageSize", pg);
        map.put("searchText", movieSearchDto.getSearchText());
        map.put("genre", movieSearchDto.getGenre());
        map.put("movieYear", movieSearchDto.getMovieYear());
        map.put("movieSearchDto", movieSearchDto);

        System.out.println("검색어: " + movieSearchDto.getSearchText());
        System.out.println("장르 : " + movieSearchDto.getGenre());
        System.out.println("년도 : " + movieSearchDto.getMovieYear());

        // 검색어가 비어 있으면 빈 문자열로 설정하여 검색 조건에 포함되도록 함
        if (movieSearchDto.getSearchText() == null || movieSearchDto.getSearchText().trim().isEmpty()) {
            movieSearchDto.setSearchText(""); // 빈 문자열로 설정
        }

        //개수계산하기
        int totalCnt = movieService.countAllMovie(map);

        PageHandler pageHandler = new PageHandler(totalCnt, pg, page);

        //검색결과 조회
        List<MovieDto> movie = movieService.searchMovies(map);

        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("movie", movie);
        model.addAttribute("movieSearchDto", movieSearchDto);

        return "search/search_searchList";
    }

    @GetMapping("/movie/{movieSeq}")
    public String movieDtl(@PathVariable("movieSeq") Long movieSeq,
                           Principal principal,
                           Model model) {
        String heartImg;
        Map<String, Object> map = new HashMap<>();
        // principal이 null인지 확인하여 로그인 여부 판단
        if (principal != null) {
            Long userSeq = userService.findUserSeq(principal.getName());
            map.put("userSeq", userSeq);
            map.put("movieSeq", movieSeq);
            Long result = movieService.userCheck(userSeq, movieSeq);

            if (result != null){
                heartImg = "/image/fullheart.jpg";
            } else {
                heartImg = "/image/heart.jpg";
            }

        } else {
            heartImg = "/image/heart.jpg"; // 로그인하지 않은 경우 기본 하트 이미지 설정
        }

        List<ScoreDto> reviewResult = movieService.showReview(movieSeq);
        MovieForm movieForm = movieService.getMovieDtl(movieSeq);

        model.addAttribute("movieForm", movieForm);
        model.addAttribute("movieSeq", movieSeq);
        model.addAttribute("heartImg", heartImg);
        model.addAttribute("reviewList", reviewResult);

        return "search/search_movieDetail";
    }


    @PostMapping("/movie/insertWish")
    @ResponseBody
    public Map<String, Object> insertWish(@RequestBody Map<String, Long> request, Principal principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            response.put("success", false);
            response.put("errorMessage", "로그인 후에 사용 가능합니다.");
            return response;
        }

        Long movieSeq = request.get("movieSeq");
        Long userSeq = userService.findUserSeq(principal.getName());
        int result = movieService.wish(userSeq, movieSeq);

        response.put("success", result > 0);
        return response;
    }


    @PostMapping("/movie/submitReview")
    @ResponseBody
    public Map<String, Object> submitReview(@RequestBody Map<String, Object> request, Principal principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            response.put("success", false);
            response.put("errorMessage", "로그인 후에 사용 가능합니다.");
            return response;
        }

        Long movieSeq = Long.valueOf(request.get("movieSeq").toString());
        String reviewText = request.get("reviewText").toString();
        int reviewStar = Integer.parseInt(request.get("reviewStar").toString());
        Long userSeq = userService.findUserSeq(principal.getName());

        Map<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("movieSeq", movieSeq);
        map.put("reviewContent", reviewText);
        map.put("score", reviewStar);

        boolean result = movieService.submitReview(map);
        response.put("success", result);
        return response;
    }

}