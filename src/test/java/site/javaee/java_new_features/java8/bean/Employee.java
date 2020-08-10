package site.javaee.java_new_features.java8.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shkstart
 * @create 2020-06-03 0:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String name;
    private int age;
    private double salary;
    private Status status;

    public Employee(Integer age) {
        this.age=age;
    }


}


