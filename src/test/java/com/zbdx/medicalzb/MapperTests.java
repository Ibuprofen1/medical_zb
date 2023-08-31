package com.zbdx.medicalzb;

import com.zbdx.medicalzb.mapper.PermissionMapper;
import com.zbdx.medicalzb.model.PermissionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MedicalZbApplication.class)
public class MapperTests {
    @Autowired
    PermissionMapper permissionMapper;
    @Test
    public void testPermission(){
        List<PermissionModel> list = permissionMapper.getPermission("ROLE_1");
        list.stream().forEach(u-> System.out.println(u));
    }
}
