package workspace;

public class ReverseList<T extends Number> {


    static class Node<T> {
        Node<T> next;
        T value;

        Node(T value) {
            this.value = value;
            this.next = null;
        }
    }

    static Node formChain() {
        Node<Integer> head = new Node(0);
        Node<Integer> head1 = new Node(1);
        Node<Integer> head2 = new Node(2);
        Node<Integer> head3 = new Node(3);

        head.next = head1;
        head1.next = head2;
        head2.next = head3;

        return head;
    }

    static Node reverseChain(Node head) {

        if (head == null || head.next == null) return head;


        Node<Integer> pre = head;
        Node<Integer> cur = head.next;
        Node<Integer> temp = null;

        head.next = null;


        System.out.println("\ntest " + pre.value);

        System.out.println("\ntest " + cur.value);


        while (cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }

        return pre;
    }

    /**
     * 遍历，将当前节点的下一个节点缓存后更改当前节点指针
     */
    public static Node reverse2(Node head) {
        if (head == null)
            return head;
        Node pre = head;// 上一结点
        Node cur = head.next;// 当前结点
        Node tmp;// 临时结点，用于保存当前结点的指针域（即下一结点）
        while (cur != null) {// 当前结点为null，说明位于尾结点
            tmp = cur.next;
            cur.next = pre;// 反转指针域的指向

            // 指针往下移动
            pre = cur;
            cur = tmp;
        }
        // 最后将原链表的头节点的指针域置为null，还回新链表的头结点，即原链表的尾结点
        head.next = null;

        return pre;
    }

    static void printChain(Node head) {
        System.out.println("printChain");
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + " - ");
            cur = cur.next;
        }
    }

    public static void main(String[] args) {

        System.out.println("{SSHA512}RNOSA1tHq5IM0GEJjMB65eIbeeMqCKqoXakK7ESe9Nkao/LKk83RENITz1P+Nf89RRAEwJasB4WeY1juxCOaO2JlZGM3ODliLWQzNjctNGRiNi1lYmE1LTFkM2YzNzljNTYwMQ==".length());

        Node head = formChain();

        printChain(head);

        Node reversehead = reverseChain(head);

        printChain(reversehead);
    }
}
