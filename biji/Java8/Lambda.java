import com.baol.entity.Item;

import java.io.Closeable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda {
    //学习网址 ：https://edu.51cto.com/center/course/lesson/index?id=186730    qq登录

    static void test() throws Exception {
        Date date = new Date();
        // java 8新函数  2020-01-01
        LocalDate date1 = LocalDate.now();
        //不会更改date1  只会新创建一个

        date1.plusDays(1);
        // java 8新函数  2020-01-01T12:01:01
        LocalDateTime dateTime = LocalDateTime.now();


        //以前实现Thread方法 中的抽象方法
        //启动一个线程执行任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread");
            }
        }).start();
        //用Lambda表达式之后
        new Thread(() -> {
            System.out.println("Thread");
        }).start();

        List<String> strs = Arrays.asList("bbbbbb", "aaaa", "vvvvvvvvvv", "f");
        Collections.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        //用Lambda表达式   特点
        // 1.参数类型自动推断  下面没有声明 a  b什么类型 自动推断成string 类型
        // 2.代码量少 简洁

        Collections.sort(strs, (a, b) -> a.length() - b.length());
        System.out.println(strs);

        //3.更容易并行
        // 下面要说的就是  函数式接口 ：只有一个抽象方法(Object的方法除外)的接口 是函数式接口

        //普通的无参数 无返回值
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("1111");
            }
        };
        r1.run();
        //lambda表达式  无参数 无返回值
        Runnable r2 = () -> {
            System.out.println("1111");
        };
        r2.run();
        //普通 无参  有返回值
        Callable<String> c1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "1111111";
            }
        };
        //这里会报错因为lambda不需要 也不允许 throws抛出异常 所以在方法上直接抛出
        Callable<String> c2 = () -> {
            return "2222";
        };
        Callable<String> c3 = () -> "3333";
        ;
        System.out.println(c1.call());
        System.out.println(c2.call());
        System.out.println(c3.call());
        UserMapper u1 = new UserMapper() {
            @Override
            public void inster(Item item) {
                System.out.println("inster" + item);
                return;
            }
        };
        u1.inster(new Item());
        UserMapper u2 = (Item item) -> {
            System.out.println("inster");
        };
        UserMapper u3 = (item) -> {
            System.out.println("inster");
        };
        u2.inster(new Item());
        u3.inster(new Item());

        OrderMaper orderMaper = new OrderMaper() {
            @Override
            public int inster(Item item) {
                System.out.println("orderMaper");
                return 0;
            }
        };
        OrderMaper orderMaper1 = (item) -> {
            System.out.println("orderMaper");
            return 0;
        };
        OrderMaper orderMaper2 = (Item item) -> {
            System.out.println("orderMaper");
            return 0;
        };
        OrderMaper orderMaper3 = (Item item) -> 0;
        //省略参数类型
        OrderMaper orderMaper4 = (item) -> 0;

        //输入 Integer  输出Integer类型
        Function<Integer, Integer> f1 = (a) -> {
            int sum = 0;
            for (int i = 0; i < a; i++) {
                sum = sum + i;
            }
            return sum;
        };

        System.out.println(f1.apply(10));
    }

    static int get() {
        return 1;
    }

    static void set() {
    }

    //Lambda 表达式
    public static void main(String[] args) {


        //为啥可以 他只执行了方法 不接受返回值
        Runnable r = () -> get();
        //这种不行 因为他直接有返回值
//        Runnable r2 = () -> 100;

        Runnable r1 = () -> set();

        Foo foo = () -> get();

        //下面的是错误的 因为 set方法 没有返回值
        //具体的 根据实现的抽象方法 决定的  如果你的抽象方法 定义 必须有返回值 就必须有返回值
         //                             如果你的抽象方法 没有定义 必须有返回值 那么 他会执行放法 而不接收方法里面的返回值
//        Foo foo1 = () ->set();


        BiFunction<String,String ,Integer> biFunction = (a,b)->{return a.length()+b.length();};
        biFunction.apply("java","add");

    }

    interface Foo {
        int get();
    }

    interface UserMapper {
        void inster(Item item);
    }

    interface OrderMaper {
        int inster(Item item);
    }

    /**
     * 总结：
     * （Object args） ->{函数式接口抽象方法实现逻辑}
     * （）里面的参数个数，根据函数式接口里面的抽象方法的参数个数来决定 当只有一个参数时  ()可以省略
     *  Function<String, Integer> f2 = a -> a.length();
     *  当expr逻辑非常简单的时候   {}  和 return 可以省略
     *
     * Lambda表达式
     *    看参数
     *    看返回值
     *
     */

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
    ////////////////////////////////////////////////////////////////////
    /////////方法的引用//////////////////////////

    public static String get1(){
        return "hello";
    }

    public static Integer get1(String  a){
        return 1;
    }

    public String get2(){
        return "hello";
    }

    public String get2(String i){
        return "hello";
    }


    public static void test1(){
        /**
         * 先来说一下方法引用使用到的操作符“::”，这个操作符把方法引用分成两边，左边是类名或者某个对象的引用，右边是方法名。引用方法有下面几种方式：
         * this 调用自己的方法  或者 super调用父类的方法
         *
         * （1）对象引用::实例方法名
         *
         * （2）类名::静态方法名
         *
         * （3）类名::实例方法名
         *
         * （4）类名::new
         *
         * （5）类型[]::new
         */

        //静态方法引用   如果函数式接口的实现 恰好可以通过调用一个静态方法来实现 那么就可以使用静态方法引用
            //类名::staticMethod
            //Lambda表达式
            Supplier<String> s = () ->Lambda.get1();
            //也可以这样写 恰好是 通过调用静态方法来实现
            Supplier<String> s1 = Lambda::get1;//  这里的get方法 不需要()  因为只是引用 不是调用方法
            //既有输入 又有输出的
            Function<String,Integer> function = a -> Lambda.get1(a);
            Function<String,Integer> function1 = Lambda::get1;
        //实例方法引用     如果函数式接口的实现 恰好可以通过调用一个实例方法来实现 那么就可以使用实例方法引用
            //inst::instMethod
            //Lambda表达式
            Supplier<String> s2 = () ->new Lambda().get2();
            //也可以这样写 恰好是 通过调用实例方法来实现
            Supplier<String> s3 = new Lambda()::get2;//  这里的get方法 不需要()  因为只是引用 不是调用方法
            //既有输入 又有输出的
            Function<String,String> function2 = a -> new Lambda().get2();
            Function<String,String> function3 = new Lambda()::get2;
        //对象方法引用  抽象方法的第一个参数类型刚好是实例方法的类型，抽象方法剩余的参数，恰好可以当做实例方法的参数，如果函数式接口的实现能
        //由上面说的实例方法调用来实现的话，那么就可以使用对象方法引用
        //注意：：： 第一个参数类型最好是自定义的类型 不要用自带的方法的
            //语法
            //类名::instMethod
                //抽象方法没有输入参数，不嫩使用对象方法引用
                //比如下面这三个函数式接口
                Runnable run = ()->{};
                Closeable c = () ->{};
                Supplier<String> s4 = () -> "";
            Consumer<Too> consumer = (Too too) -> new Too().too();
            Consumer<Too> consumer1 = Too::too;

            BiConsumer<Too,String> biConsumer = (too,str) ->new Too1().to(str);
            BiConsumer<Too1,String> biConsumer1 = Too1::to;

            BiFunction<Too2,String,Integer> biFunction = (p, j) -> new Too2().too(j);
            BiFunction<Too2,String,Integer> biFunction1 = Too2::too;
        //构造方法引用     如果函数式接口的实现 恰好可以通过调用一个构造方法来实现 那么就可以使用构造方法引用
            //语法     类名::new
            Supplier<Too3> supplier = () ->new Too3();
            Supplier<Too3> supplier1 = Too3::new;
            Supplier<String> supplier2 = String::new;
            Supplier<Thread> supplier3 = Thread::new;
            Supplier<List> supplier4 = ArrayList::new;
            Supplier<Set> supplier5 = HashSet::new;

            Consumer<String> consumer2 = str ->new Too4(str);
            Consumer<String> consumer3 = Too4::new;

    }

}

class Too {
    public void too() {
    }
}
class Too1 {
    public void too() {
    }
    public void to(String srt) {
    }
}
class Too2 {
    public Integer too(String s) {
        return 1;
    }
}

class Too3 {
}

class Too4 {
    public Too4(String i){

    }

    public static void main(String[] args) {
        //Lambda实际应用场景
        String str = "userId=1000&userName=Tom&mobile=100000&type=20&channleId=ng";
        Map<String,String> map = Stream.of(str.split("&")).map(s -> s.split("=")).collect(Collectors.toMap(s->s[0],s->s[1]));
        System.out.println(map);
    }
}

