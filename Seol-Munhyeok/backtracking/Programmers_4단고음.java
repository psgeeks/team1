import java.util.*;
/**

`*` : 음높이 세 배
`+` : 음높이를 1씩 증가
`*++` : 3단 고음
3단 고음 직후 3단 고음을 연이어하거나, 3단 고음 중 다시 3단 고음을 해서 음높이를 올릴 수 있음
음높이는 1부터 시작해서 순서대로 연산 수행
최종 음높이를 만들 수 있는 3단 고음 문자열의 개수
n = 21억
3배하는 연산이 반드시 들어가므로 최대 문자열의 길이는 log3(21억) = 20 + 2*20 = 60 정도

*++`*++`
*`*++`++
*+`*++`+

[문자열의 규칙]
`+`의 개수 = `*`의 개수 * 2
앞에서부터 볼 때 `+`의 개수가 `*`의 개수의 2배 이하이어야 함

[이렇게 하면 시간 초과]
문자열 규칙에 맞추어서 1부터 시작해서 *3 또는 +1 하는 연산을 dfs로 탐색 (불가능 시 가지치기)
`+`의 개수 = `*`의 개수 * 2 일 때마다 현재 숫자가 n과 같은 지 확인

[최적화]
dfs를 n부터 시작하여 1로 줄여나가는 역방향으로 탐색 수행
`+` 연산을 되돌리는 연산은 값을 -1
`*` 연산을 되돌리려면 값이 3으로 나누어떨어지고, 앞서 `+`가 2개 있었어야 한다.
log3(n)으로 최대 star의 개수를 기반으로 가지치기를 해야 시간 내 통과 가능
*/
class Solution {
    int answer;
    int n;
    int maxStar;
    
    public int solution(int n) {
        this.n = n;
        maxStar = (int)(Math.log(n) / Math.log(3));
        answer = 0;
        dfs(n, 0, 0);
        return answer;
    }
    
    private void dfs(int value, int starCnt, int plusCnt) {
        if (starCnt > maxStar) return;
        if (plusCnt > maxStar * 2) return;
        if (value == 1) {
            if (plusCnt == starCnt * 2) answer++;
            return;
        }
        
        // + 되돌리기
        if (value > 1) {
            dfs(value - 1, starCnt, plusCnt + 1);
        }
        
        // * 되돌리기
        if (value % 3 == 0 && plusCnt >= (starCnt + 1) * 2) {
            dfs(value / 3, starCnt + 1, plusCnt);
        }   
    }
}