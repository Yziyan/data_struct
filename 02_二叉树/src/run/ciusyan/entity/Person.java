package run.ciusyan.entity;

public class Person implements Comparable<Person> {
    private int age;
    private int height;

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public Person() {}
    public Person(int age, int height) {
        this.age = age;
        this.height = height;
    }

    @Override
    public int compareTo(Person o) {
        return age - o.age;
    }

    @Override
    public String toString() {
        return "a=" + age +
            ", h=" + height;
    }
}
