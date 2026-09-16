class Solution {
    public int numberOfSets(int n, int k) {
        long MOD = 1_000_000_007L;
        
        // Compute C(n + k - 1, 2 * k) % MOD
        long N = n + k - 1;
        long R = 2 * k;

        if (R > N) {
            return 0;
        }

        long numerator = 1;
        long denominator = 1;

        for (int i = 1; i <= R; i++) {
            numerator = (numerator * (N - i + 1)) % MOD;
            denominator = (denominator * i) % MOD;
        }

        // Fermat's Little Theorem for modular inverse
        return (int) ((numerator * power(denominator, MOD - 2, MOD)) % MOD);
    }

    private long power(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
}