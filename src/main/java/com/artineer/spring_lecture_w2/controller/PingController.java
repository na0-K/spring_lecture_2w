package com.artineer.spring_lecture_w2.controller;

import com.artineer.spring_lecture_w2.dto.Response;
import com.artineer.spring_lecture_w2.vo.ApiCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingController {
    @GetMapping    //요청할 Path 지정 가능
    public Response<String> ping() {    //Type 지정 가능
        return Response.<String>builder()
                .code(ApiCode.SUCESS)
                .data("pong")
                .build();
    }
//    public String ping(){
//        return "pong";
//    }
//    public Object ping(){
////        //응답객체라고 정의를 하고 익명객체사용(Map요소)
////        return Map.of(
////                "code", "0000",
////                "desc", "정상입니다.",
////                "data", "pong"
////        );
//
////        return new Response("0000","정상입니다","pong");
//
////        return new Response(ApiCode.SUCESS,"pong");
//
//        return Response.builder()
//                .code(ApiCode.SUCESS)
//                .data("pong")
//                .build();
//    }
}
