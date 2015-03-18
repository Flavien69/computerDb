package com.flavien.webservice;

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

import com.flavien.models.Computer;
import com.flavien.models.Page.SortCriteria;
import com.flavien.models.Page.SortOrder;

/**
 * End point of the computers resource.
 */
@Path("/computers")
public interface ComputerWebservice {

	
	/**	
	 * Return a computerDto in Json format.
	 * Get http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * @param id
	 * @return Response
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") int id);
	
	/**	
	 * Return a list computerDto in Json format.
	 * Get http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers
	 * @return Response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll();
	
	
	/**
	 * Return a PageDTO in Json format.
	 * Get http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers/dashboard
	 * 
	 * @param index
	 * @param search
	 * @param sortCriteria
	 * @param sortOrder
	 * @return Response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dashboard")
	public Response findDashboard(@DefaultValue("0") @QueryParam("index") int index,
			@DefaultValue("") @QueryParam("search") String search,
			@DefaultValue("ID") @QueryParam("sortCriteria") SortCriteria sortCriteria,
			@DefaultValue("ASC") @QueryParam("sortOrder") SortOrder sortOrder);
	
	
	
	/**
	 * Create a new computer.
	 * Post http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers
	 * @param computer
	 * @return Response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComputer(Computer computer);
	
	/**
	 * edit a computer.
	 * Post http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * @param computer
	 * @return Response
	 */
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveComputer(Computer computer);
	
	/**
	 * Delete a computer.
	 * Delete http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * @param computer
	 * @return Response
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteComputer(@PathParam("id") int id);
}
