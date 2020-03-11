package workspace.design;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MyStack {


    private int[] elements;
    private int size;
    private int count;
    private static final int LOAD_FACTOR = 2;


    public MyStack() {
        this.size = 8;
        this.elements = new int[this.size];
        this.count = 0;
    }

    public void push(int e) {
        if (count == size) {
            elements = Arrays.copyOf(elements, size * LOAD_FACTOR);
        }
        elements[count++] = e;
    }

    public int pop() {
        if (count == 0) throw new IllegalArgumentException("stack is emptty");
        return elements[count--];
    }

    public int peek() {
        if (count == 0) throw new IllegalArgumentException("stack is emptty");
        return elements[count - 1];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();

        IntStream.range(1, 10).forEach(i -> {
            myStack.push(i);
        });


        System.out.println(myStack.peek());//9
        System.out.println(myStack.size());//9
        for (int i = 0; i < 9; i++) {
            System.out.println(myStack.pop());
        }
        System.out.println(myStack.isEmpty());//true
        myStack.pop();//报错：java.lang.IllegalArgumentException: Stack is empty.

    }

}
