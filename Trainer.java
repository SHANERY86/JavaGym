public class Trainer extends Person {
    private String specialty;
    public Trainer(String email, String name, String address, String gender, String specialty) {
        super(email, name, address, gender);
    }

    public void setSpecialty(String specialty){
        this.specialty = specialty;
    }

    public String getSpecialty(){
        return specialty;
    }
}
