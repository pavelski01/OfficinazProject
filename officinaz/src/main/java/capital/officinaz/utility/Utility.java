package capital.officinaz.utility;

import capital.officinaz.dto.Commodity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utility
{
	private static String TXT_DIRECTORY;
	private final static String BRAND = "Officinaz";	
	private final static String SEPARATOR = "/";
	private final static String NEW_LINE = System.getProperty("line.separator");
	private final static String WORKSPACE = System.getProperty("user.home").replace("\\", "/") + 
		SEPARATOR + BRAND + "Project" + SEPARATOR;	
	private final static String GADGET = "gadgets.txt";
	private final static String
		MESSAGE = BRAND + "Message.txt",
		STORAGE = BRAND + "Storage.txt",
		SHOP = BRAND + "Shop.txt",
		SUBSCRIBE = BRAND + "Subscription.txt",
		TRANSACTION = BRAND + "Transaction.txt";
	private final static String[] MESSAGES = 
	{
		"LOGIN" + SEPARATOR + "PASSWORD", 
		"NAME" + SEPARATOR + "ID" + SEPARATOR + "PRICE" + SEPARATOR + "QUANTITY", 
		"LOGIN" + SEPARATOR + "NAME" + SEPARATOR + "ID" + SEPARATOR + "PRICE" + SEPARATOR + "QUANTITY",
		"LOGIN" + SEPARATOR + "SURNAME" + SEPARATOR + "FORENAME" + SEPARATOR + "MAIL" + SEPARATOR + 
			"ADDRESS" + SEPARATOR + "PHONE" + SEPARATOR + "COMPANY" + SEPARATOR + "LANGUAGE" + SEPARATOR + "MESSAGE",
		"LOGIN" + SEPARATOR + "NAME" + SEPARATOR + "QUALITY" + SEPARATOR + "PRICE" + SEPARATOR + "DESCRIPTION"
	};
	
	static 
	{
		TXT_DIRECTORY = Utility.class.getProtectionDomain().
			getCodeSource().getLocation().getPath().replace("\\", "/");
		for (int i = 0; i < 2; i++)
			TXT_DIRECTORY = TXT_DIRECTORY.substring(0, TXT_DIRECTORY.lastIndexOf(SEPARATOR));
		TXT_DIRECTORY = TXT_DIRECTORY + SEPARATOR + "content" + SEPARATOR + "txt"  + SEPARATOR;
		createSpecificFile(WORKSPACE + STORAGE, MESSAGES[0]);
		boolean flag = false;
		flag = createSpecificFile(WORKSPACE + SHOP, MESSAGES[1]);
		createSpecificFile(WORKSPACE + TRANSACTION, MESSAGES[2]);
		createSpecificFile(WORKSPACE + SUBSCRIBE, MESSAGES[3]);
		createSpecificFile(WORKSPACE + MESSAGE, MESSAGES[4]);
		if (flag) transferDataFromSrcToTrg(TXT_DIRECTORY + GADGET, WORKSPACE + SHOP);
	}
	
	public static void initialize() {}
	
	public static boolean checkSubscription(String _login)
	{
		boolean flag = false;
		if (_login == null || _login.equals("")) return flag;
		flag = true;
		File file = new File(WORKSPACE + SUBSCRIBE);
		try
        {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);            
            String line = bufferedReader.readLine();
            if (!line.equals(MESSAGES[3]))
        	{
            	bufferedReader.close();
            	flag = false;
            	return flag;
        	}
            while ((line = bufferedReader.readLine()) != null)
            {                
               line = line.split("/")[0];
               if (line.equals(_login))
        	   {
            	   flag = false;
            	   break;
        	   }
            }
            bufferedReader.close();
            fileReader.close();
        }
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
		return flag;
	}
	
	public static HashMap<Integer, Commodity> packData()
	{       
        HashMap<Integer, Commodity> stuff = new HashMap<Integer, Commodity>();
        File file = new File(WORKSPACE + SHOP);
        try
        {            
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);            
            String line, name;
            int id, price, quantity;
            int map = 0;
            line = bufferedReader.readLine();
            if (!line.equals(MESSAGES[1]))
        	{
            	bufferedReader.close();
            	return null;
        	}
            while ((line = bufferedReader.readLine()) != null)
            {                
               name = line.split("/")[0];
               id = Integer.parseInt(line.split("/")[1]);
               price = Integer.parseInt(line.split("/")[2]);
               quantity = Integer.parseInt(line.split("/")[3]);
               stuff.put(map++, new Commodity(name, id, price, quantity));
            }
            bufferedReader.close();
            fileReader.close();            
        }
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
        return stuff;       
    }
	
	public static void updateData(HashMap<Integer, Integer> _cart, String _login, String _date)
	{
        File file = new File(WORKSPACE + SHOP);
        try
        {   
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);           
            String text = "", purchased = "";
            String line, name, id, price;
            int quantity, capacity, nr = 0;
            text += line = bufferedReader.readLine() + NEW_LINE;
            while ((line = bufferedReader.readLine()) != null)
            {               
               if (_cart.get(nr) != null)
               {
                    name = line.split("/")[0];
                    id = line.split("/")[1];
                    price = line.split("/")[2];
                    quantity = Integer.parseInt(line.split("/")[3]);
                    capacity = _cart.get(nr);
                    quantity = quantity - capacity;
                    line = name + "/" + id + "/" + price + "/" + Integer.toString(quantity);
                    purchased += 
                		NEW_LINE + _login + "/" + name + "/" + id + "/" + price + "/" + 
        					Integer.toString(capacity) + "/" + _date;                    
               }
               text += line + NEW_LINE;
               nr++;
        	}
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(file);
        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        	bufferedWriter.write(text);
        	bufferedWriter.close();
        	fileWriter = new FileWriter(WORKSPACE + TRANSACTION, true);
        	bufferedWriter = new BufferedWriter(fileWriter);
        	bufferedWriter.write(purchased);
        	bufferedWriter.close();
        	fileWriter.close();        	
       }
       catch (FileNotFoundException fnfEx) {}
       catch (IOException ioEx) {}    
    }
	
	public static ArrayList<String> readData()
	{		
        File file = new File(WORKSPACE + STORAGE);        
        ArrayList<String> readedList = null;
        try
        {
        	FileReader fileReader = new FileReader(file);            
            	BufferedReader bufferedReader = new BufferedReader(fileReader);        
            	readedList = new ArrayList<String>();
            	String line = bufferedReader.readLine();
            	if (!line.equals(MESSAGES[0]))
        	{
            		bufferedReader.close();
            		return null;
        	}
      		while ((line = bufferedReader.readLine()) != null) readedList.add(line);
            	bufferedReader.close();
            	fileReader.close();            
        } 
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
        return readedList;
    }
	
	public static void writeData(String _text)
	{
		File file = new File(WORKSPACE + STORAGE);
        try
        {	
        	FileWriter fileWriter = new FileWriter(file, true);
            	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            	bufferedWriter.newLine();
            	bufferedWriter.write(_text);
            	bufferedWriter.flush();
            	fileWriter.flush();
            	bufferedWriter.close();
            	fileWriter.close();            
        }
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
    }
	
	public static void subscribe(String _text)
	{
		File file = new File(WORKSPACE + SUBSCRIBE);
        try
        {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(_text);
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();            
        }
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
    }
	
	public static void message(String _text)
	{
		File file = new File(WORKSPACE + MESSAGE);
        try
        {
            FileWriter fileWriter = new FileWriter(file, true); 
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(_text);
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();               
        }
        catch (FileNotFoundException fnfEx) {}
        catch (IOException ioEx) {}
	}
	
	private static boolean createSpecificFile(String _path, String _firstLine)
	{
		File file = new File(_path);
		if (!file.exists())
		{
			file.getParentFile().mkdirs(); 
			try { file.createNewFile(); } 
			catch (IOException ioEx) {}			
			if (!_firstLine.equals(""))
			{
				try
				{
					PrintWriter writer = new PrintWriter(_path);
					writer.print(_firstLine);
					writer.close();//works without flush()
				}
				catch (FileNotFoundException fnfEx) {}
			}
			return true;
		}
		return false;
	}
	
	private static void transferDataFromSrcToTrg(String _source, String _target)
	{
		BufferedReader in = null;
		BufferedWriter out = null;
		String line;
		try 
		{ 
			in = new BufferedReader(new FileReader(_source));
			out = new BufferedWriter(new FileWriter(_target, true));//append to end		
			while ((line = in.readLine()) != null) out.append(NEW_LINE + line);
			in.close();
			out.close();
		}
		catch (FileNotFoundException fnfEx) {} 
		catch (IOException ioEx) {}
	}
}
