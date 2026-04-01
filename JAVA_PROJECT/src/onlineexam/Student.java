package onlineexam;

// Student class extends User - this is INHERITANCE
// basically a student is a type of user, so we reuse User's fields

public class Student extends User {
    // extra field that only students have
    private String enrollmentNo;

    public Student(String name, String enrollmentNo) {
        // calling parent class constructor
        // we're just using enrollment number as both userId and password for simplicity
        super(name, enrollmentNo, enrollmentNo);
        this.enrollmentNo = enrollmentNo;
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    @Override
    public String toString() {
        return "Student: " + getName() + " | Enrollment: " + enrollmentNo;
    }
}
