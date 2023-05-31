package com.lec.exercise.exam2;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	private String path = ConnectionFactory.class.getResource("jdbc.properties").getPath();
	private String DRV = null;
	private String URL = null;
	private String USR = null;
	private String PWD = null;

	private String insert = null;
	private String select = null;
	private String update = null;
	private String delete = null;
	
	public ConnectionFactory() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setUp() throws Exception {
		Properties p = new Properties();
		path = URLDecoder.decode(path,"utf-8");
		p.load(new FileReader(path));
		// 1. DB접속정보
		DRV = p.getProperty("jdbc.mariadb.drv");
		URL = p.getProperty("jdbc.mariadb.url");
		USR = p.getProperty("jdbc.mariadb.usr");
		PWD = p.getProperty("jdbc.mariadb.pwd");
		
		// 2. SQL정보
		insert = p.getProperty("insert");
		select = p.getProperty("select");
		update = p.getProperty("update");
		delete = p.getProperty("delete");
		
		System.out.println(DRV);
		System.out.println(URL);
		System.out.println(USR);
		System.out.println(PWD);
		
		System.out.println(insert);
		System.out.println(select);
		System.out.println(update);
		System.out.println(delete);

		Class.forName(DRV);
	}
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USR, PWD);
		} catch (Exception e) {
			System.out.println("DB연결실패");
			e.printStackTrace();
		}
		return null;
		
	}

	public String getInsert() {
		return insert;
	}

	public String getSelect() {
		return select;
	}

	public String getUpdate() {
		return update;
	}

	public String getDelete() {
		return delete;
	}
	
}










