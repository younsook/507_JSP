package filter;

import java.io.IOException;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class BasicFilter implements Filter {
	FilterConfig config;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		String filterName = filterConfig.getFilterName();
		
		System.out.println("BasicFilter -> init() 호출됨: " + filterName);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String filterInitParam = config.getInitParameter("FILTER_INIT_PARAM");
		System.out.println("BasicFilter -> 초기화 매개변수 : " + filterInitParam);
		
		String method = ((HttpServletRequest)request).getMethod();
		System.out.println("BasicFilter -> 전송 방식 : "+ method);
		
		//다음 필터(혹은 최종리소드) 호출
		chain.doFilter(request, response);
		

	}
	
	@Override
	public void destroy() {
		System.out.println("BasicFilter -> destroy() 호출됨");
	}

}
