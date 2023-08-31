package com.zbdx.medicalzb.mapper;

import com.zbdx.medicalzb.model.PermissionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    List<PermissionModel> getPermission(String roleName);
}
