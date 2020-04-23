package com.springboot.securing;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LoginService implements UserDetailsService {


    
    @Autowired
    private HomeMapper homeMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO Auto-generated method stub

        ArrayList<UserVO> userAuthes = homeMapper.findByUserId(id);
        if(userAuthes.size() == 0) {
			throw new UsernameNotFoundException("User "+id+" Not Found!");
		}
        return new UserPrincipalVO(userAuthes);
    }

}