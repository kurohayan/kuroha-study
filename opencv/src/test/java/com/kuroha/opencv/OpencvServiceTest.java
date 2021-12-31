package com.kuroha.opencv;

import com.kuroha.opencv.service.OpenCvService;
import com.kuroha.opencv.service.impl.OpenCvServiceImpl;
import org.junit.jupiter.api.Test;

public class OpencvServiceTest {

    private final OpenCvService openCvService = new OpenCvServiceImpl();

    @Test
    public void test() {
        openCvService.test();
    }

    @Test
    public void test2() {
        openCvService.test2();
    }

    @Test
    public void test3() {
        openCvService.test3();
    }

    @Test
    public void surfFind() {
        String templateFilePath = "D:/tmp/photo/2.jpeg";
        String originalFilePath = "D:/tmp/photo/3.jpeg";
        openCvService.surfFind(templateFilePath,originalFilePath);
    }
}
