package workspace;

public class DoublePointers {

    public static void main(String[] args) {

    }

    static boolean hasCircle(ListNode head) {


        ListNode fast = head, slow = head;

        while (fast != null && fast.next != null) {

            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) return true;
        }
        return false;

    }


    static ListNode findMiddle(ListNode head) {

        ListNode fast = head, slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

        }
        return slow;

    }

    static ListNode findK(ListNode head, int k) {

        ListNode fast = head, slow = head;
        while (k-- > 0) {
            fast = fast.next;
        }

        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;

    }

    static int[] reverseArray(int[] nums) {

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
        return nums;
    }

    int[] twoSum(int[] nums, int target) {

        int right = nums.length - 1;
        int left = 0;
        if (nums[left] >= target) return null;

        while (right > left && nums[right--] > target) ;

        if (right <= left) return null;

        int sum = nums[left] + nums[right];
        if (sum == target) return new int[]{left + 1, right + 1};
        else if (sum < target) {
            left++;
        } else if (sum > target) {
            right--;
        }

        return null;
    }


}

