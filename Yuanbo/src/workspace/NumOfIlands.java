package workspace;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NumOfIlands {
    void dfs(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;

        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    dfs(grid, r, c);
                }
            }
        }

        return num_islands;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("new Thread - " + Thread.currentThread().getName());
        }).start();

        Consumer<String> consumer =
                (s) -> System.out.println(Thread.currentThread().getName() + "  - in Consumer : " + s);
        consumer.accept("Test Accept");

        Supplier<String> supplier = () -> "new Supplier";
        System.out.println(supplier.get());

    }
}

