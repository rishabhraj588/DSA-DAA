
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs by timestamp
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Store queries along with their original indices
        int[][] sortedQueries = new int[queries.length][2];

        for (int i = 0; i < queries.length; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // Sort queries by timestamp
        Arrays.sort(sortedQueries,
            (a, b) -> Integer.compare(a[0], b[0]));

        int[] ans = new int[queries.length];

        // Frequency of requests for each server
        int[] freq = new int[n + 1];

        // Number of distinct active servers
        int activeServers = 0;

        // Sliding window pointers
        int left = 0;
        int right = 0;

        for (int[] query : sortedQueries) {

            int t = query[0];
            int index = query[1];

            // Include logs with time <= t
            while (right < logs.length && logs[right][1] <= t) {

                int server = logs[right][0];

                if (freq[server] == 0) {
                    activeServers++;
                }

                freq[server]++;
                right++;
            }

            // Remove logs with time < t - x
            while (left < right && logs[left][1] < t - x) {

                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    activeServers--;
                }

                left++;
            }

            // Servers without requests in [t-x, t]
            ans[index] = n - activeServers;
        }

        return ans;
    }
}
