package psg;


import java.io.*;
import java.util.*;

public class B9935 {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String bomb = br.readLine();
        
        int n = str.length();
        int bn = bomb.length();
        List<Character> stack = new ArrayList<>();
        for (int i = 0 ;i < n; i++) {
            stack.add(str.charAt(i));
            int size = stack.size();
            if (size >= bomb.length()) {
                int cnt = 0;
                for (int j = 0; j < bn; j++)  // 폭탄문자열과 문자열 비교
                    if (stack.get(size - bn + j) == bomb.charAt(j)) cnt++;
                
                if (cnt == bn) {
                    while (cnt-- > 0) stack.remove(stack.size() - 1);
                }
            }
        }
        
        if (stack.isEmpty()) {
        	System.out.println("FRULA");
        	return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stack.size(); i++) {
            sb.append(stack.get(i));
        }
        System.out.println(sb);
   }
}

