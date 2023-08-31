package com.zbdx.medicalzb.filter;


import com.zbdx.medicalzb.model.AccountModel;
import com.zbdx.medicalzb.service.security.UserDetailsServiceImpl;
import com.zbdx.medicalzb.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import  org.apache.log4j.Logger;

/**
 * @description: JWT校验类
 **/
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static Logger logger = Logger.getLogger(JwtFilter.class);

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        if (StringUtils.hasLength(token)) {
            String uname = null;
            try {
                uname = (String) JwtUtils.getClaims(token).get("uname");
                System.out.println(uname);
            } catch (ExpiredJwtException e) {
                logger.info("失效身份");
            }
            if (uname != null) {
                String role = (String) JwtUtils.getClaims(token).get("role");
                UserDetails userDetails = userDetailsService.loadUserByUsername(uname);
                AccountModel model = (AccountModel) userDetails;
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
                UsernamePasswordAuthenticationToken authenticationFilter = new UsernamePasswordAuthenticationToken(model, null, grantedAuthorities);
                authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
