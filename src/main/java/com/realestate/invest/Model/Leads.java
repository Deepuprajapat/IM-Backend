package com.realestate.invest.Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Leads 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    @Column(columnDefinition = "TEXT")
    private String updatedDate;

    @JsonProperty("name")
    @Column(columnDefinition = "TEXT")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    @Column(columnDefinition = "TEXT")
    private String email;

    @JsonProperty("projectName")
    @Column(columnDefinition = "TEXT")
    private String projectName;

    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;

    @JsonProperty("source")
    @Column(columnDefinition = "TEXT")
    private String source;

    @JsonProperty("userType")
    @Column(columnDefinition = "TEXT")
    private String userType;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("frequency")
    private Long frequency;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "property_id")
    private Property property;

    private transient Long propertyId;
    private transient String propertyName;
    private transient String propertyUrl;

    public void setTransientFields() 
    {
        if (this.property != null) 
        {
            this.propertyId = this.property.getId();
            this.propertyName = this.property.getPropertyName();
            this.propertyUrl = this.property.getPropertyUrl();
        }
    }
    
    @JsonGetter("propertyId")
    public Long setPropertyId(Long propertyId) 
    {
        setTransientFields();
        return propertyId;
    }

    @JsonGetter("propertyName")
    public String setPropertyName(String propertyName) 
    {
        setTransientFields();
        return propertyName;
    }

    @JsonGetter("propertyUrl")
    public String setPropertyUrl(String propertyUrl) 
    {
        setTransientFields();
        return propertyUrl;
    }
    
    public void setProjectName(List<String> projectName) 
    {
        if (projectName != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.projectName = objectMapper.writeValueAsString(projectName);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.projectName = null;
        }
    }
    
    public List<String> getProjectName() 
    {
        if (this.projectName != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.projectName, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setSource(List<String> source) 
    {
        if (source != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.source = objectMapper.writeValueAsString(source);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.source = null;
        }
    }
    
    public List<String> getSource() 
    {
        if (this.source != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.source, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setUpdatedDate(List<String> updatedDate) 
    {
        if (updatedDate != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.updatedDate = objectMapper.writeValueAsString(updatedDate);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.updatedDate = null;
        }
    }
    
    public List<String> getUpdatedDate() 
    {
        if (this.updatedDate != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.updatedDate, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }




}
