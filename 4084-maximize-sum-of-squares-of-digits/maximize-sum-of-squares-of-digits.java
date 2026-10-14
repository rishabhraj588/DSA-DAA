public class Solution {
    public String maxSumOfSquares(int num, int sum) {
        // If the required sum exceeds the maximum possible sum or is less than 1
        if (sum > 9L * num || sum < 1) {
            return "";
        }
        
        long k = sum / 9;
        int rem = sum % 9;
        
        StringBuilder sb = new StringBuilder(num);
        
        // Append k nines
        for (long i = 0; i < k; i++) {
            sb.append('9');
        }
        
        // Append the remainder digit if it's greater than 0
        if (rem > 0) {
            sb.append((char)('0' + rem));
        }
        
        // Fill the remaining slots with zeros to match the required number of digits
        long zerosCount = num - k - (rem > 0 ? 1 : 0);
        for (long i = 0; i < zerosCount; i++) {
            sb.append('0');
        }
        
        return sb.toString();
    }
}