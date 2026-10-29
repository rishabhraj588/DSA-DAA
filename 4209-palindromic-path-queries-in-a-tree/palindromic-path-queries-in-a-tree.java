import java.util.*;

class Solution {
    private int timer = 0;
    private int[] inTime;
    private int[] outTime;
    private int[] bit;
    private int nNodes;
    private int[][] up;
    private int[] depth;
    private int LOG;

    private void updateBit(int idx, int val) {
        for (; idx <= nNodes; idx += idx & -idx) {
            bit[idx] ^= val;
        }
    }

    private void rangeXor(int l, int r, int val) {
        updateBit(l, val);
        updateBit(r + 1, val);
    }

    private int pointQuery(int idx) {
        int res = 0;
        for (; idx > 0; idx -= idx & -idx) {
            res ^= bit[idx];
        }
        return res;
    }

    private void dfs(int u, int p, int d, int currentMask, char[] s, List<List<Integer>> adj) {
        inTime[u] = ++timer;
        depth[u] = d;
        up[u][0] = p;
        for (int i = 1; i <= LOG; i++) {
            if (up[u][i - 1] != -1) {
                up[u][i] = up[up[u][i - 1]][i - 1];
            } else {
                up[u][i] = -1;
            }
        }

        currentMask ^= (1 << (s[u] - 'a'));
        rangeXor(inTime[u], inTime[u], currentMask);

        for (int v : adj.get(u)) {
            if (v != p) {
                dfs(v, u, d + 1, currentMask, s, adj);
            }
        }
        outTime[u] = timer;
    }

    private int getLca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        for (int i = LOG; i >= 0; i--) {
            if (depth[u] - (1 << i) >= depth[v]) {
                u = up[u][i];
            }
        }
        if (u == v) return u;
        for (int i = LOG; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }
        return up[u][0];
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String sStr, String[] queries) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        nNodes = n;
        inTime = new int[n];
        outTime = new int[n];
        bit = new int[n + 2];
        depth = new int[n];
        LOG = 0;
        while ((1 << LOG) <= n) {
            LOG++;
        }
        up = new int[n][LOG + 1];

        char[] s = sStr.toCharArray();
        dfs(0, -1, 0, 0, s, adj);

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            StringTokenizer st = new StringTokenizer(q);
            String type = st.nextToken();

            if (type.equals("update")) {
                int u = Integer.parseInt(st.nextToken());
                char c = st.nextToken().charAt(0);

                if (s[u] == c) continue;

                int maskChange = (1 << (s[u] - 'a')) ^ (1 << (c - 'a'));
                s[u] = c;

                rangeXor(inTime[u], outTime[u], maskChange);
            } else {
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());

                int lca = getLca(u, v);
                int maskU = pointQuery(inTime[u]);
                int maskV = pointQuery(inTime[v]);
                int pathMask = maskU ^ maskV ^ (1 << (s[lca] - 'a'));

                boolean isPalindrome = (pathMask & (pathMask - 1)) == 0;
                ans.add(isPalindrome);
            }
        }

        return ans;
    }
}