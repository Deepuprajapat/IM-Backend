package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.invest.EnumAndJsons.PropertyType;
import jakarta.persistence.CascadeType;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Floorplan 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("imageUrl")
    private String imgUrl;

    @JsonProperty("price")
    private double price;

    @JsonProperty("title")
    private String title;

    @JsonProperty("isSoldOut")
    private boolean isSoldOut;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "configuration_id")
    private ProjectConfiguration configuration;

    @JsonIgnore
    @ManyToOne(cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private transient Long projectConfigurationId;
    private transient String projectConfigurationName;
    private transient Long propertyConfigTypeId;
    private transient String propertyConfigTypeName;
    private transient PropertyType propertyType;
    private transient Long userId;
    private transient String userName;
    private transient String userEmail;
    private transient String userPhone;
    private transient String userProfile;
    private transient Long projectId;
    private transient String projectName;
    private transient String projectUrl;
    

    public void setProjectConfiguration()
    {
        if(configuration != null)
        {
            projectConfigurationId = this.configuration.getId();
            projectConfigurationName = this.configuration.getProjectConfigurationName();
            propertyConfigTypeId = this.configuration.getConfigurationType().getId();
            propertyConfigTypeName = this.configuration.getConfigurationType().getConfigurationTypeName();
            propertyType = this.configuration.getConfigurationType().getPropertyType();
        }
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
        }
    }

    @JsonGetter("projectConfigurationId")
    private Long ProjectConfigurationId()
    {
        setProjectConfiguration();
        return projectConfigurationId;
    }

    @JsonGetter("projectConfigurationName")
    private String ProjectConfigurationName()
    {
        setProjectConfiguration();
        return projectConfigurationName;
    }

    @JsonGetter("propertyConfigTypeId")
    private Long PropertyConfigTypeId()
    {
        setProjectConfiguration();
        return propertyConfigTypeId;
    }

    @JsonGetter("propertyConfigTypeName")
    private String PropertyConfigTypeName()
    {
        setProjectConfiguration();
        return propertyConfigTypeName;
    }

    @JsonGetter("propertyType")
    private PropertyType PropertyType()
    {
        setProjectConfiguration();
        return propertyType;
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



}

