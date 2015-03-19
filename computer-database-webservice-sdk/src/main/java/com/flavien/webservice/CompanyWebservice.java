package com.flavien.webservice;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * End point of the companies resource.
 */

public interface CompanyWebservice {

	
	/**
	 * Return a list of companies in Json format.
	 * Get http method.
	 * 
	 * Access this resource with this url : http://localhost:8080/computer-database-webservice/api/companies
	 * @return Response
	 */
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
	public Response deleteCompany(@PathParam("id") int id);
}
