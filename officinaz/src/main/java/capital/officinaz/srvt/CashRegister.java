package capital.officinaz.srvt;

import capital.officinaz.dto.Commodity;
import capital.officinaz.utility.Utility;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CashRegister extends HttpServlet
{    
	private static final long serialVersionUID = 1L;
	private final String EMPTY = "";
    
    public void init() throws ServletException {}
 	public void destroy() {}
    
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) 
		throws IOException, ServletException
    {
        _response.setContentType("text/html");
        _response.setHeader(
    		"cache-control", "private, no-cache, no-store, must-revalidate, "
				+ "pre-check=0, post-check=0"
		);
        _response.setHeader("pragma", "no-cache");
        _response.setDateHeader("expires", 0);
        Object objClientLogin = _request.getSession().getAttribute("clientLogin");
        String clientLogin = (objClientLogin == null) ? this.EMPTY : objClientLogin.toString();
        if (clientLogin == null || clientLogin.equals(this.EMPTY))
        	clientLogin = "unidentified/guest";
        String login = clientLogin.split("/")[0];
    	boolean disableFlag = Utility.checkSubscription(login);
    	_request.getSession().setAttribute("disable", disableFlag);
        String privilege = clientLogin.split("/")[1];
        if (privilege.equals("client"))
        {
	        HashMap<Integer, Commodity> shop = Utility.packData();
	        _request.getSession().setAttribute("assortment", shop);
	        _request.getSession().setAttribute("counted", shop.size());
            final String 
            	ASSORTMENT_SITE = "/content/jsp/assortment.jsp",
    			CONTEXT_PATH = _request.getContextPath();
            _response.sendRedirect(CONTEXT_PATH + ASSORTMENT_SITE);
        }
        else
        {
        	final String
        		CONTEXT_PATH = _request.getContextPath(),
        		ERROR_SITE = "/content/jsf/sign-in-error.xhtml";
        	_response.sendRedirect(CONTEXT_PATH + ERROR_SITE);
        }
    }
}