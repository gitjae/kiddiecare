package com.spring.kiddiecare.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import org.springframework.web.bind.annotation.*;

//@ApiResponses({
//        @ApiResponse(code = 200, message = "Success"),
//        @ApiResponse(code = 400, message = "Bad Request"),
//        @ApiResponse(code = 500, message = "Internal Server Error")
//})

@Api(tags="API Controller test")
@RestController
@RequestMapping("/v1")
public class ApiController {

    @GetMapping("/api/hello1")
    public String hello1() {
        return "hello Swagger!";
    }

    @GetMapping("/api/hello2")
    public String hello2(@RequestParam String param){
        return param;
    }

    @ApiOperation(value="덧셈", notes = "num1 + num2")
    @GetMapping(value="/add")
    public Integer addition(Integer num1, Integer num2) {
        return num1 + num2;
    }

    @ApiOperation(value="뺄셈", notes = "빼기: x - y값 입력")
    @PostMapping("/minus/{x}")
    public int minus(@PathVariable int x, @RequestParam int y) {
        return x - y;
    }


}
