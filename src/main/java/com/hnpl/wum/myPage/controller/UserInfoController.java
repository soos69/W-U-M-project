package com.hnpl.wum.myPage.controller;

import com.hnpl.wum.myPage.dto.UserInfoDto;
import com.hnpl.wum.myPage.form.UserInfoForm;
import com.hnpl.wum.myPage.service.UserInfoService;
import com.hnpl.wum.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequestMapping("/myPage")
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String myPage(Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        UserInfoDto dto = userInfoService.selectUserInfo(userSeq);
        model.addAttribute("user",dto);
        model.addAttribute("userSeq",userSeq);
        return "myPage/myPage_index";
    }

    @GetMapping("/info")
    public String myPageInfo(Model model,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        UserInfoDto dto = userInfoService.selectUserInfo(userSeq);
        model.addAttribute("user",dto);
        model.addAttribute("userSeq",userSeq);
        model.addAttribute("userInfoForm",new UserInfoForm());
        return "myPage/myPage_info";
    }
    @PostMapping("/info/update")
    public String updateInfo(@Validated @RequestBody UserInfoForm userInfoForm, BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());
        UserInfoDto dto = userInfoService.selectUserInfo(userSeq);

        if(bindingResult.hasErrors()){
            model.addAttribute("user",dto);
            model.addAttribute("userInfoForm",userInfoForm);
            return "myPage/myPage_info";
        }

        try {
            UserInfoDto infoDto = new UserInfoDto();
            infoDto.setPassword(userInfoForm.getPassword());

            if(userInfoForm.getNickname().isBlank() || userInfoForm.getNickname().equals("")){
                infoDto.setNickname(dto.getNickname());
            }else {
                infoDto.setNickname(userInfoForm.getNickname());
            }
            if(userInfoForm.getEmail().isBlank() || userInfoForm.getEmail().equals("")){
                infoDto.setEmail(dto.getEmail());
            }else {
                infoDto.setEmail(userInfoForm.getEmail());
            }
            if(userInfoForm.getTel().isBlank() || userInfoForm.getTel().equals("")){
                infoDto.setTel(dto.getTel());
            }else {
                infoDto.setTel(userInfoForm.getTel());
            }
            infoDto.setUserSeq(userSeq);

            userInfoService.updateUserInfo(infoDto,dto);
            //model을 이용하여 데이터를 전달하면 redirect에서는 메세지가 유지되지 않음
            //redirectArrtibute를 사용하여 메세지 전달
            redirectAttributes.addFlashAttribute("updateMessage","수정 완료");
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("user",dto);
            return "myPage/myPage_info";
        }

        return "redirect:/myPage/info";
    }

}
