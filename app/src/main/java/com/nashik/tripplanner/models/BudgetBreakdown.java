package com.nashik.tripplanner.models;import java.io.Serializable;
public class BudgetBreakdown implements Serializable {
    private int totalEstimated,totalActual,accommodation,transport,food,activities;
    public BudgetBreakdown(){}
    public BudgetBreakdown(int t, int days, boolean freeActivities){
        totalEstimated = t;
        if (days <= 1) {
            accommodation = 0;
            transport = (int) (t * 0.40);
            food = (int) (t * 0.40);
            activities = freeActivities ? 0 : (int) (t * 0.20);
        } else {
            accommodation = (int) (t * 0.40);
            transport = (int) (t * 0.20);
            food = (int) (t * 0.25);
            activities = freeActivities ? 0 : (int) (t * 0.15);
        }
        
        // Re-calculate total based on parts
        totalEstimated = accommodation + transport + food + activities;
        totalActual = (int) (totalEstimated * 0.90);
    }
    public int getTotalEstimated(){return totalEstimated;}public void setTotalEstimated(int v){totalEstimated=v;}
    public int getTotalActual(){return totalActual;}public void setTotalActual(int v){totalActual=v;}
    public int getAccommodation(){return accommodation;}public void setAccommodation(int v){accommodation=v;}
    public int getTransport(){return transport;}public void setTransport(int v){transport=v;}
    public int getFood(){return food;}public void setFood(int v){food=v;}
    public int getActivities(){return activities;}public void setActivities(int v){activities=v;}
    public boolean isUnderBudget(){return totalActual<totalEstimated;}
    public int getSavings(){return totalEstimated-totalActual;}
}
