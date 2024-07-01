package com.hnpl.wum.myPage.service;

import com.hnpl.wum.myPage.dto.UserScoreDto;
import com.hnpl.wum.myPage.form.UserScoreForm;
import com.hnpl.wum.myPage.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserScoreService {

    @Autowired
    MyPageMapper myPageMapper;

    public List<UserScoreDto> selectUserScore(Map map){
        return myPageMapper.selectUserScore(map);
    }
    public int countUserScore(Long userSeq){
        return myPageMapper.countUserScore(userSeq);
    }
    public UserScoreDto userScoreContent(Map map){
        return myPageMapper.userScoreContent(map);
    }
    public int deleteScore(Map map){
        return myPageMapper.deleteScore(map);
    }
    public int scoreUpdate(UserScoreDto userScoreDto){
        return myPageMapper.scoreUpdate(userScoreDto);
    }
}
