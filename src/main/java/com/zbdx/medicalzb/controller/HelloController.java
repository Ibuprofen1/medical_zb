package com.zbdx.medicalzb.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @ApiResponses(
            @ApiResponse(code = 200,message = "返回用户信息")
    )
    public String hello(@ApiParam("用户的主键id") Integer id){
        return "hello,medical";
    }
}
