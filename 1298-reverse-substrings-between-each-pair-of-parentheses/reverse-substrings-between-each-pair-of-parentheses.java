class Solution {
    public String reverseParentheses(String s) {
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch != ')') {
                stack.push(ch);
            } else {
                StringBuilder temp = new StringBuilder();

                // Pop until opening bracket
                while (stack.peek() != '(') {
                    temp.append(stack.pop());
                }

                // Remove '('
                stack.pop();

                // Push reversed substring back
                for (int i = 0; i < temp.length(); i++) {
                    stack.push(temp.charAt(i));
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        while (!stack.isEmpty()) {
            ans.append(stack.pop());
        }

        return ans.reverse().toString();
    }
}