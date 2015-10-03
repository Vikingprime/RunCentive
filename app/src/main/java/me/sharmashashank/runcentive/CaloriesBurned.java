package me.sharmashashank.runcentive;

/**
 * Created by Vikingprime on 10/3/2015.
 */
public class CaloriesBurned {
    private double mWeight;
    private double mSpeed;
    private double mTime;
    public CaloriesBurned(double weightInKilos,float speed, double time){
        mWeight = weightInKilos;
        mSpeed = calcMph(speed);
        mTime = time;
    }

    private double calcMetersPerMinute(float speed) {
        return speed*60;
    }

    public double calcCalories(){
        if(calcMph(mSpeed)<=3.7){
            return ((calcWalkingOxygen()*mWeight)/200)*(mTime/60);
        }
        else {
            return (calcRunningOxygen()*mWeight/200)*(mTime/60);
        }
    }

    private double calcRunningOxygen() {
         return (0.2*mSpeed)+3.5;
    }

    private double calcMph(double speed) {
        return (speed/60)*2.2369363;
    }

    private double calcWalkingOxygen() {
        return (0.1 * mSpeed) + 3.5;
    }
}
