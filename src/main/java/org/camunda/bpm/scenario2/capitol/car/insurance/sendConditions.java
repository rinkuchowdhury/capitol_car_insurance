package org.camunda.bpm.scenario2.capitol.car.insurance;

import connectjar.org.apache.http.client.HttpClient;
import connectjar.org.apache.http.client.methods.HttpPost;
import connectjar.org.apache.http.entity.StringEntity;
import connectjar.org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

public class sendConditions implements JavaDelegate {
    
    public void execute(DelegateExecution execution) throws Exception {
       /*{
        "messageName" : "Capitol_Conditions",
        "processInstanceId" : "0c36f88a-010a-11e8-a795-fa163e19f351",
        "processVariables" : {
          "insuranceCost" : {"value" : "51.000€", "type": "String"}
        }
      }*/
        JSONObject sendCondition = new JSONObject();
        
        sendCondition.put("messageName", "Capitol_Conditions");
        sendCondition.put("processInstanceId", execution.getVariable("bvisProcessInstanceID"));
        
        JSONObject condition = new JSONObject();
        
        int value= (int) execution.getVariable("insuranceCost");
        String type="Integer";
       
        JSONObject conditionValue = new JSONObject();
        conditionValue.put("value",value);
        conditionValue.put("type",type);
        condition.put("insuranceCost", conditionValue);
        sendCondition.put("processVariables", condition);
    
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://bvis.digicom.fg-bks.uni-koblenz.de:8080/engine-rest/message");
        StringEntity params =new StringEntity(sendCondition.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        connectjar.org.apache.http.HttpResponse response =  httpClient.execute(request);
    }  
}
