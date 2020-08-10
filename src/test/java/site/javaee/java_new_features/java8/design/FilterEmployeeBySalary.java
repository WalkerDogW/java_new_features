package site.javaee.java_new_features.java8.design;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.javaee.java_new_features.java8.bean.Employee;

/**
 * @author shkstart
 * @create 2020-06-07 23:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterEmployeeBySalary implements MyPredicate<Employee> {
    private double salary;


    @Override
    public boolean test(Employee employee) {
        return employee.getSalary()>=this.getSalary();
    }

}
