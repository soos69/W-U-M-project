package com.hnpl.wum;

import com.hnpl.wum.user.constant.Role;
import com.hnpl.wum.user.dto.UserDto;
import com.hnpl.wum.user.service.UserSecurityService;
import com.hnpl.wum.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 테스트")
    public void createMemberTest() {
        UserDto member = new UserDto();
        member.setId("user1");
        member.setPassword("11111111");
        member.setNickname("유저");
        member.setName("김유저");
        member.setBirth(LocalDate.parse("1995-10-01"));
        member.setTel("010-0101-2222");
        member.setEmail("user1@naver.com");
        member.setRole(Role.USER);

        System.out.println(member);
        int result = userService.insertMember(member);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("중복 테스트")
    public void overlapTest() {
        UserDto userDto = new UserDto();

        userDto.setId("user12");
        userDto.setNickname("디비");
        userDto.setEmail("dd2@naver.com");

        Throwable th = null;

        try{
            userService.insertMember(userDto);
        } catch (IllegalStateException e) {
            th = e;
        }
        System.out.println(th.getMessage());
        assertThat(th.getMessage()).isIn("중복된 아이디", "이미 사용중인 닉네임" ,"이미 가입한 회원");

        Throwable th2 = assertThrows(IllegalStateException.class,() -> userService.insertMember(userDto));
        System.out.println(th2.getMessage());
        assertThat(th2.getMessage()).isIn("중복된 아이디", "이미 사용중인 닉네임" , "이미 가입한 회원");
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception{
        String id = "user1";
        String password = "11111111";

        mockMvc.perform(formLogin().userParameter("id")
                .loginProcessingUrl("/user/login")
                .user(id).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/logout"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}
