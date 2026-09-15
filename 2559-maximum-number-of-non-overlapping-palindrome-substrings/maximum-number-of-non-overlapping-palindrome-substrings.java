class Solution {
public int maxPalindromes(String s, int k) {
int n = s.length();


    // dp[i][j] = true if s[i...j] is a palindrome
    boolean[][] dp = new boolean[n][n];

    // Find all palindromic substrings
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            if (s.charAt(i) == s.charAt(j) &&
                (j - i <= 2 || dp[i + 1][j - 1])) {
                dp[i][j] = true;
            }
        }
    }

    // best[i] = maximum number of valid palindromes
    // that can be selected from s[0...i-1]
    int[] best = new int[n + 1];

    for (int i = 1; i <= n; i++) {
        // Do not select a palindrome ending at i-1
        best[i] = best[i - 1];

        // Try every valid palindrome ending at i-1
        for (int start = 0; start <= i - k; start++) {
            if (dp[start][i - 1]) {
                best[i] = Math.max(best[i],
                                  best[start] + 1);
            }
        }
    }

    return best[n];
}

}
