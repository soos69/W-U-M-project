package com.hnpl.wum.user.service;

import com.hnpl.wum.require.dto.RequireDto;
import com.hnpl.wum.user.dto.UserDetailDto;
import com.hnpl.wum.user.dto.UserDto;
import com.hnpl.wum.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int insertMember(UserDto userDto) {
        overlapId(userDto.getId());
        overlapNickname(userDto.getNickname());
        overlapEmail(userDto.getEmail());
        overlapTel(userDto.getTel());

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        return userMapper.insertMember(userDto);
    }

    public void overlapId(String id) {
        UserDto findId = userMapper.overlapId(id);

        if (findId != null) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }
    }

    public void overlapNickname(String nickname) {
        UserDto findNickname = userMapper.overlapNickname(nickname);

        if (findNickname != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }
    public void overlapTel(String tel){
        UserDto findTel = userMapper.overlapTel(tel);
        if(findTel != null){
            throw new IllegalStateException("이미 사용중인 전화번호입니다.");
        }
    }

    public void overlapEmail(String email) {
        UserDto findEmail = userMapper.overlapEmail(email);

        if (findEmail != null) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    public UserDto findId(Map map){
        UserDto findResult = userMapper.findId(map);
        return findResult;
    }

    public UserDto findPw(Map map){
        UserDto pwResult = userMapper.findPw(map);
        return pwResult;
    }

    public void updatePw(UserDto userDto, String newPassword) {

        String encodedPassword = passwordEncoder.encode(newPassword);
        userDto.setPassword(encodedPassword);
        userMapper.updatePw(userDto);
    }

    public int countUsers(Map map){
        return userMapper.countUsers(map);
    }

    public List<UserDetailDto> userList(Map map){
        List<UserDetailDto> list = userMapper.userList(map);
        return list;
    }

    public Long findUserSeq(String userId){
        return userMapper.findUserSeq(userId);
    }
    public UserDto findUser(Long userSeq){
        return userMapper.findUser(userSeq);
    }

    public void deleteUsers(List<Long> userSeqList) {
        for (Long userSeq : userSeqList) {
            userMapper.deleteUsers(userSeq);
        }
    }

    public int countUsersRequire(Map map){
        return userMapper.countUsersRequire(map);
    }

    public List<RequireDto> userRequireList(Map map){
        List<RequireDto> list = userMapper.userRequireList(map);
        return list;
    }
    public UserDetailDto getUserDetail(Long userSeq){
        return userMapper.getUserDetail(userSeq);
    }
    public void deleteUsersRequire(List<Long> requireSeqList) {
        for (Long requireSeq : requireSeqList) {
            userMapper.deleteUsersRequire(requireSeq);
        }
    }
}
