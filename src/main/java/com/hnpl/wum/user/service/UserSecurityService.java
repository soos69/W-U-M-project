package com.hnpl.wum.user.service;

import com.hnpl.wum.user.constant.Role;
import com.hnpl.wum.user.dto.UserDto;
import com.hnpl.wum.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = userMapper.login(username);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("ADMIN".equals(user.getRole().toString())) {
            authorities.add(new SimpleGrantedAuthority((Role.ADMIN.toString())));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));
        }

        return User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}
