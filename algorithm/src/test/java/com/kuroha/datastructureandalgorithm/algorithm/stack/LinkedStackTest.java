package com.kuroha.datastructureandalgorithm.algorithm.stack;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LinkedStackTest {

    @Test
    public void test() {
        LinkedStack<String> stack = new LinkedStack<>();
        log.debug(stack.pop());
        stack.push("1");
        stack.push("2");
        log.debug(stack.pop());
        System.out.println(convert("LEETCODEISHIRING",4));
    }

    public String convert(String s, int numRows) {
        if(numRows==1) {
            return s;
        }
        int length = s.length();
        StringBuilder sb = new StringBuilder(length);
        int num = 2 * numRows - 2;
        char[] cs = s.toCharArray();
        int num1 = num;
        int num2 = 0;
        for(int i = 0; i < numRows; i++) {
            boolean flag = true;
            for(int j = i; j < length;) {
                if (flag) {
                    flag = false;
                    if (num1 == 0) {
                        continue;
                    }
                    sb.append(cs[j]);
                    j+=num1;
                } else {
                    flag=true;
                    if (num2 == 0) {
                        continue;
                    }
                    sb.append(cs[j]);
                    j+=num2;
                }
            }
            num1-=2;
            num2+=2;
        }
        return sb.toString();
    }
}
