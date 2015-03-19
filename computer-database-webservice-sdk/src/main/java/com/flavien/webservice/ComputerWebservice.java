package com.flavien.webservice;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.flavien.models.Computer;
import com.flavien.models.Page.SortCriteria;
import com.flavien.models.Page.SortOrder;

/**
 * End point of the computers resource.
 */
public interface ComputerWebservice {

	/**
	 * Return a computerDto in Json format. Get http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * 
	 * @param id
	 * @return Response
	 */
	public Response findById(@PathParam("id") int id);

	/**
	 * Return a list computerDto in Json format. Get http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database-webservice/api/computers
	 * 
	 * @return Response
	 */
	public Response findAll();

	/**
	 * Return a PageDTO in Json format. Get http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database
	 * -webservice/api/computers/dashboard
	 * 
	 * @param index
	 * @param search
	 * @param sortCriteria
	 * @param sortOrder
	 * @return Response
	 */
	public Response findDashboard(@DefaultValue("0") @QueryParam("index") int index,
			@DefaultValue("") @QueryParam("search") String search,
			@DefaultValue("ID") @QueryParam("sortCriteria") SortCriteria sortCriteria,
			@DefaultValue("ASC") @QueryParam("sortOrder") SortOrder sortOrder);

	/**
	 * Create a new computer. Post http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database-webservice/api/computers
	 * 
	 * @param computer
	 * @return Response
	 */
	public Response addComputer(Computer computer);

	/**
	 * edit a computer. Post http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * 
	 * @param computer
	 * @return Response
	 */
	public Response saveComputer(Computer computer, int id);

	/**
	 * Delete a computer. Delete http method.
	 * 
	 * Access this resource with this url :
	 * http://localhost:8080/computer-database-webservice/api/computers/{id}
	 * 
	 * @param computer
	 * @return Response
	 */
	public Response deleteComputer(@PathParam("id") int id);
}
