# 자바 스프링 강의 2주차
### 목차
- [프로젝트 생성](#프로젝트-생성)
- [Web 진입점 설정](#web-진입점-설정)
- [API 구현](#api-구현)
  * [빌더 패턴](#빌더-패턴)
  * [lombok](#lombok)
  * [동적 타입](#동적-타입)
  * [Rest API](#rest-api)
---
## 프로젝트 생성
* https://start.spring.io
* 프로젝트 생성
	* Project : Gradle
	* Language : Java
	* Spring Boot : 2.5.4
  * Group : com.artineer
  * Name : spring_lecture_w2
	* Packaging : Jar
	* Java : 11

* 웹 의존성 추가
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```
---
## Web 진입점 설정
* Controller를 통해 웹 진입점을 설정한다.
* Controller는 client 요청을 받는다.
```java
@RestController
public class PingController {
    @GetMapping("/")    //요청할 Path 지정
    public String ping(){
        return "pong";
    }
}
```
* `@RestController` Annotation 역할
  * PingController 객체를 Controller로써 쓰겠다  
  → 웹의 진입점으로 쓰겠다
  * RestAPI 형태로 구현
* `@GetMapping("/")` Path가 Root일 경우 `@GetMapping`으로 생략 가능
### API 테스트
* 크롬 브라우저로 접속
  http://localhost:8080/
* [Postman](https://www.postman.com/)
  * New → Collection → Add request → `localhost:8080/` GET요청
* .http 파일
  * Intellij IDE 제공해주는 HTTP 통신 클라이언트
  * Ultimate 버전만 제공
---
## API 구현
* API 에는 기본적으로 응답코드와, 응답설명이 필요
  * 응답코드 : 운영되고 있는 서비스에서 오류가 발생했을 때 어떤 이슈인지 바로 확인
  * 응답설명 : API 응답이 정상적으로 내려간 것인지, 아니라면 왜 오류가 났는지 등에 대한 설명
```java
@RestController
public class PingController {
    @GetMapping
    public Object ping(){
        return Map.of(
                "code", "0000",
                "desc", "정상입니다.",
                "data", "pong"
        );
    }
}
```
```json
//test 결과
{
    "desc": "정상입니다.",
    "data": "pong",
    "code": "0000"
}
```
* Map 구조로 응답객체를 만들게되면 다른 사람이 응답객체인것을 확인하기 어려울 수도 있기 때문에 클래스를 따로 만들어 명시해 준다.
```java
public class Response {
    private String code;
    private String desc;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Response(String code, String desc, String data){
        this.code = code;
        this.desc = desc;
        this.data = data;


    }
}
```
```java
@RestController
public class PingController {
    @GetMapping
    public Object ping(){
      return new Response("0000","정상입니다","pong");
    }
}
```
* 중복이슈 보완
  * API code, desc 이 둘간의 결합도을 높이기 위해 개선
```java
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiCode {
    /* COMMON */
    SUCESS("CM0000","정상입니다")
    ;

    private final String name;
    private final String desc;

    ApiCode(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
```
```java
public class Response {
    private ApiCode code;
    private String data;

    public Response(ApiCode code, String data){
        this.code = code;
        this.data = data;
    }

    public ApiCode getCode() {
        return code;
    }

    public void setCode(ApiCode code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
```
```java
@RestController
public class PingController {
    @GetMapping
    public Object ping(){
      return new Response(ApiCode.SUCESS,"pong");
    }
}
```
```json
//test 결과
{
    "code": {
        "name": "CM0000",
        "desc": "정상입니다"
    },
    "data": "pong"
}
```
---
## 빌더 패턴
* 불변성을 유지하고 생성자가 가지는 가독성 이슈를 해결하기 위해 사용
```java
public class Response {
    private ApiCode code;
    private String data;

    private Response() { }

    public ApiCode getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Response response;

        public Builder() {
            this.response = new Response();
        }

        public Builder code(ApiCode code) {
            this.response.code = code;
            return this;
        }

        public Builder data(String data) {
            this.response.data = data;
            return this;
        }

        public Response build() {
            return this.response;
        }
    }
}
```
```java
@RestController
public class PingController {
    @GetMapping
    public Object ping(){
      return Response.builder()
                .code(ApiCode.SUCESS)
                .data("pong")
                .build();
    }
}
```
## lombok
* 보일러플레이트 코드들이 많아지는 것을 방지하기위해 **lombok**을 활용
  * *보일러플레이트 : 꼭 필요한 간단한 기능이지만 반복적인 코드를 필요로 하고, 이것이 중복되어 많은 양의 코드를 양산하는 것*
```groovy
dependencies {
  // ...
	compileOnly 'org.projectlombok:lombok:1.18.20'
	annotationProcessor 'org.projectlombok:lombok:1.18.20'
}
```
```java
@Getter
@Builder
public class Response {
    private ApiCode code;
    private String data;
}
```
```java
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiCode {
    /* COMMON */
    SUCESS("CM0000","정상입니다")
    ;

    private final String name;
    private final String desc;

    ApiCode(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
```
---
## 동적 타입
* Response 객체의 구조를 보면 data 는 항상 String 타입을 받아서 String 구조만 가져야 한다.
* 제네릭 문법을 활용하여 이슈 개선 가능
```java
@Getter
@Builder
public  class Response<T>{
    private ApiCode code;
    private T data;
}
```
```java
@RestController
public class PingController {
    @GetMapping
    public Response<String> ping() {
        return Response.<String>builder()
                .code(ApiCode.SUCESS)
                .data("pong")
                .build();
    }
}
```
---
## Rest API
* HTTP METHOD 로 행위를 표현

	|어노테이션|역할|
	|---|---|
	|`@GetMapping`|조회 (GET)|
	|`@PostMapping`|생성 (POST)|
	|`@PutMapping`|변경 (PUT)|
  |`@DeleteMapping`| 제거 (DELETE) |
* HTTP URL 로 리소스를 표현
  * e.g. 13번 유저 `("/users/13")`
* REST 구조의 장점
  * Resource / domain 중심으로 표현하는 API
  * 다양한 API 사용 가능
  * But, 성능이 떨어질 수 있음
### Article 객체 생성
```java
@Getter
@Builder
public class Article {
    Long id;
    String title;
    String content;
}
```
### ArticleService 생성
* 실제 비지니스 로직을 처리하기 위한 영역
* 동일한 Service 기능을 다양한 controller 에서 접근이 가능하도록 구분
```java
@Service
public class ArticleService {
    private Long id = 0L;
    final List<Article> database = new ArrayList<>();

    private Long getId() {
        return ++id;
    }

    //save 함수 생성
    public Long save(Article request) {
        Article domain = Article.builder()
                .id(getId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        database.add(domain);
        return domain.getId();
    }
}
```
### ArticleController 생성
* ArticleService 생성자 주입
```java
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
}

```
*  *lombok*을 활용해서 개선
```java
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;
}

```
### Article DTO 객체 생성
* Domain 영역의 객체와 Presentation 영역의 객체가 구분되어야 한다
* 구분하기위해 DTO가 사용된다
```java
public class ArticleDto {
    @Getter
    public static class Request {
        String title;
        String content;
    }
}
```
### ArticleController Post구현
```java
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public Response<Long> post(@RequestBody ArticleDto.ReqPost request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Long id = articleService.save(article);

        return Response.<Long>builder().code(ApiCode.SUCCESS).data(id).build();
    }
}

```
### ArticleService findById 함수 구현
```java
public class ArticleService {
    //...

  public Article findById(Long id) {
    return database.stream().filter(article -> article.getId().equals(id)).findFirst().get();
  }   
}

```
### Res 객체 추가
```java
public class ArticleDto {
    //...

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private String id;
        private String title;
        private String content;
    }
}
```
### ArticleController Get 구현
```java
public class ArticleController {
    // ...
  @GetMapping("/{id}")
  public Response<ArticleDto.Res> get(@PathVariable Long id) {
    Article article = articleService.findById(id);

    ArticleDto.Res response = ArticleDto.Res.builder()
            .id(String.valueOf(article.getId()))
            .title(article.getTitle())
            .content(article.getContent())
            .build();

    return Response.<ArticleDto.Res>builder().code(ApiCode.SUCCESS).data(response).build();
  }   
}

```
---
### 참고
https://github.com/kidongYun/spring_lecture_week2
