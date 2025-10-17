package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletContext;

public class JDBConnect {
	protected Connection con;
	protected PreparedStatement psmt;  // MemberDAO 추가
    protected ResultSet rs;            // MemberDAO 추가
	
	public JDBConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/musthave";
			String id = "musthave";
			String pwd = "tiger";
			con = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB 연결 성공(기본 생성자)");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public JDBConnect(String driver, String url, String id, String pwd) {
		try {
			Class.forName(driver); 
			con = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB 연결 성공(기본 생성자 1)");
			System.out.println("URL: " + url);
			System.out.println("ID: " + id);
			System.out.println("PW: " + pwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public JDBConnect(ServletContext application) {
	try {
		String driver = application.getInitParameter("MySQLDriver");
		Class.forName(driver); 
		
		String url = application.getInitParameter("MySQLURL");
		String id = application.getInitParameter("MySQLID");
		String pwd = application.getInitParameter("MySQLPwd");
		con = DriverManager.getConnection(url, id, pwd);
		
		System.out.println("DB 연결 성공(기본 생성자 2)");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		
	}
}
	public Connection getCon() {
	    return con;
	}
	
	public void close() {
		try {
			if (con != null) con.close();
				System.out.println("JDBC 자원 해제");
			}catch (Exception e) {
				
			}
	}
}
