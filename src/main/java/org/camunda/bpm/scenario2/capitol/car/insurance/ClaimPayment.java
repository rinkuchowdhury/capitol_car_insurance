package org.camunda.bpm.scenario2.capitol.car.insurance;

import connectjar.org.apache.http.client.HttpClient;
import connectjar.org.apache.http.client.methods.HttpPost;
import connectjar.org.apache.http.entity.StringEntity;
import connectjar.org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpResponse;

public class ClaimPayment implements JavaDelegate {
    
    public void execute(DelegateExecution execution) throws Exception {   
     /*  {
      "messageName" : "Capitol_Damage_Payment",
      "processInstanceId" : "0c36f88a-010a-11e8-a795-fa163e19f351"
       }*/
     
        JSONObject payment = new JSONObject();

        payment.put("messageName", "Capitol_Damage_Payment");
        payment.put("processInstanceId", execution.getVariable("bvisProcessInstanceID"));

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://bvis.digicom.fg-bks.uni-koblenz.de:8080/engine-rest/message");
        StringEntity params =new StringEntity(payment.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        connectjar.org.apache.http.HttpResponse response =  httpClient.execute(request);

    }  
}
