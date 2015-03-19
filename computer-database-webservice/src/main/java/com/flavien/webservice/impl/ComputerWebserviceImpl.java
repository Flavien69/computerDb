package com.flavien.webservice.impl;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.flavien.dto.ComputerDTO;
import com.flavien.dto.mapper.ComputerMapperDTO;
import com.flavien.dto.mapper.PageMapperDTO;
import com.flavien.exception.PersistenceException;
import com.flavien.models.Computer;
import com.flavien.models.Page;
import com.flavien.models.Page.SortCriteria;
import com.flavien.models.Page.SortOrder;
import com.flavien.service.ComputerService;
import com.flavien.webservice.ComputerWebservice;

@Path("/computers")
public class ComputerWebserviceImpl implements ComputerWebservice {
	@Autowired
	private ComputerService computerService;

	@Autowired
	private ComputerMapperDTO computerMapperDTO;

	@Autowired
	private PageMapperDTO pageMapperDTO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.webservice.ComputerWebservice#findById(int)
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") int id) {
		ComputerDTO computerDTO = null;
		try {
			computerDTO = computerMapperDTO.toDto(computerService.getByID(id));
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		if (computerDTO == null){
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		return Response.ok(computerDTO).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.webservice.ComputerWebservice#findAll()
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		try {
			computerDTOs = computerMapperDTO.listToDto(computerService.getAll());
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok(computerDTOs).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.webservice.ComputerWebservice#findDashboard(int,
	 * java.lang.String, com.flavien.models.Page.SortCriteria,
	 * com.flavien.models.Page.SortOrder)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dashboard")
	public Response findDashboard(@DefaultValue("0") @QueryParam("index") int index,
			@DefaultValue("") @QueryParam("search") String search,
			@DefaultValue("ID") @QueryParam("sortCriteria") SortCriteria sortCriteria,
			@DefaultValue("ASC") @QueryParam("sortOrder") SortOrder sortOrder) {

		Page page = null;
		try {
			page = new Page(index, search, sortOrder, sortCriteria);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.ok(pageMapperDTO.toDto(computerService.getByPage(page))).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flavien.webservice.ComputerWebservice#addComputer(com.flavien.models
	 * .Computer)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComputer(Computer computer) {
		computerService.add(computer);
		return Response.noContent().build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flavien.webservice.ComputerWebservice#saveComputer(com.flavien.models
	 * .Computer)
	 */
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveComputer(Computer computer, @PathParam("id") int id) {
		if (computer.getId() != id){
			return Response.status(Response.Status.BAD_REQUEST).build(); 
		}
		try {
			computerService.add(computer);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.noContent().build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.webservice.ComputerWebservice#deleteComputer(int)
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteComputer(@PathParam("id") int id) {
		try {
			computerService.deleteById(id);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.noContent().build();
	}

}
