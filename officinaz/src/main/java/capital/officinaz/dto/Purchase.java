package capital.officinaz.dto;

public class Purchase
{    
    private String name;
    private int quantity, payment;
    
    public Purchase(String _name, int _quantity, int _payment)
    {        
        this.name = _name;
        this.quantity = _quantity;
        this.payment = _payment;        
    }

    public int getQuantity() { return this.quantity; }
    public String getName() { return this.name; }
    public int getPayment() { return this.payment; }
}