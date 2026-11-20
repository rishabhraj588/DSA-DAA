import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int x : nums) {
            int toPlusOne = dp.getOrDefault(x, 0) + 1;
            dp.put(x + 1, toPlusOne);
            ans = Math.max(ans, toPlusOne);

            int toSame = dp.getOrDefault(x - 1, 0) + 1;
            dp.put(x, toSame);
            ans = Math.max(ans, toSame);
        }

        return ans;
    }
}