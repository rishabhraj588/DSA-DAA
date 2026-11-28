import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        long remBudget = budget;
        long minPrice = Long.MAX_VALUE;

        int[] freq = new int[n + 1];
        for (int[] item : items) {
            freq[item[0]]++;
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        // Precompute number of multiples for each factor from 1 to n
        int[] multCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            int sum = 0;
            for (int m = f; m <= n; m += f) {
                sum += freq[m];
            }
            multCount[f] = sum;
        }

        // Collect bonus units: [price, gain] where price < 2 * minPrice and gain > 0
        List<long[]> candidates = new ArrayList<>();
        long twoMin = 2L * minPrice;
        for (int[] item : items) {
            int factor = item[0];
            long price = item[1];
            long gain = multCount[factor] - 1L;
            if (gain > 0 && price < twoMin) {
                candidates.add(new long[]{price, gain});
            }
        }

        candidates.sort(Comparator.comparingLong(a -> a[0]));

        long totalItems = 0;
        for (long[] cand : candidates) {
            long price = cand[0];
            long gain = cand[1];
            if (remBudget < price) {
                break;
            }
            long take = Math.min(gain, remBudget / price);
            totalItems += take * 2L;
            remBudget -= take * price;
        }

        // Spend remaining budget on the cheapest plain items (1 item each)
        totalItems += remBudget / minPrice;

        return (int) totalItems;
    }
}