package com.lec.exercise.exam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.lec.ex02_board.BoardVO;
import com.lec.ex02_board.ConnectionFactory;

public class MemberDAOImpl implements MemberDAOservice{

	private MemberVO inputMember() {
		MemberVO member = new MemberVO();
		
		String member_id = JOptionPane.showInputDialog("회원ID를 입력하세요");
		String member_pw = JOptionPane.showInputDialog("비밀번호를 입력하세요");
		String member_name = JOptionPane.showInputDialog("이름을 입력하세요");
		String ageStr = JOptionPane.showInputDialog("나이를 입력하세요");
		int member_age = Integer.parseInt(ageStr);
		String member_gender = JOptionPane.showInputDialog("성별을 입력하세요");
		String member_email = JOptionPane.showInputDialog("이메일을 입력하세요");
		
		member.setMember_id(member_id);
		member.setMember_pw(member_pw);
		member.setMember_name(member_name);
		member.setMember_age(member_age);
		member.setMember_gender(member_gender);
		member.setMember_email(member_email);
		
		return member;
		
	}
	
	@Override
	public void createMember() {
		MemberVO member = inputMember();
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		String sql = cf.getInsert();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,member.getMember_id());
			pstmt.setString(2,member.getMember_pw());
			pstmt.setString(3,member.getMember_name());
			pstmt.setInt(4,member.getMember_age());
			pstmt.setString(5,member.getMember_gender());
			pstmt.setString(6,member.getMember_email());
			int row = pstmt.executeUpdate();
			System.out.println(row+"건이 성공적으로 등록되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (Exception e2) {
			}
		}
		
		
	}



	@Override
	public ArrayList<MemberVO> listMember() {
		ArrayList<MemberVO> memberList = new ArrayList<>();
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = cf.getSelect();
					
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMember_id(rs.getString("member_id"));
				member.setMember_pw(rs.getString("member_pw"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_age(rs.getInt("member_age"));
				member.setMember_gender(rs.getString("member_gender"));
				member.setMember_email(rs.getString("member_email"));
				
			}
			
		} catch (Exception e) {
			System.out.println("회원목록 조회 실패!!!");
			e.printStackTrace();
		}finally{
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}			
		return memberList;
	}

	@Override
	public MemberVO viewMember(String member_id) {
		MemberVO member = new MemberVO();
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = cf.getSelect() + " where member_id = " + member_id;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member.setMember_id(rs.getString("member_id"));
				member.setMember_pw(rs.getString("member_pw"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_age(rs.getInt("member_age"));
				member.setMember_gender(rs.getString("member_gender"));
				member.setMember_email(rs.getString("member_email"));
			} else {
				System.out.println("회원아이디(" + member_id + ") - 정보가 없습니다!!");
			}
		} catch (Exception e) {
			System.out.println("회원정보 조회 실패!!!");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return null;
	}

	@Override
	public void updateMember(String member_id) {
		// BoardVO board = inputBoard();
		// update board set subject=?, .... where bno = 10;
		MemberVO member = viewMember(member_id);
		
		String member_pw = JOptionPane.showInputDialog("비밀번호를 수정하세요!", member.getMember_pw());
		String member_name = JOptionPane.showInputDialog("이름을 수정하세요!", member.getMember_name());
		String ageStr = JOptionPane.showInputDialog("나이를 수정하세요");
		int member_age = Integer.parseInt(ageStr);
		String member_gender = JOptionPane.showInputDialog("성별을 수정하세요!", member.getMember_gender());
		String member_email = JOptionPane.showInputDialog("이메일을 수정하세요!", member.getMember_email());
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		String sql = cf.getUpdate();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_pw);
			pstmt.setString(2, member_name);
			pstmt.setInt(3, member_age);
			pstmt.setString(4, member_gender);
			pstmt.setString(5, member_email);
			int row = pstmt.executeUpdate();			
			System.out.println("회원아이디(" + member_id + ") - 회원정보가 성공적으로 수정되었습니다!!");
		} catch (Exception e) {
			System.out.println("회원정보 수정 실패!!!");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void deleteMember(String member_id) {
		// TODO Auto-generated method stub
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		String sql = cf.getDelete();	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			int row = pstmt.executeUpdate();
			System.out.println("회원아이디(" + member_id + ") - 회원정보가 성공적으로 삭제되었습니다!!");
		
		} catch (Exception e) {
			System.out.println("회원정보 삭제 실패!!!");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public ArrayList<MemberVO> findByNameMember(String member_name) {
		ArrayList<MemberVO> memberList = new ArrayList<>();
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = cf.getSelect() 
				   + " where member_name like '%" + member_name + "%'"
				   + " order by bno desc";
		
		try {	
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMember_id(rs.getString("member_id"));
				member.setMember_pw(rs.getString("member_pw"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_age(rs.getInt("member_age"));
				member.setMember_gender(rs.getString("member_gender"));
				member.setMember_email(rs.getString("member_email"));			
				memberList.add(member);
			}
			
			
		} catch (Exception e) {
			System.out.println("회원 목록(by 이름) 조회 실패!!!");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}		
		return memberList;
	}

	@Override
	public ArrayList<MemberVO> findByEmailMember(String member_email) {
		ArrayList<MemberVO> memberList = new ArrayList<>();
		
		ConnectionFactory cf = new ConnectionFactory();
		Connection conn = cf.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = cf.getSelect() 
				   + " where member_email like '%" + member_email + "%'"
				   + " order by bno desc";
		
		try {	
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setMember_id(rs.getString("member_id"));
				member.setMember_pw(rs.getString("member_pw"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_age(rs.getInt("member_age"));
				member.setMember_gender(rs.getString("member_gender"));
				member.setMember_email(rs.getString("member_email"));				
				memberList.add(member);
			}
			
			
		} catch (Exception e) {
			System.out.println("회원 목록(by 이메일) 조회 실패!!!");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}		
		return memberList;
	}
	@Override
	public void updateMember(String member_id, String member_pw, String member_name, int member_age,
			String member_gender, String member_email) {
		// TODO Auto-generated method stub
		
	}

}
