class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int videoLen = strToInt(video_len);
        int opStart = strToInt(op_start);
        int opEnd = strToInt(op_end);
        int cur = strToInt(pos);
        
        if (opStart <= cur && cur <= opEnd) {
            cur = opEnd;
        }
        
        for (String cmd : commands) {
            if (cmd.equals("next")) {
                cur = Math.min(cur + 10, videoLen);
            } else {
                cur = Math.max(cur - 10, 0);
            }
            
            if (opStart <= cur && cur <= opEnd) {
                cur = opEnd;
            }
        }
        
        return intToStr(cur);
    }
    
    private int strToInt(String str) {
        int h = Integer.parseInt(str.substring(0, 2));
        int m = Integer.parseInt(str.substring(3));
        return h * 60 + m;
    }
    
    private String intToStr(int t) {
        return String.format("%02d:%02d", t / 60, t % 60);
    }
}