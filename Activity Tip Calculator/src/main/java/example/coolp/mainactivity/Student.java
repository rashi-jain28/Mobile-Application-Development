package example.coolp.mainactivity;

import java.io.Serializable;

/**
 * Created by coolp on 1/29/2018.
 */

public class Student implements Serializable {
    String name;
    String email;
    String department;
    int mood;

    @Override
    public String toString() {
        return "name "+ name + "\t email" +email+
                "\t department" + department + "\t mood" +mood;
    }

    public Student(String name, String email, String department, int mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public int getMood() {
        return mood;
    }
}
