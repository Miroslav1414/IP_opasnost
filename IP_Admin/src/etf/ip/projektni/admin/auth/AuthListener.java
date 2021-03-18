package etf.ip.projektni.admin.auth;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthListener implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String loginURI = request.getContextPath() + "/index.xhtml";
		String mainURI = request.getContextPath() + "/main.xhtml";

		// smatramo da ako postoji user u sesiji onda moze pristupiti aplikaciji. Za
		// ovaj primjer nisu obradjeni dodatni sigurnosni elementi
		if (loginURI.equals(request.getRequestURI().toString()) 
				//|| mainURI.equals(request.getRequestURI().toString())
				|| (session!=null && session.getAttribute("admin_user") != null)) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(loginURI);
		}

	}

}
