package kr.demonic;

import kr.demonic.service.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)      // Controller, Component, ControllerAdvice 등이 작성된 코드를 인식할 수 있습니다.
@AutoConfigureMybatis
// @WebMvcTest 를 이용할 경우 @SpringBootTest 생략
// 컨트롤러 테스트 할 때에는 MockMvc 타입의 객체를 사용. @WebMvcTest와 같이 사용하면 별도의 생성 없이 주입(@Autowired)만으로 코드 작성 가능
public class SampleControllerTests {

    @Autowired
    MockMvc mock;

    @Test
    public void testGetStr() throws Exception{
        mock.perform(get("/sample/testGetStr"))
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void testGetTest() throws Exception{
        mock.perform(get("/sample/getTest"))
                .andExpect(content().json("{\"id\":null,\"user_email\":\"TEST\",\"user_name\":null,\"user_status\":null,\"auth_site\":null,\"user_password\":null,\"user_role\":null}"));
    }
}
