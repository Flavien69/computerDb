package com.flavien.webservice.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.flavien.dto.ComputerDTO;
import com.flavien.dto.mapper.ComputerMapperDTO;
import com.flavien.models.Computer;
import com.flavien.models.Page;
import com.flavien.service.ComputerService;
import com.flavien.webservice.ComputerWebservice;

@Path("/computers")
public class ComputerWebserviceImpl implements ComputerWebservice{
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapperDTO computerMapperDTO;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ComputerDTO findById(@PathParam("id") int id) {		
		return computerMapperDTO.toDto(computerService.getByID(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page findAll(Page page) {	
		page = this.computerService.getByPage(page);
		return page;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void editComputer(Computer computer) {		
		computerService.update(computer);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteComputer(Computer computer) {		
		computerService.deleteById(computer.getId());
	}
	
}
