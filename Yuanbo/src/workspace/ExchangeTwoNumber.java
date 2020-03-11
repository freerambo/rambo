package workspace;

public class ExchangeTwoNumber {

    public static void main(String[] args) {
        int a = 8, b = 9;

        System.out.printf("a=%d;b=%d\n", a, b);

        a = a ^ b;
        b = a ^ b; // b = a
        a = a ^ b;

        System.out.printf("a=%d;b=%d\n", a, b);
    }
}
