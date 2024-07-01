package com.hnpl.wum.myPage.controller;

import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.form.UserRequireForm;
import com.hnpl.wum.myPage.service.UserRequireService;
import com.hnpl.wum.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/myPage")
@Controller
public class UserRequireController {
    @Autowired
    private UserRequireService userRequireService;
    @Autowired
    private UserService userService;

    //마이페이지 영화요청 게시판 리스트
    @GetMapping("/require")
    public String requireList(@RequestParam(value="page", required = false) Integer page, Model model,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        if (page == null) page = 1;
        int ps = 15; //한 화면에 표시될 데이터의 개수 pageSize
        Map map =new HashMap();
        map.put("page",page * ps -ps);
        map.put("pageSize",ps);
        map.put("userSeq",userSeq);
        int totalCount = userRequireService.countUserRequire(userSeq);
        PageHandler pageHandler = new PageHandler(totalCount,ps,page);
        List<UserRequireDto> requires = userRequireService.requireList(map);

        model.addAttribute("count",totalCount);
        model.addAttribute("pageHandler",pageHandler);
        model.addAttribute("requires",requires);
        model.addAttribute("userSeq",userSeq);

        return "myPage/myPage_requireMovie";
    }

    //마이페이지 영화요청 게시글
    @GetMapping("/requireContent")
    public String require(@RequestParam("requireSeq")Long requireSeq, Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        Map map = new HashMap<>();
        map.put("requireSeq",requireSeq);
        map.put("userSeq",userSeq);

        UserRequireDto userRequireDto = userRequireService.require(map);


        model.addAttribute("userRequireForm",new UserRequireForm());
        model.addAttribute("userRequireDto",userRequireDto);
        model.addAttribute("userSeq",userSeq);
        model.addAttribute("requireSeq",requireSeq);
        return "myPage/myPage_requireUpdate";
    }
    //게시글 수정
    @PostMapping("/requireContent")
    public String requireUpdate(@Valid @RequestBody UserRequireForm userRequireForm,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal){

        Long userSeq = userService.findUserSeq(principal.getName());
        if(bindingResult.hasErrors()){
            Map map = new HashMap<>();
            map.put("requireSeq",userRequireForm.getRequireSeq());
            map.put("userSeq",userSeq);

            UserRequireDto userRequireDto = userRequireService.require(map);

            model.addAttribute("userRequireDto",userRequireDto);
            model.addAttribute("userSeq",userSeq);
            model.addAttribute("requireSeq",userRequireForm.getRequireSeq());
            return "myPage/myPage_requireUpdate";
        }
        try{
            UserRequireDto userRequireDto =new UserRequireDto();
            userRequireDto.setTitle(userRequireForm.getTitle());
            userRequireDto.setContent(userRequireForm.getContent());
            userRequireDto.setRequireSeq(userRequireForm.getRequireSeq());

            userRequireService.requireUpdate(userRequireDto);

            model.addAttribute("userRequireDto",userRequireDto);
            model.addAttribute("userSeq",userSeq);
            redirectAttributes.addFlashAttribute("updateMessage","수정 되었습니다");
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "myPage/myPage_requireUpdate";
        }

        return "redirect:/myPage/require?page=1";
    }
    //게시판에서 게시글 삭제
    @DeleteMapping("/require/delete")
    public ResponseEntity deleteRequires(@RequestBody UserRequireDto userRequireDto,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());
        List<UserRequireDto> userRequireDtoList = userRequireDto.getUserRequireDtoList();
        userRequireService.deleteRequire(userRequireDtoList,userSeq);

        return new ResponseEntity<Long>(userRequireDto.getRequireSeq(),HttpStatus.OK);
    }
    //게시글페이지에서 삭제
    @PostMapping("/requireContent/delete")
    public String deleteContent(@RequestParam("requireSeq")Long requireSeq,RedirectAttributes redirectAttributes,Model model,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());
        try{
            Map map = new HashMap<>();
            map.put("requireSeq",requireSeq);
            map.put("userSeq",userSeq);
            userRequireService.deleteContent(map);
            redirectAttributes.addFlashAttribute("deleteMessage","게시글이 삭제되었습니다");
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "myPage/myPage_requireUpdate";
        }

        return "redirect:/myPage/require?page=1";
    }
}
