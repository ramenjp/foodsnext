
package com.dev_training.config;

import com.dev_training.service.JpaUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        // 認証状態によらず許可する。
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/bootstrap/css/**", "/bootstrap/js/**", "/jquery/**", "/images/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 認証状態によらず許可する。
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/account/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login") // ログインページのパス
                .loginProcessingUrl("/login") // 認証処理を起動させるパス
                .failureUrl("/login/?error") // ログイン処理失敗時の遷移先
                .successForwardUrl("/top")
                .usernameParameter("login_id")// ユーザid
                .passwordParameter("login_password").permitAll(); // パスワード

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト処理を起動させるパス
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/") // ログアウト完了時のパス
                .invalidateHttpSession(true).permitAll();
    }

    @Configuration
    protected static class AuthenticationConfiguration
            extends GlobalAuthenticationConfigurerAdapter {

        final JpaUserDetailsServiceImpl userDetailsService;

        @Autowired
        public AuthenticationConfiguration(JpaUserDetailsServiceImpl userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            // 認証するユーザーを設定する
            auth.userDetailsService(userDetailsService)
                    // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}