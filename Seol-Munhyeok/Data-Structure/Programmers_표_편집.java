/**
현재 선택된 행 정보
가장 최근에 삭제된 행 복구 명령 -> 삭제된 행 정보를 스택에 저장

Return 모든 명령어를 수행한 후 표의 상태와 처음 주어진 표의 상태를 비교하여 삭제되지 않은 행은 O, 삭제된 행은 X로 표시
처음 표의 행 개수 n, 처음에 선택된 행의 위치 k
    n ≤ 1,000,000
    cmd의 원소 개수 ≤ 200,000

cur 기준 앞뒤로 X칸 뒤 원소 찾고 삭제 연산 -> 배열, 링크드리스트 O(N)
    cmd에 등장하는 모든 X들의 값을 합친 결과가 1,000,000 이하이므로 가능함
*/
import java.util.*;

class Solution {
    
    Deque<Integer> stk;
    boolean[] removed;
    int[] prev, next;
    int cur;
    
    public String solution(int n, int k, String[] cmd) {              
        stk = new ArrayDeque<>();  // 최근에 삭제된 행 저장
        removed = new boolean[n];  // 행 삭제 여부 저장
        // 배열 기반 연결리스트 선언
        next = new int[n];
        prev = new int[n];
        for (int i = 0; i < n; i++) {            
            prev[i] = i - 1;
            next[i] = i + 1;
        }
        next[n - 1] = -1;
        
        // 명령어 처리
        cur = k;
        for (String c : cmd) {
            char ch = c.charAt(0);
            if (ch == 'U') {
                int x = Integer.parseInt(c.substring(2));
                selectUp(x);
            } else if (ch == 'D') {
                int x = Integer.parseInt(c.substring(2));
                selectDown(x);
            } else if (ch == 'C') {
                cancelCurrent();
            } else {
                restore();
            }
        }
        
        // 정답 구하기
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < removed.length; i++) {
            sb.append(removed[i] ? 'X' : 'O');
        }
        return sb.toString();
    }
    
    private void selectUp(int x) {
        for (int i = 0; i < x; i++) {
            cur = prev[cur];
        }
    }
    
    private void selectDown(int x) {
        for (int i = 0; i < x; i++) {
            cur = next[cur];
        }
    }
    
    private void cancelCurrent() {
        int nxt = next[cur];
        int pv = prev[cur];
        if (pv != -1) next[pv] = nxt;
        if (nxt != -1) prev[nxt] = pv;
        
        stk.push(cur);
        removed[cur] = true;
        
        if (nxt != -1) cur = nxt;
        else cur = pv;
    }
    
    private void restore() {
        int target = stk.pop();
        removed[target] = false;
        
        int pv = prev[target];
        int nxt = next[target];
        if (pv != -1) next[pv] = target;
        if (nxt != -1) prev[nxt] = target;
    }
}