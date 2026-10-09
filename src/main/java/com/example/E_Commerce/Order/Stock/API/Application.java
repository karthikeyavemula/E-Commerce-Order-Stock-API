package com.example.E_Commerce.Order.Stock.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application{
	public static void main(String[] args){
		var context=SpringApplication.run(Application.class, args);
		if (context != null) {
			System.out.println("SUCCESS: E-Commerce Order & Stock API is Live and Active!");
		}
	}
}
