package site.javaee.java_new_features.java8.design;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.javaee.java_new_features.java8.bean.Employee;

/**
 * @author shkstart
 * @create 2020-06-03 0:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterEmployeeByAge implements MyPredicate<Employee> {
    private int age;
    @Override
    public boolean test(Employee employee) {
        return employee.getAge() >= this.getAge();
    }
}
