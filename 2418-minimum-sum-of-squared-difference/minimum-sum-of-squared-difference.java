class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;

        int[] diff = new int[n];
        int maxDiff = 0;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            sum += diff[i];
        }

        // If all differences can be eliminated
        if (sum <= k) {
            return 0;
        }

        int[] freq = new int[maxDiff + 1];

        for (int d : diff) {
            freq[d]++;
        }

        // Reduce the largest differences first
        for (int d = maxDiff; d > 0 && k > 0; d--) {
            int count = freq[d];

            if (count == 0) {
                continue;
            }

            if (k >= count) {
                // Reduce all differences of size d by 1
                freq[d] = 0;
                freq[d - 1] += count;
                k -= count;
            } else {
                // Reduce only k differences by 1
                freq[d] -= (int) k;
                freq[d - 1] += (int) k;
                k = 0;
            }
        }

        long ans = 0;

        for (int d = 0; d < freq.length; d++) {
            ans += (long) d * d * freq[d];
        }

        return ans;
    }
}