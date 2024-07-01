package com.hnpl.wum.myPage.controller;

import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.dto.UserScoreDto;
import com.hnpl.wum.myPage.form.UserRequireForm;
import com.hnpl.wum.myPage.form.UserScoreForm;
import com.hnpl.wum.myPage.service.UserRequireService;
import com.hnpl.wum.myPage.service.UserScoreService;
import com.hnpl.wum.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/myPage")
@Controller
public class UserScoreController {
    @Autowired
    private UserScoreService userScoreService;
    @Autowired
    private UserService userService;

    //리뷰 리스트 페이지
    @GetMapping("/review")
    public String requireList(@RequestParam(value="page", required = false) Integer page, Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        if (page == null) page = 1;
        int ps = 4; //한 화면에 표시될 데이터의 개수 pageSize
        Map map =new HashMap();
        map.put("page",page * ps -ps);
        map.put("pageSize",ps);
        map.put("userSeq",userSeq);
        int totalCount = userScoreService.countUserScore(userSeq);

        PageHandler pageHandler = new PageHandler(totalCount,ps,page);
        List<UserScoreDto> reviews = userScoreService.selectUserScore(map);

        model.addAttribute("count",totalCount);
        model.addAttribute("pageHandler",pageHandler);
        model.addAttribute("reviews",reviews);
        model.addAttribute("userSeq",userSeq);

        return "myPage/myPage_review";
    }
    //리뷰게시판에서 삭제
    @DeleteMapping("/review/delete")
    public ResponseEntity deleteRequires(@RequestParam("scoreSeq")Long scoreSeq,Principal principal){

        Long userSeq = userService.findUserSeq(principal.getName());

        Map map = new HashMap();

        map.put("userSeq",userSeq);
        map.put("scoreSeq",scoreSeq);

        userScoreService.deleteScore(map);

        return new ResponseEntity<Long>(scoreSeq, HttpStatus.OK);
    }

    //리뷰 수정 페이지
    @GetMapping("/reviewUpdate")
    public String scoreUpdatePage(@RequestParam("scoreSeq")Long scoreSeq ,Model model,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        Map map = new HashMap<>();

        map.put("userSeq",userSeq);
        map.put("scoreSeq",scoreSeq);

        UserScoreDto dto = userScoreService.userScoreContent(map);

        model.addAttribute("userScoreDto",dto);
        model.addAttribute("userScoreForm",new UserScoreForm());
        model.addAttribute("scoreSeq",scoreSeq);
        model.addAttribute("userSeq",userSeq);

        return "myPage/myPage_reviewUpdate";
    }

    //리뷰 수정
    @PostMapping("/reviewUpdate")
    public String requireUpdate(@Valid @RequestBody UserScoreForm userScoreForm,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal){

        Long userSeq = userService.findUserSeq(principal.getName());

        if(bindingResult.hasErrors()){
            Map map = new HashMap<>();

            map.put("userSeq",userSeq);
            map.put("scoreSeq",userScoreForm.getScoreSeq());


            UserScoreDto dto = userScoreService.userScoreContent(map);

            model.addAttribute("userScoreDto",dto);
            model.addAttribute("userSeq",userSeq);
            model.addAttribute("scoreSeq",userScoreForm.getScoreSeq());
            return "myPage/myPage_reviewUpdate";
        }

        try {
            UserScoreDto dto = new UserScoreDto();

            dto.setScoreSeq(userScoreForm.getScoreSeq());
            dto.setScore(userScoreForm.getScore());
            dto.setReviewContent(userScoreForm.getReviewContent());
            userScoreService.scoreUpdate(dto);

            model.addAttribute("userSeq",userSeq);
            model.addAttribute("userScoreDto",dto);

            redirectAttributes.addFlashAttribute("updateMessage","수정 되었습니다");
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "myPage/myPage_reviewUpdate";
        }

        return "redirect:/myPage/review?page=1";
    }

    //리뷰 수정페이지에서 삭제
    @PostMapping("/reviewUpdate/delete")
    public String deleteScoreContent (@RequestParam("scoreSeq")Long scoreSeq,RedirectAttributes redirectAttributes,Model model,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        try{
            Map map = new HashMap<>();
            map.put("userSeq",userSeq);
            map.put("scoreSeq",scoreSeq);

            userScoreService.deleteScore(map);
            redirectAttributes.addFlashAttribute("deleteMessage","게시글이 삭제되었습니다");
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "myPage/myPage_requireUpdate";
        }

        return "redirect:/myPage/review?page=1";
    }
}
