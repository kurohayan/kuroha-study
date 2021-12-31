package com.kuroha.opencv;

import com.kuroha.opencv.service.OpenCvService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Disabled("explanation")
public class OpencvServiceTest {

    @Autowired
    private OpenCvService openCvService;

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
