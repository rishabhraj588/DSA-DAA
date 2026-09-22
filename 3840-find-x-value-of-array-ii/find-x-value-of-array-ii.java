class Solution {

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    int n, k;
    int[][] treeCnt;
    int[] treeProd;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        treeCnt = new int[4 * n][k];
        treeProd = new int[4 * n];

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent update
            update(1, 0, n - 1, index, value);

            // Query range [start, n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            ans[q] = res.cnt[x];
        }

        return ans;
    }

    // Build segment tree
    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            int value = nums[l] % k;

            treeProd[node] = value;
            treeCnt[node][value] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        merge(node, node * 2, node * 2 + 1);
    }

    // Point update
    private void update(int node, int l, int r, int index, int value) {
        if (l == r) {
            value %= k;

            treeProd[node] = value;

            for (int i = 0; i < k; i++) {
                treeCnt[node][i] = 0;
            }

            treeCnt[node][value] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, r, index, value);
        }

        merge(node, node * 2, node * 2 + 1);
    }

    // Merge left and right child
    private void merge(int parent, int left, int right) {

        for (int i = 0; i < k; i++) {
            treeCnt[parent][i] = treeCnt[left][i];
        }

        // Prefixes that extend from left into right
        for (int r = 0; r < k; r++) {
            int newRemainder = (treeProd[left] * r) % k;

            treeCnt[parent][newRemainder] += treeCnt[right][r];
        }

        treeProd[parent] = (treeProd[left] * treeProd[right]) % k;
    }

    // Range query
    private Node query(int node, int l, int r, int ql, int qr) {

        if (ql <= l && r <= qr) {
            Node res = new Node(k);

            res.prod = treeProd[node];

            for (int i = 0; i < k; i++) {
                res.cnt[i] = treeCnt[node][i];
            }

            return res;
        }

        int mid = l + (r - l) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return mergeNodes(left, right);
    }

    // Merge two query results
    private Node mergeNodes(Node left, Node right) {

        Node res = new Node(k);

        res.prod = (left.prod * right.prod) % k;

        // Prefixes entirely inside left
        for (int i = 0; i < k; i++) {
            res.cnt[i] = left.cnt[i];
        }

        // Prefixes that start in left and extend into right
        for (int r = 0; r < k; r++) {
            int newRemainder = (left.prod * r) % k;

            res.cnt[newRemainder] += right.cnt[r];
        }

        return res;
    }
}