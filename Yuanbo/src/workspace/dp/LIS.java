package workspace.dp;

import java.util.Arrays;

public class LIS {
    public static void main(String[] args) {
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};


        System.out.println("hello" + dpLis(arr));
    }

    /**
     *  Time complexity O(n^2)
     * @param arr
     * @return
     */
    static int dpLis(int[] arr) {

        if (arr == null) return 0;

        int[] res = new int[arr.length];
        Arrays.fill(res, 1); // fill the array with 1
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    res[i] = Math.max(res[i], res[j] + 1);
                }

            }
        }
        return Arrays.stream(res).summaryStatistics().getMax();
    }
}
