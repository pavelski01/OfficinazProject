package capital.officinaz.srvt;

import capital.officinaz.utility.Utility;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SignUp extends HttpServlet
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
        String login = _request.getParameter("login");
        String password = _request.getParameter("password");
        String authentication = login + "/" + password;
        ArrayList<String> data = Utility.readData();
        String[] stored;
        String st = this.EMPTY;
        boolean flag = false;
        for (String dat : data)
        {     
        	stored = dat.split("/");
        	st = stored[0];                                        
            if (login.equals(st) || password.equals(this.EMPTY))
            {
            	flag = true;
            	final String 
    				CONTEXT_PATH = _request.getContextPath(), 
    				ERROR_SITE = "/content/jsf/sign-up-error.xhtml";
            	_response.sendRedirect(CONTEXT_PATH + ERROR_SITE);
            	break;
            } 
        }
        if (!flag)
        {
	        Utility.writeData(authentication);
	        final String 
				CONTEXT_PATH = _request.getContextPath(), 
				ERROR_SITE = "/content/jsf/sign-up-authorized.xhtml";
	        _response.sendRedirect(CONTEXT_PATH + ERROR_SITE);
        }
	}    
}