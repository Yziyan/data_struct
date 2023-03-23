package run.ciusyan.model;

public class Student implements Comparable<Student> {
    private int score;
    protected int age;

    public int getScore() {
        return score;
    }

    public int getAge() {
        return age;
    }

    public Student(int score, int age) {
        this.score = score;
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }
}
