package capital.officinaz.bean;

import capital.officinaz.utility.Utility;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

public class Subscription implements Serializable
{    
	private static final long serialVersionUID = 1L;
	private final String EMPTY = "";
	private String 
		forename, surname, company, address, 
		mail, message, phone, language;
	private boolean disableFlag;
    
    public String getForename() { return this.forename; }
    public String getSurname() { return this.surname; }
    public String getCompany() { return this.company; }
    public String getAddress() { return this.address; }
    public String getMail() { return this.mail; }
    public String getMessage() { return this.message; }
    public String getPhone() { return this.phone; }
    public String getLanguage() { return this.language; }
    
    public void setForename(String _forename)
    { this.forename = (_forename != null) ? _forename : this.EMPTY; }
    public void setSurname(String _surname)
    { this.surname = (_surname != null) ? _surname : this.EMPTY; }
    public void setCompany(String _company) 
    { this.company = (_company != null) ? _company : this.EMPTY; }
    public void setAddress(String _address) 
    { this.address = (_address != null) ? _address : this.EMPTY; }
    public void setMail(String _mail) 
    { this.mail = (_mail != null) ? _mail : this.EMPTY; }
    public void setMessage(String _message) 
    { 
    	this.message = 
    		(_message != null) ? 
				_message.replace("\n", " ").replace("\r", this.EMPTY) :
				this.EMPTY;
	}
    public void setPhone(String _phone) 
    { this.phone = (_phone != null) ? _phone : this.EMPTY; }
    public void setLanguage(String _language)
    { this.language = (_language != null) ? _language.toLowerCase() : this.EMPTY; }
    
    public boolean getDisable()
    {
    	HttpSession session = 
			(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	String login = (String)session.getAttribute("clientLogin");
    	login = (login != null) ? login.split("/")[0] : this.EMPTY;
    	this.disableFlag = Utility.checkSubscription(login);
    	session.setAttribute("disable", this.disableFlag);
    	return this.disableFlag;
    }
    
    public String confirm()
    {
    	String login = 
			(String)(
				(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)
			).getAttribute("clientLogin");
    	login = login.split("/")[0];
    	login = (login != null) ? login : this.EMPTY;
    	Utility.subscribe(
			login + "/" + this.surname + "/" + this.forename + "/" + this.mail + "/" + 
				this.address + "/" + this.phone + "/" + this.company + "/" + this.language + "/" +
					this.message
		);
    	this.disableFlag = true;
    	return null;
    }
    
    public void validateMail(FacesContext _ctx, UIComponent _cmp, Object _value) 
		throws ValidatorException
    {
        String value = _value.toString();
        if (value.indexOf("@") < 0) 
        {
            FacesMessage msg = new FacesMessage("Invalid email (insert @)");
            throw new ValidatorException(msg);       
        }    
    }
        
    public void validateForename(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
    	char[] forename = _value.toString().toCharArray();        
        if
        (
    		Character.isLowerCase(forename[0]) ||
    			Character.isDigit(forename[0])
		)
        {
            FacesMessage msg = new FacesMessage(
        		"Forename must begin with a capital letter"
    		);            
            throw new ValidatorException(msg);
        }
    }
    
    public void validateSurname(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
    	char[] surname = _value.toString().toCharArray();        
        if
        (
    		Character.isLowerCase(surname[0]) ||
    			Character.isDigit(surname[0])
		)
        {
            FacesMessage msg = new FacesMessage(
        		"Surname must begin with a capital letter"
    		);            
            throw new ValidatorException(msg);
        }
    }
    
    public void validateLanguage(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
        String language = _value.toString().toLowerCase();        
        if
        (
    		!(
				language.equals("polish") || 
					language.equals("english") || 
						language.equals("russian")
			)
		)
        {
            FacesMessage msg = new FacesMessage(
        		"You must specify one of the available languages"
    		);
            throw new ValidatorException(msg);       
        }
    }    
    
    public void validateCompany(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
        String value = _value.toString();
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(value);
        boolean flag = matcher.find();
        char[] company = value.toCharArray();
        for (char c : company)
        {
	        if (!flag || Character.isDigit(c))
	        {
	            FacesMessage msg = new FacesMessage(
            		"Company name must consist of letters only"
        		);
	            throw new ValidatorException(msg);       
	        }
        }
    }
    
    public void validatePhone(FacesContext _ctx, UIComponent _cmp, Object _value)
		throws ValidatorException
    {
        String phone = _value.toString();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(phone);
        boolean flag = matcher.find();
        if (!flag)
        {
            FacesMessage msg = new FacesMessage(
        		"The phone must be in digital form"
    		);
            throw new ValidatorException(msg);    
        }
    }
}