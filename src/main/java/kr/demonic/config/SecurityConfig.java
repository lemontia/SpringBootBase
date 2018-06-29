package kr.demonic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 리소스들은 인증을 풀어줘줌.
        web.ignoring().antMatchers("/css/**", "/script/**", "/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 아래 순서가 중요함.
        // 실제로 스프링 문서를 보면 permitAll로 첫번째 허가를 낸 경우 authenticated 로 제한을 걸어도 걸리지 않음.
        http.authorizeRequests()
                .antMatchers("/testNoAuth").authenticated()
                .antMatchers("/**").permitAll();
    }
}
