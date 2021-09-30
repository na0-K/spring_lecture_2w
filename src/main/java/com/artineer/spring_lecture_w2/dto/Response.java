package com.artineer.spring_lecture_w2.dto;

import com.artineer.spring_lecture_w2.vo.ApiCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public  class Response<T>{
    private ApiCode code;
    private T data;
}
//public class Response {
////    private String code;
////    private String desc;
//    private ApiCode code;
//    private String data;

//    public Response(ApiCode code, String data){
//        this.code = code;
//        this.data = data;
//    }
//
//    public Response() {} //line 78
//
//    public Response(String code, String desc, String data){
//        this.code = code;
//        this.desc = desc;
//        this.data = data;
//    }

//    //getter&setter
//    public ApiCode getCode() {
//        return code;
//    }
//
//    public void setCode(ApiCode code) {
//        this.code = code;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }

//    ////getter&setter
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }

//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//        private final Response response;
//
//        public Builder() {
//            this.response = new Response();
//        }
//
//        public Builder code(ApiCode code) {
//            this.response.code = code;
//            return this;
//        }
//
//        public Builder data(String data) {
//            this.response.data = data;
//            return this;
//        }
//
//        public Response build() {
//            return this.response;
//        }
//    }
//}
