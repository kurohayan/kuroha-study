package com.kuroha.datastructureandalgirithm.stack;

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
    }
}
