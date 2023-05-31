package com.lec.ex01_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteMain {
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
			
			// 레코드 삭제하기
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,9001);
			int row = pstmt.executeUpdate();
			System.out.println("emp테이블에 "+row+"건이 성공적 삭제");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close(); 
				if(conn != null) conn.close(); 
			} catch (Exception e2) {
			}
			
		}
	}

}
