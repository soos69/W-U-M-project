package com.hnpl.wum.myPage.service;

import com.hnpl.wum.myPage.dto.UserInfoDto;
import com.hnpl.wum.myPage.dto.UserRequireDto;
import com.hnpl.wum.myPage.mapper.MyPageMapper;
import com.hnpl.wum.user.dto.UserDto;
import com.hnpl.wum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    private MyPageMapper myPageMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserInfoDto selectUserInfo (Long userSeq){
        return myPageMapper.selectUserInfo(userSeq);
    }
    public int updateUserInfo (UserInfoDto dto,UserInfoDto dto2){

        overlapNickname(dto.getNickname(),dto2);
        overlapEmail(dto.getEmail(),dto2);
        overlapTel(dto.getTel(),dto2);

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);

        return myPageMapper.updateUserInfo(dto);
    }
    public void overlapNickname(String nickname,UserInfoDto dto) {
        UserInfoDto findNickname = myPageMapper.overlapNickname(nickname);

        if (findNickname != null) {
            if(findNickname.getNickname().equals(dto.getNickname())){
                return;
            }
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }
    public void overlapTel(String tel,UserInfoDto dto){
        UserInfoDto findTel = myPageMapper.overlapTel(tel);
        if(findTel != null){
            if(findTel.getTel().equals(dto.getTel())){
                return;
            }
            throw new IllegalStateException("이미 사용중인 전화번호입니다.");
        }
    }

    public void overlapEmail(String email,UserInfoDto dto) {
        UserInfoDto findEmail = myPageMapper.overlapEmail(email);

        if (findEmail != null) {
            if(findEmail.getEmail().equals(dto.getEmail())){
                return;
            }
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

}
