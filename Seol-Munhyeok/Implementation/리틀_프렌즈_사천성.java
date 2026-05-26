import java.util.*;
/**
배치된 모든 타일을 제거하기
같은 모양의 타일을 규칙에 따라 제거
    1. 경로의 양 끝은 제거하려는 두 타일
    2. 최대 한 번만 꺾을 수 있다.
    3. 경로 사이에 다른 타일이나 장애물이 없어야 한다.
Return : 타일 모두 제거 여부.
    불가능 : "IMPOSSIBLE"
    제거 가능할 경우 제거해야하는 순서 (여러개면 사전순)
    
m : 세로, n 가로
1 <= m, n <= 100
. 빈칸
* 막힌 칸
알파벳 대문자(A-Z): 타일 (최대 52개)

[풀이]
해가 여러 개일 경우 사전순 리턴 -> 사전순으로 삭제 여부를 매번 확인
입력을 받으면서 각 알파벳의 위치를 저장 Map
사전 순으로 탐색하며 두 좌표가 주어질 때 현재 보드 상태에서 삭제 가능한지 확인(그리디)
    A가 지금 지울 수 있다면, A를 먼저 지우는 행동은 보드를 더 막히게 만들지 않는다.
    따라서 사전 순으로 앞선 타일을 먼저 지울 수 있다면 먼저 지우는 것이 사전순으로 앞선 결과를 가져온다.

삭제 가능한 경우
    1. ㄱ자 이동 : p1 -> (p1.y, p2.x) -> p2
    2. ㄴ자 이동 : p1 -> (p2.y, p1.x) -> p2
    일직선 이동은 1번, 2번 경우에 포함됨
*/

class Solution {
    
    final int[] dy = {-1, 0, 1, 0};
    final int[] dx = {0, 1, 0, -1};
    int m, n, tileCnt;
    char[][] board;
    Map<Character, List<Pos>> mp;
    
    public String solution(int m, int n, String[] board) {
        // 입력 처리
        this.m = m;
        this.n = n;
        this.board = new char[m][n];
        mp = new HashMap<>();
        tileCnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = board[i].charAt(j);
                this.board[i][j] = ch;
                if (isUpper(ch)) {
                    tileCnt++;
                    if (!mp.containsKey(ch)) {
                        mp.put(ch, new ArrayList<>());
                    }
                    mp.get(ch).add(new Pos(i, j));
                }  
            }
        }
        
        // 시뮬레이션
        StringBuilder sb = new StringBuilder();
        int removedCnt = 0;
        while (removedCnt < tileCnt) {
            boolean removed = false;
            
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                if (canRemove(ch)) {
                    removeTile(ch);
                    sb.append(ch);
                    removedCnt += 2;
                    removed = true;
                    break;
                }
            }
            
            if (!removed) {
                return "IMPOSSIBLE";
            }
        }
        
        return sb.toString();
    }
    
    private boolean canRemove(char ch) {
        if (!mp.containsKey(ch)) return false;
        
        Pos p1 = mp.get(ch).get(0);
        Pos p2 = mp.get(ch).get(1);
        
        return canGo(p1, new Pos(p1.y, p2.x), ch) && canGo(new Pos(p1.y, p2.x), p2, ch)
            || canGo(p1, new Pos(p2.y, p1.x), ch) && canGo(new Pos(p2.y, p1.x), p2, ch);
    }
    
    private boolean canGo(Pos p1, Pos p2, char ch) {
        if (p1.y == p2.y) {     // 같은 가로 선상에 있는 경우
            int sx = Math.min(p1.x, p2.x);
            int ex = Math.max(p1.x, p2.x);
            for (int x = sx; x <= ex; x++) {
                if (board[p1.y][x] == '*') return false;
                if (isUpper(board[p1.y][x]) && board[p1.y][x] != ch) return false;
            }
        } else {                // 같은 세로 선상에 있는 경우
            int sy = Math.min(p1.y, p2.y);
            int ey = Math.max(p1.y, p2.y);
            for (int y = sy; y <= ey; y++) {
                if (board[y][p1.x] == '*') return false;
                if (isUpper(board[y][p1.x]) && board[y][p1.x] != ch) return false;
            }
        }
        
        return true;
    }
    
    private void removeTile(char ch) {
        Pos p1 = mp.get(ch).get(0);
        Pos p2 = mp.get(ch).get(1);
        board[p1.y][p1.x] = '.';
        board[p2.y][p2.x] = '.';
        mp.remove(ch);
    }
    
    private boolean isUpper(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }
    
    static class Pos {
        int y, x;
        Pos(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}