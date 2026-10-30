import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        if (k < 1) {
            return -1;
        }

        // Adjacency list: u -> list of int[]{v, weight}
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adj[u].add(new int[]{v, w});
        }

        // dist[u][cnt] stores the minimum cost to reach node u with 'cnt' consecutive labels
        long[][] dist = new long[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        // Priority queue stores elements as: {cost, u, consecutive_count}
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        
        // Starting at node 0 with consecutive count 1
        pq.offer(new long[]{0, 0, 1});
        dist[0][1] = 0;

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long d = curr[0];
            int u = (int) curr[1];
            int cnt = (int) curr[2];

            // If we reached the target node, return the cost
            if (u == n - 1) {
                return (int) d;
            }

            // Skip if we've found a better path already
            if (d > dist[u][cnt]) {
                continue;
            }

            for (int[] neighbor : adj[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];

                // Determine new consecutive count
                int nextCnt = (labels.charAt(u) == labels.charAt(v)) ? cnt + 1 : 1;

                // Check constraints and relaxation condition
                if (nextCnt <= k && d + weight < dist[v][nextCnt]) {
                    dist[v][nextCnt] = d + weight;
                    pq.offer(new long[]{d + weight, v, nextCnt});
                }
            }
        }

        return -1;
    }
}