package workspace;

public class RegexTest {
    public static void main(String[] args) {
        String s = "   _ - . ZZaz097jhjhj";
         s = "";
//s=null;

        System.out.println(s.matches("^[A-Za-z0-9-_. ]*$"));;
    }
}
