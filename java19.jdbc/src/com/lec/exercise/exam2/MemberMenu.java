package com.lec.exercise.exam2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.lec.ex02_board.BoardVO;

public class MemberMenu {
	private double ver;
	public MemberMenu(double ver) {
		this.ver = ver;
	}
	// main menu
	public void mainMenu() throws Exception {
		MemberDAOImpl bddao = MemberFactory.getInstance();
		while(true) {
			mainMenuText();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			switch(br.read()) {
			case '1' : bddao.createMember();break;
			case '2' : bddao.listMember();break;
			case '3' : bddao.viewMember(null);break;
			case '4' : bddao.updateMember(null);break;
			case '5' : bddao.deleteMember(null);break;
			case '6' : bddao.findByNameMember(null);break;
			case '7' : bddao.findByEmailMember(null);break;
			case '0' : System.out.println("프로그램종료!!"); System.exit(0);
			}
			
		}
	}
	private void mainMenuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("***** 회원관리 프로그램 V" + ver + "*****\n");
		sb.append("1. 신규회원등록\n");
		sb.append("2. 전체회원조회\n");
		sb.append("3. 회원상세조회\n");
		sb.append("4. 회원정보수정\n");
		sb.append("5. 회원정보삭제\n");
		sb.append("6. 이름으로 검색\n");
		sb.append("7. 이메일로 검색\n");
		sb.append("0. 종료\n");
		sb.append("===========================\n");
		sb.append("처리할 작업번호를 입력하세요");
		System.out.println(sb.toString());		
	}
	public void mainMemberMenu() {
		MemberDAOImpl bddao = MemberFactory.getInstance();
		while(true) {
			int menuNo = mainMenuUi();
			
			switch(menuNo) {
			case 1: makeMember(bddao); break;
			case 2: listMember(bddao); break;
			case 3: viewMember(bddao); break;
			case 4: updateMember(bddao); break;
			case 5: deleteMember(bddao); break;
			case 6: findByName(bddao); break;
			case 7: findByEmail(bddao); break;
			case 0 :System.out.println("프로그램종료!!"); System.exit(0);
			
			
			}
			
		}
		
	}
	private void findByEmail(MemberDAOImpl bddao) {
		String member_email = JOptionPane.showInputDialog("회원 이메일을 입력하세요!!");
		ArrayList<MemberVO> members = bddao.findByEmailMember(member_email);
		System.out.println("===========================================================================");
		System.out.println("회원ID\t\t비밀번호\t\t이름\t\t나이\t\t성별\t\t이메일");
		System.out.println("===========================================================================");
		
		for(MemberVO member:members) {
			System.out.println(member.toString());
		}
		System.out.println("------- 회원목록 출력 종료 --------\n\n");			
	}
	private void findByName(MemberDAOImpl bddao) {
		String member_name = JOptionPane.showInputDialog("회원 이름을 입력하세요!!");				
		ArrayList<MemberVO> members = bddao.findByNameMember(member_name);
		System.out.println("===========================================================================");
		System.out.println("회원ID\t\t비밀번호\t\t이름\t\t나이\t\t성별\t\t이메일");
		System.out.println("===========================================================================");
		
		for(MemberVO member:members) {
			System.out.println(member.toString());
		}
		System.out.println("------- 회원목록 출력 종료 --------\n\n");			
	}
	private void deleteMember(MemberDAOImpl bddao) {
		String member_id = JOptionPane.showInputDialog("회원정보를 삭제할 회원아이디를 입력하세요!!");		
		
		if((member_id == null) || (member_id.equals(""))) {
			return;
		} else {
			bddao.deleteMember(member_id);
		}		
	}

	private void updateMember(MemberDAOImpl bddao) {
		String member_id = JOptionPane.showInputDialog("회원정보를 수정할 회원아이디를 입력하세요!!");		
		
		if((member_id == null) || (member_id.equals(""))) {
			return;
		} else {
			bddao.updateMember(member_id);
		}			
	}
	private void viewMember(MemberDAOImpl bddao) {
		String member_id = JOptionPane.showInputDialog("회원정보를 조회할 회원아이디를 입력하세요!!");	
		
		if(( member_id == null) || ( member_id.equals(""))) {
			return;
		} else {
			MemberVO member = bddao.viewMember( member_id);	
			System.out.println("이름 : " + member.getMember_name());
			System.out.println("나이 : " + member.getMember_age());
			System.out.println("성별 : " + member.getMember_gender());
			System.out.println("이메일 : " + member.getMember_email());
		}	
	}
	private void listMember(MemberDAOImpl bddao) {
		ArrayList<MemberVO> members = bddao.listMember();
		System.out.println("===========================================================================");
		System.out.println("회원ID\t\t비밀번호\t\t이름\t\t나이\t\t성별\t\t이메일");
		System.out.println("===========================================================================");
	
	for(MemberVO member:members) {
		System.out.println(member.toString());
	} System.out.println("------- 전체회원목록 출력 종료 --------\n\n");
	}
	
	private void makeMember(MemberDAOImpl bddao) {
		bddao.createMember();
	}
	private int mainMenuUi() {
		String menuNo = JOptionPane.showInputDialog(
				"===== 회원정보관리프로그램 V1.0 =====\n\n" +
						"1. 신규회원등록\n" + 
						"2. 전체회원조회\n" + 
						"3. 회원상세조회\n" + 
						"4. 회원정보수정\n" + 
						"5. 회원정보삭제\n" + 
						"6. 이름으로 검색\n" + 
						"7. 이메일로 검색\n" + 
						"0. 종료\n\n" + 
						"처리할 작업번호를 입력하세요"
				
				);
		if((menuNo == null)||(menuNo.equals(""))) {
			return 0;
		}else {
			return Integer.parseInt(menuNo);
		}
				
	}
	
	
	
}








