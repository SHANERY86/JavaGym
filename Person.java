
// THIS CODE IS INCOMPLETE

public class Person {
    private String name;
    private String email;
    private String address;
    private String gender;

    public void setName(String name) {
        if(name.length() > 30) {
            this.name = name.substring(0, 30);
        }
        else{
            this.name = name;
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        if(gender.equals("M") || gender.equals("F") || gender.equals("m") || gender.equals("f")){
            this.gender = gender.toUpperCase();
        }
        else{
            this.gender = "Unspecified";
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public Person(String email, String name, String address, String gender) {
        setName(name);
        this.email = email;
        this.address = address;
        setGender(gender);
    }

    public String toString(){
        return " Name: " + name
                + "\n Email: " + email
                + "\n Address: " + address
                + "\n Gender: " + gender;
    }
}

