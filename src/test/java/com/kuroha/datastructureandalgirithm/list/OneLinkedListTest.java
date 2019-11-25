package com.kuroha.datastructureandalgirithm.list;

import org.junit.Test;

public class OneLinkedListTest {

    @Test
    public void reverse() {
        OneLinkedList<Integer> linkedList = new OneLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        System.out.println(linkedList.toString());
        linkedList.reverse();
        System.out.println(linkedList.toString());
        linkedList.reverse();
        linkedList.add(4);
        System.out.println(linkedList.toString());
        System.out.println(linkedList.get(0));
        System.out.println(linkedList.centralCheck());
        System.out.println(linkedList.index());
        linkedList.remove(2);
        System.out.println(linkedList.index());
        linkedList.removeLastIndex(0);
        System.out.println(linkedList.index());
        System.out.println(linkedList);
    }

}
