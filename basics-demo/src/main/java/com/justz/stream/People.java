package com.justz.stream;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * People
 */
public class People {

    private String name;

    private int age;

    public People() {}

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static Stream<People> createTen() {
        return Stream.generate(new PeopleSupplier()).limit(10);
    }

    private static class PeopleSupplier implements Supplier<People> {

        private int index = 0;

        private Random random = new Random();

        @Override
        public People get() {
            return new People("aaa" + index++, random.nextInt(100));
        }
    }

    @Override
    public String toString() {
        return "[name: " + name + ", age: " + age + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People people = (People) o;

        if (age != people.age) return false;
        return name.equals(people.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        return result;
    }
}
