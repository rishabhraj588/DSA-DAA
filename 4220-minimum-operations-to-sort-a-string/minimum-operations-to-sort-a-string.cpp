class Solution {
public:
    int minOperations(string s) {
        int n = s.size();
        
        // Case 0: Already sorted
        bool sorted = true;
        for (int i = 1; i < n; ++i) {
            if (s[i] < s[i - 1]) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;
        
        // Case 1: Length 2 and unsorted
        if (n == 2) return -1;
        
        char mn = *min_element(s.begin(), s.end());
        char mx = *max_element(s.begin(), s.end());
        
        // Case 2: Min is at start or Max is at end
        if (s[0] == mn || s[n - 1] == mx) {
            return 1;
        }
        
        // Case 3: Min or Max is in the interior
        for (int i = 1; i < n - 1; ++i) {
            if (s[i] == mn || s[i] == mx) {
                return 2;
            }
        }
        
        // Case 4: Otherwise
        return 3;
    }
};