class Solution {
    public String[] createGrid(int m, int n) {
        // Initialize the grid filled with obstacles ('#')
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '#';
            }
        }
        
        // Make the entire first row free ('.')
        for (int j = 0; j < n; j++) {
            grid[0][j] = '.';
        }
        
        // Make the entire last column free ('.')
        for (int i = 0; i < m; i++) {
            grid[i][n - 1] = '.';
        }
        
        // Convert char[][] to String[]
        String[] result = new String[m];
        for (int i = 0; i < m; i++) {
            result[i] = new String(grid[i]);
        }
        
        return result;
    }
}