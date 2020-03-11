package workspace.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class JavaBasic {

    public String input1() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();
        return s;
    }


    public String input2() throws Exception {
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        input.close();
        return s;
    }
}
