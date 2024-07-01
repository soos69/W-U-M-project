package com.hnpl.wum.myPage.controller;

import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.dto.UserWishDto;
import com.hnpl.wum.myPage.service.UserWishService;
import com.hnpl.wum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/myPage")
@Controller
public class UserWishController {

    @Autowired
    private UserWishService userWishService;
    @Autowired
    private UserService userService;
    @GetMapping("/wishList")
    public String wishList(@RequestParam("page") Integer page , Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        if (page == null) page = 1;
        int ps = 15; //한 화면에 표시될 데이터의 개수 pageSize
        Map map =new HashMap();
        map.put("page",page * ps -ps);
        map.put("pageSize",ps);
        map.put("userSeq",userSeq);
        int totalCount = userWishService.countUserWish(userSeq);
        PageHandler pageHandler = new PageHandler(totalCount,ps,page);

        List<UserWishDto> wishList = userWishService.selectWishList(map);

        model.addAttribute("count",totalCount);
        model.addAttribute("pageHandler",pageHandler);
        model.addAttribute("wishList",wishList);
        model.addAttribute("userSeq",userSeq);
        return "myPage/myPage_wish";
    }
    @DeleteMapping("/wishList/delete")
    public ResponseEntity deleteWish(@RequestBody UserWishDto userWishDto){

        List<UserWishDto> userWishDtoList = userWishDto.getUserWishDtoList();
        userWishService.deleteWishContent(userWishDtoList);

        return new ResponseEntity<Long>(userWishDto.getWishSeq(),HttpStatus.OK);
    }

}
