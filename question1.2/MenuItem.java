public abstract class MenuItem {
    private static int ctr = 0;
    private int itemId;
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category){
        ctr++;
        this.itemId = ctr;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getItemId(){ return itemId; }
    public String getName(){ return name; }
    public double getPrice(){ return price; }
    public String getCategory(){ return category; }

    public void setPrice(double price){ this.price = price; }

    public abstract String getDetails();
}
