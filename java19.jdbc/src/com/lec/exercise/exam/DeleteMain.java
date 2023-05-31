package com.lec.exercise.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteMain {
	final static String DRV = "org.mariadb.jdbc.Driver";
	final static String URL = "jdbc:mariadb://localhost/board";
	final static String USR = "root";
	final static String PWD = "12345";
	
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;	
	static ResultSet rs = null;
	
	public static void main(String[] args) {

		String sql = null;
		
		 try {
			Class.forName(DRV);
			conn = DriverManager.getConnection(URL, USR, PWD);
			
			sql = "delete from emp where empno=?";
			
			// 레코드 삭제하기
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 9001);
			
			int row = pstmt.executeUpdate();
			System.out.println("emp테이블에 " + row + "건이 성공적으로 삭제되었습니다.");
			
		 } catch (Exception e) {
			System.out.println("DB연결실패");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {}
		}
	}
}
