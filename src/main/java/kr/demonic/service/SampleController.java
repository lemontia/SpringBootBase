package kr.demonic.service;

import kr.demonic.service.member.dto.MemberDTO;
import kr.demonic.service.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 샘플 컨트롤러
 */
@RestController
public class SampleController {

    @Autowired
    private MemberMapper memberMapper;

    // String 형 테스트
    @GetMapping(path="/sample/testGetStr")
    public String testGetStr(){
        return "Hello World";
    }

    // Mybatis 로 데이터를 불러와 Json 형 테스트
    @RequestMapping(path="/sample/getTest", method = RequestMethod.GET)
    public MemberDTO getTest(@RequestParam Map param, Model model) throws Exception{
        param.put("CODE", "0000");
        param.put("MESSAGE", "TEST");
        MemberDTO memberDTO = MemberDTO.builder().user_email("TEST").build();
        return memberDTO;
    }
}
