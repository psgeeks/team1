import java.util.*;

class Solution {
    // 우선순위 : 소요시간이 짧음 -> 요청시각이 빠름 -> 작업의 번호가 작음
    // dc는 hdd 작업 X && 대기큐 not empty -> 높은 순위작업 poll
    // 요청시간이 들어가니 끝나는 시간에서 빼면 될듯 = 반환시간
    
    private TreeMap<Integer, List<int[]>> timetable = new TreeMap<>(); // {time : [{작업번호, 요청시각, 소요시간}, ]}

    public int solution(int[][] jobs) {
        PriorityQueue<Job> pq = new PriorityQueue<>((aJob, bJob) -> { // {작업 번호, 요청시각, 소요시간}
            int[] a = aJob.job; int[] b = bJob.job;
            if (a[2] != b[2]) return Integer.compare(a[2], b[2]);
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            return Integer.compare(a[0], b[0]);
        });
        
        init(jobs);
        
        int t = 0;
        Job diskJob = null;
        int cnt = 0;
        int sum = 0;
        while (cnt < jobs.length) { 
            // 현재 시간에 작업이 종료되면
            if (diskJob != null && t - diskJob.startTime == diskJob.job[2]) {
                sum += t - diskJob.job[1];
                cnt++;
                diskJob = null;
            }
            
            // 지금 들어오는 작업을 큐에 넣음
            List<int[]> curJobs = timetable.get(t);
            if (curJobs != null) {
                for (int[] job : curJobs) pq.offer(new Job(job, t));
            }
            
            // 디스크가 놀고, 대기큐도 비어있으면 다음 작업이 들어오는 시간으로 점프
            if (diskJob == null && pq.isEmpty()) {
                Integer nextTime = timetable.ceilingKey(t);
                if (nextTime != null) {
                    t = nextTime;
                    continue;
                }
            }
            
            // 디스크가 작업x && 대기큐 not empty -> 높은 순위작업 poll and start
            if (diskJob == null && !pq.isEmpty()) {
                diskJob = pq.poll();
                diskJob.startTime = t;
            }
            t++;
        }
        
        return sum / cnt; // 반환 시간의 평균의 정수 부분
    }
    
    private void init(int[][] jobs) {
        for (int i = 0; i < jobs.length; i++) {
            timetable.computeIfAbsent(jobs[i][0], k -> new ArrayList<>());
            timetable.get(jobs[i][0]).add(new int[]{i, jobs[i][0], jobs[i][1]});
        }
    }
    
    static class Job {
        int[] job;
        int startTime;
        public Job(int[] j, int s) {job = j; startTime = s;}
    }
}