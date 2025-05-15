package com.realestate.invest.Model;

import java.util.Collections;
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
public class ReraInfo 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("createdOn")
    private Long createdOn;

    @JsonProperty("updatedOn")
    private Long updatedOn;

    @JsonProperty("phase")
    @Column(columnDefinition = "TEXT")
    private String phase;

    @JsonProperty("status")
    private String status;

    @JsonProperty("reraNumber")
    private String reraNumber;

    @JsonProperty("projectReraName")
    private String projectReraName;

    @JsonProperty("qrImages")
    private String qrImages;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    
    private transient Long userId;
    private transient String userName;
    private transient String userEmail;
    private transient String userPhone;
    private transient String userProfile;
    private transient Long projectId;
    private transient String projectName;
    private transient String projectUrl;
    private transient String projectLogo;
    
    public void setProjectConfiguration()
    {
        if(user != null)
        {
            userId = this.user.getId();
            userName = this.user.getFirstName()+" "+ this.getUser().getLastName();
            userEmail = this.user.getEmail();
            userPhone = this.user.getPhone();
            userProfile = this.user.getPhoto();
        }
        if(project != null)
        {
            projectId = this.project.getId();
            projectName = this.project.getProjectName();
            projectUrl = this.project.getProjectUrl();
            projectLogo = this.project.getAltProjectLogo();
        }
    }

    @JsonGetter("userId")
    public Long getUserId()
    {
        setProjectConfiguration();
        return userId;
    }

    @JsonGetter("userName")
    public String getUserName() 
    {
        setProjectConfiguration();
        return userName;
    }

    @JsonGetter("userEmail")
    public String getUserEmail() 
    {
        setProjectConfiguration();
        return userEmail;
    }

    @JsonGetter("userPhone")
    public String getUserPhone() 
    {
        setProjectConfiguration();
        return userPhone;
    }
    
    @JsonGetter("userProfile")
    public String getUserProfile() 
    {
        setProjectConfiguration();
        return userProfile;
    }

    @JsonGetter("projectId")
    public Long getProjectId() 
    {
        setProjectConfiguration();
        return projectId;
    }

    @JsonGetter("projectName")
    public String getProjectName() 
    {
        setProjectConfiguration();
        return projectName;
    }
    
    @JsonGetter("projectUrl")
    public String getprojecturl() 
    {
        setProjectConfiguration();
        return projectUrl;
    }
    
    @JsonGetter("projectLogo")
    public String getprojectLogo() 
    {
        setProjectConfiguration();
        return projectLogo;
    }

    
    public void setPhase(List<String> phase) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.phase = objectMapper.writeValueAsString(phase);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }
    
    public List<String> getPhase() 
    {
        if (this.phase == null) 
        {
            return Collections.emptyList(); 
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.phase, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return Collections.emptyList(); 
        }
    }

}
