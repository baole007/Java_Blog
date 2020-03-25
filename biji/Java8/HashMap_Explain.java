
import java.util.HashMap;
import java.util.Map;

public class HashMap_Explain {
    /**
     * HashMap最早出现在JDK1.2中，底层基于散列算法实现。HashMap 允许 null 键和 null 值，是非线程安全类，在多线程环境下可能会存在问题。
     *  1 问：为什么有的是链表有的是红黑树？ 答：默认链表长度大于8时转为树
     *
     */

    /**
     * HashMap讲解
     *  Hash 散列 将一个任意长度 通过某种(hash函数算法)算法 转换成一个固定值
     *  移位
     *  Map: 地图 x,y存储
     *  总结：通过Hash出来的值，通过值定位到这个map 然后value存储到这个map中
     *
     *
     */


    public static void main(String[] args) {
        HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
        hashMap.put(1,"one");// put到底是做了什么?数据存储方式是什么 -》按照什么样的数据结构进行存储的
        /**
         * 1.7  数组+链表
         * 1.7  数组+链表+红黑树
         */
        System.out.println("one".hashCode());

        hashMap.get("one");
    }



}
