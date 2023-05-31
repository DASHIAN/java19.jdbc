package com.lec.ex01_basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
  	JDBC를 이용한 자바와 DB연동하기
  	Java와 DB를 연동하기 위해서는 각 RDBMS의 버전에 맞는 JRE실행환경 라이브러리를 Java프로젝트에
  	추가해야 한다. JDBC드라이버는 DB버전에 맞는 JDBC드라이버(오라클은 ojdbc?.jar)를 추가해야 한다.
  	(Oracle JDBC의 버전별 참고사이트 http://oingdaddy.tistory.com/372)
  	
  	라이브러리를 추가하는 방법
  	1. Java프로젝트를 우클릭
  	2. Build Path > Configure Build Path > (t)Libraries > (button)Add Jars...>(button) Apply & Close
  	
  	A. JDBC프로그램 작성단계
  	
  	1. JDBC드라이버 로드(DB에 따라 드라이버가 상이)
  		a. Oracle 드라이버로딩 : Class.forName("oracle.jdbc.OracleDriver");
  		b. mariadb 드라이버로딩 : Class.forName("org.mariadb.jdbc.driver");
  		c. MySQL 드라이버로딩 : Class.forName("com.mysql.jdbc.driver");
  		
  		Class.forName()메서드는 드라이버를 읽기만 하면 자동으로 객체가 생성되고
  		DriverManager에 등록이 된다.
  		
  	2. Connection객체생성
  		Connection객체에 연결하는 것은 DriverManager에 등록된 각 드라이버를 getConnection()
  		메서드를 이용해서 식별한다. getConnection()메서드의 매개값은 'DB의 URL주소, 사용자ID,
  		비밀번호'를 전달한다. 연결할 때 url식별자 정보를 이용해서 Mapping을 하고 찾지 못했을 
  		경우에는 no suitable error가 발생한다. 찾았을 경우에는 getConnection(String url...)
  		메서드의 수행결과 Connection객체에 전달된다.
  		
  		getConnection(String url, String user, String password)
  		a. Oracle : getConnection("jdbc:oracle:thin:@오라클DB주소:port번호:서비스id", "사용자ID", "비밀번호");
  		b. mariadb : getConnection("jdbc:mariadb://마리아DB주소/db이름", "사용자ID", "비밀번호");
  		c. MySQL : getConnection("jdbc:MySQL://마이 SQL DB주소/db이름", "사용자ID", "비밀번호");
  		
  	3. Statement(Statement/PreparedStatement/CallableStatement)
  		SQL생성 및 실행하면 반환된 결과를 가져오게할 작업영역을 제공한다. Statement객체는 Connection객체의
  		createStatement()메서드를 사용하여 생성한다.
  		
  		이 단계부터는 JDBC드라이버에 구애를 받지 않는다. 즉, DB와 상관없이 동일한 명령으로 사용할 수 있다.
  		단, SQL표준문법을 준수할 경우에는 동일한 명령을 사용할 수 있지만 DB마다 고유한 SQL명령이라면 각 DB
  		의 문법에 맞게 작성되어야 한다. 
  		
  		Statement stmt = conn.createStatement();
  		PreparedStatement stmt = conn.createStatement();
  		CallableStatement cstmt = conn.preparedCall("{call 스토어프로시저명(?,?,?...)}");
  	4. Query를 실행
  		Statement객체가 생성된 후에 Statement객체의 executeQuery(), executeUpdate()메서드를 사용하여 처리한다.
  		ResultSet rs = stmt.executeQuery("select * from emp where emp = 7369");
  		ResultSet rs = pstmt.executeQuery("select * from emp where emp = ?");
  		ResultSet rs = cstmt.execute();
  	5. ResultSet처리
  		executeQuery()메서드 결과를 ResultSet으로 리턴한다. 이 ResultSet으로부터 읽은 데이터를 추출하는
  		과정이 ResultSet을 처리하는 과정이다.
  		데이터를 추출하는 방법은 ResultSet에서 rs.next()메서드로 한 행(row)씩 이동하면서 getXXX()메서드를 이용하여 데이터를
  		추출하는데 이때 rs.getInt(1) or rs.getString("컬럼명")메서드로 한 컬럼의 정보를 추출한다.
  		
  	B. JDBC에서 사용되는 객체
  	
  	1. DriverManager클래스
  		DriverManager클래스는 데이터원본에 JDBC드라이버를 통해 Connection을 만드는 역할을 한다. DriverManager
  		클래스는 Class.forName()메서드를 통해 생성되는데 이 메서드는 인터페이스 드라이버를 구현하는 작업을
  		한다.
  		
  		Class.forName()처럼 특정 클래스를 로드하면 자동으로 객체가 생성되고 DriverManager에 등록된다. 드라이버를
  		찾지 못했을 경우에는 forName()메서드는 ClassNotFoundException예외를 발생시키기 때문에 반드시 예외처리를
  		해야 한다.
  		
  		일반적으로 드라이버 글래스들은 로드될 때 자신의 인스턴스를 생성하고 자동으로 DriverManager클래스의
  		메서드를 호출하여 그 인스턴스를 등록한다 .DriverManager클래스의 모든 메서드는
  		static메서드이기 때문에 객체를 생성할 필요가 없다.
  		
  		DriverManager클래스는 Connection인터페이스의 구현객체를 생성하는데 getConnection()메서드를 사용한다.
  		
  	2. Connection 인터페이스
  		특정 데이터원본에 대한 커넥션은 Connection인터페이스가 구현된 클래스의  객체로 표현된다. 특정의 SQL문을
  		실행시키기 전에 우선 Connection객체가 있어야 한다. Connection객체는 특정 데이터원본과 연결된 커넥션
  		을 나타내고 특정한 SQL문장을 정의하고 실행시킬 수 있는 Statement객체를 생성할 때도 Connection객체를
  		사용한다.
  		
  		또한, Connection객체는 데이터베이스에 대한 데이터인 메타데이터베이스에 관한 정보를 데이터 원본에
  		질의하는 데 사용한다. 이때에는 사용가능한 테이블의 이름, 특정테이블의 컬럼정보를 포함한다.
  	
  	3. Statement 인터페이스
  		Statement인터페이스는 Connection에 의해 프로그램에 리턴되는 객체에 구현되는 일정의 메서드 집합을
  		의미한다. Statement객체는 Statement인터페이스를 구현한 객체로서 항상 인수가 없는 createStatement()
  		메서드를 호출함으로서 얻어진다.
  		
  		try{
  			Statement stmt = conn.createStatement();
  		catch(){
  			...
  		}
  		일단 Statement 객체를 생성하면 Statement.executeQuery()메서드를 호출해서 SQL질의를 실행시킬수 있다.
  		메서드의 매개값으로 SQL문장을 담은 String객체를 전달한다 Statement객체는 단순질의문을 사용하는 것이 좋다
  		
  	4. PreparedStatement 인터페이스
  		PreparedStatement인터페이스는 Connection객체의 preparedStatement() 메서드를 사용해서 객체를 생성한다.
  		PreparedStatement객체는 SQL문장이 사전에 컴파일이 되어 실행시간 동안 파라미터값을 위한 공간을 확보
  		한다는 점에서 Statement객체와 구별된다.
  		
  		PreparedStatement객체는 동일한 질의문을 특정값만 변경해서 여러번 반복 실행해야 할 때, 많은 데이터를
  		다루기 때문에 SQL문을 정리해야 할 필요가 있을 때, 파라미터값이 많아서 SQL문을 정리해야할 필요가 있을 때
  		사용하면 유용하다. 또한, Statement객체의 SQL은 실행할 때마다. DB서버에서 분석되어야 하지만
  		PreparedStatement객체는 한번만 분석(컴파일)되면 재사용할 수 있다는 장점이 있다.
  		
  		PreparedStatement인터페이스는 각각의 매개값의 위치홀더(palceholder,?)를 사용하여 SQL문장을 정의할
  		수 있게 해준다. 위치홀더는 물음표(?)로 표현된다. 위치홀더는 SQL문장에 나타나는 토큰(token)인데 이
  		것은 SQL문장이 실행되기 전에 실제값으로 대체된다. 이런 방법을 사용하면 특정값으로 문자열을 연결하는
  		방법보다 훨씬 쉽게 SQL문장을 만들 수 있다.
  		
  		PreparedStatement객체는 각각의 SQL타입을 처리할 수 있는 setXXX()메서드를 제공한다. 여기서 XXX는
  		해당 테이블의 데이터타입과 관련이 있다. 해당 필드의 데이터타입이 문자열이면 setString()이 되고 숫자값일
  		경우는 setInt(), setDouble()이 된다. setXXX(num, value)메서드는 2개의 매개값을 가지고 있는데 num
  		은 파라미터인덱스(placeholder의 위치)이고 위치홀더와 대응된다.
  		
  		PreparedStatement객체가 제공하는 메서드는
  		setString, setInt, setLong, setDouble, setFloat, setDate, setTimestamp, setObject가 있다.
  		
  		PreparedStatement의 장점
  		1) 동일한 SQL문을 특정값만 변경해서 여러번 실행해야 할 경우, 많은 데이터를 처리할 경우 SQL문을 정리
  		해야할 필요가 있을 때, 파라미터가 많아서 SQL문을 간소화시킬 필요가 있을 때 사용이 편리하다.
  		2) 사전에 컴파일되기 때문에 쿼리의 실행속도가 Statement객체보다 빠르다.
  		3) Statement객체는 쿼리실행시 작은 따옴표(')가 있으면 작은 따옴표 2개로 표시해야하는 번거로움이 있지만
  		PreparedStatement객체는  작은 따옴표의 문제를 쿼리 실행시에 자동으로 처리하기 때문에 신경 쓸 필요가 없다.
  		
  	5. CallableStatement 인터페이스
  		CallableStatement인터페이스는 Connection객체의 PrepareCall()메서드를 사용해서 객체를 생성한다.
  		CallableStatement객체는 주로 스토어드프로시저(Stored Procedure, function)를 실행하기 위해 사용한다.
  		Stored Procedure는 사전에 저장된 쿼리를 사용해서 DB에서 처리하기 때문에 수행속도가 빠르다.
  		
  		사용방법 : CallableStatement cstmt = conn.prepareCall();
  		
  		CallableStatement는 데이터베이스에 저장된 Stored Procedure를 호출하는 것만으로 실행이 가능하다.
  		conn.preparCall("{call 스토어드프로시저명(전달할 매개값...)}")의 형태로 사용한다.
  		
  		CallableStatement의 종류는
  		1) 단순호출 : {call 프로시저명(?,...)}
  		2) 호출결과를 리턴 : {? = call 프로시저명(?,...)}
  		3) 파라미터가 없는 프로시저 호출 : {call 프로시저명}
  	6. ResultSet 인터페이스
  		SQL문 중에서 select문을 사용한 쿼리가 성공했을 경우에 그 결과로서 ResultSet을 반환한다. ResultSet은
  		SQL질의에 의해 생성된 테이블을 담고 있다. 또한, ResultSet객체는 커서(cursor)라고 하는 것을 가지고
  		있는데 그것으로 ResultSet에서 특정값에 대한 참조를 처리할 수 있다.
  		
  		커서는 초기에 첫번째 행의 바로 앞을 가리키도록 되어 있는데 ResultSet객체의 nest()메서드를 이용해서
  		다음행으로 이동할 수 있다.
  		
  		ResultSet의 메서드
  		1) first() : 커서를 첫번째  행으로 이동
  		2) last() : 커서를 마지막 행으로 이동
  		3) beforeFirst() : 커서를 첫 번째 행 이전으로 이동
  		4) afterLast() : 커서를 마지막 행 이후으로 이동
  		5) next() : 커서를 다음행으로 이동
  		6) previous() : 커서를 이전행으로 이동
  		
  		ResultSet에서 행을 처리하는데 반복문을 사용하며 next()메서드가 유효한 행이 있으면 true, 없으면 false
  		를 리턴하는 것을 이용하여 while문으로 제어할 수 있다. ResultSet객체에서 값을 가져올 경우에 getXXX()메서드를
  		사용한다.
  		ResultSet이 제공하는 메서드는
  		getString, getInt, getLong,getDouble, getFloat, getDate, getTimestamp, getObject, getBytes가 있다.
  		
  	7. JDBC 트랜젝션
  		Transaction은 여러단계으 ㅣ작업을 하나로 처리하는 것으로 하나의 작업으로 인식된 작업이 모두 성공적으로
  		종료가 되면 commit가 되고 하나라도 문제가 있다면 rollback이 되어서 작업수행전으로 되돌린다.
  		이렇기 때문에 Transaction은 프로그램의 신뢰도를 보장하게 된다.
  		
  		JDBC에서도 Transcation처리에 대한 메서드를 제공한다.
  		1)commit : Transaction의 commit를 수행
  		2)rollback : Transaction의 rollback을 수행
  		
  		기본적으로 Connection객체에 setAutoCommit(boolean autoCommit)이란 메서드가 있는데 기본값이 true로 설정
  		되어있다. 그렇기 때문에 JDBC는 commit이 자동으로 수행되는데 자동으로 수행되는 것을 방지하려면 conn.
  		setAutoCommit(false)로 지정해야 한다.
  	
 
 */
public class JDBCConnectionMain {

	final static String DRV = "oracle.jdbc.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String USR = "scott";
	final static String PWD = "tiger";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
			try {
				Class.forName(DRV);
				conn = DriverManager.getConnection(URL, USR, PWD);
				System.out.println("DB연결 성공");
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from emp where empno = 7369");
				rs.next();
				
				int empno = rs.getInt(1);
				String ename = rs.getString("ename");
				System.out.println(empno+", "+ename);
			} catch (Exception e) {
				System.out.println("DB연결 실패");
				e.printStackTrace();
			//ClassLoader가 메모리(힙)영역에 Connection객체를 로딩
			}finally {
				try {
					if(rs!=null)rs.close();
					if(stmt!=null)stmt.close();
					if(conn!=null)conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
	}
}
