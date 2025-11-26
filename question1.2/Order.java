import java.util.ArrayList;

public class Order {
    private static int ctr = 0;
    private int orderId;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private double totalAmount;

    public Order(Customer customer){
        ctr++;
        this.orderId = ctr;
        this.customer = customer;
        this.items = new ArrayList<MenuItem>();
        this.totalAmount = 0.0;
    }

    public void addItem(MenuItem m){
        if(m != null) items.add(m);
    }

    public void calculateTotal(){
        double sum = 0.0;
        for(MenuItem m : items) sum += m.getPrice();
        customer.addLoyaltyPoints(sum);
        double disc = customer.getDiscount();
        double finalSum = sum - (sum * disc);
        this.totalAmount = finalSum;
    }

    public String getOrderSummary(){
        StringBuilder sb = new StringBuilder();
        sb.append("OrderID: ").append(orderId).append(", Customer: ").append(customer.getName()).append(", Total Amount: ").append((int)totalAmount).append("\n");
        sb.append("Items: ");
        for(int i=0;i<items.size();i++){
            sb.append(items.get(i).getName());
            if(i < items.size()-1) sb.append(" - ");
        }
        return sb.toString();
    }

    public double getTotalAmount(){ return totalAmount; }
}
