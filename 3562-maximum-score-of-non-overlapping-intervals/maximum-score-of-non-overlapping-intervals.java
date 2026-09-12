import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        Arrays.sort(a, (x, y) -> {
            if (x[1] != y[1])
                return Integer.compare(x[1], y[1]);
            return Integer.compare(x[3], y[3]);
        });

        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            int lo = 0;
            int hi = i - 1;
            int ans = -1;

            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;

                if (a[mid][1] < a[i][0]) {
                    ans = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            prev[i] = ans;
        }

        State[][] dp = new State[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0L, new int[0]);
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= 4; k++) {

                // Skip current interval
                State skip = dp[i - 1][k];

                // Take current interval
                State old = dp[prev[i - 1] + 1][k - 1];

                long score = old.score + a[i - 1][2];

                int[] indices =
                    Arrays.copyOf(old.indices, old.indices.length + 1);

                indices[indices.length - 1] = a[i - 1][3];

                Arrays.sort(indices);

                State take = new State(score, indices);

                dp[i][k] = better(take, skip) ? take : skip;
            }
        }

        return dp[n][4].indices;
    }

    private boolean better(State a, State b) {
        if (a.score != b.score) {
            return a.score > b.score;
        }

        return lexicographicallySmaller(a.indices, b.indices);
    }

    private boolean lexicographicallySmaller(int[] a, int[] b) {
        int n = Math.min(a.length, b.length);

        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                return a[i] < b[i];
            }
        }

        return a.length < b.length;
    }

    static class State {
        long score;
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}