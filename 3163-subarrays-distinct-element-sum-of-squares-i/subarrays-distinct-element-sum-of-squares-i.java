import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public int sumCounts(List<Integer> nums) {
        int totalSupply = 0;
        int n = nums.size();
        
        // Outer loop for the start of the subarray
        for (int i = 0; i < n; i++) {
            Set<Integer> distinctSet = new HashSet<>();
            // Inner loop for the end of the subarray
            for (int j = i; j < n; j++) {
                distinctSet.add(nums.get(j));
                int count = distinctSet.size();
                totalSupply += count * count;
            }
        }
        
        return totalSupply;
    }
}