package com.kuroha.datastructureandalgorithm.algorithm.stack;

import com.kuroha.algorithm.stack.ArrayStack;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ArrayStackTest {

    @Test
    public void test() throws Exception{
        ArrayStack<String> stack = new ArrayStack<>(3);
        System.out.println(stack.pop());
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        System.out.println(stack.pop());
    }

}
