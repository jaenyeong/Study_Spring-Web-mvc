package com.jaenyeong.springwebmvc.springWebHttpMethod;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class Event {
	// 좋은 이름 아님
	// 이벤트 생성 중 검증, 이벤트 수정 중 검증처럼 설계하는 것이 나음
//	public interface ValidateLimit{}
//	public interface ValidateName{}

	private Integer id;
//	@NotBlank(groups = ValidateName.class)
	@NotBlank
	private String name;
//	@Min(value = 0, groups = ValidateLimit.class)
	@Min(0)
	private Integer limit;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;

	public Integer getId() {
		return id;
	}

	public Event setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Event setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getLimit() {
		return limit;
	}

	public Event setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Event setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}
}
