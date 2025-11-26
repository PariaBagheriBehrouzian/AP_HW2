public class Beverage extends MenuItem {
    private String size;
    private String temperature;

    public Beverage(String name, double price, String size, String temperature){
        super(name, price, "Beverage");
        this.size = size;
        this.temperature = temperature;
    }

    @Override
    public String getDetails(){
        String si = size;
        if(si.length()>0) si = si.toLowerCase();
        String t = temperature.toLowerCase();
        return "ID: " + getItemId() + ", Name: " + getName() + ", Price: " + (int)getPrice() + ", Category: Beverage, Size: " + si + ", Temperature: " + t;
    }
}
