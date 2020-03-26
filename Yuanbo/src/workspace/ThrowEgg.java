package workspace;

public class ThrowEgg {

    /**
     * dp[K][N] : K eggs N floors
     * dp[K][N] = max{dp[K][N-i], dp[K-1][i] + 1}
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(superEggDrop(100, 100));
        System.out.println(dp(100, 100));

    }

    static int dp(int K, int N) {
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) {
            // 最坏情况下的最少扔鸡蛋次数
            res = Math.min(res,
                    Math.max(
                            dp(K - 1, i - 1), //碎
                            dp(K, N - i)      // 没碎
                    ) + 1

            );
        }
        return res;
    }

    static int superEggDrop(int K, int N) {
        // m 最多不会超过 N 次（线性扫描）
        int[][] dp = new int[K + 1][N + 1];
        // base case:
        // dp[0][..] = 0
        // dp[..][0] = 0
        // Java 默认初始化数组都为 0
        int m = 0;
        while (dp[K][m] < N) {
            m++;
            for (int k = 1; k <= K; k++)
                dp[k][m] = dp[k][m - 1] + dp[k - 1][m - 1] + 1;
        }
        return m;
    }
}
