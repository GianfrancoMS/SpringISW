package com.gianfranco.trabajoparcial.model;

public class Client {
    private String firstName;
    private String lastName;
    private String dni;
    private boolean sex;
    private String city;
    private String description;
    private int hobby;

    public int getHobby() {
        return hobby;
    }

    public void setHobby(int hobby) {
        this.hobby = hobby;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isMale() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class ClientBuilder {
        private String firstName;
        private String lastName;
        private String dni;
        private boolean sex;
        private String city;
        private String description;
        private int hobby;

        public ClientBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientBuilder withLastname(String lastname) {
            this.lastName = lastname;
            return this;
        }

        public ClientBuilder withDni(String dni) {
            this.dni = dni;
            return this;
        }

        public ClientBuilder isMale(boolean isMale) {
            this.sex = isMale;
            return this;
        }

        public ClientBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public ClientBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ClientBuilder withHobby(int hobby) {
            this.hobby = hobby;
            return this;
        }

        public Client build() {
            Client client = new Client();
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setSex(sex);
            client.setDni(dni);
            client.setCity(city);
            client.setDescription(description);
            client.setHobby(hobby);
            return client;
        }
    }
}
