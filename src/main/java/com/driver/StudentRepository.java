package com.driver;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
@Repository
public class StudentRepository {
    private HashMap<String, Student> studentHashMap = new HashMap<>();
    private HashMap<String, Teacher> teacherHashMap = new HashMap<>();
    private HashMap<String,List<String>> teacherStudentHashMap = new HashMap<>();

    // Add a Student: POST /students/add-student Pass the Student object as request body
    // Return success message wrapped in a ResponseEntity
    public void saveStudent(Student student){
        studentHashMap.put(student.getName(),student);
    }
    public void saveTeacher(Teacher teacher){
        teacherHashMap.put(teacher.getName(),teacher);
    }
    // Pair an existing student and teacher: PUT /students/add-student-teacher-pair Pass student name and teacher name as request parameters
    // Return success message wrapped in a ResponseEntity
    public void makeStudentTeacherPair(String std_name, String tch_name){
//        if(studentHashMap.containsKey(std_name) && teacherHashMap.containsKey(tch_name)){
//            List<String> std_list = new ArrayList<>();
//            // if is there an existing teacher in the teacherStudentHashMap than
//            // get the list add student into it
//            if(teacherStudentHashMap.containsKey(tch_name)){
//                std_list = teacherStudentHashMap.get(tch_name);
//            }
//            std_list.add(std_name);
//            teacherStudentHashMap.put(tch_name,std_list);
//        }
        List<String> studentsList = new ArrayList<>();
        if(teacherStudentHashMap.containsKey(tch_name)){
            studentsList = teacherStudentHashMap.get(tch_name);
            studentsList.add(std_name);
            teacherStudentHashMap.put(tch_name, studentsList);
        }
        else{
            studentsList.add(std_name);
            teacherStudentHashMap.put(tch_name,studentsList);
        }
    }
    public Student findStudentByName(String std_name){
        return  studentHashMap.get(std_name);
    }
    public Teacher findTeacherByName(String tch_name){
        return teacherHashMap.get(tch_name);
    }
    // find student list with their respected teacher
    public List<String> findStudentsByTeacherName(String tch_name){
        List<String> studentList = new ArrayList<>();
        if(teacherStudentHashMap.containsKey(tch_name)){
            studentList = teacherStudentHashMap.get(tch_name);
        }
        return studentList;
    }
    // Find all Students from student map
    public List<String> findAllStudents(){
        return new ArrayList<>(studentHashMap.keySet());
    }
    // delete teacher by name
    public void deleteTeacherByName(String tch_name){
        if(teacherStudentHashMap.containsKey(tch_name)){
            List<String> studentList = teacherStudentHashMap.get(tch_name);
            for(int i = 0; i < studentList.size(); i++){
                studentList.remove(studentList.get(i));
            }
            teacherHashMap.remove(tch_name);
            teacherStudentHashMap.remove(tch_name);
        }
    }
    // MUST delete the respected student's from student map and teacher map and teacherStudent map
    public void deleteAllTeachers(){
        for(List<String> ls: teacherStudentHashMap.values()){
            for (String studentList : ls){
                studentHashMap.remove(studentList);
            }
        }
        teacherHashMap.clear();
        teacherStudentHashMap.clear();
    }
}
