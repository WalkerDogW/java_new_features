package site.javaee.java_new_features.java8;

import org.junit.jupiter.api.Test;
import site.javaee.java_new_features.java8.bean.Employee;
import site.javaee.java_new_features.java8.bean.Status;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流（Stream）是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 * 集合讲的是数据，流讲的是计算
 *
 * @author shkstart
 * @create 2020-06-17 0:30
 */
public class StreamAPI {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 31, 5500, Status.FREE),
            new Employee("李四", 36, 6600,Status.BUSY),
            new Employee("王五", 38, 4700,Status.VOCATION),
            new Employee("马六", 23, 5200, Status.FREE),
            new Employee("马六", 23, 5200, Status.FREE),
            new Employee("马六", 23, 5200, Status.FREE),
            new Employee("赵七", 28, 3700, Status.FREE)
    );

    /*
    注意事项：
        Stream自己不会存储元素。
        Stream不会改变源对象， 进行终止操作后会返回一个持有结果的新Stream。
        Stream操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。
     */

    /**
     * 创建Stream的方式
     */
    @Test
    void create() {

        //1、Collection系列集合提供的stream()或parallelStream()获取集合流
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //2、通过Arrays中的静态方法stream()获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);

        //3、通过Stream类中的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4、通过Stream类中的静态方法iterate()或者generate()创建无限流
        Stream<Integer> stream4 = Stream.iterate(0, integer -> integer + 2);
        stream4.limit(10).forEach(System.out::println);

        Stream<Double> stream5 = Stream.generate(() -> Math.random());
        stream5.limit(10).forEach(System.out::println);
    }

    /**
     * 筛选：filter，接收lambda，从流中排除某些元素。
     * 限流：limit，使元素不超过给定数量
     * 跳过：skip，跳过元素，返回一个扔掉了前n个元素的流
     * 去重：distinct，通过hashCode（）和equals（）去除重复元素
     */
    @Test
    void filter() {
        /*
        惰性求值：
            多个中间操作可以连接起来形成一个流水线，除非流水线上出发终止操作，
            否则中间操作不会执行任何处理，而在终止操作时一次性全部处理。

         */
        Stream<Employee> stream = employees.stream().filter(employee -> {
            System.out.println("中间操作");
            return employee.getAge() > 35;
        });
        //终止操作，foreach内部迭代
        stream.forEach(System.out::println);
    }

    @Test
    void limit() {
        employees.stream()
                .filter(employee -> employee.getSalary() > 5000)
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    void distinct() {
        employees.stream()
                .filter(employee -> employee.getSalary() > 5000)
                .skip(1)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map：接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，
     * 该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * FlatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    void map() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    @Test
    void flatMap() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        Stream<Stream<Character>> stream = list.stream()
                .map(StreamAPI::filterCharacter);
        stream.forEach(characterStream -> {
            characterStream.forEach(System.out::println);
        });

        System.out.println("---------------------------");

        Stream<Character> stream2 = list.stream()
                .flatMap(StreamAPI::filterCharacter);
        stream2.forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }


    /**
     * 排序
     * sorted():自然排序(Comparable接口)
     * sorted(Comparator com)():自定义排序(Comparator接口)
     */
    @Test
    void sort() {
        List<String> list = Arrays.asList("ccc", "bbb", "aaa", "ddd", "eee");
        list.stream().sorted().forEach(System.out::println);
        int s= 4;

        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                //以下写法有误，原因不知
//                .sorted((o1, o2) -> {
//                    return o1.getAge()-o2.getAge();
//                })
                .forEach(System.out::println);
    }

    /**
     * 终止操作:查找与匹配
     *  allMatch:检查是否匹配所有元素
     *  anyMatch：至少匹配一个元素
     *  noneMatch：没有匹配的元素
     *
     *  findFirst：取第一个
     *  findAny：随机返回
     *  count：总个数
     *
     *  max：最大值
     *  min：最小值
     */
    @Test
    void stop(){
        //allMatch
        boolean b = employees.stream().allMatch(employee -> employee.getStatus().equals(Status.BUSY));
        System.out.println(b);

        //anyMatch
        boolean b2 = employees.stream().anyMatch(employee -> employee.getStatus().equals(Status.BUSY));
        System.out.println(b2);

        //noneMatch
        boolean b3 = employees.stream().noneMatch(employee -> employee.getStatus().equals(Status.BUSY));
        System.out.println(b3);

        //findFirst
        Optional<Employee> optional = employees.stream().sorted((o1, o2) -> -Double.compare(o1.getSalary(), o2.getSalary())).findFirst();
        //使用optional解决空指针异常
        optional.orElse(new Employee());
        System.out.println(optional.get());

        //findAny
        Optional<Employee> optional2 = employees.stream().filter(employee -> employee.getStatus().equals(Status.FREE)).findAny();
        System.out.println(optional2.get());

        //count
        long count = employees.stream().filter(employee -> employee.getStatus().equals(Status.FREE)).count();
        System.out.println(count);

        //max
        Optional<Employee> optional3 = employees.stream().max((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        System.out.println(optional3.get());

        //min
        Optional<Integer> optional4 = employees.stream().map(Employee::getAge).min(Double::compare);
        System.out.println(optional4.get());
    }

    /**
     * 归约（reduce）：将流中元素反复结合起来，得到一个值
     */
    @Test
    void reduce(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum =  list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);


        Optional<Double> optional = employees.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(optional.get());
    }


    /**
     * 收集(collect):将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    void collect(){
        //list
        List<String> list = employees.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(list);
        //map
        Set<String> set = employees.stream().map(Employee::getName).collect(Collectors.toSet());
        System.out.println(set);
        //hashmap
        HashSet<String> hashSet = employees.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        System.out.println(hashSet);
        //计数
        Long count = employees.stream().collect(Collectors.counting());
        System.out.println(count);
        //平均值
        Double avgSalary = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avgSalary);
        //总和
        Double sumSalary = employees.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sumSalary);
        //最大值
        Optional<Employee> maxSalary = employees.stream().collect(Collectors.maxBy((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())));
        System.out.println(maxSalary.get());
        //分组
        Map<Status, List<Employee>> groupMap = employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(groupMap);
        //多级分组
        final Map<Status, Map<String, List<Employee>>> manyGroupMap = employees.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(employee -> {
            if (employee.getAge() <= 35) {
                return "青年";
            } else if (employee.getAge() <= 50) {
                return "中年";
            } else {
                return "老年";
            }
        })));
        System.out.println(manyGroupMap);
        //分区
        Map<Boolean, List<Employee>> partMap = employees.stream().collect(Collectors.partitioningBy(employee -> employee.getSalary() > 8000));
        System.out.println(partMap);
        //汇总
        DoubleSummaryStatistics summaryStatistics = employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(summaryStatistics.getAverage());
        //连接
        String joinEmps = employees.stream().map(Employee::getName).collect(Collectors.joining(",","s","e"));
        System.out.println(joinEmps);
    }

    @Test
    void practice(){

    }





}
