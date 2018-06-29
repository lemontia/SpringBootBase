package kr.demonic.service.member.mapper;

import kr.demonic.service.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    int insertMember(Map map);

    int insertMember(MemberDTO memberDTO);
}
