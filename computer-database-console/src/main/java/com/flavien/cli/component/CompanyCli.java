package com.flavien.cli.component;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.flavien.cli.HelperCli;
import com.flavien.models.Company;
import com.flavien.service.CompanyService;

/**
 * 
 * Command line interface to handle company interaction.
 * 
 */
@Component
public class CompanyCli {

	@Autowired
	private CompanyService companyService;

	private Client client;

	private WebTarget companyTarget;

	public CompanyCli() {
		client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
		companyTarget = client.target("http://localhost:8080/computer-database-webservice/api/companies");
	}

	/**
	 * 
	 * Show a list of company
	 * 
	 */
	public void showCompany() {

		// Consume the API
		Response response = companyTarget.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == HttpStatus.OK.value()) {
			List<Company> companyList = response.readEntity(new GenericType<List<Company>>() {});
			for (Company company : companyList) {
				System.out.println(company.toString());
			}
		} else {
			throw new RuntimeException("Erreur: http code " + response.getStatus());
		}
	}

	/**
	 * 
	 * Delete a company
	 * 
	 */
	public void deleteCompany() {
		System.out.println("\n***************** DELETE A COMPANY ************************\n");
		showCompany();

		System.out.println("\nchoose a company to delete (ID of the company):");
		int id = HelperCli.getIntInput(HelperCli.NO_MAX_VALUE);
		while (id == HelperCli.RESULT_SKIP) {
			System.out.println("\nERREUR: choose a computer to delete (ID of the computer):");
		}

		// Consume the API
		Response response = companyTarget.path("/" + id).request(MediaType.APPLICATION_JSON).delete();

		switch (HttpStatus.valueOf(response.getStatus())) {
			case NO_CONTENT:
				System.out.println("company deleted!\n");
				break;
	
			case BAD_REQUEST:
				System.out.println("Invalid id");
				break;
	
			default:
				throw new RuntimeException("Erreur: http code " + response.getStatus());
		}
	}
}
