package capital.officinaz.dto;

public class Commodity
{    
    private String name;
    private int id, price, quantity;
        
    public Commodity(String _name, int _id, int _price, int _quantity) 
    {            
        this.name = _name;       
        this.id = _id;
        this.price = _price;
        this.quantity = _quantity;        
    }
    
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public int getPrice() { return this.price; }
    public int getQuantity() { return this.quantity; }            
}