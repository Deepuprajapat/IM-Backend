package com.realestate.invest.Utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import com.realestate.invest.Model.Leads;

@Component
public class LMSUtils 
{

    public static void sendLeadsToExternalAPI(Leads leads) throws Exception 
    {
        Map<String, String> leadData = new HashMap<>();
        String fullName = leads.getName() != null ? leads.getName() : "Client";

        leadData.put("name", fullName);
        leadData.put("email", leads.getEmail());
        leadData.put("phone", leads.getPhone());
        leadData.put("projectName", leads.getProjectName().get(0).toString());
        leadData.put("queryInfo", leads.getMessage());
        leadData.put("source", "Organic");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(leadData, headers);
        String url = "http://148.66.133.154:8181/new-leads/from/open-source";
        try 
        {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) 
            {
                System.out.println("Lead data submitted successfully. "+response.getBody());
            } 
            else 
            {
                System.out.println("Failed to submit lead data. Status: " + response.getStatusCode() + 
                                    ", Body: " + response.getBody());
            }
        } 
        catch (HttpClientErrorException e) 
        {
            String errorBody = e.getResponseBodyAsString();
            System.out.println("Error occurred while sending lead data. Status: " + e.getStatusCode() + 
                                ", Body: " + errorBody);
        } 
        catch (Exception e) 
        {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

}
