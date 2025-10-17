package utils;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspWriter;

public class JSFunction {
	// JSP 전용
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = ""
					+"<script>"
					+"	alert('"+msg+"');"
					+"	location.href='"+url+"';"
					+"</script>";
			out.println(script);
		}catch(Exception e) {
			
		}
		
	}
	// JSP 전용
	public static void alertBack(String msg, JspWriter out ) {
		try {
			String script = ""
					+"<script>"
					+"	alert('"+msg+"');"
					+"	history.back();"
					+"</script>";
			out.println(script);		
					
					
		}catch(Exception e) {
			
		}
	}
	
	// Servlet 전용
    public static void alertLocation(HttpServletResponse resp, String msg, String url) {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.println("<script>");
            writer.println("alert('" + msg + "');");
            writer.println("location.href='" + url + "';");
            writer.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alertBack(HttpServletResponse resp, String msg) {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.println("<script>");
            writer.println("alert('" + msg + "');");
            writer.println("history.back();");
            writer.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
