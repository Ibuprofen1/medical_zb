package com.zbdx.medicalzb.mapper;


import com.zbdx.medicalzb.model.AccountModel;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    AccountModel login(String uname, String encrypt);

    AccountModel securityLogin(String uname);
}
