class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        ListNode prev = head;
        ListNode curr = head.next;

        int pos = 1;

        int first = -1;
        int prevCritical = -1;

        int minDist = Integer.MAX_VALUE;
        int maxDist = -1;

        while (curr.next != null) {

            ListNode next = curr.next;
            pos++;

            // Check if curr is a critical point
            boolean isMax = curr.val > prev.val && curr.val > next.val;
            boolean isMin = curr.val < prev.val && curr.val < next.val;

            if (isMax || isMin) {

                // First critical point
                if (first == -1) {
                    first = pos;
                } 
                else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, pos - prevCritical);

                    // Distance from first critical point
                    maxDist = pos - first;
                }

                prevCritical = pos;
            }

            prev = curr;
            curr = next;
        }

        // Fewer than two critical points
        if (maxDist == -1) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}