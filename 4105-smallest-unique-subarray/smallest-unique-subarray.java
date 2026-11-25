import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final long BASE = 100_003L;
    private static final long MOD = 1_000_000_007L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        long[] pow = new long[n + 1];
        pow[0] = 1L;
        for (int i = 1; i <= n; i++) {
            pow[i] = (pow[i - 1] * BASE) % MOD;
        }

        int low = 1;
        int high = n;
        int ans = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueOfLength(nums, mid, pow)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUniqueOfLength(int[] nums, int len, long[] pow) {
        int n = nums.length;
        long hash = 0L;

        for (int i = 0; i < len; i++) {
            hash = (hash * BASE + nums[i]) % MOD;
        }

        Map<Long, Integer> freq = new HashMap<>();
        freq.put(hash, 1);

        long highestPow = pow[len - 1];
        for (int i = len; i < n; i++) {
            // Remove outgoing element nums[i - len] and append incoming element nums[i]
            long outgoing = (nums[i - len] * highestPow) % MOD;
            hash = (hash - outgoing + MOD) % MOD;
            hash = (hash * BASE + nums[i]) % MOD;

            freq.put(hash, freq.getOrDefault(hash, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
}