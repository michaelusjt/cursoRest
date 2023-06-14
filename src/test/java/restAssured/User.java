package restAssured;

import javax.xml.bind.annotation.XmlAttribute;

public class User {

    @XmlAttribute           ///Foi adicionado um atributo xml no id, pois sem isso ficará nulo no teste
    private Long id;
    private String name;
    private Integer age;
    private Double salary;


    //Utilizado pelo xml
    public User() {

    }

    public User(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + ", salary=" + salary + "]";
    }
}
