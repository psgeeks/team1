class Solution {
    public long solution(int dist_limit, int split_limit) {
        long answer = 1;

        for (long power2 = 1; power2 <= split_limit; power2 *= 2) {
            for (long power3 = 1; power2 * power3 <= split_limit; power3 *= 3) {

                long frontier = 1;
                long budget = dist_limit;
                long leaves = 0;
                boolean stopped = false;

                // 2분배 층들
                for (long cur2 = 1; cur2 < power2; cur2 *= 2) {
                    if (budget >= frontier) {
                        budget -= frontier;
                        frontier *= 2;
                    } else {
                        leaves = budget * 2 + (frontier - budget);
                        stopped = true;
                        break;
                    }
                }

                // 3분배 층들
                if (!stopped) {
                    for (long cur3 = 1; cur3 < power3; cur3 *= 3) {
                        if (budget >= frontier) {
                            budget -= frontier;
                            frontier *= 3;
                        } else {
                            leaves = budget * 3 + (frontier - budget);
                            stopped = true;
                            break;
                        }
                    }
                }

                if (!stopped) {
                    leaves = frontier;
                }

                answer = Math.max(answer, leaves);
            }
        }

        return answer;
    }
}