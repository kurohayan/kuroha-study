package com.kuroha.datastructureandalgorithm.algorithm.stream;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void test() {
        int[] ints = new int[]{1,2,3,4,5};
        ints = Arrays.stream(ints).limit(3).toArray();
        System.out.println(JSON.toJSONString(ints));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list = list.stream().limit(3).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2 = list2.stream().limit(3).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list2));
    }

    @Test
    public void test2() {
        int[] ints = new int[]{1,4,3,2,4};
        int[] ints1 = Arrays.stream(ints).map(operand -> operand + 1).toArray();
        System.out.println(JSON.toJSONString(ints1));
        int reduce = Arrays.stream(ints).reduce(0, Integer::sum);
        System.out.println(reduce);
    }
    @Test
    public void test3() {
        List<String> list = new ArrayList<>();
        list.add("a-1");
        list.add("b-2");
        list.add("c-3");
        list.add("d-4");
        list.add("e-5");
        Optional<String> any = list.stream().flatMap(e -> Stream.of(e.split("-"))).parallel().max(Comparator.comparingInt(a -> a.getBytes()[0]));
        any.ifPresent(System.out::println);

    }
    @Test
    public void test4() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        String reduce = list.stream().parallel().reduce("", (a, b) -> a + b);
        System.out.println(reduce);
    }
    /**
     * consumer
     */
    @Test
    public void test5() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.stream().parallel().forEachOrdered(e->System.out.println(Thread.currentThread().getName()+e));
    }
    /**
     * function
     */
    @Test
    public void test6() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.stream().parallel().map(String::length).forEach(System.out::println);
    }
    /**
     * function
     */
    @Test
    public void test7() {
        Function<Integer,Integer> function = i -> i * i;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.stream().map(function.andThen(function)).forEach(System.out::println);
        System.out.println("-------");
        list.stream().map(function.compose(e->e -1)).forEach(System.out::println);
        System.out.println("-------");
        list.stream().map(function.andThen(function.compose(e->e/2))).forEach(System.out::println);
    }
    /**
     * Operator
     */
    @Test
    public void test8() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(Integer::intValue))).ifPresent(System.out::println);
        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
    }
    /**
     * Operator
     */
    @Test
    public void test9() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(-3);
        list.add(4);
        list.add(5);
        list.stream().filter(a->a>0).sorted(Comparator.comparing(Integer::intValue)).limit(2).forEach(System.out::println);
        Stream.generate(()->2).limit(10).forEach(System.out::println);
    }
    /**
     * Collectors-toCollection
     */
    @Test
    public void test10() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(4);
        list.add(5);
        ArrayList<Integer> collect = list.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(JSON.toJSONString(collect));
        HashSet<Integer> collect1 = list.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println(JSON.toJSONString(collect1));
    }
    @Data
    static class Student {
        private int id;
        private String name;
        private int age;

        public Student() {
        }

        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    /**
     * Collectors-toMap
     */
    @Test
    public void test11() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(3, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Map<Integer, Student> collect = Stream.of(s1, s2, s3).collect(Collectors.toMap(Student::getId, Function.identity(),BinaryOperator.maxBy(Comparator.comparing(Student::getName))));
        System.out.println(JSON.toJSONString(collect));
        ConcurrentMap<Integer, Student> collect1 = Stream.of(s1, s2, s3).collect(Collectors.toConcurrentMap(Student::getId, student -> student, (student, student2) -> {
            byte[] bytes = student.getName().getBytes();
            byte[] bytes1 = student2.getName().getBytes();
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] > bytes1[i]) {
                    return student;
                }
            }
            return student2;
        }));
        System.out.println(JSON.toJSONString(collect1));
        LinkedHashMap<Integer, Student> collect2 = Stream.of(s1, s2, s3).collect(Collectors.toMap(Student::getId, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Student::getName)), LinkedHashMap::new));
        System.out.println(JSON.toJSONString(collect2));
    }
    /**
     * Collectors-joining
     */
    @Test
    public void test12() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(3, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        String collect = Stream.of(s1, s2, s3).map(Student::getName).collect(Collectors.joining());
        System.out.println(collect);
        String collect1 = Stream.of(s1, s2, s3).map(Student::getName).collect(Collectors.joining("^_^"));
        System.out.println(collect1);
        String collect2 = Stream.of(s1, s2, s3).map(Student::getName).collect(Collectors.joining("^_^","[","]"));
        System.out.println(collect2);
    }
    /**
     * Collectors-counting
     */
    @Test
    public void test13() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(3, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Long collect = Stream.of(s1, s2, s3).map(Student::getName).collect(Collectors.counting());
        System.out.println(collect);
        Long collect1 = Stream.of(s1, s2, s3).map(Student::getName).count();
        System.out.println(collect1);
    }
    /**
     * Collectors-max,min,maxBy,minBy
     */
    @Test
    public void test14() {
        Stream.of(1,4,6,2,6,23,6,3).collect(Collectors.maxBy(Integer::compareTo)).ifPresent(System.out::println);
        Stream.of(1,4,6,2,6,23,6,3).collect(Collectors.minBy(Integer::compareTo)).ifPresent(System.out::println);
        Stream.of(1, 4, 6, 2, 6, 23, 6, 3).max(Integer::compareTo).ifPresent(System.out::println);
        Stream.of(1, 4, 6, 2, 6, 23, 6, 3).min(Integer::compareTo).ifPresent(System.out::println);
    }
    /**
     * Collectors-summarizingInt
     */
    @Test
    public void test15() {
        IntSummaryStatistics collect = Stream.of(1, 4, 6, 2, 6, 23, 6, 3).collect(Collectors.summarizingInt(Integer::valueOf));
        System.out.println(JSON.toJSONString(collect));
    }
    /**
     * Collectors-averagingInt
     */
    @Test
    public void test16() {
        Double collect = Stream.of(1, 4, 6, 2, 6, 23, 6, 3).collect(Collectors.averagingInt(Integer::valueOf));
        System.out.println(collect);
    }
    /**
     * Collectors-averagingInt
     */
    @Test
    public void test17() {
        Double collect = Stream.of(1, 4, 6, 2, 6, 23, 6, 3).collect(Collectors.averagingInt(Integer::valueOf));
        System.out.println(collect);
    }
    /**
     * Collectors-averagingInt
     */
    @Test
    public void test18() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(2, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Stream.of(s1, s2, s3).map(s -> s.getName().length()).collect(Collectors.reducing(Integer::sum)).ifPresent(System.out::println);
        Integer collect = Stream.of(s1, s2, s3).map(s -> s.getName().length()).collect(Collectors.reducing(0, (a, b) -> a + b));
        System.out.println(collect);
        Integer collect1 = Stream.of(s1, s2, s3).collect(Collectors.reducing(0, s -> ((Student) s).getName().length(), Integer::sum));
        System.out.println(collect1);
    }
    /**
     * Collectors-groupingBy
     */
    @Test
    public void test19() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(2, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Map<String, Set<Student>> collect = Stream.of(s1, s2, s3).collect(Collectors.groupingBy(s -> {
            if (s.getId() > 2) {
                return "大于";
            } else if (s.getId() < 2) {
                return "小于";
            } else {
                return "等于";
            }
        }, Collectors.toSet()));
        System.out.println(JSON.toJSONString(collect));
    }
    /**
     * Collectors-partitioningBy
     */
    @Test
    public void test20() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(2, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Map<Boolean, Set<Student>> collect = Stream.of(s1, s2, s3).collect(Collectors.partitioningBy(o -> o.getId() == 2, Collectors.toSet()));
        System.out.println(collect);
    }
    /**
     * Collectors-mapping
     */
    @Test
    public void test21() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(2, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        List<String> collect = Stream.of(s1, s2, s3).collect(Collectors.mapping(Student::getName, Collectors.toList()));
        System.out.println(collect);
    }
    /**
     * Collectors-collectingAndThen
     */
    @Test
    public void test22() {
        Student s1 = new Student(3, "小明", 18);
        Student s2 = new Student(2, "小红", 17);
        Student s3 = new Student(1, "小李", 18);
        Iterator<Student> collect = Stream.of(s1, s2, s3).collect(Collectors.collectingAndThen(Collectors.toList(), List::iterator));
    }
    /**
     * Collectors-collectingAndThen
     */
    @Test
    public void test23() throws ExecutionException {
        LoadingCache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String s) throws Exception {
                return 0;
            }
        });
        Integer num = cache.get("1");
        cache.put("1",++num);
        num = cache.get("1");
        System.out.println(JSON.toJSONString(num));
    }



}
