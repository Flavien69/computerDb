package com.flavien.cli.component;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.flavien.cli.HelperCli;
import com.flavien.dto.ComputerDTO;
import com.flavien.dto.PageDTO;
import com.flavien.models.Company;
import com.flavien.models.Computer;
import com.flavien.models.Page;
import com.flavien.service.CompanyService;
import com.flavien.service.ComputerService;

/**
 * 
 * Command line interface to handle company interaction.
 * 
 */
@Component
public class ComputerCli {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ComputerService computerService;

	@Autowired
	private CompanyCli companyCli;

	private Client client;

	private WebTarget computerTarget;

	public ComputerCli() {
		client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
		computerTarget = client.target("http://localhost:8080/computer-database-webservice/api/computers");
	}

	/**
	 * 
	 * Show a list of computers
	 * 
	 */
	public void showComputers() {

		// Consume the API
		Response response = computerTarget.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == HttpStatus.OK.value())
			displayComputer(response.readEntity(new GenericType<List<ComputerDTO>>() {}));
		else
			throw new RuntimeException("Erreur: http code " + response.getStatus());
	}

	/**
	 * 
	 * Show a list of computer page by page using a Page object
	 * 
	 */
	public void showComputersPage() {
		String input;
		PageDTO pageDTO = new PageDTO(-1);
		do {
			pageDTO.setIndex(pageDTO.getIndex() + 1);

			// Consume the API
			Response response = computerTarget.path("/dashboard").queryParam("index", pageDTO.getIndex())
					.request(MediaType.APPLICATION_JSON).get();

			if (response.getStatus() == HttpStatus.OK.value()) {
				pageDTO = response.readEntity(PageDTO.class);
				displayComputer(pageDTO.getComputerList());

				System.out.println("\npage " + pageDTO.getIndex() + "/" + pageDTO.getNbTotalPage());
				System.out.println("\n'enter' to search the next or 'exit' to return in the menu\n");
				input = HelperCli.getStringInput();
			} else {
				throw new RuntimeException("Erreur: http code " + response.getStatus());
			}

		} while (input == null && pageDTO.getComputerList().size() == Page.DEFAULT_NB_ENTITY_BY_PAGE);
	}


	/**
	 * 
	 * Create a computer using the cli interface
	 * 
	 */
	public void createComputer() {
		Boolean isCompanyIdError = false;
		Computer computer = new Computer.Builder().build();

		System.out.println("\n***************** CREATE A COMPUTER ***********************************\n");
		String name = null;
		do {
			System.out.println("choose a name (field needed)");
			name = HelperCli.getStringInput();
			computer.setName(name);
		} while (name == null);

		System.out.println("Vchoose a date of introduced (" + HelperCli.DATE_FORMAT
				+ " or 'enter' to skip) :");
		computer.setIntroduced(HelperCli.getDateInput());

		System.out.println("choose a date of discontinued (" + HelperCli.DATE_FORMAT
				+ " or 'enter' to skip) :");
		computer.setDiscontinued(HelperCli.getDateInput());

		companyCli.showCompany();
		Company company = null;
		do {
			if (!isCompanyIdError)
				System.out.println("\nchoose your company (ID of the company or 'enter' to skip):");
			else
				System.out.println("\nERREUR: choose your company (ID of the company or 'enter' to skip):");

			int computerId = HelperCli.getIntInput(HelperCli.NO_MAX_VALUE);
			if (computerId != HelperCli.RESULT_SKIP) {
				company = companyService.getByID(computerId);
				if (company != null)
					computer.setCompany(company);
			} else
				break;
			isCompanyIdError = true;
		} while (company == null);

		// Consume the API
		Response response = computerTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(computer, MediaType.APPLICATION_JSON));

		if (response.getStatus() == HttpStatus.NO_CONTENT.value())
			System.out.println("Computer saved");
		else
			throw new RuntimeException("Erreur: http code " + response.getStatus());
		
		
		
	}

	/**
	 * 
	 * Update a computer using the cli interface
	 * 
	 */
	public void updateComputer() {

		System.out.println("\n***************** UPDATE A COMPUTER ***********************************\n");
		showComputers();

		ComputerDTO computerDTO = null;
		Boolean isComputerIdError = false;
		Boolean isCompanyIdError = false;

		do {
			if (!isComputerIdError)
				System.out.println("\nchoose the computer to update (ID of the computer):");
			else
				System.out.println("\nERREUR: choose the computer to update (ID of the computer):");

			// Consume the API
			Response responseGet = computerTarget
					.path("/" + HelperCli.getIntInput(HelperCli.NO_MAX_VALUE))
					.request(MediaType.APPLICATION_JSON)
					.get();
			switch (HttpStatus.valueOf(responseGet.getStatus())) {
			case OK:
				computerDTO =  responseGet.readEntity(ComputerDTO.class);
				break;

			case BAD_REQUEST:
				System.out.println("Invalid id");
				break;

			default:
				throw new RuntimeException("Erreur: http code " + responseGet.getStatus());

			}
			
			isComputerIdError = true;
		} while (computerDTO == null);

		Computer newComputer = new Computer();
		newComputer.setId(computerDTO.getId());
		
		System.out.println("Choose a name or 'enter' to skip: ");
		String name = HelperCli.getStringInput();
		if (name != null)
			newComputer.setName(name);

		System.out
				.println("Choose a date of introduced (" + HelperCli.DATE_FORMAT + " or 'enter' to skip) :");
		LocalDateTime introducedDate = HelperCli.getDateInput();
		if (introducedDate != null)
			newComputer.setIntroduced(introducedDate);

		System.out.println("Choose a date of discontinued (" + HelperCli.DATE_FORMAT
				+ " or 'enter' to skip) :");
		LocalDateTime discontinued = HelperCli.getDateInput();
		if (introducedDate != null)
			newComputer.setDiscontinued(discontinued);

		companyCli.showCompany();
		Company company = null;
		do {
			if (!isCompanyIdError)
				System.out.println("\nchoose your company (ID of the company or 'enter' to skip):");
			else
				System.out.println("\nERREUR: choose your company (ID of the company or 'enter' to skip):");

			int computerId = HelperCli.getIntInput(HelperCli.NO_MAX_VALUE);
			if (computerId != HelperCli.RESULT_SKIP) {
				company = companyService.getByID(computerId);
				if (company != null)
					newComputer.setCompany(company);
			} else
				break;
			isCompanyIdError = true;
		} while (company == null);

		// Consume the API
		Response response = computerTarget.path("/" + newComputer.getId()).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(newComputer, MediaType.APPLICATION_JSON));

		if (response.getStatus() == HttpStatus.NO_CONTENT.value())
			System.out.println("Computer saved");
		else
			throw new RuntimeException("Erreur: http code " + response.getStatus());
	}

	/**
	 * 
	 * Delete a computer using the cli interface
	 * 
	 */
	public void deleteComputer() {

		System.out.println("\n***************** DELETE A COMPUTER ***********************************\n");
		showComputers();

		System.out.println("\nchoose a computer to delete (ID of the computer):");
		int id = HelperCli.getIntInput(HelperCli.NO_MAX_VALUE);
		while (id == HelperCli.RESULT_SKIP) {

			System.out.println("\nERREUR: choose a computer to delete (ID of the computer):");
		}

		// Consume the API
		Response response = computerTarget.path("/" + id).request(MediaType.APPLICATION_JSON).delete();

		switch (HttpStatus.valueOf(response.getStatus())) {
			case NO_CONTENT:
				System.out.println("Computer deleted!\n");
				break;

			case BAD_REQUEST:
				System.out.println("Invalid id");
				break;

			default:
				throw new RuntimeException("Erreur: http code " + response.getStatus());
		}
	}

	/**
	 * 
	 * Get a computer using the cli interface
	 * 
	 */
	public void getComputer() {

		System.out.println("\n***************** GET A COMPUTER ***********************************\n");
		showComputers();

		System.out.println("\nchoose a computer to check (ID of the computer):");
		int id = HelperCli.getIntInput(HelperCli.NO_MAX_VALUE);
		while (id == HelperCli.RESULT_SKIP) {

			System.out.println("\nERREUR: choose a computer to check (ID of the computer):");
		}

		// Consume the API
		Response response = computerTarget.path("/" + id).request(MediaType.APPLICATION_JSON).get();

		switch (HttpStatus.valueOf(response.getStatus())) {
		case OK:
			System.out.println(response.readEntity(ComputerDTO.class).toString());
			break;

		case BAD_REQUEST:
			System.out.println("Invalid id");
			break;

		default:
			throw new RuntimeException("Erreur: http code " + response.getStatus());

		}
	}
	
	/**
	 * 
	 * Display a list of computer
	 * 
	 */
	public void displayComputer(List<ComputerDTO> computerList) {
		for (ComputerDTO computerDTO : computerList) {
			System.out.println(computerDTO.toString());
		}
	}

}
