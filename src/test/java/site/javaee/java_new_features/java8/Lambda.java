package site.javaee.java_new_features.java8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xmlunit.util.Predicate;
import site.javaee.java_new_features.java8.bean.Employee;
import site.javaee.java_new_features.java8.bean.Status;
import site.javaee.java_new_features.java8.design.FilterEmployeeByAge;
import site.javaee.java_new_features.java8.design.MyPredicate;

import java.io.PrintStream;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Lambda 是一个匿名函数，可以把 Lambda 表达式理解为是一段可以传递的代码（将代码 像数据一样进行传递）。
 *
 * @author shkstart
 * @create 2020-06-03 0:27
 */
@SpringBootTest
public class Lambda {
    List<Employee> employees = Arrays.asList(
            new Employee("张三", 31, 5500, Status.FREE),
            new Employee("李四", 36, 6600, Status.FREE),
            new Employee("王五", 38, 4700, Status.FREE)
    );

    /**
     * 从匿名内部类到Lambada表达式
     */
    @Test
    void anonymous() {
        //匿名内部类
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);

        //Lambada表达式
        Comparator<Integer> comparator2 = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> treeSet2 = new TreeSet<>(comparator2);
    }

    /**
     *匿名内部类到lambda
     */
    @Test
    void optimize() {

        List<Employee> list;

        //获取当前公司中员工年龄大于35的员工信息，需要为每一个过滤条件编写一个额外的方法
        list = filterEmployees(employees);
        System.out.println(list);

        //获取当前公司中员工年龄大于35的员工信息(接口-实现类)
        list = filterEmployees3(employees, new FilterEmployeeByAge(35));
        System.out.println(list);

        //获取当前公司中员工工资大于6500的员工信息(接口-匿名内部类）
        list = filterEmployees3(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() >= 6500;
            }
        });
        System.out.println(list);

        //获取当前公司中员工工资大于6500的员工信息(接口-lambda）
        list = filterEmployees3(employees,(employee) -> employee.getSalary() >= 6500);
        System.out.println(list);

        //获取当前公司中员工工资大于6500的员工信息(Stream）
        employees.stream()
                .filter((employee) -> employee.getSalary()>=6500)
                .limit(2)
                .forEach(System.out::println);
    }

    /**
     * 获取当前公司中员工年龄大于35的员工信息
     *
     * @param list
     * @return
     */
    public static List<Employee> filterEmployees(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if (emp.getAge() >= 35) {
                emps.add(emp);
            }
        }
        return emps;
    }

    /**
     * 获取当前公司中员工工资大于6500的员工信息
     *
     * @param list
     * @return
     */
    public static List<Employee> filterEmployees2(List<Employee> list) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if (emp.getSalary() >= 6500) {
                emps.add(emp);
            }
        }
        return emps;
    }

    /**
     * 策略设计模式优化,将过滤条件作为接口
     */
    public static List<Employee> filterEmployees3(List<Employee> list, MyPredicate<Employee> myPredicate) {
        List<Employee> emps = new ArrayList<>();
        for (Employee emp : list) {
            if (myPredicate.test(emp)) {
                emps.add(emp);
            }
        }
        return emps;
    }


    /**
     * lambda基础语法
     */
    @Test
    void base(){
        /*
        Lambda 表达式在Java 语言中引入了一个新的语法元素和操作符。这个操作符为 “->” ， 该操作符被称 为 Lambda 操作符或剪头操作符。
            左侧：指定了 Lambda 表达式需要的所有参数
            右侧：指定了 Lambda 体，即 Lambda 表达式要执行 的功能。

        函数式接口：
            Lambda表达式需要“函数式接口”的支持，即接口中只有一个抽象方法的接口。
            可以使用@FunctionalInterface修饰，注解会检测是否是函数式接口。
         */

        /*
        语法格式一：无参，无返回值
         */
        Runnable runnable = new Runnable() {
            int num=0;
            @Override
            public void run() {
                //jdk7 以前匿名内部类使用同级别变量时，变量必须用final修饰，jdk8会默认给变量加上final。
                System.out.println("Hello!" + num);
            }
        };
        runnable.run();

        Runnable runnable2 = ()-> System.out.println("Hello!");
        runnable2.run();

        /*
        语法格式二：Lambda 带参，有返回值
         */
        //lambda表达式的参数列表的数据类型可以不写，JVM编译器通过上下文推断出数据类型，即“类型推断”
        Consumer<String> consumer = (x)-> System.out.println(x);
        //只有一个参数
            //只有一个参数时，参数的小括号可以省略
        Consumer<String> consumer2 = x-> System.out.println(x);
        consumer.accept("go to lambda!");
        //有多个参数
        Comparator<Integer> comparator = (o1, o2) -> {
            //lambda体有多条语句时需要用大括号
            System.out.println("函数式接口");
            return Integer.compare(o1, o2);
        };
            //lambda体只有一条语句时，return和大括号可以省略
        Comparator<Integer> comparator2 = (o1, o2) -> Integer.compare(o1, o2);

    }

    /**
     * 作为参数传递lambda表达式
     */
    @Test
    void trans(){
        Comparator<Employee> comparator = (emp1, emp2) -> {
          if(emp1.getAge() == emp2.getAge()){
              return emp1.getName().compareTo(emp2.getName());
          }else {
              return Integer.compare(emp1.getAge(),emp2.getAge());
              //倒序
              // return -Integer.compare(emp1.getAge(),emp2.getAge());
          }
        };
        //为了将 Lambda 表达式作为参数传递，接 收Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口 的类型。
        Collections.sort(employees,comparator);
        System.out.println(employees);
    }


    /**
     * 函数式接口，Java 内置四大核心函数式接口
     */
    @Test
    void functional(){
        //Consumer<T>消费型接口，void accept(T t)
        happy(10000, aDouble -> System.out.println("大娱乐每次花费："+aDouble+"元"));

        //Supplier<T>供给型接口，T get();
        List<Integer> nums = getNums(100, () -> (int) (Math.random() * 100));
        System.out.println(nums);

        //Function<T, R> 函数型接口，R apply(T t);
        String strNew = strHandler("\t\t\t 你看我牛逼不  ", s -> s.trim());
        System.out.println(strNew);

        //Predicate<T>断定型接口，boolean test(T t);
        List<String> list = Arrays.asList("Hello","World","Go","Home");
        List<String> strings = filterStr(list, s -> s.length() >= 3);
        System.out.println(strings);

    }
    public static void happy(double money,Consumer<Double> consumer){
        consumer.accept(money);
    }
    public static List<Integer> getNums(int number, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<number; i++){
            Integer n =  supplier.get();
            list.add(n);
        }
        return  list;
    }
    public static String strHandler(String str, Function<String,String>function){
        return function.apply(str);
    }
    public static List<String> filterStr(List<String> list, Predicate<String> predicate){
        List<String> strList = new ArrayList<>();
        for(String str:list){
            if(predicate.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }


    /**
     * 方法引用：若lambda体重的内容有方法已经实现了，我们可以使用“方法引用”
     * （可以理解为方法引用是 Lambda 表达式的另外一种表现形式）
     */
    @Test
    void methodRef(){
        /*
        语法：
            1、对象::实例方法名
            2、类::静态方法名
            3、类::实例方法名
        注意：
            1、lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致。
            2、若lambda参数列表中的第一参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
         */

        //对象::实例方法名
        Consumer<String> con = (x)-> System.out.println(x);
        PrintStream out = System.out;
        Consumer<String> con2 = out::println;
        con2.accept("abcdef");

        Employee employee = new Employee("张三",12,4000, Status.FREE);
        Supplier<String> supplier = () -> employee.getName();
        System.out.println(supplier.get());
        Supplier<String> supplier2 = employee::getName;
        System.out.println(supplier.get());


        //类::静态方法名
        Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);
        Comparator<Integer> comparator2 = Integer::compare;



        //类::实例方法名
        BiPredicate<String,String> biPredicate = (s, s2) -> s.equals(s2);
        BiPredicate<String,String> biPredicate2 = String::equals;

    }

    /**
     * 构造器引用
     */
    @Test
    void constructorRef(){
        /*
        语法:ClassName::new

        注意：
            是要调用的构造器参数列表要与函数式接口中抽象方法的参数列表保持一致
         */
        Supplier<Employee> supplier=() -> new Employee();
        Supplier<Employee> supplier2 = Employee::new;
        System.out.println(supplier2.get());

        Function<Integer,Employee> function = integer -> new Employee(integer);
        Function<Integer,Employee>  function2=Employee::new;
        Employee employee  = function2.apply(101);
        System.out.println(employee);
    }


    /**
     * 数组引用
     */
    @Test
    void arrayRef(){
        /*
        语法：
         */
        Function<Integer,String[]> function = integer -> new String[integer];
        System.out.println(function.apply(10).length);

        Function<Integer,String[]> function2 = String[]::new ;
        System.out.println(function2.apply(20).length);
    }
}
