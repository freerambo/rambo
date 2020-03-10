package workspace;

/**
 * 二分查找：思路很简单，细节是魔鬼。很多人喜欢拿整型溢出的 bug 说事儿，
 * 但是二分查找真正的坑根本就不是那个细节问题，而是在于到底要给 mid 加一还是减一，while 里到底用 <= 还是 <。
 */
public class BinarySearch {


    public static void main(String[] args) {

        int nums[] = {1, 3, 4, 5, 7, 9};
        int target = 8;

        System.out.println(binarySearch(nums, target));
    }

    static int binarySearch(int[] nums, int target) {


        int left = 0, right = nums.length - 1;

        int index = -1;
        while (left <= right) {

            int mid = left + (right - left) / 2; // 防止了 left 和 right 相加导致溢出。
            System.out.println(mid);

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) right = mid - 1;
            else if (nums[mid] < target) left = mid + 1;

        }
        return -1;
    }
}
