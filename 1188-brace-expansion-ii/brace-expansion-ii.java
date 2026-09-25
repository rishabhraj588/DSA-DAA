import java.util.*;

class Solution {
    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parseUnion();

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Handles expressions separated by commas
    private Set<String> parseUnion() {
        Set<String> result = new HashSet<>();

        while (index < s.length() && s.charAt(index) != '}') {
            Set<String> part = parseConcat();
            result.addAll(part);

            if (index < s.length() && s.charAt(index) == ',') {
                index++; // skip comma
            } else {
                break;
            }
        }

        return result;
    }

    // Handles concatenation of adjacent expressions
    private Set<String> parseConcat() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()) {
            char ch = s.charAt(index);

            if (ch == '}' || ch == ',') {
                break;
            }

            Set<String> next = parseAtom();

            Set<String> combined = new HashSet<>();

            for (String a : result) {
                for (String b : next) {
                    combined.add(a + b);
                }
            }

            result = combined;
        }

        return result;
    }

    // Handles a single letter or a {...} expression
    private Set<String> parseAtom() {
        Set<String> result = new HashSet<>();

        char ch = s.charAt(index);

        if (ch == '{') {
            index++; // skip '{'

            result = parseUnion();

            index++; // skip '}'
        } else {
            result.add(String.valueOf(ch));
            index++;
        }

        return result;
    }
}