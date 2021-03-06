package kr.demonic.service;


import kr.demonic.config.security.UserCustom;
import kr.demonic.service.member.dto.MemberDTO;
import kr.demonic.service.member.mapper.MemberMapper;
import kr.demonic.util.error.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Member;
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


    @GetMapping(path="/testGetStr")
    @ResponseBody
    public String testGetStr(){
        return "Hello World";
    }

    @RequestMapping(path = {"/member", "/login"})
    public String login(){
        // 로그인 인증하기 전 페이지 기억
        // 장점. 로그인을 하고나서 전 페이지로 이동이 수월함
        // 단점. 로그인이 필요한 서비스로 이동하다가 걸린경우,
        //      로그인 이후에 해당페이지로 이동하는게 아니라 호출한 페이지로 이동
        //      예) /main 에서 /admin 으로 접근하다가 로그인이 걸린건데, 로그인 성공이후 호출했던
        //          /main 페이지로 이동하게 됨
//        String referrer = request.getHeader("Referer");
//        request.getSession().setAttribute("prevPage", referrer);
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(@AuthenticationPrincipal UserCustom user) {
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


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response
            , @AuthenticationPrincipal UserCustom userCustom){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // (remember-me 토큰 삭제) persistent_logins 테이블에 저장되어있는 Token 삭제.
        memberMapper.deletePersistentLogins(userCustom);
        return "redirect:/main";
    }

    @RequestMapping(path="/loginChk", produces = "text/html")
    public String loginChk(){
        return "this is loginChk";
    }

    @RequestMapping(path="/saveMember", method = RequestMethod.POST)
    @ResponseBody
    public MemberDTO saveMember(@ModelAttribute MemberDTO memberDTO){
        PasswordEncoder pe = new BCryptPasswordEncoder();
        memberDTO.setUser_password(pe.encode(memberDTO.getUser_password()));

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

    // 트랜잭션 테스트
    @PostMapping(path="/transactionTest")
    @ResponseBody
    @Transactional(rollbackFor = {Exception.class})
    public MemberDTO transactionTest(@ModelAttribute MemberDTO memberDTO) throws CustomException {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        memberDTO.setUser_password(pe.encode(memberDTO.getUser_password()));

        int result = memberMapper.insertMember(memberDTO);

        if (1 != 0){
            throw new CustomException("트랜잭션 테스트 중");
        }

        return memberDTO;
    }
}
