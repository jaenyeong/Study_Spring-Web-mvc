package com.jaenyeong.springwebmvc.springJSP;

import java.time.LocalDateTime;

public class Event {
	private String name;
	private LocalDateTime startDate;

	public String getName() {
		return name;
	}

	public Event setName(String name) {
		this.name = name;
		return this;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public Event setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
		return this;
	}
}
