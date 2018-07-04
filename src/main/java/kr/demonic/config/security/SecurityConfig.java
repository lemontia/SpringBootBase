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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
// 아래 설정이 빠지면 admin에 접속할때 Forbidden 에러가 발생
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 리소스들은 인증을 풀어줘줌.
        web.ignoring().antMatchers("/css/**", "/script/**", "/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 아래 순서가 중요함.
        // 실제로 스프링 문서를 보면 permitAll로 첫번째 허가를 낸 경우 authenticated 로 제한을 걸어도 걸리지 않음.
        http
                .authorizeRequests()
//                .antMatchers("/testNoAuth").authenticated()
                .antMatchers("/admin").authenticated()
                // ADMIN 권한이 있는것만 가능.
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()        // form 태그 기반 로그인 지원 설정.
                    .loginPage("/login")
                    .loginProcessingUrl("/loginAction")
                    .defaultSuccessUrl("/")
                    .successHandler(new LoginSuccessHandler("/"))
                    .failureUrl("/loginFail")
                    // 로그인 ID 비교할 파라미터.
                    // 만약 아랫것을 바꾼다면 CustomUserDetailService.loadUserByUsername 의 파라미터도 변경되어야 함.
                    .usernameParameter("user_email")
                    .passwordParameter("user_password")
                .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)        // HttpSession의 정보를 무효화
                    .deleteCookies("JSESSIONID")
                    .permitAll()

                // Remember Me 추가
                // 아래의 코드를 추가하면 'configureGlobal' 을 사용하지 않아도 된다.
                //      만약 passwordEncoder 를 사용하는 경우는 사용해야 한다.
                // 아래도 체인 순서는 중요하다.
                .and()
                .rememberMe()
                    .key("springBootBase")           // 쿠키값으로 암호화된 값을 전달하고, 로그인 상태를 기억합니다.
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(2419200)          // 쿠키유지 시간(설정되어있지 않으면 기본 2주)
                    .tokenRepository(getJDBCRepository())       // DB에 토큰 저장. DB 테이블 생성은 아래주석 참조.
        ;


        // 아래의 코드를 추가하면 'configureGlobal' 을 사용하지 않아도 된다.
        //      만약 passwordEncoder 를 사용하는 경우는 사용해야 한다.
        // 아래도 체인 순서는 중요하다.
        http
                .rememberMe().key("key")           // 쿠키값으로 암호화된 값을 전달하고, 로그인 상태를 기억합니다.
                .tokenValiditySeconds(2419200)          // 쿠키유지 시간(설정되어있지 않으면 기본 2주)
                .tokenRepository(getJDBCRepository())       // DB에 토큰 저장. DB 테이블 생성은 아래주석 참조.
        ;
    }
    /*
    * 토큰을 DB에 저장하기 위해 사용되는 메서드.(디비에 토큰을 저장하지 않을 거라면 사용하지 않아도 됨)
    *
    * DB에 다음 스키마 추가
                   create table persistent_logins (username varchar(64) not null,
                                series varchar(64) primary key,
                                token varchar(64) not null,
                                last_used timestamp not null);
    */
    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }


    /**
     * 스프링 시큐리티 인증방법 설명
     *  1) 모든 인증은 인증매니저(AuthenticationManager)를 통해서 이루어진다.
     *      인증 매니저를 생성하기 위해 인증매니저 빌더(AuthenticationManagerBuilder)가 사용된다.
     *  2) 인증 매니저를 이용해 인증(Authentication) 작업이 수행된다.
     *  3) 인증 매니저들은 인증/인가를 위해 UserDetailsService를 통해 필요한 정보를 가져온다.
     *      (CustomUserDetailService 에서 구현되어 있음)
     *  4) UserDetailsService가 구현된 클래스에서 조회한 유저정보를 UserDetails 을 리턴한다.
     *      (UserDetails 는 사용자 정보 + 권한 정보를 묶어 관리한다.)
     */


    // 패스워드 암호화 구현체
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 인증매니저를 생성하는 빌더
    //   @예외: remember-me 를 사용할 경우 아래 메서드를 쓰지 않지만 passwordEncoder를 사용하기 위해 아래 메서드를 살려둔다.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
