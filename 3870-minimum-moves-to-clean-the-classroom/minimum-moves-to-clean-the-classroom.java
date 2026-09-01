
import java.util.*;

class Solution {

    static class State {
        int r, c, mask, energy, moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();   // FIXED: String uses length()

        int sr = 0, sc = 0;
        int litterCount = 0;

        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        // Find starting position and number every litter
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // No litter to collect
        if (litterCount == 0) {
            return 0;
        }

        int allMask = (1 << litterCount) - 1;

        boolean[][][][] visited =
            new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> queue = new LinkedList<>();

        queue.offer(new State(sr, sc, 0, energy, 0));
        visited[sr][sc][0][energy] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            State cur = queue.poll();

            // All litter collected
            if (cur.mask == allMask) {
                return cur.moves;
            }

            // Try all 4 directions
            for (int d = 0; d < 4; d++) {

                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Cannot move without energy
                if (cur.energy == 0) {
                    continue;
                }

                int newEnergy = cur.energy - 1;
                int newMask = cur.mask;

                // Collect litter
                if (litterId[nr][nc] != -1) {
                    newMask |= (1 << litterId[nr][nc]);
                }

                // Reset energy at R
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                // Visit this state if not visited before
                if (!visited[nr][nc][newMask][newEnergy]) {

                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newMask,
                            newEnergy,
                            cur.moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }
}

