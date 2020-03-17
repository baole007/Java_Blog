package com.baol.project;


//函数式接口   一个   抽象类

import java.util.Comparator;
import java.util.concurrent.Callable;

/**
 * JDK1.8之前的一些函数式接口
 * java.lang.Runable
 * package java.util.concurrent.Callable<V>
 *     java.util.Comparator<T>
 *     java.util.function
 *                      Supplier<T> 代表一个输出
 *                      Consumer<T> 代表一个输入(消费)
 *                      BiConsumer<T, U> 代表两个输入
 *                      Function<T, R>  T是输入 R是输出     （输入输出 一般不是同一种类型）
 *                      UnaryOperator<T> extends Function<T, T>  代表一个输入 一个输出 （输入输出 同一种类型）
 *                      public interface BiFunction<T, U, R>  两个输入 一个输出  （输入输出 一般不是同一种类型）
 *                      BinaryOperator<T> extends BiFunction<T,T,T>  代表两个输入 一个输出（输入输出 同一种类型）
 *
 */
@FunctionalInterface
public interface UserMapper {
    int insert();

    public int hashCode();//Object 除外

    default int delete(){//default 除外
        return 1;
    }
    static int update(){// static 除外
        return 1;
    }
}
