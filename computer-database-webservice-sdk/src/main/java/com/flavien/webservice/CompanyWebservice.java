package com.flavien.webservice;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * End point of the companies resource.
 */
@Path("/companies")
public interface CompanyWebservice {

	
	/**
	 * Return a list of companies in Json format.
	 * Get http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/companies
	 * @return Response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll();
	

	/**
	 * 	
	 * Delete a company
	 * Delete http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/companies/{id}
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteCompany(@PathParam("id") int id);
}
