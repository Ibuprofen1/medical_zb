package com.zbdx.medicalzb.config.security;

import com.zbdx.medicalzb.filter.JwtFilter;
import com.zbdx.medicalzb.handler.security.CustomizeAuthenticationEntryPoint;
import com.zbdx.medicalzb.handler.security.MyAccessDeniedHandler;
import com.zbdx.medicalzb.handler.security.MyAuthenticationFailHandler;
import com.zbdx.medicalzb.handler.security.MyAuthenticationSuccessHandler;
import com.zbdx.medicalzb.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;//自定义登录业务类

    @Autowired
    private MyAuthenticationFailHandler authenticationFailHandler;//登录失败处理器

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;//登录成功处理器

    @Autowired
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;//未登录或登录失效处理器

    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;//无权限处理器

    @Autowired
    private JwtFilter jwtFilter;//jwt校验类

    @Bean   //将当前方法的返回值交给Spring容器管理
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailHandler)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and().authorizeRequests()
                .antMatchers("/api/login","/login","/v2/api-docs", "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**", "api/login", "//api/login","/image/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(configurationSource())
                .and()
                .csrf().disable();
    }


    /**
     * 解决前后端使用security跨域问题
     * @return
     */
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        //corsConfiguration.setAllowCredentials(true);//前后端分离项目需要携带cookie时，需要此句，但加上之后origin里就不能为"*"
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

}
