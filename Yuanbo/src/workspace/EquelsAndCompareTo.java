package workspace;

public class EquelsAndCompareTo {

    public static void main(String[] args) {

        System.out.println("foo".equals(null)); //false

        System.out.println("foo".compareTo(null)); //NullPointerException

    }

}