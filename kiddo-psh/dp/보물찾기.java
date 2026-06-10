package dp;

import java.util.function.Function;

class 보물찾기 {

    public int solution(int[] depth, int money, Function<Integer, Integer> excavate) {

        int n = depth.length;

        long[][] cost = new long[n][n];
        int[][] pick = new int[n][n];

        for (int i = 0; i < n; i++) {
            cost[i][i] = depth[i];
            pick[i][i] = i;
        }

        for (int len = 2; len <= n; len++) {

            for (int l = 0; l + len - 1 < n; l++) {

                int r = l + len - 1;

                long bestCost = Long.MAX_VALUE;
                int bestPick = -1;

                for (int mid = l; mid <= r; mid++) {

                    long leftCost = (mid > l) ? cost[l][mid - 1] : 0;
                    long rightCost = (mid < r) ? cost[mid + 1][r] : 0;

                    long worst =
                            depth[mid] + Math.max(leftCost, rightCost);

                    if (worst < bestCost) {
                        bestCost = worst;
                        bestPick = mid;
                    }
                }

                cost[l][r] = bestCost;
                pick[l][r] = bestPick;
            }
        }

        int l = 0;
        int r = n - 1;

        while (true) {

            int col = pick[l][r];

            // 문제의 열 번호는 1-based
            int result = excavate.apply(col + 1);

            if (result == 0) {
                return col + 1;
            }

            if (result == -1) {
                r = col - 1;
            } else { 
                l = col + 1;
            }
        }
    }
}