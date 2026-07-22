/**
표 크기 50*50으로 고정
(r, c) -> 1-based

MERGE r1 c1 r2 c2
선택한 두 위치의 셀이 같은 셀이면 무시
인접하지 않으면 그 사이의 셀은 영향이 없고, 두 셀만 영향을 받음
한 셀이 값이 있으면 그 값을 따르고, 둘 다 값이 있으면 (r1, c1) 위치의 셀 값을 가짐
이후 (r1, c1) 와 (r2, c2) 중 어느 위치를 선택하여도 병합된 셀로 접근

UNMERGE r c
선택한 셀이 포함하고 있던 모든 셀은 프로그램 실행 초기의 상태로
병합 해제 전 셀이 값을 가지고 있으면 (r, c) 위치의 셀이 그 값을 가짐

Return "PRINT r c" 명령어에 대한 실행결과를 순서대로 1차원 문자열 배열에 담아 return
    선택한 셀이 비어있을 경우 "EMPTY"를 출력
1 ≤ commands의 길이 ≤ 1,000
*/

import java.util.*;

class Solution {
    
    String[][] table;
    Map<Integer, List<Integer>> connected;
    
    public String[] solution(String[] commands) {
        table = new String[50][50];
        connected = new HashMap<>();
        for (int key = 0; key < 50 * 50; key++) {
            connected.computeIfAbsent(key, k -> new ArrayList<>()).add(key);
        }
        List<String> answerList = new ArrayList<>();
        
        for (String cmds : commands) {
            String[] cmd = cmds.split(" ");
            if (cmd[0].equals("UPDATE")) {
                if (cmd.length == 4) {
                    update(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]), cmd[3]);
                } else {
                    update(cmd[1], cmd[2]);
                }
            } else if (cmd[0].equals("MERGE")) {
                merge(Integer.parseInt(cmd[1]),
                      Integer.parseInt(cmd[2]),
                      Integer.parseInt(cmd[3]),
                      Integer.parseInt(cmd[4]));
            } else if (cmd[0].equals("UNMERGE")) {
                unmerge(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
            } else {
                answerList.add(table[Integer.parseInt(cmd[1]) - 1][Integer.parseInt(cmd[2]) - 1]);
            }
        }
        
        // 리스트 배열 변환
        String[] answer = new String[answerList.size()];
        int idx = 0;
        for (String s : answerList) {
            answer[idx++] = (s == null) ? "EMPTY" : s;
        }
        return answer;
    }
    
    private void update(int r, int c, String value) {
        r--; c--;
        int groupKey = findGroupKey(r * 50 + c);
        for (int key : connected.get(groupKey)) {
            table[key / 50][key % 50] = value;
        }
    }
    
    private void update(String value1, String value2) {
        for (int r = 0; r < 50; r++) {
            for (int c = 0; c < 50; c++) {
                if (Objects.equals(table[r][c], value1)) {
                    table[r][c] = value2;
                }
            }
        }
    }
    
    private void merge(int r1, int c1, int r2, int c2) {
        r1--; c1--; r2--; c2--;
        int groupKey1 = findGroupKey(r1 * 50 + c1);
        int groupKey2 = findGroupKey(r2 * 50 + c2);
        if (groupKey1 == groupKey2) return;  //선택한 두 위치의 셀이 같은 셀일 경우 무시
        
        for (int key : connected.get(groupKey2)) {
            connected.get(groupKey1).add(key);
        }
        connected.get(groupKey2).clear();
        
        String value = (table[r1][c1] == null) ? table[r2][c2] : table[r1][c1];
        for (int key : connected.get(groupKey1)) {
            table[key / 50][key % 50] = value;
        } 
    }
    
    private void unmerge(int r, int c) {
        r--; c--;
        int groupKey = findGroupKey(r * 50 + c);
        String value = table[groupKey / 50][groupKey % 50];
        List<Integer> lst = new ArrayList<>(connected.get(groupKey));
        connected.get(groupKey).clear();
        for (int key : lst) {
            table[key / 50][key % 50] = null;
            connected.computeIfAbsent(key, k -> new ArrayList<>()).add(key);
        }
        table[r][c] = value;      
    }
    
    private int findGroupKey(int key) {
        for (Map.Entry<Integer, List<Integer>> entry : connected.entrySet()) {
            for (int pos : entry.getValue()) {
                if (pos == key) {
                    return entry.getKey();
                }
            }     
        }
        return -1;
    }
}