import java.text.DecimalFormat;
import java.util.*;

class student{
    private String name;
    private int roll;
    private String email;
    private List<Course> majorCourses;
    private Course optionalCourse;
    private int evaluationMetric;
    // List to store marks for each course
    private List<List<Integer>> courseMarks;
    private double gpa;
    private double totalMarks;



    public student(String name, int roll, String email) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this. majorCourses = new ArrayList<>();
        this.courseMarks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            courseMarks.add(new ArrayList<>()); 
        }
    }

    //method for add course
     public void enrollMajorCourse(Course course) {
        if (majorCourses.size() < 3) {
            majorCourses.add(course);
        } else {
            System.out.println("Maximum major courses reached.");
        }
    }
    public void setOptionalCourse(Course course) {
        this.optionalCourse = course;
    }
    public void setEvaluationMetric(int evaluationMetric) {
        this.evaluationMetric = evaluationMetric;
    }
    public void addMarks(Course course, List<Integer> marks) {
        int index = majorCourses.indexOf(course);
        if (index != -1) {
            courseMarks.get(index).addAll(marks); // Add marks for major course
        } else if (optionalCourse != null && optionalCourse.equals(course)) {
            int optionalIndex = majorCourses.size();
            if (optionalIndex < courseMarks.size()) {
                courseMarks.get(optionalIndex).addAll(marks); // Add marks for optional course
            } else {
                // Expand the courseMarks list to accommodate the optional course marks
                while (courseMarks.size() <= optionalIndex) {
                    courseMarks.add(new ArrayList<>());
                }
                courseMarks.get(optionalIndex).addAll(marks);
            }
        } else {
            System.out.println("Course not found.");
        }
    }
    
    public void addGpa(double gpa) {
        this.gpa = gpa;
    }
    public void addTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }
    

    // getter and setter
    public String getName() {
        return name;
    }

    public int getRoll() {
        return roll;
    }

    public String getEmail() {
        return email;
    }

   public List<Course> getMajorCourses() {
        return majorCourses;
    }

    public Course getOptionalCourse() {
        return optionalCourse;
    } 
    public List<List<Integer>> getCourseMarks() {
        return courseMarks;
    }
    public int getEvaluationMetric() {
        return evaluationMetric;
    }
    public double getGpa() {
        return gpa;
    }
    public double getTotalMarks() {
        return totalMarks;
    }
}

class Course {
    private String name;
    private double credits;
    private List<student> students;

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, double credits) {
        this.name = name;
        this.credits = credits;
        students = new ArrayList<>();
    }

    public void enrollStudent(student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }

    // Getters for name and credits
    public String getName() {
        return name;
    }

    public double getCredits() {
        return credits;
    }
    public List<student> getStudents() {
        return students;
    }
}

class Evaluation {
    private int midtermMarks;
    private int regularAssessmentMarks;
    private int finalMarks;

    public Evaluation(int midtermMarks, int regularAssessmentMarks, int finalMarks) {
        this.midtermMarks = midtermMarks;
        this.regularAssessmentMarks = regularAssessmentMarks;
        this.finalMarks = finalMarks;
    }

    public int getMidtermMarks() {
        return midtermMarks;
    }

    public int getRegularAssessmentMarks() {
        return regularAssessmentMarks;
    }

    public int getFinalMarks() {
        return finalMarks;
    }
}



class generate{

    public List<student> generateStudents(int n) {
        List<student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            students.add(new student("Student" + i, i, "student" + i + "@example.com"));
        }
        return students;
    }


    public List<Course> generateCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Artificial Intelligence"));
        courses.add(new Course("Security"));
        courses.add(new Course("Networking"));
        courses.add(new Course("Operation Research"));
        courses.add(new Course("Embeded System"));
        return courses;
    }

    public void generateGpa(List<student> students) {
        GradeCalculator gradeCalculator = new GradeCalculator();
        for (student student : students) {
            double gpa = gradeCalculator.calculateGPA(student);
            student.addGpa(gpa);
            //System.out.println(student.getName() + " " + student.getRoll() + " " + student.getEmail() + " " + new DecimalFormat("0.00").format(gpa));
        }
    }
    public void generateCourseGpa(List<student> students,List<Evaluation>evaluations){
        GradeCalculator g = new GradeCalculator();
        for(student student:students){
            for(Course course:student.getMajorCourses()){
                double grade = g.calculateCourseGrade(student, course);
               // System.out.println(student.getName()+" Grade for " + course.getName() + " is: " + grade);
            }
            Course course = student.getOptionalCourse();
            double grade = g.calculateCourseGrade(student, course);
          //  System.out.println(student.getName()+" Grade for " + course.getName() + " is: " + grade);
            
        }

    }

    public void generateTotalMarks(List<student> students){
        for(student student:students){
            double totalMarks = 0;
            for(Course course:student.getMajorCourses()){
                List<Integer> marks = student.getCourseMarks().get(student.getMajorCourses().indexOf(course));
                for(int mark:marks){
                    totalMarks += mark;
                }
            }
            List<Integer> marks = student.getCourseMarks().get(student.getMajorCourses().size());
            for(int mark:marks){
                totalMarks += mark;
            }
            student.addTotalMarks(totalMarks);
            //System.out.println(student.getName() + " " + student.getRoll() + " " + student.getEmail() + " " + totalMarks);
            
        }
    }



}

class Assign {
    public void assignCourses(List<student> students, List<Course> maCourses, List<Course> opCourses) {
        Random random = new Random();
        for (student student : students) {
            while (student.getMajorCourses().size() < 3) {
                Course course = maCourses.get(random.nextInt(maCourses.size()));
                if (!student.getMajorCourses().contains(course)) {
                    student.enrollMajorCourse(course);
                    course.enrollStudent(student);
                }
            }
            Course course = opCourses.get(random.nextInt(opCourses.size()));
            if(student.getOptionalCourse() == null && !student.getMajorCourses().contains(course)) {
            student.setOptionalCourse(course);
            course.enrollStudent(student);
        }
    }
    }

    public void assignMarks(List<student> students, List<Evaluation> evaluations) {
        Random random = new Random();
        for (student student : students) {
            int evaluationIndex = random.nextInt(evaluations.size());
            student.setEvaluationMetric(evaluationIndex);
            Evaluation evaluation = evaluations.get(evaluationIndex);
    
            // Assign marks for major courses
            for (Course course : student.getMajorCourses()) {
                List<Integer> marks = new ArrayList<>();
                int midtermMarks = Math.max(1, evaluation.getMidtermMarks());
                int regularAssessmentMarks = Math.max(1, evaluation.getRegularAssessmentMarks());
                int finalMarks = Math.max(1, evaluation.getFinalMarks());
                marks.add(random.nextInt(midtermMarks));
                marks.add(random.nextInt(regularAssessmentMarks));
                marks.add(random.nextInt(finalMarks));
                student.addMarks(course, marks);
            }
    
            // Assign marks for optional course
            List<Integer> marks = new ArrayList<>();
            int midtermMarks = Math.max(1, evaluation.getMidtermMarks()); 
            int regularAssessmentMarks = Math.max(1, evaluation.getRegularAssessmentMarks());
            int finalMarks = Math.max(1, evaluation.getFinalMarks());
            marks.add(random.nextInt(midtermMarks));
            marks.add(random.nextInt(regularAssessmentMarks));
            marks.add(random.nextInt(finalMarks));
            student.addMarks(student.getOptionalCourse(), marks);
        }
    }
    
    
}


class GradeCalculator {

    public double calculateCourseGrade(student student, Course course) {
        double totalMarks = 0;
        int index = student.getMajorCourses().indexOf(course);
        if (index != -1) {
            List<Integer> marks = student.getCourseMarks().get(index);
            for (int mark : marks) {
                totalMarks += mark;
            }
        } else if (student.getOptionalCourse() != null && student.getOptionalCourse().equals(course)) {
            List<Integer> marks = student.getCourseMarks().get(student.getMajorCourses().size());
            for (int mark : marks) {
                totalMarks += mark;
            }
        }

        if(totalMarks>=80) return 4.0;
        else if(totalMarks<80 && totalMarks>=75) return 3.75;
        else if(totalMarks<75 && totalMarks>=70) return 3.5;
        else if(totalMarks<70 && totalMarks>=65) return 3.25;
        else if(totalMarks<65 && totalMarks>=60) return 3.0;
        else if(totalMarks<60 && totalMarks>=55) return 2.75;
        else if(totalMarks<55 && totalMarks>=50) return 2.5;
        else if(totalMarks<50 && totalMarks>=45) return 2.25;
        else if(totalMarks<45 && totalMarks>=40) return 2.0;
        else return 0.0;
    }

    public double calculateGPA(student student) {
        double totalCredits = 0;
        double totalScore = 0;
        for (Course course : student.getMajorCourses()) {
            totalCredits += course.getCredits();
            totalScore += calculateCourseGrade(student, course) * course.getCredits();
        }
        totalCredits += student.getOptionalCourse().getCredits();
        totalScore += calculateCourseGrade(student, student.getOptionalCourse()) * student.getOptionalCourse().getCredits();
        return totalScore / totalCredits;
    }
}



class print{

    public void courseDetails(List<student> students, List<Course> courses,List<Evaluation> evaluations){
        for (Course course : courses) {
            System.out.println("*********  "+course.getName() + " " + course.getCredits()+"  *********");
            System.out.println("        ******* "+"Students:"+course.getStudents().size()+" *******");
            System.out.println();
            for (student student : course.getStudents()) {
                System.out.println(student.getName() + " " + student.getRoll() + " " + student.getEmail());
                int evaluationIndex = student.getEvaluationMetric();
            if (evaluationIndex >= 0 && evaluationIndex < evaluations.size()) {
                Evaluation evaluation = evaluations.get(evaluationIndex);
                System.out.println("Evaluation Metric: midTerm, regularAssessment, finalMarks");
                System.out.println("["+evaluation.getMidtermMarks() + ", " + evaluation.getRegularAssessmentMarks() + ", " + evaluation.getFinalMarks()+"]");
                System.out.println();
            } else {
                System.out.println("Evaluation Metric not found.");
            }
            }
        }
       
    }
    public void sortGpa(List<student> students) {
        students.sort((s1, s2) -> {
            int gpaComparison = Double.compare(s2.getGpa(), s1.getGpa()); // Compare by GPA in descending order
            if (gpaComparison != 0) {
                return gpaComparison;
            } else {
                // If GPA is equal, compare by the total marks
                double totalMarksS1 = s1.getTotalMarks();
                double totalMarksS2 = s2.getTotalMarks();
                return Double.compare(totalMarksS2, totalMarksS1);
            }
        });
        System.out.println();
        System.out.println("*********** "+"Sorted Student Name: Roll:  Email:  GPA"+" ***********");
        for (student student : students) {
            System.out.println(student.getName() + " " + student.getRoll() + " " + student.getEmail() + " "+" GPA:" + new DecimalFormat("0.00").format(student.getGpa()));
        }
    }

    public void courseBasedSortGpa(List<Course> courses, List<student> students) {
        for (Course course : courses) {
            List<student> courseStudents = course.getStudents();
            courseStudents.sort((s1, s2) -> {
                double gpa1 = new GradeCalculator().calculateCourseGrade(s1, course);
                double gpa2 = new GradeCalculator().calculateCourseGrade(s2, course);
                return Double.compare(gpa2, gpa1);
            });
            System.out.println();
            System.out.println("********** "+"Course: " + course.getName() + " " + course.getCredits()+" **********");
            System.out.println("        ******* "+"Students:"+course.getStudents().size()+" *******");
            System.out.println();
            for (student student : courseStudents) {
                System.out.println(student.getName() + " " + student.getRoll() + " " + student.getEmail()+" "+"Course GPA:"+new GradeCalculator().calculateCourseGrade(student, course));
            }
        }
    }
   
    
}

public class studentDatabase{
    public static void main(String args[]){

            generate g1 = new generate();
            List<student> students = g1.generateStudents(20);

            List<Course> majorCourses = new ArrayList<>();
            majorCourses.add(new Course("Artificial Intelligence", 3.0));
            majorCourses.add(new Course("Security", 3.0));
            majorCourses.add(new Course("Networking", 3.0));
            majorCourses.add(new Course("Operation Research", 3.0));
            majorCourses.add(new Course("Embeded System", 3.0));

            List<Course> optionalCourses = new ArrayList<>();
            optionalCourses.add(new Course("Artificial Intelligence", 1.5));
            optionalCourses.add(new Course("Security", 1.5));
            optionalCourses.add(new Course("Networking", 1.5));
            optionalCourses.add(new Course("Operation Research", 1.5));
            optionalCourses.add(new Course("Embeded System", 1.5));
       
//***********   function call to random generate  ***********

            //random assign courses to students(5c3+5c1 choice)
            Assign a1 = new Assign();
            a1.assignCourses(students, majorCourses, optionalCourses);

            //random assign evaluation metric to students(3c1 choice)
            List<Evaluation> evaluations = new ArrayList<>();
            evaluations.add(new Evaluation(30, 10, 60));
            evaluations.add(new Evaluation(20, 10, 70));
            evaluations.add(new Evaluation(40, 0, 60));

            a1.assignMarks(students, evaluations);
//***********   function call to random generate  ***********




//***********   function call to generate the grade  ***********

            generate g2 = new generate();
            // calculate total grade for each student
            g2.generateGpa(students);
            //calculate the grade for each student for each course
            g2.generateCourseGpa(students,evaluations);
            //calculate the total marks for each student
            g2.generateTotalMarks(students);
            
//***********   function call to generate the grade  ***********



            
//***********   function call to print the output  ***********
            print p1 = new print();
            //comprehensive list containing all the information relevantto a particular course
               p1.courseDetails(students, majorCourses,evaluations);
             //print all student gpa in decending order
               p1.sortGpa(students);
            //print all student gpa for each course in decending order
               p1.courseBasedSortGpa(majorCourses,students);
//***********   function call to print the output  ***********


    } 
}
