import java.util.*;
class Solution {
    public int[] solution(int n, long left, long right) {
        // left ~ right 사이 포함된 행 모두 추출
        int startRow = (int)(left / n);
        int endRow = (int)(right / n);
        List<Integer> ansList = new ArrayList<>();
        for (int r = startRow; r <= endRow; r++) {
            for (int i = 0; i < r + 1; i++) {
                ansList.add(r + 1);
            }
            for (int num = r + 2; num <= n; num++) {
                ansList.add(num);
            }
        }
       
        // 유효한 범위 내에서 추출
        int size = (int)(right - left + 1);
        int[] answer = new int[size];
        int rowCount = endRow - startRow + 1;
        int startIdx = (int)(left % n);
        for (int i = 0; i < size; i++) {
            answer[i] = ansList.get(startIdx + i);
        }        
        return answer;
    }
}