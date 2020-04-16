package com.cherrypicks.myproject.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MonitorController extends BaseController {
	
	@GetMapping(value = "/apiMonitor")
	public String apiMonitor() {
		return "1";
	}

	@GetMapping(value = "/apiMonitor1")
	public String apiMonitor1() {
		return "1";
	}

}
