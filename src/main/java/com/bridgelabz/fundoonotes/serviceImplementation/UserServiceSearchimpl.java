package com.bridgelabz.fundoonotes.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.UserInformationSearch;
import com.bridgelabz.fundoonotes.repository.UserDaoSearch;
import com.bridgelabz.fundoonotes.service.UserServiceSearch;
@Service
public class UserServiceSearchimpl implements UserServiceSearch {
	@Autowired
	private UserDaoSearch rep;
	@Override
	public Iterable<UserInformationSearch> saveAll(List<UserInformationSearch> customers) {
		// TODO Auto-generated method stub
		return rep.saveAll(customers);
	}
	@Override
	public List<UserInformationSearch> findByFirstName(String name) {
		// TODO Auto-generated method stub
		return rep.findbyName(name);
	}
	@Override
	public Iterable<UserInformationSearch> findAll() {
		// TODO Auto-generated method stub
		return rep.findAll();
	}

	
}
