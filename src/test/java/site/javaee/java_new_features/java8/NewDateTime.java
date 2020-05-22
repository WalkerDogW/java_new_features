package site.javaee.java_new_features.java8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;

/**
 * @author Tao
 * @create 2020/5/22 15:16
 */
@SpringBootTest
public class NewDateTime {
    /**
     * Java 8 Date Time API是开发人员最热门的变更之一。
     * Java从一开始就缺少日期和时间的一致方法，Java 8 Date Time API是核心Java API的一个受欢迎的补充。
     */
    @Test
    void why() {
        /*
        1、Java Date Time类没有一致定义，我们在包java.util和java.sql包中都有Date Class 。再次格式化和解析类在java.text包中定义。
        2、java.util.Date包含日期和时间，而java.sql.Date仅包含日期。java.sql包装中的这个没有意义。这两个类都有相同的名称，这本身就是一个非常糟糕的设计。
        3、时间，时间戳，格式和解析没有明确定义的类。我们有java.text.DateFormat用于解析和格式化需求的抽象类。通常SimpleDateFormat类用于解析和格式化。
        4、所有Date类都是可变的，因此它们不是线程安全的。这是Java Date和Calendar类的最大问题之一。
        5、日期类不提供国际化，没有时区支持。所以java.util.Calendar和java.util.TimeZone类进行了介绍，但他们也有上面列出的所有问题。

         */
    }

    @Test
    void what() {
        /*
        1、不可变性：新Date Time API中的所有类都是不可变的，适用于多线程环境。
        2、关注点分离：新API明确区分人类可读日期时间和机器时间（unix时间戳）。它为Date，Time，DateTime，Timestamp，Timezone等定义了单独的类。
        3、清晰度：方法明确定义，并在所有类中执行相同的操作。例如，要获取当前实例，我们有now()方法。在所有这些类中定义了format（）和parse（）方法，而不是为它们设置单独的类。
            所有类都使用工厂模式和策略模式来更好地处理。一旦你在其中一个类中使用了这些方法，那么使用其他类并不难。
        4、实用程序操作：所有新的Date Time API类都带有执行常见任务的方法，例如加号，减号，格式，解析，在日期/时间中获取单独的部分等。
        5、可扩展：新的Date Time API适用于ISO-8601日历系统，但我们也可以将其与其他非ISO日历一起使用。

         */
    }

    /**
     * LocalDate是一个不可变类，表示默认格式为yyyy-MM-dd的Date。
     */
    @Test
    void localDateExample (){
        //当前日期
        LocalDate today = LocalDate.now();
        System.out.println("Current Date="+today);

        //自定义日期
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date="+firstDay_2014);

        //通过ZoneId获取特定时区的日期。//中国区Asia/Shanghai
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata);

        //基于1970-01-01的偏移天数
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= "+dateFromBase);

        //基于指定年份的偏移天数
        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014="+hundredDay2014);
    }

    /**
     * LocalTime是一个不可变类，其实例表示人类可读格式的时间。它的默认格式是hh：mm：ss.zzz。
     */
    @Test
    void localTimeExample  (){
        //当前时间
        LocalTime time = LocalTime.now();
        System.out.println("Current Time="+time);

        //自定义时间
        LocalTime specificTime = LocalTime.of(12,20,25,40);
        System.out.println("Specific Time of Day="+specificTime);


        //指定时区。//中国区Asia/Shanghai
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST="+timeKolkata);

        //基于1970-01-01的偏移秒数
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= "+specificSecondTime);

        //基于指定天数的偏移纳秒数
        //LocalTime.ofNanoOfDay();
    }

    /**
     * LocalDateTime是一个不可变的日期时间对象，表示日期时间，默认格式为yyyy-MM-dd-HH-mm-ss.zzz。
     */
    @Test
    void localDateTimeExample(){
        //当前日期时间
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current DateTime="+today);

        //通过LocalDate和LocalTime创建日期时间
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime="+now);

        //自定义日期时间
        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date="+specificDate);

        //指定时区。//中国区Asia/Shanghai
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata);

        //基于1970-01-01的偏移秒数
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        System.out.println("10000th second time from 01/01/1970= "+dateFromBase);
    }


    /**
     * Instant类用于处理机器可读的时间格式，它将日期时间存储在unix时间戳中。
     */
    @Test
    void  instantExample (){
        //当前时间戳
        Instant timestamp = Instant.now();
        System.out.println("Current Timestamp = "+timestamp);

        //指定时间戳
        Instant specificTime = Instant.ofEpochMilli(timestamp.toEpochMilli());
        System.out.println("Specific Time = "+specificTime);

    }

    /**
     * Period 类表示一段时间的年、月、日
     */
    @Test
    void period(){
        // 使用between()方法获取两个日期之间的差作为Period 对象返回
        LocalDate startDate = LocalDate.of(2015, 2, 20);
        LocalDate endDate = LocalDate.of(2017, 1, 15);
        Period period = Period.between(startDate, endDate);
        System.out.println(period);

        //从Period对象中获取日期单元，使用getYears(),getMonhs(),getDays()方法:
        System.out.println("Years:" + period.getYears() +
                " months:" + period.getMonths() +
                " days:"+period.getDays());

        //任何一个时间单元为负数，则isNegative()方法返回true，因此可以用于判断endDate是否大于startDate：
        System.out.println(period.isNegative());

        //另一个创建Period类型对象是基于年、月、日和周，通过下面方法：
        Period fromUnits = Period.of(3, 10, 10);
        Period fromDays = Period.ofDays(50);
        Period fromMonths = Period.ofMonths(5);
        Period fromYears = Period.ofYears(10);
        Period fromWeeks = Period.ofWeeks(40);
        System.out.println(fromUnits+"\n"+fromDays+"\n"+fromMonths+"\n"+fromYears+"\n"+fromWeeks);

        //可以通过解析文本序列来创建Period，其格式为“PnYnMnD”:
        Period fromCharYears = Period.parse("P2Y");
        System.out.println(fromCharYears.getYears());
        Period fromCharUnits = Period.parse("P2Y3M5D");
        System.out.println( fromCharUnits.getDays());

        //period的值可以通过plusX()、minusX()方法进行增加或减少，其中X表示日期单元：
        System.out.println( period.plusDays(50).getDays());
        System.out.println( period.minusMonths(2).getMonths());
    }


    /**
     * Duration类表示秒或纳秒时间间隔，适合处理较短的时间，需要更高的精确性。
     */
    @Test
    void duration(){
        //Duration类表示秒或纳秒时间间隔，适合处理较短的时间，需要更高的精确性。我们能使用between()方法比较两个瞬间的差
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");
        Duration duration = Duration.between(start, end);
        System.out.println(duration);
    }
}
