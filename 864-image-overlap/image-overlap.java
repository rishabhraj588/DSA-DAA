class Solution {
public int largestOverlap(int[][] img1, int[][] img2) {
int n = img1.length;

    // Store coordinates of all 1s
    java.util.List<int[]> ones1 = new java.util.ArrayList<>();
    java.util.List<int[]> ones2 = new java.util.ArrayList<>();

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (img1[i][j] == 1) {
                ones1.add(new int[]{i, j});
            }

            if (img2[i][j] == 1) {
                ones2.add(new int[]{i, j});
            }
        }
    }

    // Count how many pairs have the same translation
    java.util.Map<String, Integer> map = new java.util.HashMap<>();

    int answer = 0;

    for (int[] p1 : ones1) {
        for (int[] p2 : ones2) {

            // Translation needed to move p1 onto p2
            int dr = p2[0] - p1[0];
            int dc = p2[1] - p1[1];

            String key = dr + "," + dc;

            int count = map.getOrDefault(key, 0) + 1;
            map.put(key, count);

            answer = Math.max(answer, count);
        }
    }

    return answer;
}

}
