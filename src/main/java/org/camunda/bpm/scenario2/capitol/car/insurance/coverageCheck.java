package org.camunda.bpm.scenario2.capitol.car.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class coverageCheck implements JavaDelegate {
    
    public void execute(DelegateExecution execution) throws Exception {
        
        int limit = 2000;
        
        
        
        final String customerType = (String) execution.getVariable("customerType");
        
        if (customerType == "private")
        {
            limit = 500;
           
        }   
 
        final Boolean theft = (Boolean) execution.getVariable("theftCover");
        
        final Boolean full = (Boolean) execution.getVariable("fullCover");
        
        final Boolean half = (Boolean) execution.getVariable("halfCover");
        
        final Boolean thirdP = (Boolean) execution.getVariable("thirdPartyLiability");

        final String damageDetails = (String) execution.getVariable("damageReport");
       
        Boolean isStolen = null;
        Boolean isThirdParty = null ;
        Boolean isOther = null;
        int total_cost = 0;
        JSONArray damageArray = new JSONArray(damageDetails);
        for (int i = 0; i < damageArray.length(); i++) {
            
            JSONObject damageObject = damageArray.getJSONObject(i);
            int damageCost = damageObject.getInt("damageToCar");
            total_cost += damageCost;
            isStolen  = damageObject.getBoolean("stolen");
            isThirdParty  = damageObject.getBoolean("thirdParty");
            isOther  = damageObject.getBoolean("other");
           
        }
        
        if (isStolen)
        {
            if (theft)
            {
                if (half)
                {
                    if ((total_cost/2) < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost / 2) );
                    }
                    else if ((total_cost/2) > limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost / 2) );   
                    }
                }
                else if (full)
                {
                    if (total_cost < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost) );
                    }
                    else
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost) );  
                    }
                }   
            }
            else
            {
                execution.setVariable("covered","no");
            }
        }
        
         if (isThirdParty)
        {
            if (thirdP)
            {
                if (half)
                {
                    if ((total_cost/2) < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost / 2) );
                    }
                    else if ((total_cost/2) > limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost / 2) );
                    }
                }
                else if (full)
                {
                    if (total_cost < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost) );
                    }
                    else
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost) );
                    }
                }   
            }
            else
            {
                execution.setVariable("covered","no");
            }
        }
         
         if (isOther)
        {
             if (half)
                {
                    if ((total_cost/2) < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost / 2) );
                    }
                    else if ((total_cost/2) > limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost / 2) );
                    
                    }
                }
                else if (full)
                {
                    if (total_cost < limit)
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "no");
                        execution.setVariable("fee", (total_cost) );
                    }
                    else
                    {
                        execution.setVariable("covered","yes");
                        execution.setVariable("clerk", "yes");
                        execution.setVariable("fee", (total_cost) );
                    }
                } 
        }
    }    
}
