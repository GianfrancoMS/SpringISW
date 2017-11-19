package com.gianfranco.trabajoparcial.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    @Pattern(regexp = "[0-9]{8}", message = "DNI must followed a certain pattern. e.g, 74123456")
    private String dni;

    @NotBlank(message = "You must enter the client's first name")
    @Size(min = 2, max = 50, message = "First name's length must be between {min} and {max}")
    @Column(name = "first_name")
    private String firstname;

    @NotBlank(message = "You must enter client's last name")
    @Size(min = 2, max = 50, message = "Last name's length must be between {min} and {max}")
    @Column(name = "last_name")
    private String lastname;

    @Column(nullable = false)
    private boolean sex;

    @ManyToOne
    private City city;

    @ManyToMany
    @JoinTable(name = "hobby_client",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "hobby_id")})
    @NotEmpty(message = "Client must have at least 1 hobby")
    private List<Hobby> hobbies;

    @NotBlank(message = "You must enter client's description")
    @Size(max = 10000)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
