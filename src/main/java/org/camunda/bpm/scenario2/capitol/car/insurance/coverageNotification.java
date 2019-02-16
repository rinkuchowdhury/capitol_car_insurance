package org.camunda.bpm.scenario2.capitol.car.insurance;

import connectjar.org.apache.http.client.HttpClient;
import connectjar.org.apache.http.client.methods.HttpPost;
import connectjar.org.apache.http.entity.StringEntity;
import connectjar.org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class coverageNotification implements JavaDelegate {
    
    public void execute(DelegateExecution execution) throws Exception {
        
        int value=0;
        String type="Integer";
            
        if (execution.getVariable("covered") == "no")
        {
            value=0;         
        }
        
        else if (execution.getVariable("covered") == "yes" && execution.getVariable("clerk") == "no")
        {
            value = (int) execution.getVariable("fee");  
        }
        
        else if (execution.getVariable("covered") == "yes" && execution.getVariable("clerk") == "yes")
        {
            if (execution.getVariable("clerkAnswer") == "no")
            {
                value=0;    
            }
            
            else
            {
                value=(int) execution.getVariable("fee");    
            }   
        }
        
        JSONObject notification = new JSONObject();

        notification.put("messageName", "Capitol_Coverage_Report");
        notification.put("processInstanceId", execution.getVariable("bvisProcessInstanceID"));

        JSONObject coverageCost = new JSONObject();

        JSONObject cost = new JSONObject();
        cost.put("value",value);
        cost.put("type",type);
        coverageCost.put("totalCoverage", cost);
        notification.put("processVariables", coverageCost);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://bvis.digicom.fg-bks.uni-koblenz.de:8080/engine-rest/message");
        StringEntity params =new StringEntity(notification.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        connectjar.org.apache.http.HttpResponse response =  httpClient.execute(request);        
    }  
}

