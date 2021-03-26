import java.rmi.ServerError;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MenuController {

    private Scanner input = new Scanner(System.in);
    private GymAPI GymAPI;
    private ArrayList<Member> members;
    private ArrayList<Trainer> trainers;
    private ArrayList<String> emails;
    private ArrayList<String> names;
    private HashMap<String,String> packages = new HashMap<String, String>();

    public static void main(String[] args) {
MenuController mc = new MenuController();
    }

public MenuController(){
        GymAPI = new GymAPI();
        try {
            GymAPI.load();
        }
        catch(Exception e){
            System.err.println("Error loading from file: " + e);
        }
        fillPackages();  //this is loading the hashmap 'packages' with keys and values related to the package name and its details
        loginMenu();   //this starts the program off by presenting the login menu with loginMenuDisplay(), with options that work through the loginMenu() method
}

private int loginMenuDisplay(){
        int option = 0;
        boolean correctInputTest = false;    //this boolean is made true if the correct input is put in, it ensures a number is entered and not a letter
        while(!correctInputTest) {
            System.out.println("WELCOME TO THE GYM ONLINE PORTAL");
            System.out.println("--------------------------------");
            System.out.println("1 ) Login");
            System.out.println("2 ) Register");
            System.out.println("--------------------------------");
            System.out.println("0 ) Exit");
            System.out.println("--------------------------------");
            System.out.println("===>");
            try {
                option = input.nextInt();  //this option is passed to the loginMenu() method
                correctInputTest = true;
            } catch (Exception e) {
                System.out.println("Incorrect Input: Expecting a number, press enter to continue..");
                input.nextLine();
                input.nextLine();
            }
        }
    return option;
}

/* the method below runs if option 1: Login is selected from the loginMenu()
It asks for an email, and the member list is searched for a non-case sensitive match, if there is nothing found
the trainer list is searched for a non-case sensitive match. When there is a match found in either the member list
or trainer list, the appropriate method is launched, either memberMenu or trainerMenu, and the matched member or trainer
is passed as a field into that method.
 */
private void login(){
        input.nextLine();
    boolean userLogin = false;  //a boolean to control the while loop, until this is satisfied the method will loop.
    while(!userLogin) {
        System.out.println("Please enter your email address");
        System.out.println("==>");
        String email = input.nextLine();
        for (Member member : GymAPI.getMembers()) {
            if (email.equalsIgnoreCase(member.getEmail())) {
                memberMenu(member);
                userLogin = true;
            }
        }
        for (Trainer trainer : GymAPI.getTrainers()) {
            if (email.equalsIgnoreCase(trainer.getEmail())) {
                trainerMenu(trainer);
                userLogin = true;
            }
        }
        System.out.println("The email your entered is not registered, press enter to try again.");
        input.nextLine();
    } //end of while loop. When there is no match, the string above will prompt the user.
}

/*The method below is run along with trainerMenu when a trainer has logged in

 */

private int trainerMenuDisplay(String name){
    System.out.println("TRAINER MENU");
    System.out.println("------------");
    System.out.println("Welcome " + name);
    System.out.println("Please choose one of the following options");
    System.out.println("1 ) Add a new member");
    System.out.println("2 ) List all members");
    System.out.println("3 ) Search for a member by email");
    System.out.println("4 ) Manage member assessments");
    System.out.println("0 ) Exit");
    System.out.println("==>");
    int option = input.nextInt(); //this option is passed to the trainerMenu() method
    return option;
}

/* The method below is run along with assessmentMenu() when option 4: Manage member assessments is chosen from the
trainerMenu()
 */
private int assessmentMenuDisplay(){
    System.out.println("ASSESSMENT MENU");
    System.out.println("---------------");
    System.out.println("1 ) Add new assessment");
    System.out.println("2 ) Update assessment comment");
    System.out.println("3 ) Return to trainer menu");
    int option = input.nextInt();
    return option;
}

/* The method below is run along with addAssessment() when option 1 is chosen from the assessmentMenu()
It prompts the trainer to enter the members email.
The members list is checked for a non-case sensitive match.
The trainer is then prompted to enter in the details that will complete an assessment: Date,Members Weight,Thigh size,Waist size, and comments.
A new assessment is created, and the date field and assessment are added to a HashMap in the member class using the addAssessment() method in the Member class.
The new information is saved to the XML file
 */

private void addAssessmentMenu(){
    Assessment assessment;
    String assessmentDetails = "";
    Member memberBeingAssessed = null;
    System.out.println("Please enter the members email:");
    System.out.println("==>");
    input.nextLine();
    String email = input.nextLine();
    for (Member member : GymAPI.getMembers()) {
        if (email.equalsIgnoreCase(member.getEmail())) {
            memberBeingAssessed = member;
        }
    }
    System.out.println("Please enter the date:");
    System.out.println("==>");
    String date = input.nextLine();
    System.out.println("Please enter the members current weight:");
    System.out.println("==>");
    float weight = input.nextInt();
    System.out.println("Please enter the members current thigh size:");
    System.out.println("==>");
    float thigh = input.nextInt();
    System.out.println("Please enter the members current waist size:");
    System.out.println("==>");
    float waist = input.nextInt();
    System.out.println("Please enter any comments:");
    System.out.println("==>");
    input.nextLine();
    String comments = input.nextLine();
    assessment = new Assessment(weight,thigh,waist,comments);
//    assessmentDetails = assessment.toString();
    memberBeingAssessed.addAssessment(date,assessment);
   try {
        GymAPI.save();
    } catch (Exception e) {
        System.out.println("Failed to save" + e);
    }
}

/* This method below is run when option 2 is chosen from the assessmentMenu() method
It prompts the trainer to enter the members email and then the date that the assessment occurred.
Then using the entered date the getAssessment() method calls the value of the HashMap related to that date key
to bring up the appropriate Assessment.
Then the user is prompted to add a comment which is sent to the assessment using a setter.
The new information is saved to the XML file.
 */
private void updateComment(){
    Assessment assessment;
    String assessmentDetails = "";
    Member memberBeingAssessed = null;
    System.out.println("Please enter the members email:");
    System.out.println("==>");
    input.nextLine();
    String email = input.nextLine();
    for (Member member : GymAPI.getMembers()) {
        if (email.equalsIgnoreCase(member.getEmail())) {
            memberBeingAssessed = member;
        }
    }
    System.out.println("Please enter the date of the assessment:");
    System.out.println("==>");
    String date = input.nextLine();
    assessment = memberBeingAssessed.getAssessment(date);
    System.out.println("Please enter new comments:");
    System.out.println("==>");
    String comments = input.nextLine();
    assessment.setComment(comments);
    try {
        GymAPI.save();
    } catch (Exception e) {
        System.out.println("Failed to save" + e);
    }
}

/*This method is run along with memberMenu() when an email that matches one in the member list is entered
during the login. It passes in a name field, that is obtained using the Member.getName() function with the member
that has been matched by the email input by the user
 */
private int memberMenuDisplay(String name){
    int option = 0;
    boolean correctInputTest = false;
    while(!correctInputTest) {
        System.out.println("MEMBER MENU");
        System.out.println("------------");
        System.out.println("Welcome " + name);
        System.out.println("Please choose one of the following options: ");
        System.out.println("1 ) View your profile");
        System.out.println("2 ) Update your profile");
        System.out.println("3 ) View your progress");
        System.out.println("4 ) Return to Start Menu");
        System.out.println("------------------------");
        System.out.println("0 ) Exit");
        System.out.println("==>");
        try {
            option = input.nextInt();
            correctInputTest = true;
        } catch (Exception e) {
            System.out.println("Incorrect Input: Expecting a number, press enter to continue..");
            input.nextLine();
            input.nextLine();
        }
    }
    return option;
}


/*this method is run along with updateMenu() when option 2: Update your profile is chosen from the member menu
 */
private int updateMenuDisplay(){
    System.out.println("MEMBER UPDATE MENU");
    System.out.println("------------------");
    System.out.println("Please choose one of the following options");
    System.out.println("1 ) Update name");
    System.out.println("2 ) Update address");
    System.out.println("3 ) Update gender");
    System.out.println("4 ) Update package");
    System.out.println("5 ) Return to Member Menu");
    System.out.println("-------------------------");
    System.out.println("0 ) Exit");
    int option = input.nextInt();
    return option;
}

/*this method is run along with progressSubMenu() when option 3: View your progress is chosen from the member menu

 */
private int progressSubMenuDisplay(){
    System.out.println("VIEW YOUR PROGRESS");
    System.out.println("------------------");
    System.out.println("1 ) View Progress by weight");
    System.out.println("2 ) View Progress by waist measurement");
    System.out.println("3 ) View a full assessment on a given day");
    System.out.println("4 ) Return to Member Menu");
    System.out.println("------------------");
    System.out.println("0 ) Exit");
    int option = input.nextInt();
    return option;
}

/* this method is run when option 2: Register is chosen from the loginMenu... and new member was chosen from the prompt afterwards
This method will create a new Member
The new user is prompted to enter an email address which is checked to see if its already registered, if so it prompts the user to try again
Following a new email being entered, the user is prompted to enter all of the details required to create a new Member.
Then the user is prompted to choose either a Student membership or Premium membership.
After that the user is then prompted to pick an appropriate package related to that membership.
 */
private Member memberRegister(){
        boolean duplicateTestPass = false;
        Member newMember = null;
        String email = "";
        String chosenPackage = "";
    input.nextLine();
    while(!duplicateTestPass) {  //this while loop will ensure that this prompt loops until an email is entered that is not a duplicate of a pre existing email in the system
        System.out.println("Please enter an email address:");
        email = input.nextLine();
        members = GymAPI.getMembers();
        emails = new ArrayList<String>();
        for (Member member : members) {
            emails.add(member.getEmail());
        }
        if (emails.contains(email)) {
            System.out.println("This email address is already registered");
        } else {
            duplicateTestPass = true;
        }
    }
        System.out.println("Please enter a Name:");
        String name = input.nextLine();
        System.out.println("Please enter a Gender:");
        String gender = input.nextLine();
        System.out.println("Please enter an Address:");
        String address = input.nextLine();
        System.out.println("Please enter a height:");
        float height = input.nextFloat();
        System.out.println("Please enter a starting weight:");
        float weight = input.nextFloat();
        int choice = 0;
        boolean correctInput = false;
        boolean premiumMember = false;
        while(!correctInput) {  //while loop that loops this menu until a number is entered
            System.out.println("Please choose account type:");
            System.out.println("1 ) Student");
            System.out.println("2 ) Premium");
            input.nextLine();
            try {
                choice = input.nextInt();
                correctInput = true;
            } catch (Exception e) {
                System.out.println("Expecting a number");
            }
        }
        if (choice == 1) {
            premiumMember = false;
        }
        else if (choice == 2) {
            premiumMember = true;
        } else {
            System.out.println("Wrong number entered.");
        }

        if(premiumMember) { //if premiumMember is selected run this code
            int option = 0;
            boolean correctInputTest = false;
            boolean correctInputPackagePremium = false;
            while(!correctInputPackagePremium) { //this while loop loops this menu until one of the correct option is chosen
                while(!correctInputTest) {  //this while loop loops this menu below until a number is entered
                    System.out.println("Please choose a chosen Package:" +
                            "\n 1 ) Package 1: Allowed access anytime to gym. Free access to all classes. Access to all changing areas including deluxe changing rooms."
                            + "\n 2 ) Package 2: Allowed access anytime to gym. €3 fee for all classes. Access to all changing areas including deluxe changing rooms.");
                    try {
                        option = input.nextInt();
                        correctInputTest = true;
                    } catch (Exception e) {
                        System.out.println("Incorrect Input: Expecting a number, press enter to continue..");
                        input.nextLine();
                        input.nextLine();
                    }
                }
                if(option == 1) {
                    correctInputPackagePremium = true;
                    chosenPackage = "Package 1";
                    newMember = new PremiumMember(email, name, address, gender, height, weight, chosenPackage);
                }
                else if(option == 2){
                    correctInputPackagePremium = true;
                    chosenPackage = "Package 2";
                    newMember = new PremiumMember(email, name, address, gender, height, weight, chosenPackage);
                }
                else{
                    System.out.println("Incorrect input for package");
                }
            }
        } //end if(premiumMember)
        else{ //if student member is selected run this code
                System.out.println("Please enter your college name");
                input.nextLine();
                chosenPackage = input.nextLine(); //chosenPackage is run to the StudentMember class where it undergoes validation, if "WIT" is not entered, package 3 is chosen.
                    newMember = new StudentMember(email, name, address, gender, height, weight, chosenPackage);
            }
        GymAPI.addMember(newMember);
        try {
            GymAPI.save();
        } catch (Exception e) {
            System.out.println("Failed to save" + e);
        }
        return newMember;
}

/* This method is run when new trainer is selected after selecting option 2: Register from the loginMenu
This method will create a new trainer.
It prompts the user for an email address, and checks to ensure it is not already in the system
Then it will prompt the user to enter the details required to add a new Trainer.
The new information is saved to the XML file
 */
private Trainer trainerRegister(){
    boolean duplicateTestPass = false;
    Trainer newTrainer;
    String email = "";
    trainers = GymAPI.getTrainers();
    emails = new ArrayList<String>();
    for(Trainer trainer : trainers){
        emails.add(trainer.getEmail());
    }
    input.nextLine();
    while(!duplicateTestPass) {
        System.out.println("Please enter your email address:");
        email = input.nextLine();
            if (emails.contains(email)) {
                System.out.println("This email address is already registered");
            }
            else{
                duplicateTestPass = true;
            }
    }
    System.out.println("Please enter your Name:");
    String name = input.nextLine();
    System.out.println("Please enter your Gender (M for male, F for female, any key for Unspecified:");
    String gender = input.nextLine();
    System.out.println("Please enter your Address:");
    String address = input.nextLine();
    System.out.println("Please enter your Specialty:");
    String specialty = input.nextLine();
    newTrainer = new Trainer(email,name,address,gender,specialty);
    GymAPI.addTrainer(newTrainer);
    try {
        GymAPI.save();
    } catch (Exception e) {
        System.out.println("Failed to save" + e);
    }
    trainerMenu(newTrainer);
    return newTrainer;
}

/*This is the functioning code for the loginMenuDisplay() method
            System.out.println("WELCOME TO THE GYM ONLINE PORTAL");
            System.out.println("--------------------------------");
            System.out.println("1 ) Login");
            System.out.println("2 ) Register");
            System.out.println("--------------------------------");
            System.out.println("0 ) Exit");
            System.out.println("--------------------------------");
            System.out.println("===>");
 */
private void loginMenu(){
        int choice = 0;
        int option = loginMenuDisplay();
        while (option != 0){

            switch(option){
                case 1: login();
                break;
                case 2: System.out.println("Are you a new Trainer or a new Member?");
                        System.out.println("1 ) Trainer");
                        System.out.println("2 ) Member");
                        try {
                            choice = input.nextInt();
                        }
                        catch(Exception e){
                            System.out.println("Expecting a number");
                        }
                        if (choice == 1) {
                            trainerRegister();
                        }
                        else if (choice == 2) {
                            Member newMember = memberRegister();
                            memberMenu(newMember);
                        } else {
                            System.out.println("Wrong number entered.");
                        }
                break;
                default: System.out.println("Invalid option entered: " + option);
                break;
            }
            System.out.println("Press enter to continue..");
            input.nextLine();
            input.nextLine();

            option = loginMenuDisplay();
        }
        System.out.println("Exiting the system, good bye!");
        System.exit(0);
}

/* this is the functioning code for the memberMenuDisplay() method.
It passes in a member object that is retrieved from the member found through the match with the email found during login
        System.out.println("MEMBER MENU");
        System.out.println("------------");
        System.out.println("Welcome " + name);
        System.out.println("Please choose one of the following options: ");
        System.out.println("1 ) View your profile");
        System.out.println("2 ) Update your profile");
        System.out.println("3 ) View your progress");
        System.out.println("4 ) Return to Start Menu");
        System.out.println("------------------------");
        System.out.println("0 ) Exit");
        System.out.println("==>");
 */
private void memberMenu(Member member){
    int option = memberMenuDisplay(member.getName());
    while(option != 0) {
        switch (option) {
            case 1:
                System.out.println(member); //this will print the toString method for the member
                System.out.println(packages.get(member.getChosenPackage()));  //this will print the chosen package, which goes through validation in StudentMember
                break;
            case 2:
                updateMenu(member);
                break;
            case 3:
                progressSubMenu(member);
            case 4:
                loginMenu();
                break;
            default: System.out.println("Invalid option entered: " + option);
            break;
        }
        System.out.println("Press enter to continue");
        input.nextLine();
        input.nextLine();

        option = memberMenuDisplay(member.getName());
    }
    System.out.println("Exiting the system, good bye!");
    System.exit(0);
}

/*this is the functional code for the trainerMenuDisplay() method.
    System.out.println("TRAINER MENU");
    System.out.println("------------");
    System.out.println("Welcome " + name);
    System.out.println("Please choose one of the following options");
    System.out.println("1 ) Add a new member");
    System.out.println("2 ) List all members");
    System.out.println("3 ) Search for a member by email");
    System.out.println("4 ) Manage member assessments");
    System.out.println("0 ) Exit");
    System.out.println("==>");
 */
private void trainerMenu(Trainer trainer) {

    int option = trainerMenuDisplay(trainer.getName());
    while (option != 0) {
        switch (option) {
            case 1:
                memberRegister();
                break;
            case 2:
                String str = "";
                for (Member member : GymAPI.getMembers()) {
                    str += member.getName() + "\n";
                }
                System.out.println(str);
                break;
            case 3:
                System.out.println("Please enter the email address:");
                input.nextLine();
                String email = input.nextLine();
                Member memberFound = null;
                for (Member member : GymAPI.getMembers()){
                    if(email.equalsIgnoreCase(member.getEmail())){
                        memberFound = member;
                    }
                }
                System.out.println(memberFound);
                break;
            case 4:
                assessmentMenu(trainer);
                break;
        }


    System.out.println("Press enter to continue");
    input.nextLine();
    input.nextLine();

    option = trainerMenuDisplay(trainer.getName());
}
    System.out.println("Exiting the system, good bye!");
    System.exit(0);
}

/*This is the functional code for the assessmentMenuDisplay() method
it passes in a trainer object that is logged in from the trainer menu
    System.out.println("ASSESSMENT MENU");
    System.out.println("---------------");
    System.out.println("1 ) Add new assessment");
    System.out.println("2 ) Update assessment comment");
    System.out.println("3 ) Return to trainer menu");
    int option = input.nextInt();
    return option;
 */
private void assessmentMenu(Trainer trainer){
    int option = assessmentMenuDisplay();
    while (option != 0) {
        switch (option) {
            case 1:
                addAssessmentMenu();
                break;
            case 2:
                updateComment();
                break;
            case 3:
                trainerMenu(trainer);
                break;
        }
        System.out.println("Press enter to continue");
        input.nextLine();
        input.nextLine();

        option = assessmentMenuDisplay();
    }
    System.out.println("Exiting the system, good bye!");
    System.exit(0);
}

/*This is the functional code for the updateMenuDisplay() method
    System.out.println("MEMBER UPDATE MENU");
    System.out.println("------------------");
    System.out.println("Please choose one of the following options");
    System.out.println("1 ) Update name");
    System.out.println("2 ) Update address");
    System.out.println("3 ) Update gender");
    System.out.println("4 ) Update package");
    System.out.println("5 ) Return to Member Menu");
    System.out.println("-------------------------");
    System.out.println("0 ) Exit");
 */
private void updateMenu(Member member){
    int option = updateMenuDisplay();
    while(option != 0) {
        switch (option) {
            case 1:
                System.out.println("Please enter your name:");
                input.nextLine();
                String newName = input.nextLine();
                member.setName(newName);
                try {
                    GymAPI.save();
                } catch (Exception e) {
                    System.out.println("Failed to save" + e);
                }
                break;
            case 2:
                System.out.println("Please enter your address:");
                input.nextLine();
                String newAddress = input.nextLine();
                member.setAddress(newAddress);
                try {
                    GymAPI.save();
                } catch (Exception e) {
                    System.out.println("Failed to save" + e);
                }
                break;
            case 3:
                System.out.println("Please enter your gender (M for male, F for female, any key for unspecified");
                input.nextLine();
                String newGender = input.nextLine();
                member.setGender(newGender);
                try {
                    GymAPI.save();
                } catch (Exception e) {
                    System.out.println("Failed to save" + e);
                }
                break;
            case 4:
                String newPackage = "";
                boolean correctInput = false;
                boolean correctInputTest = false;
                if(member.getChosenPackage().equals("Package 1") || member.getChosenPackage().equals("Package 2")) {
                    while (!correctInput) {
                        while(!correctInputTest){
                        System.out.println("Please choose a chosen Package:" +
                                "\n 1 ) Package 1: Allowed access anytime to gym. Free access to all classes. Access to all changing areas including deluxe changing rooms."
                                + "\n 2 ) Package 2: Allowed access anytime to gym. €3 fee for all classes. Access to all changing areas including deluxe changing rooms.");
                        try {
                            option = input.nextInt();
                            correctInputTest = true;
                        } catch (Exception e) {
                            System.out.println("Incorrect Input: Expecting a number, press enter to continue..");
                            input.nextLine();
                            input.nextLine();
                        }
                    }
                        if(option == 1) {
                            correctInput = true;
                            newPackage = "Package 1";
                        }
                        else if(option == 2){
                            correctInput = true;
                            newPackage = "Package 2";
                        }
                        else{
                            System.out.println("Incorrect input for package");
                        }
                    }
                }
                if(member.getChosenPackage().equals("WIT") || member.getChosenPackage().equals("Package 3")) {
                    System.out.println("Please enter your college name:");
                    input.nextLine();
                    newPackage = input.nextLine();
                }
                member.setChosenPackage(newPackage);
                try {
                    GymAPI.save();
                } catch (Exception e) {
                    System.out.println("Failed to save" + e);
                }
            case 5:
                memberMenu(member);
                break;
        }
        System.out.println("Press any key to continue..");
        input.nextLine();
        input.nextLine();

        option = updateMenuDisplay();
    }
    System.out.println("Exiting the system, good bye!");
    System.exit(0);
}

/*this is the functional code for the progressSubMenuDisplay() method
    System.out.println("VIEW YOUR PROGRESS");
    System.out.println("------------------");
    System.out.println("1 ) View Progress by weight");
    System.out.println("2 ) View Progress by waist measurement");
    System.out.println("3 ) View a full assessment on a given day");
    System.out.println("4 ) Return to Member Menu");
    System.out.println("------------------");
    System.out.println("0 ) Exit");
 */
    private void progressSubMenu(Member member){
        int option = progressSubMenuDisplay();
        while(option != 0){
            switch(option){
                case 1:
                    System.out.println("PROGRESS BY WEIGHT");
                    System.out.println("-----------------");
                    System.out.println("DATE:     WEIGHT:");
//                    System.out.println(member.getWeights().size());
//                    System.out.println(member.getWaists().size());
                    member.weightsByDate();
                    break;
                case 2:
                    System.out.println("PROGRESS BY WAIST");
                    System.out.println("-----------------");
                    System.out.println("DATE:     WAIST:");
                    member.waistsByDate();
                    break;
                case 3:
                    System.out.println("DATES ON WHICH YOU WERE ASSESSED:");
                    System.out.println(member.sortedAssessmentDates());
                    System.out.println("Please enter the date of the assessment you would like to view:");
                    System.out.println("==>");
                    input.nextLine();
                    String inputDate = input.nextLine();
                    System.out.println(member.getAssessment(inputDate));
                    break;
                case 4:
                    memberMenu(member);
            }
            System.out.println("Press any key to continue..");
            input.nextLine();
            input.nextLine();

            option = progressSubMenuDisplay();
        }
        System.out.println("Exiting the system, good bye!");
        System.exit(0);
    }


/* this method populates the packages HashMap, for reference to the appropriate package using the appropriate String as a key
 */

    private void fillPackages(){
        packages.put("Package 1", "Allowed access anytime to gym.\nFree access to all classes.\nAccess to all changing areas including deluxe changing rooms.");

        packages.put("Package 2", "Allowed access anytime to gym.\n€3 fee for all classes.\nAccess to all changing areas including deluxe changing rooms.");

        packages.put("Package 3", "Allowed access to gym at off-peak times. \n€5 fee for all classes. \nNo access to deluxe changing rooms.");

        packages.put("WIT", "Allowed access to gym during term time. \n€4 fee for all classes.  \nNo access to deluxe changing rooms.");
    }

}
