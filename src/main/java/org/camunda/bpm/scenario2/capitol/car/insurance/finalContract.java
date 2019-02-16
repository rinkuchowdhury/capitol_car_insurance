package org.camunda.bpm.scenario2.capitol.car.insurance;

import connectjar.org.apache.http.HttpEntity;
import connectjar.org.apache.http.client.HttpClient;
import connectjar.org.apache.http.client.methods.HttpPost;
import connectjar.org.apache.http.entity.StringEntity;
import connectjar.org.apache.http.impl.client.HttpClientBuilder;
import connectjar.org.apache.http.util.EntityUtils;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import static org.camunda.bpm.engine.impl.util.json.XMLTokener.entity;

public class finalContract implements JavaDelegate {
    
    //private final static Logger LOGGER = Logger.getLogger("Process Start");
   
    public void execute(DelegateExecution execution) throws Exception {    
      /* {
        "messageName" : "Capitol_Contract",
        "processInstanceId" : "abeb400e-0805-11e8-a795-fa163e19f351",
        "processVariables" : {
          "contract" : {"value" : "This is a text based contract prototype", "type": "String"}
        }
      }*/
        JSONObject finalContract = new JSONObject();
        
        finalContract.put("messageName", "Capitol_Contract");
        finalContract.put("processInstanceId", execution.getVariable("bvisProcessInstanceID"));
        
        JSONObject finalCost = new JSONObject();
        
        String value=String.valueOf(execution.getVariable("insuranceCost"));
        String type="String";
       
        JSONObject cost = new JSONObject();
        cost.put("value",value);
        cost.put("type",type);
        finalCost.put("contract", cost);
        finalContract.put("processVariables", finalCost);
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://bvis.digicom.fg-bks.uni-koblenz.de:8080/engine-rest/message");
        StringEntity params =new StringEntity(finalContract.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        connectjar.org.apache.http.HttpResponse response =  httpClient.execute(request); 
        //HttpEntity entity = response.getEntity();
        //String content = EntityUtils.toString(entity);

       /* LOGGER.info("Processing parmas" + params);
        LOGGER.info("Processing finalcontract" + finalContract.toString());
        LOGGER.info("Processing content" + content);
        LOGGER.info("Processing pid" + execution.getVariable("bvisProcessInstanceID"));
        LOGGER.info("Processing response" + response.toString());*/
    } 
}
