public class Employee extends Person {
    private static int ctr = 0;
    private String employeeId;
    private String position;
    private double salary;
    private int hoursWorked;

    public Employee(String name, String phoneNumber, String position, double salary){
        super(name, phoneNumber);
        ctr++;
        this.employeeId = String.format("E%03d", ctr);
        this.position = position;
        this.salary = salary;
        this.hoursWorked = 0;
    }

    public String getEmployeeId(){ return employeeId; }
    public String getPosition(){ return position; }
    public void setPosition(String position){ this.position = position; }
    public double getSalary(){ return salary; }
    public void setSalary(double salary){ this.salary = salary; }
    public int getHoursWorked(){ return hoursWorked; }

    public void addHoursWorked(int h){
        if(h > 0) this.hoursWorked += h;
    }

    public double calculateSalary(){
        if(hoursWorked <= 160) return salary;
        int ot = hoursWorked - 160;
        double perHour = salary / 160.0;
        double overtimePay = ot * perHour * 1.5;
        return salary + overtimePay;
    }

    @Override
    public String getInfo(){
        return "ID: " + employeeId + ", Name: " + getName() + ", Phone:" + getPhoneNumber() + ", Position: " + position + ", HoursWorked: " + hoursWorked;
    }
}
