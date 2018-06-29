package kr.demonic.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// 아래 설정이 빠지면 admin에 접속할때 Forbidden 에러가 발생
@EnableWebSecurity
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
//                .antMatchers("/testNoAuth").authenticated()
                .antMatchers("/admin").authenticated()
                // ADMIN 권한이 있는것만 가능.
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginAction")
                .defaultSuccessUrl("/")
                .failureUrl("/loginFail")
                // 로그인 ID 비교할 파라미터.
                // 만약 아랫것을 바꾼다면 CustomUserDetailService.loadUserByUsername 의 파라미터도 변경되어야 함.
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // 패스워드 암호화 구현체
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(auth);
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
