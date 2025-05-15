package com.realestate.invest.Model;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class City 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonIgnore
    private Long createdDate;

    @JsonIgnore
    private Long updatedDate;
    
    @JsonProperty("stateName")
    private String stateName;

    @JsonProperty("name")
    private String cityName;
    
    @JsonProperty("url")
    private String cityUrl;

    @JsonProperty("phoneNumber")
    @Column(columnDefinition = "TEXT")
    private String phoneNumber;
    
    @JsonProperty("isActive")
    private boolean isActive;

    
    public void setPhoneNumber(List<String> phoneNumber) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.phoneNumber = objectMapper.writeValueAsString(phoneNumber);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }
    
    public List<String> getPhoneNumber() 
    {
        if (this.phoneNumber == null) 
        {
            return Collections.emptyList(); 
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.phoneNumber, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return Collections.emptyList(); 
        }
    }
    
}
