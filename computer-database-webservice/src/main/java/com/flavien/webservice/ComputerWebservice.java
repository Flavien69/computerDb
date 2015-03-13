package com.flavien.webservice;

import com.flavien.dto.ComputerDTO;
import com.flavien.dto.PageDTO;
import com.flavien.models.Computer;
import com.flavien.models.Page.SortCriteria;
import com.flavien.models.Page.SortOrder;

public interface ComputerWebservice {

	public ComputerDTO findById(int id);
	
	public PageDTO findDashboard(int index,String search,SortCriteria sortCriteria,SortOrder sortOrder);
	
	public void saveComputer(Computer computer);
	
	public void deleteComputer(Computer computer);
}
