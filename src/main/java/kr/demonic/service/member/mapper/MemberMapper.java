package kr.demonic.service.member.mapper;

import kr.demonic.config.security.UserCustom;
import kr.demonic.service.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Member;
import java.util.Map;

@Mapper
public interface MemberMapper {
    MemberDTO chkLogin(String user_email);

    int insertMember(MemberDTO memberDTO);

    // 로그아웃-토큰삭제
    int deletePersistentLogins(UserCustom userCustom);
}
