import java.util.*;

public class BankApp{
    public static void main(String[] args){
        ArrayList<String[]> u = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        int cur = -1;
        while(true){
            if(!s.hasNextLine()) break;
            String line = s.nextLine().trim();
            if(line.length()==0) continue;
            String[] t = line.split(" ");
            String cmd = t[0];
            if(cmd.equals("register")){
                if(t.length < 6){
                    System.out.println("Error: invalid register command.");
                    continue;
                }
                String un = t[1];
                String pw = t[2];
                String em = t[t.length-1];
                String ph = t[t.length-2];
                String fn = "";
                for(int i=3;i<=t.length-3;i++){
                    if(i>3) fn += " ";
                    fn += t[i];
                }
                boolean ok = true;
                for(String[] a : u) if(a[0].equals(un)) { ok=false; break; }
                if(!ok){ System.out.println("Error: username already exists."); continue; }
                if(!validPhone(ph)){ System.out.println("Error: invalid phone number."); continue; }
                if(!validEmail(em)){ System.out.println("Error: invalid email."); continue; }
                if(!validPass(pw)){ System.out.println("Error: invalid password."); continue; }
                String card = genCard(u);
                String[] arr = new String[7];
                arr[0]=un; arr[1]=pw; arr[2]=fn; arr[3]=ph; arr[4]=em; arr[5]=card; arr[6]="0.0";
                u.add(arr);
                System.out.println("Registered successfully.");
                System.out.println("Assigned card number: " + card);
            } else if(cmd.equals("login")){
                if(t.length<3){ System.out.println("Error: invalid login command."); continue; }
                String un = t[1]; String pw = t[2];
                int idx=-1;
                for(int i=0;i<u.size();i++) if(u.get(i)[0].equals(un) && u.get(i)[1].equals(pw)) { idx=i; break; }
                if(idx==-1) System.out.println("Error: invalid username or password.");
                else{ cur = idx; System.out.println("Login successful."); }
            } else if(cmd.equals("show") && t.length>=2 && t[1].equals("balance")){
                if(cur==-1){ System.out.println("Error: You should login first."); continue; }
                System.out.println("Current balance: " + u.get(cur)[6]);
            } else if(cmd.equals("deposit")){
                if(cur==-1){ System.out.println("Error: You should login first."); continue; }
                if(t.length<2){ System.out.println("Error: invalid deposit command."); continue; }
                double a = parseNum(t[1]);
                if(a<=0){ System.out.println("Error: invalid amount."); continue; }
                double b = Double.parseDouble(u.get(cur)[6]);
                b += a;
                u.get(cur)[6] = String.valueOf(b);
                System.out.println("Deposit successful. Current balance: " + u.get(cur)[6]);
            } else if(cmd.equals("withdraw")){
                if(cur==-1){ System.out.println("Error: You should login first."); continue; }
                if(t.length<2){ System.out.println("Error: invalid withdraw command."); continue; }
                double a = parseNum(t[1]);
                if(a<=0){ System.out.println("Error: invalid amount."); continue; }
                double b = Double.parseDouble(u.get(cur)[6]);
                if(b < a){ System.out.println("Error: insufficient balance."); continue; }
                b -= a;
                u.get(cur)[6] = String.valueOf(b);
                System.out.println("Withdrawal successful. Current balance: " + u.get(cur)[6]);
            } else if(cmd.equals("transfer")){
                if(cur==-1){ System.out.println("Error: You should login first."); continue; }
                if(t.length<3){ System.out.println("Error: invalid transfer command."); continue; }
                String card = t[1];
                double a = parseNum(t[2]);
                if(a<=0){ System.out.println("Error: invalid amount."); continue; }
                int toIdx = -1;
                for(int i=0;i<u.size();i++) if(u.get(i)[5].equals(card)) { toIdx=i; break; }
                if(toIdx==-1){ System.out.println("Error: invalid card number."); continue; }
                double b = Double.parseDouble(u.get(cur)[6]);
                if(b < a){ System.out.println("Error: insufficient balance."); continue; }
                b -= a;
                double bb = Double.parseDouble(u.get(toIdx)[6]);
                bb += a;
                u.get(cur)[6] = String.valueOf(b);
                u.get(toIdx)[6] = String.valueOf(bb);
                System.out.println("Transferred successfully.");
            } else if(cmd.equals("logout")){
                if(cur==-1) System.out.println("Error: No user logged in.");
                else{ cur = -1; System.out.println("Logout successful."); }
            } else if(cmd.equals("exit")){
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Error: unknown command.");
            }
        }
        s.close();
    }

    public static boolean validPhone(String p){
        if(p.length()!=11) return false;
        if(!p.startsWith("09")) return false;
        for(int i=0;i<p.length();i++) if(!Character.isDigit(p.charAt(i))) return false;
        return true;
    }
    public static boolean validEmail(String e){
        int at = e.indexOf('@');
        if(at<=0) return false;
        if(!e.endsWith("aut.com")) return false;
        return true;
    }
    public static boolean validPass(String p){
        if(p.length()<8) return false;
        boolean up=false, lo=false, di=false, sp=false;
        for(int i=0;i<p.length();i++){
            char c = p.charAt(i);
            if(Character.isUpperCase(c)) up=true;
            else if(Character.isLowerCase(c)) lo=true;
            else if(Character.isDigit(c)) di=true;
            else {
                if(c=='@' || c=='!' || c=='&' || c=='$' || c=='?') sp=true;
            }
        }
        return up && lo && di && sp;
    }
    public static String genCard(ArrayList<String[]> u){
        Random r = new Random();
        while(true){
            String s = "6037";
            for(int i=0;i<12;i++) s += r.nextInt(10);
            boolean ok = true;
            for(String[] a : u) if(a[5].equals(s)) { ok=false; break; }
            if(ok) return s;
        }
    }
    public static double parseNum(String x){
        try{ return Double.parseDouble(x); } catch(Exception ex){ return -1; }
    }
}
