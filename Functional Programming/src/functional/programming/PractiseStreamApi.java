package functional.programming;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class PractiseStreamApi {
    public static void main(String[] args) {
        List<Employee> employees = Stream.of(new Employee(10, "Ahmed", "DEV", 5000),
                new Employee(10, "Shehab", "HR", 8000),
                new Employee(10, "Mohamed", "DEVOPS", 9000),
                new Employee(10, "Yasser", "DEVOPS", 1000),
                new Employee(10, "Mostafa", "HR", 2000),
                new Employee(10, "Ali", "SLAES", 3000)).collect(toList());
        Map<String, Optional<Employee>> collect = employees.stream().collect(groupingBy(
                Employee::getDepa,
                reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))
        ));


        Map<String, Double> collect1 = employees.stream()
                .collect(groupingBy(Employee::getDepa, summingDouble(Employee::getSalary)));
        System.out.println(collect1);
    }
}
