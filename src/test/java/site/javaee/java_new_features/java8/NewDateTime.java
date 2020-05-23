package site.javaee.java_new_features.java8;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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

    /**
     * New DateTime特性
     */
    @Test
    void what() {
        /*
        1、不可变性：新Date Time API中的所有类都是不可变的，适用于多线程环境。
        2、关注点分离：新API明确区分人类可读日期时间和机器时间（unix时间戳）。它为Date，Time，DateTime，Timestamp，Timezone等定义了单独的类。
        3、清晰度：方法明确定义，并在所有类中执行相同的操作。例如，要获取当前实例，我们有now()方法。在所有这些类中定义了format（）和parse（）方法，而不是为它们设置单独的类。
            所有类都使用工厂模式和策略模式来更好地处理。一旦你在其中一个类中使用了这些方法，那么使用其他类并不难。
        4、实用程序操作：所有新的Date Time API类都带有执行常见任务的方法，例如加号，减号，格式，解析，在日期/时间中获取单独的部分等。
        5、可扩展：新的Date Time API适用于ISO-8601日历系统，但我们也可以将其与其他非ISO日历一起使用。

        DateTime/Date/Iime/Instant 实现了Temporal
        Duration 实现了 TemporalAmount
        Period 实现了 ChronoPeriod
        ChronoUnit 实现了 TemporalUnit
         */
    }

    /**
     * LocalDate是一个不可变类，表示默认格式为yyyy-MM-dd的Date。
     */
    @Test
    void localDate() {
        //当前日期
        LocalDate today = LocalDate.now();
        System.out.println("Current Date=" + today);

        //自定义日期
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date=" + firstDay_2014);

        //通过ZoneId获取特定时区的日期。//中国区Asia/Shanghai
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST=" + todayKolkata);

        //基于1970-01-01的偏移天数
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= " + dateFromBase);

        //基于指定年份的偏移天数
        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014=" + hundredDay2014);
    }

    /**
     * LocalTime是一个不可变类，其实例表示人类可读格式的时间。它的默认格式是hh：mm：ss.zzz。
     */
    @Test
    void localTime() {
        //当前时间
        LocalTime time = LocalTime.now();
        System.out.println("Current Time=" + time);

        //自定义时间
        LocalTime specificTime = LocalTime.of(12, 20, 25, 40);
        System.out.println("Specific Time of Day=" + specificTime);


        //指定时区。//中国区Asia/Shanghai
        LocalTime timeKolkata = LocalTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Time in IST=" + timeKolkata);

        //基于1970-01-01的偏移秒数
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
        System.out.println("10000th second time= " + specificSecondTime);

        //基于指定天数的偏移纳秒数
        //LocalTime.ofNanoOfDay();
    }

    /**
     * LocalDateTime是一个不可变的日期时间对象，表示日期时间，默认格式为yyyy-MM-dd-HH-mm-ss.zzz。
     */
    @Test
    void localDateTime() {
        //当前日期时间
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current DateTime=" + today);

        //通过LocalDate和LocalTime创建日期时间
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println("Current DateTime=" + now);

        //自定义日期时间
        //Creating LocalDateTime by providing input arguments
        LocalDateTime specificDate = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
        System.out.println("Specific Date=" + specificDate);

        //指定时区。//中国区Asia/Shanghai
        LocalDateTime todayKolkata = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST=" + todayKolkata);

        //基于1970-01-01的偏移秒数
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(10000, 0, ZoneOffset.UTC);
        System.out.println("10000th second time from 01/01/1970= " + dateFromBase);
    }


    /**
     * Instant类用于处理机器可读的时间格式，它将日期时间存储在unix时间戳中。
     */
    @Test
    void instant() {
        //当前时间戳
        Instant timestamp = Instant.now();
        System.out.println("Current Timestamp = " + timestamp);

        //指定时间戳
        Instant specificTime = Instant.ofEpochMilli(timestamp.toEpochMilli());
        System.out.println("Specific Time = " + specificTime);

        //可以通过Instant，在Date和LocalDate、LocalTime、LocalDateTime之间做转换。

    }

    /**
     * Period 类表示一段时间的年、月、日
     */
    @Test
    void period() {
        // 使用between()方法获取两个日期之间的差作为Period 对象返回
        LocalDate startDate = LocalDate.of(2015, 2, 20);
        LocalDate endDate = LocalDate.of(2017, 1, 15);
        Period period = Period.between(startDate, endDate);
        System.out.println(period);

        //从Period对象中获取日期单元，使用getYears(),getMonhs(),getDays()方法:
        System.out.println("Years:" + period.getYears() +
                " months:" + period.getMonths() +
                " days:" + period.getDays());

        //任何一个时间单元为负数，则isNegative()方法返回true，因此可以用于判断endDate是否大于startDate：
        System.out.println(period.isNegative());

        //另一个创建Period类型对象是基于年、月、日和周，通过下面方法：
        Period fromUnits = Period.of(3, 10, 10);
        Period fromDays = Period.ofDays(50);
        Period fromMonths = Period.ofMonths(5);
        Period fromYears = Period.ofYears(10);
        Period fromWeeks = Period.ofWeeks(40);
        System.out.println(fromUnits + "\n" + fromDays + "\n" + fromMonths + "\n" + fromYears + "\n" + fromWeeks);

        //可以通过解析文本序列来创建Period，其格式为“PnYnMnD”:
        Period fromCharYears = Period.parse("P2Y");
        System.out.println(fromCharYears.getYears());
        Period fromCharUnits = Period.parse("P2Y3M5D");
        System.out.println(fromCharUnits.getDays());

        //period的值可以通过plusX()、minusX()方法进行增加或减少，其中X表示日期单元：
        System.out.println(period.plusDays(50).getDays());
        System.out.println(period.minusMonths(2).getMonths());
    }


    /**
     * Duration类表示秒或纳秒时间间隔，适合处理较短的时间，需要更高的精确性。
     */
    @Test
    void duration() {
        //Duration类表示秒或纳秒时间间隔，适合处理较短的时间，需要更高的精确性。使用between()方法比较两个瞬间的差
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");
        Duration duration = Duration.between(start, end);
        System.out.println(duration);

        //使用getSeconds() 或 getNanoseconds() 方法获取时间单元的值：
        System.out.println(duration.getSeconds());

        //通过LocalDateTime 类获取获取Duration对象
        LocalTime start2 = LocalTime.of(1, 20, 25, 1024);
        LocalTime end2 = LocalTime.of(3, 22, 27, 1544);
        Duration duration2 = Duration.between(start2, end2);
        System.out.println(duration2.getSeconds());

        //isNegative()方法能用于验证后面时间是否大于前面时间：
        System.out.println(duration.isNegative());

        //能基于下面的方法获得Duration对象，ofDays(), ofHours(), ofMillis(), ofMinutes(), ofNanos(), ofSeconds():
        Duration fromDays = Duration.ofDays(1);
        System.out.println( fromDays.getSeconds());
        Duration fromMinutes = Duration.ofMinutes(60);
        System.out.println(fromMinutes.getSeconds());

        //可以通过文本序列创建Duration对象，格式为 “PnDTnHnMn.nS”:
        Duration fromChar1 = Duration.parse("P1DT1H10M10.5S");
        Duration fromChar2 = Duration.parse("PT10M");
        System.out.println(fromChar1);
        System.out.println(fromChar2);

        //使用toDays(), toHours(), toMillis(), toMinutes()方法把Duration对象可以转成其他时间单元：
        System.out.println(fromDays.toHours());

        //通过 plusX()、minusX()方法增加或减少Duration对象，其中X表示days, hours, millis, minutes, nanos 或 seconds:
        System.out.println( duration.plusSeconds(60).getSeconds());
        System.out.println(  duration.minusSeconds(30).getSeconds());

        //使用plus()和minus()方法带TemporalUnit 类型参数进行加减
        System.out.println(duration.plus(50, ChronoUnit.SECONDS).getSeconds());

    }


    /**
     * ChronoUnit 是期枚举类，提供了一组标准的日期时间单位，这是一个最终的、不可变的和线程安全的枚举。。
     */
    @Test
    void chronoUnit(){
        //当前时间
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);
        //一周后
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Next week: " + nextWeek);
        //一个月后
        LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + nextMonth);
        //一年后
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Next year: " + nextYear);
        //十年后
        LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
        System.out.println("Date after ten year: " + nextDecade);


        //当前时间
        LocalDate today2 = LocalDate.now();
        //一周后
        LocalDate nextWeek2 = today.plus(1, ChronoUnit.WEEKS);
        //时间差
        long diff = ChronoUnit.WEEKS.between(today, nextWeek);
        System.out.println(diff);
    }


    /**
     * TemporalAdjuster可以执行复杂的日期操作，例如，可以获得下一个星期日对于日期、当月的最后一天、下一年的第一天。
     */
    @Test
    void temporalAdjusters(){
        //当前日期
        LocalDate localDate = LocalDate.now();
        System.out.println("current date : " + localDate);

        //当前月的第一天
        LocalDate with = localDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("firstDayOfMonth : " + with);

        //当前月的最后一天
        LocalDate with1 = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("lastDayOfMonth : " + with1);

        //下个周一
        LocalDate with2 = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("next monday : " + with2);

        //下个月的第一天
        LocalDate with3 = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("firstDayOfNextMonth : " + with3);

        //"一周中的某一天
        LocalDate with4 = localDate.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
        System.out.println("这个月第二周的星期一 : " + with4);


        //nextOrSame/previousOrSame 创建新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定条件（这里使用了星期一举例））要求的日期，如果该日期已经符合要求，直接返回该对象
        LocalDateTime with5 = LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        System.out.println("从现在开始获取上周一的零点 : "+with5);
        LocalDateTime with6 =LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay().minusSeconds(1);
        System.out.println("从现在时间往前获取上周末的23:59:59 : "+with6);
    }

    /**
     * Date API实用程序
     */
    @Test
    void dateApi(){
        //当前日期
        LocalDate today = LocalDate.now();

        //获取年份，判断是否闰年
        System.out.println("Year "+today.getYear()+" is Leap Year? "+today.isLeapYear());

        //两个日期比较先后
        System.out.println("Today is before 01/01/2015? "+today.isBefore(LocalDate.of(2015,1,1)));

        //通过LocalTime创建 LocalDateTime
        System.out.println("Current Time="+today.atTime(LocalTime.now()));

        //日期增减操作
        System.out.println("10 days after today will be "+today.plusDays(10));
        System.out.println("3 weeks after today will be "+today.plusWeeks(3));
        System.out.println("20 months after today will be "+today.plusMonths(20));
        System.out.println("10 days before today will be "+today.minusDays(10));
        System.out.println("3 weeks before today will be "+today.minusWeeks(3));
        System.out.println("20 months before today will be "+today.minusMonths(20));

        //当月第一天
        System.out.println("First date of this month= "+today.with(TemporalAdjusters.firstDayOfMonth()));
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("Last date of this year= "+lastDayOfYear);

        //今年剩余天数
        Period period = today.until(today.with(TemporalAdjusters.lastDayOfYear()));
        System.out.println("Period Format= "+period);
        System.out.println("Months remaining in the year= "+period.getMonths());
    }


    /**
     * 日期解析和格式化
     */
    @Test
    void parser(){
        //Format examples
        LocalDate date = LocalDate.now();
        //default format
        System.out.println("Default format of LocalDate="+date);
        //specific format
        System.out.println(date.format(DateTimeFormatter.ofPattern("d::MMM::uuuu")));
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));


        LocalDateTime dateTime = LocalDateTime.now();
        //default format
        System.out.println("Default format of LocalDateTime="+dateTime);
        //specific format
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss")));
        System.out.println(dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));

        Instant timestamp = Instant.now();
        //default format
        System.out.println("Default format of Instant="+timestamp);

        //Parse examples
        LocalDateTime dt = LocalDateTime.parse("27::Apr::2014 21::39::48",
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss"));
        System.out.println("Default format after parsing = "+dt);
    }

    /**
     * 新旧dateTime转换
     */
    void oldToNew(){
        //Date to Instant
        Instant timestamp = new Date().toInstant();
        //Now we can convert Instant to LocalDateTime or other similar classes
        LocalDateTime date = LocalDateTime.ofInstant(timestamp,
                ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        System.out.println("Date = "+date);

        //Calendar to Instant
        Instant time = Calendar.getInstance().toInstant();
        System.out.println(time);
        //TimeZone to ZoneId
        ZoneId defaultZone = TimeZone.getDefault().toZoneId();
        System.out.println(defaultZone);

        //ZonedDateTime from specific Calendar
        ZonedDateTime gregorianCalendarDateTime = new GregorianCalendar().toZonedDateTime();
        System.out.println(gregorianCalendarDateTime);

        //Date API to Legacy classes
        Date dt = Date.from(Instant.now());
        System.out.println(dt);

        TimeZone tz = TimeZone.getTimeZone(defaultZone);
        System.out.println(tz);

        GregorianCalendar gc = GregorianCalendar.from(gregorianCalendarDateTime);
        System.out.println(gc);
    }
}
