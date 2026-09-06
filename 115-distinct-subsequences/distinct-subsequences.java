
class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length();
        int n = t.length();

        // dp[i][j] = number of ways to form t[0...j-1]
        // using s[0...i-1]
        long[][] dp = new long[m + 1][n + 1];

        // Empty string t can always be formed in 1 way
        dp[0][0] = 1;

        for (int i = 1; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                // Don't use s[i-1]
                dp[i][j] = dp[i - 1][j];

                // Use s[i-1] if characters match
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }

        return (int) dp[m][n];
    }
}

