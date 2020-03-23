package workspace.dp;

public class Fib {

    static int fib(int N) {
        if (N == 1 || N == 2) return 1;
        return fib(N - 1) + fib(N - 2);
    }

    static int fibDP(int N) {
        int[] res = new int[N];
        res[0] = 1;
        res[1] = 1;

        for (int i = 2; i < N; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }

        return res[N - 1];
    }


    static int fibDPOptMal(int N) {
        int res = 0;
        int pre = 1, cur = 1;


        for (int i = 2; i < N; i++) {
            res = pre + cur;
            pre = cur;
            cur = res;
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(fib(6));
        System.out.println(fibDP(6));
        System.out.println(fibDPOptMal(6));

    }
}
