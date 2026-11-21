import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] origAdj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            origAdj[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            origAdj[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        newParent[0] = -1;
        int[] last = new int[26];
        Arrays.fill(last, -1);
        int[] prevLast = new int[n];

        // Iterative DFS to find newParent for each node without recursion depth risk
        int[] nodeStack = new int[2 * n];
        int[] typeStack = new int[2 * n]; // 0 = enter, 1 = exit
        int top = 0;
        nodeStack[top] = 0;
        typeStack[top] = 0;
        top++;

        while (top > 0) {
            top--;
            int u = nodeStack[top];
            int type = typeStack[top];
            int c = s.charAt(u) - 'a';

            if (type == 0) {
                if (u > 0) {
                    newParent[u] = (last[c] != -1) ? last[c] : parent[u];
                }
                prevLast[u] = last[c];
                last[c] = u;

                nodeStack[top] = u;
                typeStack[top] = 1;
                top++;

                for (int v : origAdj[u]) {
                    nodeStack[top] = v;
                    typeStack[top] = 0;
                    top++;
                }
            } else {
                last[c] = prevLast[u];
            }
        }

        // Build the final tree from newParent
        List<Integer>[] finalAdj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            finalAdj[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            finalAdj[newParent[i]].add(i);
        }

        // Compute subtree sizes in the final tree via iterative post-order traversal
        int[] ans = new int[n];
        top = 0;
        nodeStack[top] = 0;
        typeStack[top] = 0;
        top++;

        while (top > 0) {
            top--;
            int u = nodeStack[top];
            int type = typeStack[top];

            if (type == 0) {
                nodeStack[top] = u;
                typeStack[top] = 1;
                top++;
                for (int v : finalAdj[u]) {
                    nodeStack[top] = v;
                    typeStack[top] = 0;
                    top++;
                }
            } else {
                int size = 1;
                for (int v : finalAdj[u]) {
                    size += ans[v];
                }
                ans[u] = size;
            }
        }

        return ans;
    }
}