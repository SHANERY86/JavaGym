import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
// THIS CODE IS INCOMPLETE

public class GymAPI {
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;
    private ArrayList<Assessment> assessments;

    public GymAPI() {
        this.members = new ArrayList<Member>();
        this.trainers = new ArrayList<Trainer>();
        this.assessments = new ArrayList<Assessment>();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }


    public void addMember(Member member) {
        members.add(member);

    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);

    }

    public int numberOfMembers() {
        return members.size();
    }

    public int numberOfTrainers() {
        return trainers.size();
    }


// Methods below checks to see if the input number is within the boundaries of the array of members or trainers
    public boolean isValidMemberIndex(int index) {
        if(index < members.size()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isValidTrainerIndex(int index) {
        if(index < trainers.size()){
            return true;
        }
        else {
            return false;
        }
    }

// Methods below searches member list for the email entered, it returns a match if it finds a complete match without case sensitivity

    public Member searchMembersByEmail(String emailEntered) {
        int index = 0;
        Member memberMatch = null;
        boolean memberFound = false;
        for (Member member : members) {
            if (emailEntered.equalsIgnoreCase(member.getEmail())) {
                memberMatch = members.get(index);
                memberFound = true;
            }
            index++;
        }
        if (memberFound) {
            return memberMatch;
        } else {
            return null;
        }
    }

    public Trainer searchTrainersByEmail(String emailEntered) {
        int index = 0;
        Trainer trainerMatch = null;
        boolean trainerFound = false;
        for (Trainer trainer : trainers) {
            if (emailEntered.equalsIgnoreCase(trainer.getEmail())) {
                trainerMatch = trainers.get(index);
                trainerFound = true;
            }
            index++;
        }
        if (trainerFound) {
            return trainerMatch;
        } else {
            return null;
        }
    }


//Methods below searches the list of names and returns a match if a partial match is found

    public ArrayList<String> searchMembersByName(String nameEntered) {
        int count = 0;
        int index = 0;
        ArrayList<String> nameMatches = new ArrayList<String>();
        for (Member member : members) {
            if (members.get(index).getName().contains(nameEntered)) {
                nameMatches.add(members.get(index).getName());
            }
            index++;
        }
        return nameMatches;
    }

    public ArrayList<String> searchTrainersByName(String nameEntered){
        int count = 0;
        int index = 0;
        ArrayList<String> nameMatches = new ArrayList<String>();
        for (Trainer trainer : trainers) {
            if (trainers.get(index).getName().contains(nameEntered)) {
                nameMatches.add(trainers.get(index).getName());
            }
            index++;
        }
        return nameMatches;
    }

    public ArrayList<Member> listMembers() {
        return members;

    }

/*Method below checks the list of members weight, and if it falls in the category of ideal weight it adds it to an arraylist
   the method then returns this arraylist */

    public ArrayList<Member> listMembersWithIdealWeight() {
        ArrayList<Member> idealWeightMembers = new ArrayList<Member>();

            for (Member member : listMembers()) {

                if (GymUtility.isIdealBodyWeight(member, member.latestAssessment())) {
                    idealWeightMembers.add(member);
                }
            }
            return idealWeightMembers;
    }

/* Method below checks the list of members to see which fall into the specific BMI categories, it checks for matches
without case sensitivity and also partial matches where appropriate
 */


    public ArrayList<Member> listMembersBySpecificBMICategory(String category) {
        ArrayList<Member> membersInThisCategory = new ArrayList<Member>();
        for (Member member : listMembers()){
            if((category.equalsIgnoreCase("SEVERELY UNDERWEIGHT") || category.contains("UNDERWEIGHT")) && GymUtility.calculateBMI(member,member.latestAssessment()) < 16){
                membersInThisCategory.add(member);
            }
            else if((category.equalsIgnoreCase("UNDERWEIGHT") || category.contains("UNDERWEIGHT")) && GymUtility.calculateBMI(member,member.latestAssessment()) >= 16 && GymUtility.calculateBMI(member,member.latestAssessment()) < 18.5){
                membersInThisCategory.add(member);
            }
            else if((category.equalsIgnoreCase("NORMAL") || category.contains("NORMAL")) && GymUtility.calculateBMI(member,member.latestAssessment()) >= 18.5 && GymUtility.calculateBMI(member,member.latestAssessment()) < 25){
                membersInThisCategory.add(member);
            }
            else if((category.equalsIgnoreCase("OVERWEIGHT") || category.contains("OVERWEIGHT")) && GymUtility.calculateBMI(member,member.latestAssessment()) >= 25 && GymUtility.calculateBMI(member,member.latestAssessment()) < 30) {
                membersInThisCategory.add(member);
            }
            else if((category.equalsIgnoreCase("MODERATELY OBESE") || category.contains("OBESE")) && GymUtility.calculateBMI(member,member.latestAssessment()) >= 30 && GymUtility.calculateBMI(member,member.latestAssessment()) < 35) {
                membersInThisCategory.add(member);
            }
            else if((category.equalsIgnoreCase("SEVERELY OBESE")  || category.contains("OBESE")) && GymUtility.calculateBMI(member,member.latestAssessment()) >= 35){
                membersInThisCategory.add(member);
            }
        }
        return membersInThisCategory;
    }

    public String listMemberDetailsImperialAndMetric() {
        int index = 0;
       String str = "";
        for (Member member : members) {
            str += members.get(index).getName() + ": " + Math.round(members.get(index).latestAssessment().getWeight()) + " kg " + Math.round(members.get(index).latestAssessment().getImperialWeight()) + " lbs "
                    + Math.round(members.get(index).getHeight()*10)*0.1 + " metres " + Math.round(members.get(index).getHeightInches()) + " inches" + "\n";
            index++;
        }
        System.out.println(str);
        if (members.size() > 0) {
           return str;
        } else {
            return "No registered members";
        }
    }

    public void load() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Product class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated

        Class<?>[] classes = new Class[] { Member.class, Trainer.class, Assessment.class, PremiumMember.class,StudentMember.class };
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("list.xml"));
        members = (ArrayList<Member>) is.readObject();
        trainers = (ArrayList<Trainer>) is.readObject();
        assessments = (ArrayList<Assessment>) is.readObject();
        is.close();

    }

    public void save() throws Exception
    {
        XStream xstream = new XStream(new DomDriver());

        // ------------------ PREVENT SECURITY WARNINGS-----------------------------
        // The Product class is what we are reading in.
        // Modify to include others if needed by modifying the next line,
        // add additional classes inside the braces, comma separated

        Class<?>[] classes = new Class[] { Member.class, Trainer.class, Assessment.class, PremiumMember.class,StudentMember.class };


        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);
        // -------------------------------------------------------------------------

        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("list.xml"));
        out.writeObject(members);
        out.writeObject(trainers);
        out.writeObject(assessments);
        out.close();
    }

}


