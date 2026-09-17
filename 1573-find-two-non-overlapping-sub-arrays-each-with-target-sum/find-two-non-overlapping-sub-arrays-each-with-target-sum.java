class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;
        
        // best[i] = minimum length of a valid subarray
        // completely inside arr[0...i]
        int[] best = new int[n];
        
        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }
        
        int left = 0;
        int sum = 0;
        int answer = INF;
        
        for (int right = 0; right < n; right++) {
            sum += arr[right];
            
            while (sum > target && left <= right) {
                sum -= arr[left++];
            }
            
            // Carry forward the best previous subarray
            if (right > 0) {
                best[right] = best[right - 1];
            }
            
            // Found a subarray [left...right]
            if (sum == target) {
                int len = right - left + 1;
                
                // A previous valid subarray must end before 'left'
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(answer, len + best[left - 1]);
                }
                
                // This becomes the best subarray for future positions
                best[right] = Math.min(best[right], len);
            }
        }
        
        return answer == INF ? -1 : answer;
    }
}