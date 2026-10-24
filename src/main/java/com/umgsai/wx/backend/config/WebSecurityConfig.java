package com.umgsai.wx.backend.config;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Shang
 * 2020/5/8 20:47
 * Spring Security 5 -> 7 迁移：
 * WebSecurityConfigurerAdapter 已移除，改用 SecurityFilterChain Bean；
 * authorizeRequests/antMatchers 改为 authorizeHttpRequests/requestMatchers；
 * @EnableGlobalMethodSecurity 改为 @EnableMethodSecurity
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                // charSequence 用户输入的密码， s 数据库中存储的password字段
                if (s == null) {
                    return false;
                }
                if (charSequence == null) {
                    return false;
                }
                String md5Hex = DigestUtil.md5Hex(charSequence.toString());
                return StringUtils.equals(md5Hex, s);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // 如果有允许匿名的url，填在下面
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                // 设置登陆页
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        // 设置登陆成功页
                        .defaultSuccessUrl("/index.html")
                        .failureUrl("/login.html?error=true")
                        .permitAll())
                .logout(logout -> logout.permitAll())
                // 关闭CSRF跨域
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
