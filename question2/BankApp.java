import java.util.*;
public class BankApp {
    static HashMap<String, User> byUser = new HashMap<>();
    static HashMap<String, User> byCard = new HashMap<>();
    static Random rnd = new Random();
    static User cur = null;
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        while(true){
            if(!s.hasNextLine()) break;
            String line = s.nextLine().trim();
            if(line.length()==0) continue;
            if(line.startsWith(\"register \")){
                handleRegister(line);
            } else if(line.startsWith(\"login \")){
                handleLogin(line);
            } else if(line.equals(\"show balance\")){
                if(cur==null) System.out.println(\"Error: You should login first.\");
                else System.out.println(\"Current balance: \" + cur.getBalance());
            } else if(line.startsWith(\"deposit \")){
                if(cur==null) System.out.println(\"Error: You should login first.\");
                else {
                    String[] t = line.split(\" \");
                    try{
                        double a = Double.parseDouble(t[1]);
                        if(a<=0){ System.out.println(\"Error: invalid amount.\"); }
                        else{ cur.deposit(a); System.out.println(\"Deposit successful. Current balance: \" + cur.getBalance()); }
                    }catch(Exception ex){ System.out.println(\"Error: invalid amount.\"); }
                }
            } else if(line.startsWith(\"withdraw \")){
                if(cur==null) System.out.println(\"Error: You should login first.\");
                else {
                    String[] t = line.split(\" \");
                    try{
                        double a = Double.parseDouble(t[1]);
                        if(a<=0){ System.out.println(\"Error: invalid amount.\"); }
                        else{
                            boolean ok = cur.withdraw(a);
                            if(ok) System.out.println(\"Withdrawal successful. Current balance: \" + cur.getBalance());
                            else System.out.println(\"Error: insufficient balance.\");
                        }
                    }catch(Exception ex){ System.out.println(\"Error: invalid amount.\"); }
                }
            } else if(line.startsWith(\"transfer \")){
                if(cur==null) { System.out.println(\"Error: You should login first.\"); }
                else {
                    String[] t = line.split(\" \");
                    if(t.length<3){ System.out.println(\"Error: invalid command.\"); continue; }
                    String card = t[1];
                    double a=0;
                    try{ a = Double.parseDouble(t[2]); } catch(Exception ex){ System.out.println(\"Error: invalid amount.\"); continue; }
                    User dest = byCard.get(card);
                    if(dest==null){ System.out.println(\"Error: invalid card.\"); continue; }
                    if(a<=0){ System.out.println(\"Error: invalid amount.\"); continue; }
                    if(cur.getBalance() + 1e-9 < a){ System.out.println(\"Error: insufficient balance.\"); continue; }
                    cur.withdraw(a);
                    dest.deposit(a);
                    System.out.println(\"Transferred successfully.\");
                }
            } else if(line.equals(\"logout\")){
                if(cur==null) System.out.println(\"Error: No user logged in.\");
                else{ cur = null; System.out.println(\"Logout successful.\"); }
            } else if(line.equals(\"exit\")){
                System.out.println(\"Goodbye!\");
                break;
            } else {
                System.out.println(\"invalid command\");
            }
        }
        s.close();
    }

    static void handleRegister(String line){
        String rest = line.substring(9).trim();
        String[] parts = rest.split(\" \");
        if(parts.length < 4){ System.out.println(\"Error: invalid command.\"); return; }
        String username = parts[0];
        String password = parts[1];
        String phone = parts[parts.length-2];
        String email = parts[parts.length-1];
        StringBuilder sb = new StringBuilder();
        for(int i=2;i<parts.length-2;i++){
            if(i>2) sb.append(\" \");
            sb.append(parts[i]);
        }
        String fullname = sb.toString();
        if(byUser.containsKey(username)){ System.out.println(\"Error: username already exists.\"); return; }
        if(!validPhone(phone)){ System.out.println(\"Error: invalid phone number.\"); return; }
        if(!validEmail(email)){ System.out.println(\"Error: invalid email.\"); return; }
        if(!validPassword(password)){ System.out.println(\"Error: invalid password.\"); return; }
        String card = genCard();
        while(byCard.containsKey(card)) card = genCard();
        User u = new User(username, password, fullname, card, phone, email);
        byUser.put(username, u);
        byCard.put(card, u);
        System.out.println(\"Registered successfully.\");
        System.out.println(\"Assigned card number: \" + card);
    }

    static void handleLogin(String line){
        String[] t = line.split(\" \");
        if(t.length < 3){ System.out.println(\"Error: invalid command.\"); return; }
        String username = t[1];
        String password = t[2];
        User u = byUser.get(username);
        if(u==null){ System.out.println(\"Error: invalid username or password.\"); return; }
        if(!u.getPassword().equals(password)){ System.out.println(\"Error: invalid username or password.\"); return; }
        cur = u;
        System.out.println(\"Login successful.\");
    }

    static boolean validPhone(String ph){
        if(ph.length()!=11) return false;
        if(!ph.startsWith(\"09\")) return false;
        for(int i=0;i<ph.length();i++) if(!Character.isDigit(ph.charAt(i))) return false;
        return true;
    }
    static boolean validEmail(String em){
        int at = em.indexOf('@');
        if(at <= 0) return false;
        String after = em.substring(at+1);
        if(!after.equals(\"aut.com\")) return false;
        if(em.charAt(0)=='.') return false;
        return true;
    }
    static boolean validPassword(String pw){
        if(pw.length() < 8) return false;
        boolean up=false, lo=false, dig=false, sp=false;
        String specials = \"@!&$\";
        for(char ch: pw.toCharArray()){
            if(Character.isUpperCase(ch)) up=true;
            else if(Character.isLowerCase(ch)) lo=true;
            else if(Character.isDigit(ch)) dig=true;
            else if(specials.indexOf(ch) >= 0) sp=true;
        }
        return up && lo && dig && sp;
    }
    static String genCard(){
        StringBuilder sb = new StringBuilder();
        sb.append(\"6037\");
        for(int i=0;i<12;i++) sb.append(rnd.nextInt(10));
        return sb.toString();
    }
}
