public class GymUtility {
    public GymUtility() {
    }

    public static double calculateBMI(Member member, Assessment assessment){
        double BMI;
        BMI = assessment.getWeight()/(member.getHeight()*member.getHeight());
        return BMI;
    }

    public static String determineBMICategory(double BMIvalue){
        if(BMIvalue < 16) {
            return "SEVERELY UNDERWEIGHT";
        }
        if(BMIvalue >= 16 && BMIvalue < 18.5){
            return "UNDERWEIGHT";
        }
        if(BMIvalue >= 18.5 && BMIvalue < 25){
            return "NORMAL";
        }
        if(BMIvalue >= 25 && BMIvalue < 30){
            return "OVERWEIGHT";
        }
        if(BMIvalue >= 30 && BMIvalue < 35){
            return "MODERATELY OBESE";
        }
        if(BMIvalue >= 35){
            return "SEVERELY OBESE";
        }
        else
            return null;
    }

/* This method below checks a Members weight against the Devine formula to ascertain if the member is within the ideal
weight range. It takes and input of member and Assessment and the formula is applied to this Member for this particular Assessment
 */

    public static boolean isIdealBodyWeight(Member member, Assessment assessment){
        boolean isIdeal = false;
        Float weight;
        if(assessment == null) {
            weight = member.getStartWeight();
        }
        else {
            weight = assessment.getWeight();
        }
        if(member.getGender().equals("M")){
            if(member.getHeightInches() <= 60){
                if(weight == 50) {
                    isIdeal = true;
                }
            }
            if(member.getHeightInches() > 60){
                double heightAboveFiveFeetInInches = member.getHeightInches() - 60;
                double idealWeightAboveFiveFeet = heightAboveFiveFeetInInches*2.3;
                double devineFormulaResult = 50 + idealWeightAboveFiveFeet;
                if(weight >= devineFormulaResult-0.2 && weight <= devineFormulaResult+0.2){
                    isIdeal = true;
                }

            }
        }

        if(member.getGender().equals("F") || (member.getGender().equals("Unspecified"))){
            if(member.getHeightInches() <= 60){
                if(weight == 45.5) {
                    isIdeal = true;
                }
            }
            if(member.getHeightInches() > 60){
                double heightAboveFiveFeetInInches = member.getHeightInches() - 60;
                double idealWeightAboveFiveFeet = heightAboveFiveFeetInInches*2.3;
                double devineFormulaResult = 45.5 + idealWeightAboveFiveFeet;
                if(weight >= devineFormulaResult-0.2 && weight <= devineFormulaResult+0.2){
                    isIdeal = true;
                }
            }
        }


        return isIdeal;
    }

}
