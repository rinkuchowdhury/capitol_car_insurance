package org.camunda.bpm.scenario2.capitol.car.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessRequest implements JavaDelegate{
    
     public String messageName;
     public void execute(DelegateExecution execution) throws Exception {
        
         messageName = (String) execution.getVariable("messageName");
        
        if (messageName != "contract" | messageName != "claim" )
        {
            // wronge customer type
        }
     } 
}
