package capital.officinaz.bean;

import capital.officinaz.utility.Utility;

import java.io.Serializable;
import java.util.Random;

public class Selection implements Serializable
{    
	private static final long serialVersionUID = 1L;
	private static Random random = new Random();
	private static int low = 1, low2 = 1, high = 3, high2 = 2;
	
	public String getStart()
	{ 
		Utility.initialize();
		return null;
	}
	
	public int getRandom()
	{ return random.nextInt(high - low + 1) + low; }
	
	public int getRandom2()
	{ return random.nextInt(high2 - low2 + 1) + low2; }
}