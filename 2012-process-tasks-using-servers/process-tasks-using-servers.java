import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        // freeServers heap: sorts by weight, then by index
        // Element format: int[]{weight, index}
        PriorityQueue<int[]> freeServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        // busyServers heap: sorts by the time the server becomes free
        // Element format: int[]{freeTime, weight, index}
        PriorityQueue<int[]> busyServers = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        // Initialize all servers into the free heap
        for (int i = 0; i < n; i++) {
            freeServers.add(new int[]{servers[i], i});
        }

        int time = 0;
        for (int j = 0; j < m; j++) {
            // The task is only available at second j or later
            time = Math.max(time, j);

            // If no free servers are available, jump time to the earliest server release time
            if (freeServers.isEmpty()) {
                time = Math.max(time, busyServers.peek()[0]);
            }

            // Release all servers that finished their tasks by the current 'time'
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= time) {
                int[] freeServer = busyServers.poll();
                freeServers.add(new int[]{freeServer[1], freeServer[2]});
            }

            // Assign the top free server to the current task j
            int[] currentServer = freeServers.poll();
            ans[j] = currentServer[1];

            // Put the server into the busy heap with its new free time
            busyServers.add(new int[]{time + tasks[j], currentServer[0], currentServer[1]});
        }

        return ans;
    }
}