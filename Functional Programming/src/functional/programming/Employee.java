package functional.programming;

public class Employee {
    private int id;

    private String name;

    private String depa;

    private float salary;

    public Employee(int id, String name, String depa, float salary) {
        this.id = id;
        this.name = name;
        this.depa = depa;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepa() {
        return depa;
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", depa='" + depa + '\'' +
                ", salary=" + salary +
                '}';
    }
}
