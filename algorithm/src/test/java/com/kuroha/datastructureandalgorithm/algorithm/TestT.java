package com.kuroha.datastructureandalgorithm.algorithm;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TestT {


    @Test
    public void testt() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/Users/a123123/sql/pdf/运营领域/医疗/医疗.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/a123123/sql/pdf/运营领域/医疗/医疗2.txt"));
        while (br.ready()) {
            String str = br.readLine();
            String[] split = str.split("\t");
            String s = split[0];
            bw.write(s);
            bw.newLine();
        }
        bw.close();
        br.close();
    }
}
