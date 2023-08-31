package com.zbdx.medicalzb.service.security;

import com.zbdx.medicalzb.mapper.AccountMapper;
import com.zbdx.medicalzb.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AccountModel accountModel = accountMapper.securityLogin(s);
        if(accountModel == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String utype = accountModel.getUtype();
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(utype);
        System.out.println(accountModel.getRealname());
        return new AccountModel(accountModel.getId(),accountModel.getUname(),accountModel.getRealname(), utype, accountModel.getPwd(), authorities);
    }
}
