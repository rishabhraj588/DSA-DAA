class Solution {
    public int closedIsland(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Step 1: Flood fill all land connected to the boundary
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r == 0 || r == rows - 1 || c == 0 || c == cols - 1) && grid[r][c] == 0) {
                    dfs(grid, r, c);
                }
            }
        }

        // Step 2: Count remaining closed islands in the interior
        int closedIslands = 0;
        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (grid[r][c] == 0) {
                    closedIslands++;
                    dfs(grid, r, c);
                }
            }
        }

        return closedIslands;
    }

    private void dfs(int[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == 1) {
            return;
        }

        grid[r][c] = 1; // Mark as visited

        dfs(grid, r + 1, c);
        dfs(grid, r - 1, c);
        dfs(grid, r, c + 1);
        dfs(grid, r, c - 1);
    }
}