class Solution {
    public long countCommas(long n) {
        long ans = 0;

        for (long start = 1000; start <= n; ) {
            ans += n - start + 1;

            // Move to the next comma level
            if (start > n / 1000) {
                break;
            }

            start *= 1000;
        }

        return ans;
    }
}