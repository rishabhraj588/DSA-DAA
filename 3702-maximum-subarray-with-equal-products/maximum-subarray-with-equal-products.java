class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        // Maximum possible LCM of numbers in [1..10] is 2520, max GCD is 10
        final long MAX_VAL = 2520L * 10L;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            long g = 0;
            long l = 1;

            for (int j = i; j < n; j++) {
                long x = nums[j];
                prod *= x;
                if (prod > MAX_VAL) {
                    break;
                }
                g = (g == 0) ? x : gcd(g, x);
                l = lcm(l, x);

                if (prod == l * g) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}