import java.util.*;
class Solution {
    public String[] solution(String[] expressions) {        
        // 가능한 최소 진법 찾기
        int maxDigit = -1;
        for (String expr : expressions) {
            for (char ch : expr.toCharArray()) {
                if (ch >= '0' && ch <= '9') {
                    maxDigit = Math.max(maxDigit, ch - '0');
                }
            }
        }
        
        // 가능한 진법 찾기
        List<Integer> answerRadix = new ArrayList<>();
        for (int radix = maxDigit + 1; radix <= 9; radix++) {
            boolean ok = true;
            
            for (String expr : expressions) {
                if (expr.contains("X")) continue;
                
                int idx = expr.indexOf("=");
                int result = calc(expr.substring(0, idx), radix);
                int x = Integer.parseInt(expr.substring(idx + 1).trim(), radix);

                if (result != x) {
                    ok = false;
                    break;
                }
            }
            if (ok) answerRadix.add(radix);
        }
        
        // X 처리된 수식의 정답 찾기
        List<String> answerList = new ArrayList<>();
        for (String expr : expressions) {
            if (expr.contains("X")) {
                Set<String> answerSet = new HashSet<>();
                int idx = expr.indexOf("=");
                for (int radix : answerRadix) {
                    int result = calc(expr.substring(0, idx), radix);
                    answerSet.add(Integer.toString(result, radix));
                }
                
                if (answerSet.size() == 1) {
                    answerList.add(expr.substring(0, idx) + "= " + answerSet.iterator().next());
                } else {
                    answerList.add(expr.substring(0, idx) + "= ?");
                }
            }
        }

        return answerList.toArray(new String[0]);
    }
    
    private int calc(String expr, int radix) {
        int idx = (expr.indexOf("+") == -1) ? expr.indexOf("-") : expr.indexOf("+");
        int a = Integer.parseInt(expr.substring(0, idx).trim(), radix);
        int b = Integer.parseInt(expr.substring(idx + 1).trim(), radix);
        
        return (expr.charAt(idx) == '+') ? a + b : a - b;
    }
}