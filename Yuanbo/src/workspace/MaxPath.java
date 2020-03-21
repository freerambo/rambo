package workspace;

/**
 * 求二叉树中最大路径和
 * 后序遍历
 */
public class MaxPath {

    static int ans = Integer.MIN_VALUE;

    static int oneSideMax(TreeNode root) {
        if (root == null) return 0;
        int left = Math.max(0, oneSideMax(root.left));
        int right = Math.max(0, oneSideMax(root.right));
        ans = Math.max(ans, left + right + root.val);
        return Math.max(left, right) + root.val;
    }

    public static void main(String[] args) {

        Integer maxPath = oneSideMax(TraverseDemo.getTreeNode());
        System.out.println(maxPath);
    }
}
