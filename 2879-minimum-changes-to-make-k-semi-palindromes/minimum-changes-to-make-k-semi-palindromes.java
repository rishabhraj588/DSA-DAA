import java.util.Arrays;

class Solution {
    private static final int MAXN = 200;
    private static final int[][] divisors = new int[MAXN + 1][];
    private static final int INF = 1000000000;

    static {
        for (int i = 1; i <= MAXN; i++) {
            int count = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) count++;
            }
            divisors[i] = new int[count];
            int idx = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    divisors[i][idx++] = j;
                }
            }
        }
    }

    public int minimumChanges(String s, int k) {
        int n = s.length();
        char[] chars = s.toCharArray();
        
        // cost[i][j] stores the minimum changes to make substring s[i...j] a semi-palindrome
        int[][] cost = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                cost[i][j] = getMinCost(chars, i, j);
            }
        }
        
        // dp[i][j] represents the min changes to partition s[0...j] into i parts
        int[][] dp = new int[k + 1][n];
        for (int i = 1; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }
        
        // Base case: 1 partition for prefix s[0...j]
        for (int j = 0; j < n; j++) {
            dp[1][j] = cost[0][j];
        }
        
        // DP transitions
        for (int p = 2; p <= k; p++) {
            for (int i = 0; i < n; i++) {
                for (int j = p - 2; j < i; j++) {
                    dp[p][i] = Math.min(dp[p][i], dp[p - 1][j] + cost[j + 1][i]);
                }
            }
        }
        
        return dp[k][n - 1];
    }
    
    // Helper to find the minimum changes to make s[l...r] a semi-palindrome
    private int getMinCost(char[] chars, int l, int r) {
        int len = r - l + 1;
        int minChanges = INF;
        
        for (int d : divisors[len]) {
            int changes = 0;
            // Check each of the d groups
            for (int start = 0; start < d; start++) {
                int left = l + start;
                int right = l + len - d + start; // last element in this group
                while (left < right) {
                    if (chars[left] != chars[right]) {
                        changes++;
                    }
                    left += d;
                    right -= d;
                }
            }
            minChanges = Math.min(minChanges, changes);
        }
        
        return minChanges;
    }
}