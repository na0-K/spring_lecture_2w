package com.artineer.spring_lecture_w2.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)    //객체 스타일로 바꿀때
public enum ApiCode {
    /* COMMON */  //CM, 공통적으로 사용하는 코드
    SUCESS("CM0000","정상입니다")
    ;

    private final String name;
    private final String desc;

    ApiCode(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

//    //getter 생성
//    public String getName() {
//        return name;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
}
