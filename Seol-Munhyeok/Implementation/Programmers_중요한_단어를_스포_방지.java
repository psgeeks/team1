import java.util.*;

class Solution {
    
    List<int[]> words;
    String message;
    int[][] spoiler_ranges;
    
    public int solution(String message, int[][] spoiler_ranges) {
        // 변수 초기화
        this.message = message;
        this.spoiler_ranges = new int[spoiler_ranges.length][2];
        for (int i = 0; i < spoiler_ranges.length; i++) {
            this.spoiler_ranges[i] = spoiler_ranges[i].clone();
        }
    
        words = getWordIndices();
        Set<String> nonSpoilerWords = getNonSpoilerWords();
    
        int answer = 0;
        
        // 스포일러 구간의 단어를 하나씩 확인
        for (int[] w : words) {
            if (isSpoiler(w[0], w[1])) {
                String spoilerWord = message.substring(w[0], w[1] + 1);
                if (!nonSpoilerWords.contains(spoilerWord)) {
                    answer++;
                    nonSpoilerWords.add(spoilerWord);  // 공개된 단어를 스포일러가 아닌 단어에 추가
                }
            }
        }
        
        return answer;
    }
    
    List<int[]> getWordIndices() {
        List<int[]> indices = new ArrayList<>();
        boolean inWord = false;
        int start = 0; int end = 0;
        
        for (int i = 0; i < message.length(); i++) {
            if (!inWord && message.charAt(i) != ' ') {
                inWord = true;
                start = i;
            }
            
            if (inWord && message.charAt(i) == ' ') {
                inWord = false;
                end = i - 1;
                indices.add(new int[] {start, end});
            }
        }
        
        // 마지막 단어 처리
        if (inWord) {
            indices.add(new int[] {start, message.length() - 1});
        }
        
        return indices;
    }
    
    boolean isSpoiler(int start, int end) {
        for (int[] sp : spoiler_ranges) {
            int s = sp[0];
            int e = sp[1];
            // 단어 구간 [start, end]와 스포 구간 [s, e]이 한 글자라도 겹치면 스포일러 단어
            if (!(e < start || end < s)) return true;
        }
        return false;
    }
    
    Set<String> getNonSpoilerWords() {
        Set<String> nonSpoilerWords = new HashSet<>();
        for (int[] w : words) {
            if (!isSpoiler(w[0], w[1])) {
                nonSpoilerWords.add(message.substring(w[0], w[1] + 1));
            }
        }
        return nonSpoilerWords;
    }
}