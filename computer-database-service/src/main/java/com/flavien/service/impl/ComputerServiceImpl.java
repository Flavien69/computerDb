package com.flavien.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flavien.dao.mapper.PageRequestMapper;
import com.flavien.dao.repository.ComputerRepository;
import com.flavien.exception.PersistenceException;
import com.flavien.models.Computer;
import com.flavien.models.Page;
import com.flavien.service.ComputerService;

/**
 * 
 * Class that implement the computer service API.
 * 
 */
@Service
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private PageRequestMapper pageRequestMapper;

	@Autowired
	private ComputerRepository computerRepository;

	public ComputerServiceImpl() {
	}

	public ComputerServiceImpl(ComputerRepository computerRepository) {
		this.computerRepository = computerRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.service.ComputerService#add(com.flavien.models.Computer)
	 */
	@Override
	public void add(Computer computer) {
		computerRepository.save(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.service.ComputerService#getAll()
	 */
	@Override
	public List<Computer> getAll() {
		return computerRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flavien.service.ComputerService#getByPage(com.flavien.models.Page,
	 * java.lang.String)
	 */
	@Override
	@Transactional()
	public Page getByPage(Page page) {

		int count = computerRepository.getCount(page.getSearch());

		page.setComputerList(computerRepository.findByNameContainingOrCompanyNameContaining(page.getSearch(),
				page.getSearch(), pageRequestMapper.toPageRequest(page)).getContent());

		page.setNbTotalComputer(count);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.service.ComputerService#deleteById(int)
	 */
	@Override
	public void deleteById(int computerId) {
		try {
			computerRepository.delete(computerId);
		} catch (EmptyResultDataAccessException e) {
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flavien.service.ComputerService#update(com.flavien.models.Computer)
	 */
	@Override
	public void update(Computer computer) {
		computerRepository.save(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.service.ComputerService#getByID(int)
	 */
	@Override
	public Computer getByID(int computerId) {
		Computer computer = null;
		try {
			computer = computerRepository.findOne(computerId);
		} catch (EmptyResultDataAccessException e) {
			throw new PersistenceException(e);
		}
		return computer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flavien.service.ComputerService#getByName(java.lang.String)
	 */
	@Override
	public List<Computer> getByName(String name) {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = computerRepository.findByName(name);
		} catch (EmptyResultDataAccessException e) {
			throw new PersistenceException(e);
		}
		return computers;

	}

}
