class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();

        // Store positions of all 1s
        int[] pos = new int[n];
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                pos[count++] = i;
            }
        }

        // Not enough 1s
        if (count < k) {
            return "";
        }

        String answer = "";

        // Check every group of k consecutive 1s
        for (int i = 0; i + k - 1 < count; i++) {
            int left = pos[i];
            int right = pos[i + k - 1];

            String current = s.substring(left, right + 1);

            // First candidate OR better candidate
            if (answer.equals("")
                    || current.length() < answer.length()
                    || (current.length() == answer.length()
                        && current.compareTo(answer) < 0)) {

                answer = current;
            }
        }

        return answer;
    }
}