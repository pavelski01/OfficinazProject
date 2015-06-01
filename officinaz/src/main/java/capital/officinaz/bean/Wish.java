package capital.officinaz.bean;

import capital.officinaz.utility.Utility;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

public class Wish implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final String EMPTY = "";	
    private String name, quality, price, description;
    private boolean disableFlag;

    public String getQuality() { return this.quality; }
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public String getPrice() { return this.price; }
    
    public void setQuality(String _quality)
    { this.quality = (_quality != null) ? _quality.toLowerCase() : this.EMPTY; }
    public void setName(String _name) { this.name = (_name != null) ? _name : this.EMPTY; }
    public void setDescription(String _description)
    { 
    	this.description = 
			(_description != null) ? 
				_description.replace("\n", " ").replace("\r", this.EMPTY) : 
				this.EMPTY; 
	}
    public void setPrice(String _price) { this.price = (_price != null) ? _price : this.EMPTY; }
    
    public String navigate()
    {
    	this.disableFlag = false;
    	return "suggestion-show";
    }
    
    public boolean getDisable()
    {
    	if 
    	(
			this.name == null || this.name.equals(this.EMPTY) ||
				this.quality == null || this.quality.equals(this.EMPTY) ||
					this.price == null || this.price.equals(this.EMPTY)
		) this.disableFlag = true;
    	return this.disableFlag;
	}
    
    public String confirm()
    {
    	HttpSession session = 
			(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	String login = (String)session.getAttribute("clientLogin");
    	login = (login != null) ? login.split("/")[0] : this.EMPTY;
    	this.description = (this.description != null) ? this.description : this.EMPTY;
    	if
    	(
			this.name != null && this.quality != null && this.price != null
		)    	
	    	Utility.message(
				login + "/" + this.name + "/" + this.quality + "/" + this.price + "/" + this.description
			);
    	this.disableFlag = !this.disableFlag;
    	return null;
    }

    public void validateQuality(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {        
        String qualityVer = _value.toString().toLowerCase();        
        if
        (
    		!(qualityVer.equals("standard") || qualityVer.equals("elite"))
		)
        {
            FacesMessage msg = new FacesMessage("Choose from the available");
            throw new ValidatorException(msg);       
        }
    }
    
    public void validatePrice(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
    	int priceVer;
        try { priceVer = Integer.parseInt(_value.toString().trim()); } 
        catch (NumberFormatException nfe)
        {
        	FacesMessage msg = 
				new FacesMessage("The value must be a number");
    		throw new ValidatorException(msg);
        }
        if (priceVer < 2 || priceVer > 1000000)
        {
            FacesMessage msg = 
        		new FacesMessage("The price must be between: 2-1000000");
            throw new ValidatorException(msg);
        } 
    }
}
