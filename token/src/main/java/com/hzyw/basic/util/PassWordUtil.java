package com.hzyw.basic.util;

public class PassWordUtil {

    public static String generatePassWord(int length){
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            double rand = Math.random();
            double randTri = Math.random() * 3;
            if (randTri >= 0 && randTri < 1) {
                sb.append((char) (rand * ('9' - '0') + '0'));
            } else if (randTri >= 1 && randTri < 2) {
                sb.append((char) (rand * ('Z' - 'A') + 'A'));
            } else {
                sb.append((char) (rand * ('z' - 'a') + 'a'));
            }
        }
        return sb.toString();
    }
}
