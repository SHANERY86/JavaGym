
public class PremiumMember extends Member {
    private String chosenPackage;


    public PremiumMember(String email, String name, String address, String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender, height, startWeight, chosenPackage);
        setChosenPackage(chosenPackage);
    }

    public void setChosenPackage(String packageChoice){
this.chosenPackage = packageChoice;
    }

 /*   public String toString(){

        return super.toString() + "\n Height: " + height
                + "m\n Starting Weight: " + startWeight
                + "kg\n Chosen Package: " + chosenPackage + ": ";

    } */

    public String getChosenPackage() {
        return chosenPackage;
    }

    public String toString(){

        return super.toString() + "Chosen Package: " + chosenPackage + ": ";

    }
}
