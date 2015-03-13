package com.flavien.webservice;

import java.util.List;

import com.flavien.models.Company;

public interface CompanyWebservice {

	public List<Company> findAll();
	
	public void deleteCompany(int id);
}
