package com.kuroha.datastructureandalgirithm.test;

/**
 * @author kuroha
 */
public class Test1 {

    public static double find(double num) {
        double low = 0;
        double high = num;
        double mid = low + (high - low) /2;
        while ((high - low) > 0.000001) {
            double v = mid * mid;
            if (v > num) {
                high = mid;
            } else if (v < num) {
                low = mid;
            } else {
                return mid;
            }
            mid = low + (high - low) /2;
        }
        if (num >=0) {
            return mid;
        } else {
            return -mid;
        }
    }

    public static void main(String[] args) {
        System.out.println(find(9));
    }

}
