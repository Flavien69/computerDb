package com.flavien.webservice.impl;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.flavien.models.Company;
import com.flavien.service.CompanyService;
import com.flavien.webservice.CompanyWebservice;

@Path("/companies")
public class CompanyWebserviceImpl implements CompanyWebservice {

	@Autowired
	private CompanyService companyService;

	@GET
	@Produces("application/json")
	public List<Company> findAll() {		
		List<Company> companies = companyService.getAll();
		return companies;
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteCompany(@PathParam("id") int id) {		
		companyService.deleteByID(id);
	}

}
