package com.hnpl.wum.user.mapper;

import com.hnpl.wum.require.dto.RequireDto;
import com.hnpl.wum.user.dto.UserDetailDto;
import com.hnpl.wum.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    UserDto login(String id);

    int insertMember(UserDto userDto);

    UserDto overlapId(String id);

    UserDto overlapNickname(String nickname);

    UserDto overlapEmail(String email);

    UserDto overlapTel(String tel);

    UserDto findId(Map map);

    UserDto findPw(Map map);

    void updatePw(UserDto userDto);

    List<UserDetailDto> userList(Map map);

    int countUsers(Map map);

    Long findUserSeq(String userId);

    void deleteUsers(Long userSeq);

    UserDto findUser(Long userSeq);

    int countUsersRequire(Map map);

    List<RequireDto> userRequireList(Map map);

    UserDetailDto getUserDetail(Long userSeq);

    void deleteUsersRequire(Long requireSeq);

}
