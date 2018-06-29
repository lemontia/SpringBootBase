package kr.demonic.service;


import kr.demonic.service.member.dto.MemberDTO;
import kr.demonic.service.member.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @RequestMapping(path = {"/member", "/login"})
    public String test(Model model){
        return "member";
    }

    @RequestMapping("/admin")
    public String admin(@AuthenticationPrincipal User user) {
        System.out.println("================= " + user);
        return "admin";
    }

    @RequestMapping("/admin/test")
    public String admin_test(@AuthenticationPrincipal User user){
        System.out.println("================= " + user);
        return "admin_test";
    }

    @RequestMapping("/loginFail")
    public String loginFail(){
        return "loginFail";
    }

    @RequestMapping(path="/loginChk", produces = "text/html")
    public String loginChk(){
        return "this is loginChk";
    }

    @RequestMapping(path="/saveMember", method = RequestMethod.POST)
    @ResponseBody
    public MemberDTO saveMember(@ModelAttribute MemberDTO memberDTO){
        Map map = new HashMap();

        int result = memberMapper.insertMember(memberDTO);
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
