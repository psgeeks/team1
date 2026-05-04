class Solution {
    int[][] dp;

    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        dp = new int[m+1][n+1];

        return dfs(0, 0, s, p);
    }

    private boolean dfs(int i, int j, String text, String pattern) {
        if (dp[i][j] != 0) return dp[i][j] == 1;

        boolean ans;
        if (j == pattern.length()) {
            ans = i == text.length(); // 매칭 끝
        } else {
            boolean firstMatch = i < text.length()
                && (pattern.charAt(j) == text.charAt(i)
                || pattern.charAt(j) == '.');
            
            if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*') {
                ans = dfs(i, j+2, text, pattern) || (firstMatch && dfs(i+1, j+1, text, pattern));
            } else {
                ans = firstMatch && dfs(i+1, j+1, text, pattern);
            }
        }
        dp[i][j] = ans ? 1 : 2;
        return ans;
    }
}