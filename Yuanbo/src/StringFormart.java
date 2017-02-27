//https://www.dotnetperls.com/format-java
public class StringFormart {

	public static void main(String[] args) {

        String first = "Marcus";
        String last = "Aurelius";

        // Use simple string format.
        String value = String.format("%s %s", first, last);
        System.out.println(value);

        // Use indexes before simple string format.
        value = String.format("%1s %2s", first, last);
        System.out.println(value);

        // Use $ symbol before string character.
        value = String.format("%1$s %2$s", first, last);
        System.out.println(value);
    }

}

