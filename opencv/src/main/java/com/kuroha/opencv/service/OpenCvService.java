package com.kuroha.opencv.service;

import org.opencv.core.Mat;

/**
 * @author samtofor
 */
public interface OpenCvService {
    void test();

    void test2();

    void test3();

    void surfFind(Mat templateImage, Mat originalImage);

    void surfFind(String templateFilePath, String originalFilePath);

}
