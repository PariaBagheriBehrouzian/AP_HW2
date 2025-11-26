public class Food extends MenuItem {
    private String spiceLevel;
    private int preparationTime;

    public Food(String name, double price, String spiceLevel, int preparationTime){
        super(name, price, "food");
        this.spiceLevel = spiceLevel;
        this.preparationTime = preparationTime;
    }

    @Override
    public String getDetails(){
        String s = spiceLevel;
        if(s.length()>0) s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
        return "ID: " + getItemId() + ", Name: " + getName() + ", Price: " + (int)getPrice() + ", Category: food, Spice: " + s + ", Preparation Time: " + preparationTime + "min";
    }
}
