package kr.demonic.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping(path={"/", "/main"})
    public String main(){
        return "main";
    }

    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("paramTest", "파라미터 테스트");
        return "test";
    }

    @RequestMapping("/testNoAuth")
    public String testNoAuth(Model model){
        return "testNoAuth";
    }
}
