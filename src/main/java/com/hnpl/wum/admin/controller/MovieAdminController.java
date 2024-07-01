package com.hnpl.wum.admin.controller;

import com.hnpl.wum.admin.dto.MovieDto;
import com.hnpl.wum.admin.dto.MovieSearchDto;
import com.hnpl.wum.admin.form.MovieForm;
import com.hnpl.wum.admin.service.MovieService;
import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.require.dto.RequireDto;
import com.hnpl.wum.user.dto.UserDetailDto;
import com.hnpl.wum.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MovieAdminController {

    private final MovieService movieService;

    private final UserService userService;

    @GetMapping(value = {"/movieList", "/movieList/{page}"})
    public String movieListPage(@PathVariable(value="page", required = false) Integer page,
                                @RequestParam(value="searchText", required = false) String searchText,
                                @ModelAttribute("movieSearchDto") MovieSearchDto movieSearchDto,
                                Model model) {
        int ps = 10;
        if (page == null) page = 1;

        Map map = new HashMap();

        if (searchText != null) {
            movieSearchDto.setSearchText(searchText);
        }

        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("movieSearchDto", movieSearchDto);
        map.put("searchText",searchText);


        System.out.println("map(MovieController) : " + map);

        int totalCnt = movieService.countAllMovie(map);

        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        //목록조회
        List<MovieDto> movieDto = movieService.movieListPage(map);

        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("movieDto", movieDto);
        model.addAttribute("movieSearchDto", movieSearchDto);

        return "adminPage/admin_movieList";
    }

    @GetMapping("/movie/new")
    public String movieForm(Model model) {
        model.addAttribute("movieForm", new MovieForm());
        return "adminPage/admin_movieAdd";
    }

    @PostMapping("/movie/new")
    public String movieNew(@Valid MovieForm movieForm,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam(value = "movieImgFile") MultipartFile movieImgFile) {
        if (bindingResult.hasErrors()) {
            return "adminPage/admin_movieAdd";
        }

        if (movieImgFile.isEmpty() && movieForm.getMovieSeq() == null) {
            model.addAttribute("errorMessage", "영화포스터는 필수입니다.");
            return "adminPage/admin_movieAdd";
        }

        try {
            movieService.insertMovie(movieForm, movieImgFile);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "영화 등록 중 오류 발생");
        }

        return "redirect:/";
    }

    @GetMapping("/movie/{movieSeq}")
    public  String movieUpdate(Model model, @PathVariable("movieSeq") Long movieSeq) {
        try{
            MovieForm movieForm = movieService.editMovie(movieSeq);
            model.addAttribute("movieForm", movieForm);

        } catch (NullPointerException e) {
            model.addAttribute("movieForm", new MovieForm());
            return "adminPage/admin_movieUpdate";
        }

        return "adminPage/admin_movieUpdate";
    }

    @PostMapping("/movie/{movieSeq}")
    public String movieUpdate(@Valid MovieForm movieForm,
                              BindingResult bindingResult, Model model,
                              @RequestParam("movieImgFile") MultipartFile movieImgFile) {

        if (bindingResult.hasErrors()) {
            return "adminPage/admin_movieUpdate";
        }

        if (movieImgFile.isEmpty() && movieForm.getMovieSeq() == null) {
            model.addAttribute("errorMessage", "포스터는 필수입니다.");
            return "adminPage/admin_movieUpdate";
        }

        try{
            movieService.updateMovie(movieForm, movieImgFile);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage","영화 수정 중 에러 발생");
            return "adminPage/admin_movieUpdate";
        }
        return "redirect:/admin/movieList";
    }

    @DeleteMapping("/movieList/delete")
    public ResponseEntity<Void> deleteMovie(@RequestBody List<Long> movieSeqList) {
        movieService.deleteMovie(movieSeqList);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/list","/list/{page}"})
    public String memberList(@PathVariable(value = "page", required = false) Integer page,
                             @ModelAttribute("userDetailDto") UserDetailDto userDetailDto,
                             Model model) {
        int ps = 5; //한 화면에 표시될 데이터의 개수
        if (page == null) page = 1;
        Map map = new HashMap();

        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("userDetailDto", userDetailDto);

        int totalCnt = userService.countUsers(map);

        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);

        List<UserDetailDto> list = userService.userList(map);

        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("userList", list);

        return "adminPage/admin_memberList";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUsers(@RequestBody List<Long> userSeqList) {
        System.out.println("요청 시퀀스 :  " + userSeqList);
        userService.deleteUsers(userSeqList);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/info/{seq}"})
    public String requireList(@PathVariable(value = "seq") Long userSeq,
                              @RequestParam(value = "page", required = false) Integer page,
                              Model model) {
        int ps = 5; //한 화면에 표시될 데이터의 개수
        if (page == null) page = 1;
        Map<String, Object> map = new HashMap<>();

        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("seq", userSeq);
        int totalCnt = userService.countUsersRequire(map);
        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);
        List<RequireDto> list = userService.userRequireList(map);
        UserDetailDto userDetail = userService.getUserDetail(userSeq);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("userRequireList", list);
        model.addAttribute("userDetail", userDetail);

        return "adminPage/admin_memberInfo";
    }

    @DeleteMapping("/info/delete")
    public ResponseEntity<Void> deleteUsersRequire(@RequestBody List<Long> requireSeqList) {
        System.out.println("요청 시퀀스 :  " + requireSeqList);
        userService.deleteUsersRequire(requireSeqList);
        return ResponseEntity.ok().build();
    }
}
