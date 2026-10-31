class Solution {
    public int uniquePaths(int[][] grid) {
        int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;
        
        // dp[c][0] -> ways entering cell from top (moving down)
        // dp[c][1] -> ways entering cell from left (moving right)
        int[][] dp = new int[n + 1][2];
        
        // Base setup for starting point simulation
        dp[1][0] = 1;
        dp[1][1] = 1;

        for (int r = 0; r < m; r++) {
            int[][] nextDp = new int[n + 1][2];
            for (int c = 0; c < n; c++) {
                // If it's a mirror cell
                if (grid[r][c] == 1) {
                    // Moving right turns down: dp[c+1][0] gets dp[c+1][1] from previous or side
                    // Let's align with the transition state correctly:
                    // If moving right into mirror -> turns down
                    // If moving down into mirror -> turns right
                }
            }
        }
        
        // Using concise DP state transformation similar to optimal rolling array structure
        int[] curr = new int[2];
        curr[0] = 1; 
        curr[1] = 1;
        
        // Direct implementation using 1D/2D table per row
        int[][] rowDp = new int[n + 1][2];
        rowDp[1][0] = 1;
        rowDp[1][1] = 1;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    // Mirror reflection behavior:
                    // Entering from left (moving right) turns down -> maps to down state
                    // Entering from top (moving down) turns right -> maps to right state
                    rowDp[c + 1] = new int[] { rowDp[c + 1][1], rowDp[c][0] };
                } else {
                    int val = (rowDp[c + 1][1] + rowDp[c][0]) % MOD;
                    rowDp[c + 1] = new int[] { val, val };
                }
            }
        }
        
        return rowDp[n][0];
    }
}