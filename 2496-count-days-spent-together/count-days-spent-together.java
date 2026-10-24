class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        // Days in each month for a non-leap year
        int[] daysInMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // Find the latest arrival and earliest departure
        String latestArrive = arriveAlice.compareTo(arriveBob) > 0 ? arriveAlice : arriveBob;
        String earliestLeave = leaveAlice.compareTo(leaveBob) < 0 ? leaveAlice : leaveBob;
        
        // If latest arrival is after earliest leave, they don't overlap
        if (latestArrive.compareTo(earliestLeave) > 0) {
            return 0;
        }
        
        // Calculate overlap inclusive of start and end dates
        return dateToDayOfYear(earliestLeave, daysInMonths) - dateToDayOfYear(latestArrive, daysInMonths) + 1;
    }
    
    // Helper function to convert "MM-DD" into day of the year (1-365)
    private int dateToDayOfYear(String dateStr, int[] daysInMonths) {
        String[] parts = dateStr.split("-");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        
        int totalDays = day;
        for (int i = 0; i < month - 1; i++) {
            totalDays += daysInMonths[i];
        }
        return totalDays;
    }
}