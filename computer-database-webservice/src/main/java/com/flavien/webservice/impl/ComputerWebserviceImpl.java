package com.flavien.webservice.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.flavien.dto.ComputerDTO;
import com.flavien.dto.PageDTO;
import com.flavien.dto.mapper.ComputerMapperDTO;
import com.flavien.dto.mapper.PageMapperDTO;
import com.flavien.models.Computer;
import com.flavien.models.Page;
import com.flavien.models.Page.SortCriteria;
import com.flavien.models.Page.SortOrder;
import com.flavien.service.ComputerService;
import com.flavien.webservice.ComputerWebservice;

@Path("/computers")
public class ComputerWebserviceImpl implements ComputerWebservice{
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapperDTO computerMapperDTO;
	
	@Autowired
	private PageMapperDTO pageMapperDTO;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ComputerDTO findById(@PathParam("id") int id) {		
		return computerMapperDTO.toDto(computerService.getByID(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComputerDTO> findAll() {		
		return computerMapperDTO.listToDto(computerService.getAll());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dashboard")
	public PageDTO findDashboard(@DefaultValue("0") @QueryParam("index") int index,
			@DefaultValue("") @QueryParam("search") String search,
			@DefaultValue("ID") @QueryParam("sortCriteria") SortCriteria sortCriteria,
			@DefaultValue("ASC") @QueryParam("sortOrder") SortOrder sortOrder) {	
		Page page = new Page(index,search,sortOrder,sortCriteria);
		return pageMapperDTO.toDto(computerService.getByPage(page));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComputer(Computer computer) {		
		computerService.add(computer);
	}
	
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveComputer(Computer computer) {		
		computerService.add(computer);
	}
	
	
	@DELETE
	@Path("/{id}")
	public void deleteComputer(@PathParam("id") int id) {		
		computerService.deleteById(id);
	}
	
}
