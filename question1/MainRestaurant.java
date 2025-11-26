import java.util.ArrayList;

public class MainRestaurant{
    public static void main(String[] args){
        ArrayList<Employee> e = new ArrayList<>();
        e.add(new Employee("Ali","09120000001","chef",3000000));
        e.add(new Employee("Sara","09120000002","accountant",2500000));
        e.add(new Employee("Reza","09120000003","waiter",2200000));

        ArrayList<Customer> c = new ArrayList<>();
        c.add(new Customer("Omid","09130000001"));
        c.add(new Customer("Neda","09130000002"));
        c.add(new Customer("Mina","09130000003"));
        c.add(new Customer("Hassan","09130000004"));
        c.add(new Customer("Laleh","09130000005"));

        ArrayList<MenuItem> m = new ArrayList<>();
        m.add(new Food(1,"Pizza",400000,"food","medium",25));
        m.add(new Food(2,"Kebab",300000,"food","mild",20));
        m.add(new Beverage(3,"Tea",60000,"beverage","large","hot"));
        m.add(new Beverage(4,"Soda",30000,"beverage","small","cold"));
        m.add(new Food(5,"Pasta",200000,"food","spicy",15));

        ArrayList<Order> o = new ArrayList<>();
        for(int i=0;i<c.size();i++){
            Customer cc = c.get(i);
            for(int j=0;j<3;j++){
                ArrayList<MenuItem> it = new ArrayList<>();
                it.add(m.get((i+j)%m.size()));
                it.add(m.get((i+j+1)%m.size()));
                it.add(m.get((i+j+2)%m.size()));
                Order or = new Order(cc,it);
                or.calculateTotal();
                o.add(or);
            }
        }

        e.get(0).addHoursWorked(178);
        e.get(1).addHoursWorked(170);
        e.get(2).addHoursWorked(165);

        System.out.println("Customers info:");
        for(Customer cc : c) System.out.println(cc.getInfo());

        System.out.println("\nEmployees info and salary:");
        for(Employee ee : e){
            System.out.println(ee.getInfo() + ", Calculated Salary: " + ee.calculateSalary());
        }

        System.out.println("\nMenu items:");
        for(MenuItem mm : m) System.out.println(mm.getDetails());

        System.out.println("\nOrders summary:");
        for(Order or : o) System.out.println(or.getOrderSummary());

        Customer best = c.get(0);
        for(Customer cc : c) if(cc.getLoyaltyPoints() > best.getLoyaltyPoints()) best = cc;
        System.out.println("\nMost loyal customer: " + best.getInfo());
    }
}

abstract class Person{
    private String name;
    private String phone;
    public Person(String n,String p){ name=n; phone=p; }
    public String getName(){ return name; }
    public String getPhone(){ return phone; }
    public void setName(String s){ name=s; }
    public void setPhone(String s){ phone=s; }
    public abstract String getInfo();
}

class Customer extends Person{
    private static int cnt = 0;
    private String id;
    private int points;
    public Customer(String n,String p){
        super(n,p);
        cnt++; id = String.format("C%03d", cnt);
        points = 0;
    }
    public void addLoyaltyPoints(long sum){
        if(sum > 1000000) points += 2;
        else if(sum > 500000) points += 1;
    }
    public int getDiscount(){
        if(points > 5) return 10;
        if(points > 3) return 5;
        return 0;
    }
    public String getCustomerId(){ return id; }
    public int getLoyaltyPoints(){ return points; }
    public String getInfo(){
        return "ID: " + id + ", Name: " + getName() + ", Phone: " + getPhone() + ", Loyalty Points: " + points;
    }
}

class Employee extends Person{
    private static int cnt = 0;
    private String id;
    private String position;
    private long baseSalary;
    private int hoursWorked;
    public Employee(String n,String p,String pos,long sal){
        super(n,p);
        cnt++; id = String.format("E%03d", cnt);
        position = pos; baseSalary = sal; hoursWorked = 0;
    }
    public void addHoursWorked(int h){ hoursWorked += h; }
    public long calculateSalary(){
        if(hoursWorked <= 160) return baseSalary;
        int extra = hoursWorked - 160;
        double add = ((double)extra / 160.0) * (baseSalary * 1.5);
        return baseSalary + (long)Math.round(add);
    }
    public String getInfo(){
        return "ID: " + id + ", Name: " + getName() + ", Phone:" + getPhone() + ", Position: " + position + ", HoursWorked: " + hoursWorked;
    }
}

abstract class MenuItem{
    private int id;
    private String name;
    private long price;
    private String category;
    public MenuItem(int i,String n,long p,String c){ id=i; name=n; price=p; category=c; }
    public int getItemId(){ return id; }
    public String getName(){ return name; }
    public long getPrice(){ return price; }
    public String getCategory(){ return category; }
    public abstract String getDetails();
}

class Food extends MenuItem{
    private String spice;
    private int prep;
    public Food(int i,String n,long p,String c,String s,int pr){ super(i,n,p,c); spice=s; prep=pr; }
    public String getDetails(){
        return "ID: " + getItemId() + ", Name: " + getName() + ", Price: " + getPrice() + ", Category: food, Spice: " + cap(spice) + ", Preparation Time: " + prep + "min";
    }
    private String cap(String s){ if(s==null||s.length()==0) return s; return s.substring(0,1).toUpperCase() + s.substring(1); }
}

class Beverage extends MenuItem{
    private String size;
    private String temp;
    public Beverage(int i,String n,long p,String c,String s,String t){ super(i,n,p,c); size=s; temp=t; }
    public String getDetails(){
        return "ID: " + getItemId() + ", Name: " + getName() + ", Price: " + getPrice() + ", Category: Beverage, Size: " + size + ", Temperature: " + temp;
    }
}

class Order{
    private static int cnt = 0;
    private int id;
    private Customer customer;
    private ArrayList<MenuItem> items;
    private long total;
    public Order(Customer c,ArrayList<MenuItem> it){ customer=c; items=it; cnt++; id=cnt; total=0; }
    public void calculateTotal(){
        long s = 0;
        for(MenuItem m : items) s += m.getPrice();
        customer.addLoyaltyPoints(s);
        int d = customer.getDiscount();
        total = s - (s * d / 100);
    }
    public String getOrderSummary(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append(", Customer: ").append(customer.getName()).append(", Total Amount: ").append(total).append("\nItems: ");
        for(int i=0;i<items.size();i++){
            sb.append(items.get(i).getName());
            if(i<items.size()-1) sb.append(" - ");
        }
        return sb.toString();
    }
}
