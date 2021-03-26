public class StudentMember extends Member  {
    private String chosenPackage;

    public StudentMember(String email, String name, String address, String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender, height, startWeight, chosenPackage);
        setChosenPackage(chosenPackage);
    }

    public void setChosenPackage(String packageChoice){
        if(packageChoice.equals("WIT")){
            this.chosenPackage = packageChoice;
        }
        else{
            this.chosenPackage = "Package 3";
        }
    }

    public String getChosenPackage() {
        return chosenPackage;
    }

    public String toString(){

        return super.toString() + "Chosen Package: " + chosenPackage + ": ";

    }
}
