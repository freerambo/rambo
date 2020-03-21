package workspace;

public class TraverseDemo {

    public static void main(String[] args) {

//        ListNode l1 = getListNode();

//        traverse(l1);
//        reverse(l1);
//        traverseIncur(l1);
//        traverse(reverse(l1));

        TreeNode t1 = getTreeNode();

        traverseTreeNode(t1);

    }

    private static ListNode getListNode() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        return l1;
    }

    public static TreeNode getTreeNode() {
        TreeNode l1 = new TreeNode(1);
        TreeNode l2 = new TreeNode(2);
        TreeNode l3 = new TreeNode(3);
        TreeNode l4 = new TreeNode(4);
        TreeNode l5 = new TreeNode(5);

        l1.left = l2;
        l1.right = l3;
        l2.left = l4;
        l4.right = l5;
        return l1;
    }

    static void traverse(ListNode head) {

        for (ListNode p = head; p != null; p = p.next) {
            // 迭代访问 p.val
            System.out.println("" + p.val);
        }
    }

    static void traverseTreeNode(TreeNode head) {
        if (head == null) return;
        traverseTreeNode(head.left);


        traverseTreeNode(head.right);

        System.out.println(head.val);

    }

    static void traverseIncur(ListNode head) {
        if (head == null) return;
        System.out.println(head.val);
        // 递归访问 head.val
        traverse(head.next);
    }


    static ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode temp = head;
        ListNode next = temp, prev = temp.next;

        while (prev != null) {
            temp = prev;
            prev = prev.next;
            temp.next = next;
            next = temp;

        }

        head.next = null;

        return temp;
    }


}

/* 基本的单链表节点 */
class ListNode {
    public int val;
    public ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}


class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
    }
}