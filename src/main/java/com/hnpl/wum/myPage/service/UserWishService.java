package com.hnpl.wum.myPage.service;

import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.dto.UserWishDto;
import com.hnpl.wum.myPage.mapper.MyPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserWishService {
    @Autowired
    private MyPageMapper myPageMapper;

    public List<UserWishDto> selectWishList(Map map){
        return myPageMapper.selectWish(map);
    }
    public int countUserWish(Long userSeq){
        return myPageMapper.countUserWish(userSeq);
    }
    public void deleteWishContent(List<UserWishDto> userWishDto){
        for (UserWishDto userWishDto1 : userWishDto){
            myPageMapper.deleteWish(userWishDto1.getWishSeq());
        }
    }

}
