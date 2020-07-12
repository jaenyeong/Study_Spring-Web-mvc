package com.jaenyeong.springwebmvc.springJSP;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

//@Controller
public class EventController {

//	@GetMapping("/events")
	public String getEvents(Model model) {
		Event event1 = new Event();
		event1.setName("스프링 웹 MVC 스터디 1");
		event1.setStartDate(LocalDateTime.of(2020, 7, 1, 11, 30));

		Event event2 = new Event();
		event2.setName("스프링 웹 MVC 스터디 2");
		event2.setStartDate(LocalDateTime.of(2020, 7, 1, 12, 30));

		List<Event> events = List.of(event1, event2);

		model.addAttribute("events", events);
		model.addAttribute("message", "Message Test");

		// JSP
		return "/events/list";
	}
}
