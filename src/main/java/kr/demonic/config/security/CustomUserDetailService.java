package kr.demonic.config.security;

import kr.demonic.service.member.dto.MemberDTO;
import kr.demonic.service.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 유저 정보를 조회하여 UserDetailsImpl 에 던져주는 클래스.
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
        System.out.println("====================== loadUserByUsername: " + user_email);
        MemberDTO memberDTO = memberMapper.chkLogin(user_email);

        // 조회가 되지않는 고객은 익셉션.
        if(memberDTO == null){
            throw new UsernameNotFoundException(user_email);
        }

        UserDetails userDetails = new UserDetailsImpl(memberDTO);
        return userDetails;
    }
}
