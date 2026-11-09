import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            available.add(i);
        }
        
        // PriorityQueue to store busy servers: [free_time, server_id]
        PriorityQueue<long[]> busy = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        int[] requestCount = new int[k];
        int maxRequests = 0;
        
        for (int i = 0; i < arrival.length; i++) {
            long arrivalTime = arrival[i];
            long loadTime = load[i];
            
            // Free up servers that have completed their tasks by the current arrival time
            while (!busy.isEmpty() && busy.peek()[0] <= arrivalTime) {
                available.add((int) busy.poll()[1]);
            }
            
            // If all servers are busy, the request is dropped
            if (available.isEmpty()) {
                continue;
            }
            
            // Find the first available server >= (i % k)
            Integer server = available.ceiling(i % k);
            if (server == null) {
                // Wrap around to the beginning if none found >= (i % k)
                server = available.first();
            }
            
            // Assign the request to this server
            available.remove(server);
            busy.offer(new long[] { arrivalTime + loadTime, server });
            requestCount[server]++;
            maxRequests = Math.max(maxRequests, requestCount[server]);
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}