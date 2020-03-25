package com.baol.dao;

////Lambda 表达式
//public class Test {
//    public boolean Find(int target, int[][] array) {
//        if (array.length == 0) return false;
//
//        for (int i = 0; i < array.length; i++) {
//            if (array[i][1] < target) {
//                return false;
//            }
//            for (int j = 0; j < array.length; j++) {
//                if (array[i][j] == target) {
//                    return true;
//                } else {
//                    j++;
//                }
//            }
//        }
//        return false;
//    }
//
//}
public class Test {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final Test test = new Test();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                };
            }.start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }
}
