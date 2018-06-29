package kr.demonic.config.security;

import kr.demonic.service.member.dto.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * [SpringSecurity] UserDetails 를 구현하는 구현체.
 */
public class UserDetailsImpl extends User {

    //
    public UserDetailsImpl(MemberDTO memberDTO){
        super(memberDTO.getUser_email()
                , memberDTO.getUser_password()
                , authorities(memberDTO));
    }

    // DB에 등록된 권한에 따라 유저권한 부여 user_role
    private static Collection<? extends GrantedAuthority> authorities(MemberDTO memberDTO) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(memberDTO.getUser_role().equals("1")){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}
