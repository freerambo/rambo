package workspace;

import java.util.stream.Stream;


/**
 * 「备忘录」或者「dp table」的方法来优化递归树
 *
 * 计算机解决问题其实没有任何奇技淫巧，它唯一的解决办法就是穷举，穷举所有可能性。算法设计无非就是先思考“如何穷举”，然后再追求“如何聪明地穷举”。
 * 列出动态转移方程，就是在解决“如何穷举”的问题。之所以说它难，一是因为很多穷举需要递归实现，二是因为有的问题本身的解空间复杂，不那么容易穷举完整。
 * 备忘录、DP table 就是在追求“如何聪明地穷举”。用空间换时间的思路，是降低时间复杂度的不二法门，除此之外，试问，还能玩出啥花活？
 *
 *「最优子结构」
 * 1、遍历的过程中，所需的状态必须是已经计算出来的。
 * 2、遍历的终点必须是存储结果的那个位置。
 */
public class CoinChanges {


    public static void main(String[] args) {
        int sum = 17;

        int[] coins = {5, 2, 1};

        Stream.of(coins).anyMatch((val) -> {
            return true;
        });

        System.out.println(dp(sum, coins));

        System.out.println("-------------------------");

        System.out.println(dp_table(sum, coins));


    }


    static int dp(int sum, int[] coins) {

        int res = Integer.MAX_VALUE;

        if (sum == 0) return 0;

        if (sum < 0) return -1;

        for (int i = 0; i < coins.length; i++) {
            int sub = dp(sum - coins[i], coins);
            if (sub == -1) continue;
//            System.out.println("log - " + coins[i]);
            res = Math.min(res, 1 + sub);
        }

        return res != Integer.MAX_VALUE ? res : -1;
    }

    static int dp_table(int sum, int[] coins) {

        int[] res = new int[sum+1];
        res[0] = 0;
        res[1] = 1;
        res[2] = 1;
        res[3] = 2;
        res[4] = 2;
        res[5] = 1;

        for (int i = 6; i <= sum; i++) {
            int sub = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                sub = Math.min(sub, res[i - coins[j]]);
            }
            res[i] = 1 + sub;
        }

        return res[sum];
    }
}
