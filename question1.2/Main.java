import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Employee e1 = new Employee("Ali Chef", "09110000001", "chef", 2000000);
        Employee e2 = new Employee("Sara Acc", "09110000002", "accountant", 1800000);
        Employee e3 = new Employee("Reza Wait", "09110000003", "waiter", 1500000);

        e1.addHoursWorked(170);
        e2.addHoursWorked(160);
        e3.addHoursWorked(180);

        Customer c1 = new Customer("Ali", "09120000001");
        Customer c2 = new Customer("Mary", "09120000002");
        Customer c3 = new Customer("Nima", "09120000003");
        Customer c4 = new Customer("Sara", "09120000004");
        Customer c5 = new Customer("Omid", "09120000005");

        ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
        menu.add(new Food("Pizza", 400000, "Medium", 25));
        menu.add(new Beverage("Tea", 60000, "large", "hot"));
        menu.add(new Food("Sushi", 750000, "mild", 20));
        menu.add(new Beverage("Soda", 30000, "medium", "cold"));
        menu.add(new Food("Burger", 220000, "spicy", 15));

        Customer[] cs = {c1,c2,c3,c4,c5};
        for(int i=0;i<cs.length;i++){
            for(int j=0;j<3;j++){
                Order o = new Order(cs[i]);
                o.addItem(menu.get((i + j) % menu.size()));
                o.addItem(menu.get((i + j + 1) % menu.size()));
                o.addItem(menu.get((i + j + 2) % menu.size()));
                o.calculateTotal();
            }
        }

        double maxPoints = -1;
        Customer top = null;
        for(Customer c : cs){
            if(c.getLoyaltyPoints() > maxPoints){
                maxPoints = c.getLoyaltyPoints();
                top = c;
            }
        }

        System.out.println("=== Employees info and calculated salary ===");
        System.out.println(e1.getInfo() + ", Calculated Salary: " + (int)e1.calculateSalary());
        System.out.println(e2.getInfo() + ", Calculated Salary: " + (int)e2.calculateSalary());
        System.out.println(e3.getInfo() + ", Calculated Salary: " + (int)e3.calculateSalary());

        System.out.println("\n=== Customers info ===");
        for(Customer c : cs) System.out.println(c.getInfo());

        System.out.println("\n=== Menu items ===");
        for(MenuItem m : menu) System.out.println(m.getDetails());

        System.out.println("\nMost loyal customer: " + (top != null ? top.getInfo() : "none"));
    }
}
