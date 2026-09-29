class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Total path length must be even
        if ((m + n - 1) % 2 == 1) {
            return false;
        }

        // First character must be '('
        if (grid[0][0] == ')') {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][m + n];

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance < m + n; balance++) {

                    if (grid[i][j] == '(') {

                        // Previous cell from top
                        if (i > 0 && balance > 0 &&
                            dp[i - 1][j][balance - 1]) {
                            dp[i][j][balance] = true;
                        }

                        // Previous cell from left
                        if (j > 0 && balance > 0 &&
                            dp[i][j - 1][balance - 1]) {
                            dp[i][j][balance] = true;
                        }

                    } else {

                        // Previous cell from top
                        if (i > 0 &&
                            balance + 1 < m + n &&
                            dp[i - 1][j][balance + 1]) {
                            dp[i][j][balance] = true;
                        }

                        // Previous cell from left
                        if (j > 0 &&
                            balance + 1 < m + n &&
                            dp[i][j - 1][balance + 1]) {
                            dp[i][j][balance] = true;
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}