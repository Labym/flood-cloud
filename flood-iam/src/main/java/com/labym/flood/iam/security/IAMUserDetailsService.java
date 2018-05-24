package com.labym.flood.iam.security;

import com.google.common.collect.Lists;
import com.labym.flood.iam.model.dto.IAMUserDetails;
import com.labym.flood.iam.model.po.UserPO;
import com.labym.flood.iam.repository.UserRepository;
import com.labym.flood.iam.util.UserUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class IAMUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public IAMUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByLogin(username)
                .map(this::toUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("can't find user by username("+username+")"));
    }

    private IAMUserDetails toUserDetails(UserPO userPO){
        GrantedAuthority authority=new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        };
        return IAMUserDetails
                .builder()
                .username(userPO.getLogin())
                .password(userPO.getPassword())
                .accountNonExpired(UserUtils.accountNonExpired(userPO.getExpireAt()))
                .credentialsNonExpired(true)
                .enabled(userPO.getActivated())
                .authorities(Lists.newArrayList(authority))
                .build();
    }
}
