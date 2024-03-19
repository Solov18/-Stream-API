package com.javacurse.ce.JavaJunior.Homework;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        List<Department> departments = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));

        }
        Map<Department, Person> departmentOldestPerson = printDepartmentOldestPerson(persons);
        departmentOldestPerson.forEach((department, person) ->
                System.out.println(department.getName() + " -> " + person.getName()));


        printNamesOrdered(persons);
        findFirstPersons(persons);


        Department topDepartment = findTopDepartment(persons).orElse(null);
        if (topDepartment != null) {
            System.out.println("Department with the highest total salary: " + topDepartment.getName());
        } else {
            System.out.println("No persons available");
        }
    }




    /**
     * Вывести на консоль отсортированные (по алфавиту) имена персонов
     **/
    public static void printNamesOrdered(List<Person> persons) {
        persons.stream().
                sorted(Comparator
                        .comparing(Person::getName));
        System.out.println("Отсортированный список:");
        for (Person person : persons) {
            System.out.println(person);


        }
    }

    /**
     * В каждом департаменте найти самого взрослого сотрудника.
     **/
    public static Map<Department, Person> printDepartmentOldestPerson(List<Person> persons) {
        return persons.stream()
                .collect(
                        Collectors.groupingBy(
                                person -> person.getDepartment(),
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingInt(Person::getAge)),
                                        Optional::get)));

    }

    /**
     * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
     **/
    public static void findFirstPersons(List<Person> persons) {
        persons.stream()
                .filter(it -> it.getAge() < 30)
                .filter(it -> it.getSalary() > 50_000)
                .limit(10)
                .peek(System.out::println)
                .collect(Collectors.toList());


        }

    /** Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна **/
    public static Optional<Department> findTopDepartment(List<Person> persons) {
        Map<Department, Integer> departmentSalaries = new HashMap<>();

        for (Person person : persons) {
            Department department = person.getDepartment();
            int salary = (int) person.getSalary();

            departmentSalaries.put(department, departmentSalaries.getOrDefault(department, 0) + salary);
        }

        Optional<Map.Entry<Department, Integer>> topDepartment = departmentSalaries.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return topDepartment.map(Map.Entry::getKey);
    }


    }



















