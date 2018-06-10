package org.camunda.bpm.scenario2.capitol.car.insurance;

//import org.apache.http.HttpResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpResponse;

/**
 * @author Rinku on 01.02.18
 */

public class ProcessRequest implements JavaDelegate{
    
     public void execute(DelegateExecution delegateExecution) throws Exception {
          
         // parse reqest type 
        final String messageName = (String) delegateExecution.getVariable("messageName");
        delegateExecution.setVariable("messageName", messageName);
        
        // parse BVIS process instance ID
        final String bvisProcessInstanceID = (String) delegateExecution.getVariable("bvisProcessInstanceID");
        delegateExecution.setVariable("bvisProcessInstanceID", bvisProcessInstanceID);
        
        // parse customer details
        final String customerType = (String) delegateExecution.getVariable("customerType");
        delegateExecution.setVariable("customerType", customerType);
        final String customerFirstName = (String) delegateExecution.getVariable("customerFirstName");
        delegateExecution.setVariable("customerFirstName", customerFirstName);
        final String customerLastName = (String) delegateExecution.getVariable("customerLastName");
        delegateExecution.setVariable("customerLastName", customerLastName);
        
        // parse insurance details
        final Boolean fullCover = (Boolean) delegateExecution.getVariable("fullCover");
        delegateExecution.setVariable("fullCover", fullCover);
        final Boolean halfCover = (Boolean) delegateExecution.getVariable("halfCover");
        delegateExecution.setVariable("halfCover", halfCover);
        final Boolean theftCover = (Boolean) delegateExecution.getVariable("theftCover");
        delegateExecution.setVariable("theftCover", theftCover);
        final Boolean thirdPartyLiability = (Boolean) delegateExecution.getVariable("thirdPartyLiability");
        delegateExecution.setVariable("thirdPartyLiability", thirdPartyLiability);
   
        //parse car details
        final String startDate = (String) delegateExecution.getVariable("startDate");
        delegateExecution.setVariable("startDate", startDate);
        final String endDate = (String) delegateExecution.getVariable("endDate");
        delegateExecution.setVariable("endDate", endDate);
        
            // retrieves number of cars 
        final int numberOfDays = (int) delegateExecution.getVariable("numberOfDays");
        delegateExecution.setVariable("numberOfDays", numberOfDays);
      
            // retrieves number of cars 
        //final int numberOfCars = carDetails.length();
        //delegateExecution.setVariable("numberOfCars", numberOfCars);
        
        final String carDetails = (String) delegateExecution.getVariable("cars");
        JSONArray carArray = new JSONArray(carDetails);
        for (int i = 0; i < carArray.length(); i++) {
            
            JSONObject carObject = carArray.getJSONObject(i);
            int carID = carObject.getInt("id");
            int costPerDay = carObject.getInt("costPerDay");
            String registrationNumber = carObject.getString("registrationNumber");
            
             // to store data into the database {require database connection}
            JSONObject carData = new JSONObject();
            
            if(messageName.equals("contract")){
            carData.put("pid", bvisProcessInstanceID);
            carData.put("customerType", customerType);
            carData.put("car_id", carID);
            carData.put("registrationNumber", registrationNumber);
            carData.put("startDate", startDate);
            carData.put("endDate", endDate);
            carData.put("numberOfDays", numberOfDays);
            carData.put("costPerDay", costPerDay);
                if(fullCover | halfCover | theftCover | thirdPartyLiability ==true){
                    if(fullCover){
                        carData.put("fullCover", fullCover);
                        break;
                    }
                    else if(halfCover){
                        carData.put("halfCover", halfCover);
                    }
                    else if(theftCover){
                        carData.put("theftCover", theftCover);
                    }
                    else if(thirdPartyLiability){
                        carData.put("thirdPartyLiability", thirdPartyLiability);
                    }
                }
           /*HttpConnector http = Connectors.getConnector(HttpConnector.ID);
            HttpResponse response = http.createRequest()
                    .post()
                    .url("domain:port/api/insurance")
                    .payload(carData.toString())
                    .header("Content-Type", "application/json")
                    .execute();
            response.close();*/
            }
            else{
            // store damageType
            
            }
        }      
     }
}
