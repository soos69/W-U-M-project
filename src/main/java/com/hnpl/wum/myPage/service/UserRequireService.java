package com.hnpl.wum.myPage.service;

import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.form.UserRequireForm;
import com.hnpl.wum.myPage.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRequireService {
    @Autowired
    private MyPageMapper myPageMapper;

    public List<UserRequireDto> requireList(Map map){
        return myPageMapper.selectUserRequire(map);
    }
    public int countUserRequire(Long userSeq){
        return myPageMapper.countUserRequire(userSeq);
    }
    public UserRequireDto require(Map map){
        return myPageMapper.userRequireContent(map);
    }
    public void requireUpdate(UserRequireDto userRequireDto){
        myPageMapper.requireUpdate(userRequireDto);
    }
    public void deleteRequire(List<UserRequireDto> userRequireDto,Long userSeq){
        for (UserRequireDto userRequireDto1 : userRequireDto){
            Map map = new HashMap();
            map.put("userSeq",userSeq);
            map.put("requireSeq",userRequireDto1.getRequireSeq());
            myPageMapper.deleteRequire(map);
        }
    }
    public void deleteContent(Map map){
        myPageMapper.deleteRequire(map);
    }
}
