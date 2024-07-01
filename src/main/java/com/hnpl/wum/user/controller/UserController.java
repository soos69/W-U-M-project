package com.hnpl.wum.user.controller;

import com.hnpl.wum.user.constant.Role;
import com.hnpl.wum.user.dto.UserDto;
import com.hnpl.wum.user.form.UserFindForm;
import com.hnpl.wum.user.form.UserJoinForm;
import com.hnpl.wum.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String UserForm(Model model) {
        /*CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.println(token.getHeaderName() + "=" +token.getToken());*/

        model.addAttribute("userJoinForm", new UserJoinForm());
        return "user/user_register";
    }

    @PostMapping("/new")
    public String newUser(@Valid UserJoinForm userJoinForm,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes rttr) {
        int year = Integer.parseInt(userJoinForm.getYear());
        int month = Integer.parseInt(userJoinForm.getMonth());
        int day = Integer.parseInt(userJoinForm.getDay());
        LocalDate birthDate = LocalDate.of(year, month, day);

        if (bindingResult.hasErrors()) {
            //회원가입실패시 입력데이터값 유지
            model.addAttribute("userJoinForm",userJoinForm);
            return "user/user_register";
        }
        System.out.println(userJoinForm.toString());

        try {
            UserDto dto = new UserDto();
            dto.setId(userJoinForm.getId());
            dto.setPassword(userJoinForm.getPassword());
            dto.setNickname(userJoinForm.getNickname());
            dto.setName(userJoinForm.getName());
            dto.setBirth(birthDate);
            dto.setEmail(userJoinForm.getEmail());
            dto.setTel(userJoinForm.getTel());
            dto.setRole(Role.USER);

            userService.insertMember(dto);

            rttr.addFlashAttribute("joinMessage","회원가입을 환영합니다.");

            System.out.println(userJoinForm.toString());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "user/user_register";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/user_login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요.");

        return "user/user_login";
    }

    @GetMapping("/find")
    public String findAccount(Model model,UserFindForm userFindForm){
        userFindForm = new UserFindForm();
        model.addAttribute("userFindForm",userFindForm);
        return "user/user_findAccount";
    }

    @PostMapping("/findId")
    public String findId(UserFindForm userFindForm,
                         BindingResult bindingResult,
                         Model model) {

        System.out.println(userFindForm.toString());

        if (bindingResult.hasErrors()) {
            return "user/user_findAccount";
        }

        Map map = new HashMap();
        map.put("name",userFindForm.getName());
        map.put("tel",userFindForm.getTel());
        // 이름과 전화번호로 아이디 찾기 로직 구현
        UserDto userId = userService.findId(map);

        if (userId == null) {
            model.addAttribute("errorMessage", "일치하는 정보가 없습니다.");
            return "user/user_findAccount";
        }

        model.addAttribute("userId", userId.getId());
        return "user/user_findId";
    }

    @PostMapping("/findPw")
    public String findPassword(@ModelAttribute UserFindForm userFindForm,
                               BindingResult bindingResult,
                               Model model){
        if (bindingResult.hasErrors()) {
            return "user/user_findAccount";
        }

        Map map = new HashMap();
        map.put("id",userFindForm.getId());
        map.put("name",userFindForm.getName());
        map.put("tel",userFindForm.getTel());

        UserDto userDto = userService.findPw(map);

        if (userDto == null) {
            model.addAttribute("errorMessage2", "일치하는 정보가 없습니다.");
            return "user/user_findAccount";
        }

        model.addAttribute("userDto", userDto);
        return "user/user_findPw";
    }

    @PostMapping("/updatePw")
    public String updatePassword(@ModelAttribute UserDto userDto,
                                 BindingResult bindingResult,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("newPassword2") String newPassword2,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/find_Account";
        }
        if (!newPassword.equals(newPassword2)) {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/user/findPw";
        }
        userService.updatePw(userDto, newPassword);
        return "redirect:/";
    }
}

