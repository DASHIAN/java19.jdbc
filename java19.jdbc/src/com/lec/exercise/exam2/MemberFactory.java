package com.lec.exercise.exam2;

public class MemberFactory {

	// 외부에서 생성금지(싱글톤)
	private MemberFactory() {}
	private static MemberDAOImpl dbdao = null;
	public static MemberDAOImpl getInstance() {
		if(dbdao == null) dbdao = new MemberDAOImpl();
		return dbdao;
	}
}
