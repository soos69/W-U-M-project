package com.hnpl.wum;

import com.hnpl.wum.admin.dto.MovieMainDto;
import com.hnpl.wum.admin.dto.MovieSearchDto;
import com.hnpl.wum.admin.service.MovieService;
import com.hnpl.wum.config.PageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final MovieService movieService;

    @GetMapping("/")
    public String main(
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value = "searchText", required=false) String searchText,
            @ModelAttribute("movieSearchDto") MovieSearchDto movieSearchDto,
            Model model) {

        //페이지 크기
        int pg = 6;

        if (page==null) page =1;

        Map map = new HashMap();


        if(searchText != null){
            movieSearchDto.setSearchText(searchText);
        }

        System.out.println("movieSearchDto : " + movieSearchDto);
        map.put("page", page * pg - pg);
        map.put("pageSize", pg);
        map.put("movieSearchDto", movieSearchDto);

        System.out.println("map(HelloController) : "+ map);

        //개수계산하기
        int totalCnt = movieService.countAllMovie(map);

        PageHandler pageHandler = new PageHandler(totalCnt, pg, page);

        List<MovieMainDto> movieMain = movieService.mainSelect(map);

        model.addAttribute("movieMain", movieMain);
        model.addAttribute("pageHandler", pageHandler);
        model.addAttribute("movieSearchDto", movieSearchDto);
        model.addAttribute("maxPage",5);

        return "index";
    }
}
