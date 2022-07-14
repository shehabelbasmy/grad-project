package test;

import functional.programming.Employee;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollectors {
    List<Employee> employees ;

    @BeforeTest
    public void createListOfEmployees(){
        employees = Stream.of(
                new Employee(10, "shehab", "DEV", 61723),
                new Employee(20, "Eslam", "DEVOPS", 51723),
                new Employee(30, "Ahmed", "HR", 23723),
                new Employee(40, "Mahmoud", "HR", 1223),
                new Employee(50, "Yasser", "DEVOPS", 81723)
        ).collect(Collectors.toList());
    }
    @Test
    public void getHighestSalaryInEachDepartment(){
        Map<String, Optional<Employee>> collect = employees.stream().collect(Collectors.groupingBy(
            Employee::getDepa,
            Collectors.maxBy(Comparator.comparing(Employee::getSalary))
        ));
        System.out.println(collect);
    }

    @Test
    public void getHighestSalaryInEachDepartment2() {
        Map<String, Optional<Employee>> collect = employees.stream().collect(Collectors.groupingBy(
            Employee::getDepa,
                Collectors.reducing(
                BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)
            ))
        ));
        System.out.println(collect);
    }

    @Test
    public void getHighestSalaryInEachDepartment2() {
        Map<String, Optional<Employee>> collect = employees.stream().collect(Collectors.groupingBy(
                Employee::getDepa,
                Collectors.reducing(
                        BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)
                        ))
        ));
        System.out.println(collect);
    }
}
