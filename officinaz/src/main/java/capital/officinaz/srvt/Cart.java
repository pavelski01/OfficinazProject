package capital.officinaz.srvt;

import capital.officinaz.dto.Commodity;
import capital.officinaz.dto.Purchase;
import capital.officinaz.utility.Utility;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Cart extends HttpServlet
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
        int purchased = 0, quantity = 0, map = 0, id = 0, price = 0, counted = 0;
        String name = "0";
        HashMap<Integer, Commodity> things = new HashMap<Integer, Commodity>();
        ArrayList<Integer> werePurchased = new ArrayList<Integer>();
        HashMap<Integer, Integer> cart = new HashMap<Integer, Integer>();        
        counted = Integer.parseInt(_request.getParameter("counted"));        
        for (int nr = 0; nr < counted; nr++)
        {        
        	purchased = Integer.parseInt(
    			_request.getParameter("obj" + Integer.toString(nr) + "/purchased")
			);
        	map = Integer.parseInt(
    			_request.getParameter("obj" + Integer.toString(nr) + "/map")
			);
	        name = _request.getParameter("obj" + Integer.toString(nr) + "/name");
	        id = Integer.parseInt(_request.getParameter("obj" + Integer.toString(nr) + "/id"));
	        price = Integer.parseInt(_request.getParameter("obj" + Integer.toString(nr) + "/price"));
	        quantity = Integer.parseInt(
        		_request.getParameter("obj" + Integer.toString(nr) + "/quantity")
    		);
	        things.put(map, new Commodity(name, id, price, quantity));
	        werePurchased.add(purchased);
        }
        ArrayList<Purchase> shopping = new ArrayList<Purchase>();
        for (int i = 0; i < counted; i++)
        {            
        	if 
        	(
        		things.get(i).getQuantity() >= werePurchased.get(i) &&
        			werePurchased.get(i) > 0
        	)
        	{           
        		cart.put(i, werePurchased.get(i));
        		Purchase purchase = new Purchase(
        			things.get(i).getName(), werePurchased.get(i),
        			cart.get(i) * things.get(i).getPrice()
        		);
        		shopping.add(purchase);            
        	}  
        }
        _request.getSession().setAttribute("shopping", shopping);
        Object objClientLogin = _request.getSession().getAttribute("clientLogin");
        String clientLogin = (objClientLogin == null) ? this.EMPTY : objClientLogin.toString();
        if (clientLogin == null || clientLogin.equals(this.EMPTY))
        	clientLogin = "unidentified/guest";
        String login = clientLogin.split("/")[0];
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("[dd-MM-yyyy][hh:mm:ss]");
        String now = formatter.format(date);
        Utility.updateData(cart, login, now);        
        final String 
        	PURCHASES_SITE = "/content/jsp/purchases.jsp",
    		CONTEXT_PATH = _request.getContextPath();
        _response.sendRedirect(CONTEXT_PATH + PURCHASES_SITE);
    }
}