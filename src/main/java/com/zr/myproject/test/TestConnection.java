package com.zr.myproject.test;

import java.sql.Connection;

import com.zr.myproject.utils.MyDBUtils;

public class TestConnection {

	public static void main(String[] args) {
		
		Connection connection = MyDBUtils.getConnection();
		System.out.println(connection);
	}
	
}