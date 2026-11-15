class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1_000_000_007;
        long result = 0;
        
        int prefixSum = 0;
        int evenCount = 1; // Empty prefix sum (0) is even
        int oddCount = 0;
        
        for (int num : arr) {
            prefixSum += num;
            
            if (prefixSum % 2 != 0) {
                // Current prefix sum is odd; pair with previous even prefix sums
                result = (result + evenCount) % MOD;
                oddCount++;
            } else {
                // Current prefix sum is even; pair with previous odd prefix sums
                result = (result + oddCount) % MOD;
                evenCount++;
            }
        }
        
        return (int) result;
    }
}