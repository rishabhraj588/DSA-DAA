import java.util.*;

class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count frequency of every character
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        // More than one odd frequency => impossible
        if (odd > 1) {
            return "";
        }

        // Frequency of characters in left half
        int[] cnt = new int[26];

        for (int i = 0; i < 26; i++) {
            cnt[i] = freq[i] / 2;
        }

        int halfLen = n / 2;

        char[] left = new char[halfLen];

        // Construct smallest valid palindrome
        if (!solve(left, 0, cnt, target, middle, n)) {
            return "";
        }

        return buildPalindrome(left, middle, n);
    }

    private boolean solve(
            char[] left,
            int pos,
            int[] cnt,
            String target,
            int middle,
            int n) {

        // All positions filled
        if (pos == left.length) {

            String candidate =
                    buildPalindrome(left, middle, n);

            return candidate.compareTo(target) > 0;
        }

        // Try characters from 'a' to 'z'
        for (int c = 0; c < 26; c++) {

            if (cnt[c] == 0) {
                continue;
            }

            char ch = (char) ('a' + c);

            left[pos] = ch;
            cnt[c]--;

            /*
             * Check whether current prefix
             * can still be >= target prefix.
             */
            boolean possible = true;

            for (int i = 0; i <= pos; i++) {

                if (left[i] < target.charAt(i)) {
                    possible = false;
                    break;
                }

                if (left[i] > target.charAt(i)) {
                    break;
                }
            }

            if (possible) {

                if (solve(
                        left,
                        pos + 1,
                        cnt,
                        target,
                        middle,
                        n)) {

                    cnt[c]++;
                    return true;
                }
            }

            // Backtrack
            cnt[c]++;
        }

        return false;
    }

    private String buildPalindrome(
            char[] left,
            int middle,
            int n) {

        StringBuilder sb = new StringBuilder();

        // Add left half
        for (char ch : left) {
            sb.append(ch);
        }

        // Add middle character
        if (n % 2 == 1) {
            sb.append((char) ('a' + middle));
        }

        // Add reverse of left half
        for (int i = left.length - 1; i >= 0; i--) {
            sb.append(left[i]);
        }

        return sb.toString();
    }
}