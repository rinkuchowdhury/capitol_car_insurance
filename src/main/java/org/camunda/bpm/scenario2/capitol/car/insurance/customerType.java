package org.camunda.bpm.scenario2.capitol.car.insurance;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class customerType implements JavaDelegate {
    
    
    public String customerType;
    public void execute(DelegateExecution execution) throws Exception {
        
        customerType = (String) execution.getVariable("customerType");
        
        if (customerType != "private" | customerType != "business" )
        {
            // wronge customer type
        }
 
    } 
}

