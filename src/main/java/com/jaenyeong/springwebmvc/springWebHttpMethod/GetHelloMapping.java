package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

// java doc과 같은 유형으로 문서화
@Documented
// 사용할 곳(컨텍스트) 지정
@Target(ElementType.METHOD)
// 어노테이션 유지 기간 옵션 런타임, 소스 등 지정
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET, value = "/helloCustomMapping")
public @interface GetHelloMapping {
}
