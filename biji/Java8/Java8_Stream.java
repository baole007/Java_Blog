import sun.jvm.hotspot.utilities.IntArray;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream是一组用来处理数组，集合的api
 * 1.不是数据结构 没有内部存储
 * 2.不支持索引访问
 * 3.延迟计算
 * 4.支持并行
 * 5.很容易生成数组或集合（List  Set）
 * 6.支持过滤 查找 转换 汇总 聚合等操作
 *
 *
 * Stream运行机制
 *      Stream分为源source  中间操作 终止操作
 *      流的源 可以使一个数组 一个集合 一个生成器 一个I/O通道等等
 *      一个流可以有零个和或者多个中间操作，每一个中间操作都会但会一个新的额流 供下一个操作使用 一个流只会有一个终止操作
 *      Sream 只有遇到终止操作  他的源才开始执行遍历操作
 *
 *
 * Stream 常用的API
     * 常见的中间操作
     *      过滤 filer
     *      去重 distinct
     *      排序 sorted
     *      截取 limit skip
     *      转换   map/flatMap
     *      其他 peek
     * 终止操作
     *      循环 forEach
     *      计算 min,max,count,average
     *      匹配 anyMatch,allMatch,noneMatch,findFist,findAny
     *      汇聚 reduce
     *      收集器 toArray collect
     * Stream 的创建
     *  1通过数组
     *  2、通过集合来
     *  3、通过Stream.generate方法来创建
     *  4、通过Stream.iterate方法来创建
     *  5、其他API创建
 */
public class Java8_Stream {

    //主要
    static void gen1(){
        String[]  arr = {"a","b","1","2"};
        Stream<String>stream = Stream.of(arr);
        stream.forEach(System.out::println);

    }
    //主要
    static void gen2(){
        List<String> list = Arrays.asList("a","b","1","2");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);

    }
    static void gen3(){
        Stream<Integer> stream = Stream.generate( ()->1  );
        //generate遇到终止操作 会一直执行下去
//        stream.forEach(System.out::println);
        //如果想要结束 需要用到前面提到的 截取
        stream.limit(10).forEach(System.out::println);

    }

    static void gen4(){
        Stream<Integer> stream = Stream.iterate(  1,x ->x+1 );
        //generate遇到终止操作 会一直执行下去
//        stream.forEach(System.out::println);
        //如果想要结束 需要用到前面提到的 截取
        stream.limit(10).forEach(System.out::println);

    }

    static void gen5(){
        String str = "abcd12345";
        IntStream stream = str.chars();
//        stream.forEach(x -> System.out.println("1111"+x));
        stream.forEach(System.out::println);
    }

    public void test() {
        //下面的filter 是筛选
       Arrays.asList(1,2,3,4,5,6).stream().filter(x ->{
               System.out.println("222");
               return  x%2 == 0;
       }
       //下面如果没有forEach这个终止操作 他就会一直延迟计算  只有遇到终止操作的时候 才会执行 filter
       ).forEach(System.out::println);

       //下面 通过mapToInt 的终止操作 进行里面所有Int值得sum
        int sum = Arrays.asList(1,2,3,4,5,6).stream().filter(x ->x%2 == 0).mapToInt(x->x).sum();
        System.out.println(sum);

        //选出最大  max里面传一个比较器 两个参数
        int max = Arrays.asList(1,2,3,4,5,6).stream().max((x,y) -> x-y).get();
        System.out.println(max);


        //过滤器
        System.out.println("111111");
        Optional<Integer> optionalInteger = Arrays.asList(1,2,3,4,5,6).stream().filter(x ->x%2 == 0).findAny();
        System.out.println(optionalInteger.get());


        //所有 1-50 里面的所有偶数 放在一个list里面
        System.out.println("1231232131");
        Stream.iterate(  1,x ->x+1 ).limit(50).filter(x -> x%2==0).forEach(System.out::println);
        //收集器
        List<Integer> list= Stream.iterate(  1,x ->x+1 ).limit(50).filter(x -> x%2==0).collect(Collectors.toList());
        System.out.println(list);

        //转换 把字符串分割 依次转换成int 然后求和
        String str = "11,22,33,44,55";
        int sum1 = Stream.of(str.split(",")).mapToInt(x ->Integer.valueOf(x)).sum();
        int sum2 = Stream.of(str.split(",")).mapToInt(Integer::valueOf).sum();
        String st2 = "Admin,Apche,RabbitMq,Redis";
        Stream.of(st2.split(",")).map(x->Person.build(x)).forEach(System.out::println);
        Stream.of(st2.split(",")).map(Person::build).forEach(System.out::println);
        //peek  基本上就是记录一个日志
        Stream.of(st2.split(",")).peek(System.out::println).map(Person::build).forEach(System.out::println);

    }

    public static void main(String[] args) {
        //并行流  内部多线程的方式 实现并行
        //parallel 并行流
        //sequential 同行流
        //那个在后面 算哪个
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","5");
        Optional<Integer> optionalInteger1 = Stream.iterate(1,x ->x+1).limit(200).peek(x->{
            System.out.println(Thread.currentThread().getName());
        }).parallel().max(Integer::compare);
        System.out.println(optionalInteger1);
    }



}
class Person{

    public static Person build(String name){
        Person person = new Person();
        person.setName(name);
        return person;
    }

    private String name;
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}