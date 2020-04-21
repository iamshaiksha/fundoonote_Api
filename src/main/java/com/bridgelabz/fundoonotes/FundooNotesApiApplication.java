package com.bridgelabz.fundoonotes;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundooNotesApiApplication {
	
	public static void main(String[] args) {
//		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/shaiksha?user=root&password=root123$");
		SpringApplication.run(FundooNotesApiApplication.class, args);
	}

}
