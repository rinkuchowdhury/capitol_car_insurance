package org.camunda.bpm.scenario2.capitol.car.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class addBenifits implements JavaDelegate {
    
    public void execute(DelegateExecution execution) throws Exception {
        
        int percentage = 0;
       
        final String customerType = (String) execution.getVariable("customerType");
        
        final Boolean theft = (Boolean) execution.getVariable("theftCover");
        
        final Boolean full = (Boolean) execution.getVariable("fullCover");
        
        final Boolean half = (Boolean) execution.getVariable("halfCover");
        
        final Boolean thirdP = (Boolean) execution.getVariable("thirdPartyLiability");
        
        final String carDetails = (String) execution.getVariable("cars");
        
        int total_cost = 0;
        JSONArray carArray = new JSONArray(carDetails);
        for (int i = 0; i < carArray.length(); i++) {
            
            JSONObject carObject = carArray.getJSONObject(i);
            int costPerDay = carObject.getInt("costPerDay");
            total_cost += costPerDay;               
        }
        
        if (customerType.equals("private"))
        {   
            
            if (half)
            {
                if(theft)
                {
                    percentage = 25;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));
                }
            else if (thirdP)
                {
                    percentage = 30;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));
                }
            else 
                {
                    percentage = 15;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));
                }         
            }
            
            else if (full)
            {
             if(theft)
                {
                    percentage = 30;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100)); 
                }
                else if (thirdP)
                {
                    percentage = 35;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100));
                }
                else 
                {
                    percentage = 20;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100));
                }
            }
        }
        
        else if (customerType.equals("business"))
            
        { 

            if (half)
            {
                if(theft)
                {
                    percentage = 20;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));  
                } 
                else if (thirdP)
                {
                    percentage = 25;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));
                }
                else 
                {
                    percentage = 10;
                    execution.setVariable("insuranceCost", Math.round(((total_cost / 2) * percentage) / 100));
                }  
            }
            
            else if (full)
            {
             if(theft)
                {
                    percentage = 15;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100));
                }
            else if (thirdP)
                {
                    percentage = 20;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100));
                }
            else 
                {
                    percentage = 5;
                    execution.setVariable("insuranceCost", Math.round(((total_cost) * percentage) / 100));
                }
            }
        }
    }
}

