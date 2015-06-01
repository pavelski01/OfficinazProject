package capital.officinaz.srvt;

import capital.officinaz.utility.Utility;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SignIn extends HttpServlet
{    
	private static final long serialVersionUID = 1L;
	private final String EMPTY = "";

	public void init() throws ServletException {}
 	public void destroy() {}
	
	public void doPost(HttpServletRequest _request, HttpServletResponse _response) 
		throws IOException, ServletException
	{
		_response.setContentType("text/html");
        _response.setHeader(
    		"cache-control", "private, no-cache, no-store, must-revalidate, "
				+ "pre-check=0, post-check=0"
		);
        _response.setHeader("pragma", "no-cache");
        _response.setDateHeader("expires", 0);
        String privilege = "guest";
        String login = _request.getParameter("login");
        if (login == null || login.equals(this.EMPTY)) login = "unidentified";
        String password = _request.getParameter("password");
        if (password == null || password.equals(this.EMPTY)) password = "absence";        
        String authentication = login + "/" + password;
        String clientLogin = this.EMPTY;
        Cookie[] cake = _request.getCookies();        
        String cookieLogin = this.pullOffCookies(cake);
        _request.getSession().invalidate();
        HttpSession session = _request.getSession(true);
        session.setAttribute("assortment", this.EMPTY);
        session.setAttribute("counted", 0);
        ArrayList<String> data = Utility.readData();
        boolean throwAway = true;
        for (String dat : data)                                            
            if (authentication.equals(dat) || login.equals(cookieLogin))
            {
            	privilege = "client";
                Cookie cookie = new Cookie("cookieLogin", login);
                cookie.setMaxAge(30 * 60);
                _response.addCookie(cookie);
                clientLogin = login + "/" + privilege;
                session.setAttribute("clientLogin", clientLogin);
                throwAway = false;
                break;
            }        
        if (throwAway)
    	{
        	final String 
        		CONTEXT_PATH = _request.getContextPath(), 
    			ERROR_SITE = "/content/jsf/sign-in-error.xhtml"; 
            clientLogin = login + "/guest";
            session.setAttribute("clientLogin", clientLogin);
            _response.sendRedirect(CONTEXT_PATH + ERROR_SITE);
		}
        else
    	{
        	final String
        		CONTEXT_PATH = super.getServletConfig().getServletContext().getContextPath(),
        		CHECK_DO = "/check.do";
           	_response.sendRedirect(CONTEXT_PATH + CHECK_DO);
    	}
    }
	
	private String pullOffCookies(Cookie[] cookies) 
    {    	
    	if (cookies == null) return this.EMPTY;
    	String cookieLogin = this.EMPTY;    	
        for (int index = 0; index < cookies.length; index++)
            if (cookies[index].getName().equals("cookieLogin"))
            {                
            	cookieLogin = cookies[index].getValue();   
                break;                
            }
        return cookieLogin;        
    }
}