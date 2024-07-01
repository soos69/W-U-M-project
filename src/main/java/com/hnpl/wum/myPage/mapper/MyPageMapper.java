package com.hnpl.wum.myPage.mapper;

import com.hnpl.wum.myPage.dto.UserInfoDto;
import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.dto.UserScoreDto;
import com.hnpl.wum.myPage.dto.UserWishDto;
import com.hnpl.wum.myPage.form.UserRequireForm;
import com.hnpl.wum.myPage.form.UserScoreForm;
import com.hnpl.wum.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper {
    // 유저 정보 변경
    UserInfoDto selectUserInfo(Long userSeq);
    int updateUserInfo (UserInfoDto userInfoDto);
    UserInfoDto overlapNickname(String nickname);
    UserInfoDto overlapEmail(String email);
    UserInfoDto overlapTel(String tel);


    // 유저 영화요청
    List<UserRequireDto> selectUserRequire(Map map);
    int countUserRequire (Long userSeq);
    UserRequireDto userRequireContent (Map map);
    int requireUpdate(UserRequireDto userRequireDto);
    int deleteRequire(Map map);

    // 유저 리뷰
    List<UserScoreDto> selectUserScore(Map map);
    int countUserScore(Long userSeq);
    UserScoreDto userScoreContent(Map map);
    int deleteScore(Map map);
    int scoreUpdate(UserScoreDto userScoreDto);

    // 유저 찜목록
    List<UserWishDto> selectWish(Map map);
    int countUserWish(Long userSeq);
    int deleteWish(Long wishSeq);
}
