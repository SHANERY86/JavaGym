import java.util.HashMap;
import java.util.HashSet;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Assessment {
private float weight;
private float thigh;
private float waist;
private String comment;


public Assessment(){

}

    public Assessment(float weight, float thigh, float waist, String comment) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;

    }

    public float getWeight() {
        return weight;
    }

    public double getImperialWeight() {
        double imperialWeight;
        imperialWeight = weight * 2.205;
        return imperialWeight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getThigh() {
        return thigh;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return  "Progress" + "\nWeight: " + weight
                + "\nThigh: " + thigh
                + "\nWaist: " + waist
                + "\nComment:" + comment;
    }
}
