public class User {
    String u;
    String p;
    String f;
    String c;
    String ph;
    String e;
    double b;
    public User(String u, String p, String f, String c, String ph, String e){
        this.u=u; this.p=p; this.f=f; this.c=c; this.ph=ph; this.e=e; this.b=0.0;
    }
    public String getUsername(){ return u; }
    public String getPassword(){ return p; }
    public String getCard(){ return c; }
    public double getBalance(){ return b; }
    public void deposit(double x){ this.b += x; }
    public boolean withdraw(double x){
        if(this.b + 1e-9 >= x){ this.b -= x; return true; }
        return false;
    }
    public String toString(){ return "User{" + u + "," + f + "," + c + "," + b + "}"; }
}
