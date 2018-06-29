package kr.demonic.service;


import kr.demonic.service.member.dto.MemberDTO;
import kr.demonic.service.member.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 관련 컨트롤러.
 */
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMapper memberMapper;

    @RequestMapping("/member")
    public String test(Model model){

        return "member";
    }

    @RequestMapping(path="/loginChk", produces = "text/html")
    public String loginChk(){
        return "this is loginChk";
    }

    @RequestMapping(path="/saveMember", method = RequestMethod.POST)
    @ResponseBody
    public MemberDTO saveMember(@ModelAttribute MemberDTO memberDTO){
        Map map = new HashMap();

        if (memberDTO.getAuth_site() == null || memberDTO.getAuth_site().equals("")) {
            String auth_site = "KAKAO";
            memberDTO.setAuth_site(auth_site);
        }
        int result = memberMapper.insertMember(memberDTO);

        System.out.println("=================== " + result);
        return memberDTO;
    }

    // 테스트 GET 테스트
    @RequestMapping(path="/getTest", method = RequestMethod.GET)
    @ResponseBody
    public MemberDTO getTest(@RequestParam Map param, Model model) throws Exception{
        param.put("CODE", "0000");
        param.put("MESSAGE", "TEST");
        MemberDTO memberDTO = MemberDTO.builder().user_email("TEST").build();
        return memberDTO;
    }
}
