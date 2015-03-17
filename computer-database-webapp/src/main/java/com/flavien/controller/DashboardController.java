package com.flavien.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flavien.models.Page;
import com.flavien.service.CompanyService;
import com.flavien.service.ComputerService;

/**
 * Controller to get a page of computers.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
		
	private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	
	/**
	 * Get the page of computers
	 * 
	 * @param page
	 * @param map
	 * @return the dashboard page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Page page, ModelMap map) {
		page = this.computerService.getByPage(page);
		map.addAttribute("page", page);
		logger.info("return the page to the dashboard");

		return "dashboard";
	}
}