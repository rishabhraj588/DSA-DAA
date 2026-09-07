class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007L;

        // last[c] = dp value before the previous occurrence of c
        long[] last = new long[26];

        // dp includes the empty subsequence
        long dp = 1;

        for (char c : s.toCharArray()) {
            int idx = c - 'a';

            long newDp = (2 * dp - last[idx] + MOD) % MOD;

            last[idx] = dp;
            dp = newDp;
        }

        // Remove the empty subsequence
        return (int) ((dp - 1 + MOD) % MOD);
    }
}