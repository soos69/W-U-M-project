package com.hnpl.wum.require.controller;

import com.hnpl.wum.config.PageHandler;
import com.hnpl.wum.require.dto.RequireDto;
import com.hnpl.wum.require.dto.RequireSearchDto;
import com.hnpl.wum.require.form.RequireForm;
import com.hnpl.wum.require.service.RequireService;
import com.hnpl.wum.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/require")
@Controller
public class RequireController {
    @Autowired
    private RequireService requireService;
    @Autowired
    private UserService userService;

    //영화요청 게시판 리스트
    @GetMapping("")
    public String requireList(@RequestParam(value="page", required = false) Integer page, @ModelAttribute("requireSearchDto") RequireSearchDto requireSearchDto, Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        if (page == null) page = 1;
        int ps = 15; //한 화면에 표시될 데이터의 개수 pageSize
        Map map =new HashMap();
        map.put("page",page * ps -ps);
        map.put("pageSize",ps);
        map.put("userSeq",userSeq);
        map.put("requireSearchDto",requireSearchDto);
        int totalCount = requireService.countRequire();
        PageHandler pageHandler = new PageHandler(totalCount,ps,page);
        List<RequireDto> requires = requireService.selectList(map);

        model.addAttribute("count",totalCount);
        model.addAttribute("pageHandler",pageHandler);
        model.addAttribute("requires",requires);
        model.addAttribute("userSeq",userSeq);

        return "board/requireMovie_board";
    }

    //영화요청 게시글 디테일
    @GetMapping("/detail")
    public String require(@RequestParam("requireSeq")Long requireSeq, Model model){
        RequireDto requireDto = requireService.requireDetail(requireSeq);

        model.addAttribute("requireDto",requireDto);
        model.addAttribute("requireSeq",requireSeq);
        return "board/requireMovie_detail";
    }

    //게시글 등록 페이지
    @GetMapping("/addContent")
    public String requireInsertPage(Model model, Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        model.addAttribute("requireForm",new RequireForm());
        model.addAttribute("userSeq",userSeq);
        return "board/requireMovie_insert";
    }

    @PostMapping("/addContent")
    public String requireInsert(@Valid RequireForm requireForm,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal){

        RequireDto requireDto =new RequireDto();
        Long userSeq = userService.findUserSeq(principal.getName());

        if(bindingResult.hasErrors()){
            model.addAttribute("requireForm",requireForm);
            model.addAttribute("userSeq",userSeq);
            return "board/requireMovie_insert";
        }
        try{
            requireDto.setUserSeq(userSeq);
            requireDto.setTitle(requireForm.getTitle());
            requireDto.setContent(requireForm.getContent());

            requireService.insertRequire(requireDto);

            model.addAttribute("requireDto",requireDto);
            model.addAttribute("userSeq",userSeq);
            redirectAttributes.addFlashAttribute("insertMessage","게시글이 등록 되었습니다");
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "board/requireMovie_insert";
        }

        return "redirect:/require/detail?requireSeq="+requireDto.getRequireSeq();
    }

    //게시글 업데이트 페이지
    @GetMapping("/update")
    public String requireUpdatePage(@RequestParam("requireSeq")Long requireSeq,@RequestParam("userSeq")Long userSeq,RedirectAttributes redirectAttributes, Model model, Principal principal){
        Long userSeq1 = userService.findUserSeq(principal.getName());
        if(userSeq1.longValue() != userSeq.longValue()){
            redirectAttributes.addFlashAttribute("errorMessage","수정 권한이 없습니다");
            return "redirect:/require/detail?requireSeq="+requireSeq;
        }

        RequireDto requireDto = requireService.requireDetail(requireSeq);

        model.addAttribute("requireForm",new RequireForm());
        model.addAttribute("requireDto",requireDto);
        model.addAttribute("userSeq",userSeq);
        model.addAttribute("requireSeq",requireSeq);
        return "board/requireMovie_update";
    }
    //게시글 수정
    @PostMapping("/update")
    public String requireUpdate(@Valid RequireForm requireForm,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal){

        Long userSeq = userService.findUserSeq(principal.getName());

        if(bindingResult.hasErrors()){
            RequireDto requireDto = requireService.requireDetail(requireForm.getRequireSeq());

            model.addAttribute("requireDto",requireDto);
            model.addAttribute("requireForm",requireForm);
            model.addAttribute("userSeq",userSeq);
            model.addAttribute("requireSeq",requireForm.getRequireSeq());
            return "board/requireMovie_update";
        }
        try{
            RequireDto requireDto =new RequireDto();
            requireDto.setTitle(requireForm.getTitle());
            requireDto.setContent(requireForm.getContent());
            requireDto.setRequireSeq(requireForm.getRequireSeq());
            requireDto.setUserSeq(userSeq);

            requireService.updateRequire(requireDto);

            model.addAttribute("requireDto",requireDto);
            model.addAttribute("userSeq",userSeq);
            redirectAttributes.addFlashAttribute("updateMessage","수정 되었습니다");
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "board/requireMovie_update";
        }

        return "redirect:/require/detail?requireSeq="+requireForm.getRequireSeq();
    }
    //게시글 삭제
    @GetMapping("/delete")
    public String deleteContent(@RequestParam("requireSeq")Long requireSeq,@RequestParam("userSeq")Long userSeq, RedirectAttributes redirectAttributes,Model model,Principal principal){
        Long userSeq2 = userService.findUserSeq(principal.getName());

        if(userSeq2.longValue() != userSeq.longValue()){
            redirectAttributes.addFlashAttribute("errorMessage","삭제 권한이 없습니다");
            return "redirect:/require/detail?requireSeq="+requireSeq;
        }

        try{
            Map map = new HashMap<>();
            map.put("requireSeq",requireSeq);
            map.put("userSeq",userSeq);
            requireService.deleteRequire(map);
            redirectAttributes.addFlashAttribute("deleteMessage","게시글이 삭제되었습니다");
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "board/requireMovie_detail";
        }

        return "redirect:/require";
    }

    @GetMapping("/recommend")
    public String recommendUp (@RequestParam("requireSeq")Long requireSeq,Principal principal){
        Long userSeq = userService.findUserSeq(principal.getName());

        requireService.recommend(requireSeq,userSeq);

        return "redirect:/require/detail?requireSeq="+requireSeq;
    }
}
