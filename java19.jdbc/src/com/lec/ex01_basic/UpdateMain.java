package com.lec.ex01_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateMain {
	final static String DRV = "oracle.jdbc.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String USR = "scott";
	final static String PWD = "tiger";
	
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String sql = null;
	public static void main(String[] args) {
		try {
			Class.forName(DRV);
			conn = DriverManager.getConnection(URL,USR,PWD);
			sql = "update emp set ename=?, sal=?, comm=? where empno=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "나얼");
			pstmt.setInt(2, 2000);
			pstmt.setInt(3, 100);
			pstmt.setInt(4, 9001);
			int row = pstmt.executeUpdate();
			System.out.println(row+"건 수정완료");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(pstmt != null) pstmt.close(); 
				if(conn != null) conn.close(); 
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}

}
