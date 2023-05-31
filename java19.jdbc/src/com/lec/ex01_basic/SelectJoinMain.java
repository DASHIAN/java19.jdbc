package com.lec.ex01_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class SelectJoinMain {

	final static String DRV = "oracle.jdbc.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String USR = "scott";
	final static String PWD = "tiger";
	
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	
	public static void main(String[] args) {
		// 실습. JOptionPane으로 부서번호를 입력받아서 부서명출력하기(join)
		JOptionPane aa = new JOptionPane();
		String deptno = aa.showInputDialog("부서번호");
		int number=Integer.parseInt(deptno);
		
		System.out.println(deptno);
//		
		// select empno, ename, emp.deptno, dname 
		// from emp emp,dept dpt 
		// where emp.deptno = ?
		// and emp.deptno = dpt.deptno;
		
		
		// 사원번호, 사원이름 ,부서번호, 부서이름
		
		try {
			Class.forName(DRV);
			conn = DriverManager.getConnection(URL, USR, PWD);
			String sql = "select empno, ename, emp.deptno, dname "
					+ "from emp emp,dept dpt "
					+ "where emp.deptno = ? "
					+ "and emp.deptno = dpt.deptno";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(deptno));//////////////////
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int empno = rs.getInt(1);
				String ename = rs.getString(2);
				int dno = rs.getInt(3); /////////////////////
				String dname = rs.getString(4);
				
				System.out.print(empno+"\t\t");
				System.out.print(ename+"\t\t");
				System.out.print(dno+"\t\t");
				System.out.print(dname+"\t\t");
				
				
			}else {
				System.out.println("자료를 찾지 못했습니다");
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("DB연결실패");
		}finally {
			try {
				if(rs != null) rs.close(); 
				if(pstmt != null) pstmt.close(); 
				if(conn != null) conn.close(); 
			} catch (Exception e2) {
			}
		}
	}

}
