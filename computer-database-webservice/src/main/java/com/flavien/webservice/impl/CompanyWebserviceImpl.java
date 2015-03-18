package com.flavien.webservice.impl;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.flavien.exception.PersistenceException;
import com.flavien.service.CompanyService;
import com.flavien.webservice.CompanyWebservice;

@Path("/companies")
public class CompanyWebserviceImpl implements CompanyWebservice {

	@Autowired
	private CompanyService companyService;

	/* (non-Javadoc)
	 * @see com.flavien.webservice.CompanyWebservice#findAll()
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {		
		return Response.ok(companyService.getAll()).build();
	}
	
	/* (non-Javadoc)
	 * @see com.flavien.webservice.CompanyWebservice#deleteCompany(int)
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteCompany(@PathParam("id") int id) {	
		try {
			companyService.deleteByID(id);
		} catch (PersistenceException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		return Response.noContent().build();
	}

}
