package com.jaenyeong.springwebmvc.basic;

import java.time.LocalDateTime;

// 롬복 어노테이션 사용시 컴파일 타임에(build 디렉토리에 있는 class파일) 추가됨
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Event {
	private String name;
	private int limitOfEnrollment;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
}
