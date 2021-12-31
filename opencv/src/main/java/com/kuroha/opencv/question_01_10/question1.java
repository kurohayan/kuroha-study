package com.kuroha.opencv.question_01_10;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author samtofor
 */
public class question1 {

    public static void main(String[] args) {
        System.load(ClassLoader.getSystemResource(Core.NATIVE_LIBRARY_NAME + ".dll").getPath());
        Mat image = Imgcodecs.imread("image/imori.jpg");

        System.out.println(image);
    }

}
