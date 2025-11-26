import java.text.DecimalFormat;

public class Customer extends Person {
    private static int ctr = 0;
    private String customerId;
    private int loyaltyPoints;

    public Customer(String name, String phoneNumber){
        super(name, phoneNumber);
        ctr++;
        this.customerId = String.format("C%03d", ctr);
        this.loyaltyPoints = 0;
    }

    public String getCustomerId(){ return customerId; }
    public int getLoyaltyPoints(){ return loyaltyPoints; }

    public void addLoyaltyPoints(double orderAmount){
        if(orderAmount > 1000000) loyaltyPoints += 2;
        else if(orderAmount > 500000) loyaltyPoints += 1;
    }

    public double getDiscount(){
        if(loyaltyPoints > 5) return 0.10;
        if(loyaltyPoints > 3) return 0.05;
        return 0.0;
    }

    @Override
    public String getInfo(){
        return "ID: " + customerId + ", Name: " + getName() + ", Phone: " + getPhoneNumber() + ", Loyalty Points: " + loyaltyPoints;
    }
}
