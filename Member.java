
// THIS CODE IS INCOMPLETE

import java.util.*;

public class Member extends Person {
    private float height;
    private float startWeight;
    private String chosenPackage;
    private HashMap<String, Assessment> assessmentLog;
    private HashSet<String> dates;
    private SortedSet<String> sortedDates;

    public Member(String email, String name, String address,
                  String gender, float height, float startWeight, String chosenPackage) {
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startWeight);
        setChosenPackage(chosenPackage);
        assessmentLog = new HashMap<String,Assessment>();
        sortedDates = new TreeSet<String>();
    }

    public double getHeight() {
        return height;
    }

    public double getHeightInches(){
        double heightInches = height * 39.3701;
        return heightInches;
    }

    public void setHeight(float height) {
        if(height >= 1 && height <= 3){
            this.height = height;
        }
        if(height < 1) {
            this.height = 1;
        }
        if(height > 3) {
            this.height = 3;
        }
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        if(startWeight >= 35 && startWeight <= 250) {
            this.startWeight = startWeight;
        }
        if(startWeight < 35) {
            this.startWeight = 35;
        }
        if(startWeight > 250){
            this.startWeight = 250;
        }
    }

    public String getChosenPackage() {
        return chosenPackage;
    }

    public void setChosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }


/* This method below takes in a date and an assessment, and places it in the hashmap "AssessmentLog"

 */
    public void addAssessment(String date, Assessment assessment){
        assessmentLog.put(date, assessment);
    }

/* THis method below takes in a date as a key and produces the relevant assessment for that date via the HashMap "AssessmentLog"

 */
    public Assessment getAssessment(String date){
        Assessment ass = assessmentLog.get(date);
        return ass;
    }

     public HashMap<String,Assessment> getAssessments(){
        return assessmentLog;
     }

     public void setAssessmentLog(HashMap<String,Assessment> assessmentLog){
        this.assessmentLog = assessmentLog;
     }


/*This method below is referencing its superclass "Person" to create a string of details about the member
 */
    public String toString(){

        return super.toString() + "\n Height: " + height
                + "m\n Starting Weight: " + startWeight
                + "kg\n";
    }

/* This method below is using the "last" function on the SortedSet "sortedAssessmentDates" to produce the latest entry of "AssessmentLog"

 */
   public Assessment latestAssessment() {
          Assessment lastAssessment = null;
          if(assessmentLog.size()>0) {
              lastAssessment = assessmentLog.get(sortedAssessmentDates().last());
          }
       return lastAssessment;
   }

/* This method below produces a SortedSet of dates in ascending order
A forEach loop is used on the HashMap "AssessmentLog" to iterate between the entries
  this sorted set of dates is used in progress sub menu to display progress chronologically*/
     public SortedSet<String> sortedAssessmentDates(){
         SortedSet<String> dates = new TreeSet<>();
         assessmentLog.forEach((key, value) -> {
             dates.add(key);
         });
         return dates;
     }


/*This method below produces an ArrayList of weights that are used in the progress sub menu
to display progress by weight chronologically
 */
     public ArrayList<Float> getWeights(){
        ArrayList<Float> weights = new ArrayList<Float>();
        ArrayList<Assessment> assessments = new ArrayList<Assessment>();
        SortedSet<String> sortedDates;
        sortedDates = sortedAssessmentDates();
        sortedDates.forEach((value) -> {
            assessments.add(assessmentLog.get(value));
        });
        for(Assessment assessment : assessments){
            weights.add(assessment.getWeight());
        }
        return weights;
     }
    /*This method below produces an ArrayList of waist measurements that are used in the progress sub menu
    to display progress by waist chronologically
     */
     public ArrayList<Float> getWaists() {
         ArrayList<Float> waists = new ArrayList<Float>();
         ArrayList<Assessment> assessments = new ArrayList<Assessment>();
         SortedSet<String> sortedDates = new TreeSet<String>();
         sortedDates = sortedAssessmentDates();
         sortedDates.forEach((value) -> {
             assessments.add(assessmentLog.get(value));
         });
         for(Assessment assessment : assessments){
             waists.add(assessment.getWaist());
         }
         return waists;
     }

 /*This method does some if checks to determine if a user has gained or lost weight since the previous entry
 and prints to the console the chronological list of progress by weight for the user
  */
     public void weightsByDate() {
         ArrayList<String> dates = new ArrayList<String>();
         ArrayList<String> weightComparisons = new ArrayList<String>();
         String weightStatement = "";
         SortedSet<String> sortedDates = sortedAssessmentDates();
         sortedDates.forEach((value) -> {
             dates.add(value);
         });
         int index = 0;
         for (int i = 0; i < getWeights().size(); i++) {

             if (getWeights().size() > 1 && index > 0) {
                 if (getWeights().get(index) < getWeights().get(index - 1)) {
                     weightStatement = "You lost weight!";
                 } else if (getWeights().get(index) > getWeights().get(index - 1)) {
                     weightStatement = "You gained weight";
                 } else if (getWeights().get(index) == getWeights().get(index - 1)) {
                     weightStatement = "Your weight stayed the same";
                 }
             }
             index++;
             weightComparisons.add(weightStatement);
         }
         int count = 0;
         for (int i = 0; i < getWeights().size(); i++) {
             System.out.println(dates.get(count) + " : " + getWeights().get(count) + "kg " + weightComparisons.get(count));
             count++;
         }
     }

    /*This method does some if checks to determine if a user has gained or lost waist size since the previous entry
    and prints to the console the chronological list of progress by waist size for the user
     */
     public void waistsByDate() {
         ArrayList<String> dates = new ArrayList<String>();
         ArrayList<String> waistComparisons = new ArrayList<String>();
         String waistStatement = "";
         SortedSet<String> sortedDates = sortedAssessmentDates();
         sortedDates.forEach((value) -> {
             dates.add(value);
         });
         int index = 0;
         for (int i = 0; i < getWeights().size(); i++) {

             if (getWeights().size() > 1 && index > 0) {
                 if (getWeights().get(index) < getWeights().get(index - 1)) {
                     waistStatement = "Your waist got smaller!";
                 } else if (getWeights().get(index) > getWeights().get(index - 1)) {
                     waistStatement = "Your waist got bigger";
                 } else if (getWeights().get(index) == getWeights().get(index - 1)) {
                     waistStatement = "Your waist stayed the same";
                 }
             }
             index++;
             waistComparisons.add(waistStatement);
         }
         int count=0;
         for (int i = 0; i < getWaists().size(); i++) {
             System.out.println(dates.get(count) + " : " + getWaists().get(count) + "kg " + waistComparisons.get(count));
             count++;
         }

     }
}

