package com.zbdx.medicalzb.service;

import com.zbdx.medicalzb.domain.Permission;
import com.zbdx.medicalzb.mapper.PermissionMapper;
import com.zbdx.medicalzb.model.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public List<PermissionModel> getAllPermission(String roleName) {
        String finalRoleName = "ROLE_"+roleName;
        List<PermissionModel> allPermission = permissionMapper.getPermission(finalRoleName.trim());
        PermissionModel fatherPermisson = new PermissionModel();
        List<PermissionModel> finalPermisson = new ArrayList<>();
        for (PermissionModel per:allPermission) {
            if (per.getPid() == 0) {
//                BeanUtils.copyProperties(per, fatherPermisson);
//                finalPermisson.add(selectChildren(fatherPermisson, allPermission));
                finalPermisson.add(selectChildren(per, allPermission,finalRoleName));
            }
        }
        return finalPermisson;
    }

    private PermissionModel selectChildren(PermissionModel father, List<PermissionModel> allPermission,String finalRoleName) {
        List<Permission> list = new ArrayList<>();
        allPermission.forEach(item -> {
            if (!finalRoleName.equals("ROLE_1")) {
                String title = item.getMeta().getTitle().replace("管理", "查询");
                item.getMeta().setTitle(title);
            }
            if (father.getId() == item.getPid()) {
                father.setChildren(list);
                father.getChildren().add(selectChildren(item, allPermission,finalRoleName));
            }
        });
        return father;
    }
}
