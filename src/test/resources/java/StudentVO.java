package java;

public class StudentVO {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public StudentVO transferStudentDTO2VO(StudentDTO studentDTO) {
        if(studentDTO == null) {
            return null;
        }
        StudentVO vo = new StudentVO();
        vo.setAge(studentDTO.getAge());
        vo.setName(studentDTO.getName());
        return vo;
    }
}