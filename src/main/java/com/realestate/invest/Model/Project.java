package com.realestate.invest.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Project 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("name")
    private String projectName;

    @JsonProperty("address")
    private String projectAddress;

    @JsonProperty("rera")
    private String projectRera;

    @JsonProperty("area")
    private String projectArea;

    @JsonProperty("units")
    private String projectUnits;

    @JsonProperty("launchDate")
    private String projectLaunchDate;

    @JsonProperty("possessionDate")
    private String projectPossessionDate;

    @JsonProperty("totalFloor")
    private String totalFloor;

    @JsonProperty("videos")
    private List<String> projectVideos;

    @JsonProperty("videoCount")
    private Long projectVideoCount;

    @JsonProperty("locationUrl")
    @Column(columnDefinition = "TEXT")
    private String projectLocationUrl;

    @JsonProperty("siteplanImg")
    private String siteplanImg;

    @JsonProperty("coverPhoto")
    @Column(columnDefinition = "TEXT")
    private String coverPhoto;

    @JsonProperty("altSitePlanImg")
    private String altSitePlanImg;

    @JsonProperty("reraLink")
    private String reraLink;

    @JsonProperty("availableUnit")
    private String availableUnit;

    @JsonProperty("isPriority")
    private boolean isPriority;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    @JsonProperty("isPremium")
    private boolean isPremium;

    @JsonProperty("isDeleted")
    private boolean isDeleted;
    @Column(columnDefinition = "TEXT")

    @JsonProperty("brochure")
    private String projectBrochure;

    @JsonProperty("about")
    @Column(columnDefinition = "TEXT")
    private String projectAbout;

    @JsonProperty("metaTitle")
    @Column(columnDefinition = "TEXT")
    private String metaTitle;

    @JsonProperty("metaDesciption")
    @Column(columnDefinition = "TEXT")
    private String metaDesciption;

    @JsonProperty("metaKeywords")
    @Column(columnDefinition = "TEXT")
    private String metaKeywords;

    @JsonProperty("url")
    @Column(columnDefinition = "TEXT")
    private String projectUrl;

    @JsonProperty("shortAddress")
    @Column(columnDefinition = "TEXT")
    private String shortAddress;

    @JsonProperty("schema")
    @Column(columnDefinition = "TEXT")
    private String projectSchema;
    @Column(columnDefinition = "TEXT")

    @JsonProperty("description")
    private String projectDescription;

    @JsonProperty("usps")
    @Column(columnDefinition = "TEXT")
    private String usp;

    @JsonProperty("configurations")
    @Column(columnDefinition = "TEXT")
    private String projectConfigurations;

    @JsonProperty("overviewPara")
    @Column(columnDefinition = "TEXT")
    private String overviewPara;

    @JsonProperty("floorPara")
    @Column(columnDefinition = "TEXT")
    private String floorPara;

    @JsonProperty("priceListPara")
    @Column(columnDefinition = "TEXT")
    private String priceListPara;

    @JsonProperty("paymentPara")
    @Column(columnDefinition = "TEXT")
    private String paymentPara;

    @JsonProperty("amenitiesPara")
    @Column(columnDefinition = "TEXT")
    private String amenitiesPara;

    @JsonProperty("videoPara")
    @Column(columnDefinition = "TEXT")
    private String videoPara;

    @JsonProperty("siteplanPara")
    @Column(columnDefinition = "TEXT")
    private String siteplanPara;

    @JsonProperty("whyPara")
    @Column(columnDefinition = "TEXT")
    private String whyPara;

    @JsonProperty("locationMap")
    @Column(columnDefinition = "TEXT")
    private String locationMap;

    @JsonProperty("locationPara")
    @Column(columnDefinition = "TEXT")
    private String locationPara;

    @JsonProperty("totalTowers")
    @Column(columnDefinition = "TEXT")
    private String totalTowers;

    @JsonProperty("projectLogo")
    @Column(columnDefinition = "TEXT")
    private String projectLogo;

    @JsonProperty("altProjectLogo")
    @Column(columnDefinition = "TEXT")
    private String altProjectLogo;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    
    @JsonProperty("configurationsType")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "property_configuration_type_id")
    private ProjectConfigurationType configurationsType;
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "developer_id")
    private Developer developer;
    
    @JsonProperty("locality")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "project_amenities",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "amenities_id"))
    private List<Amenities> projectAmenities = new ArrayList<>();

    @JsonIgnore
    @ElementCollection
    @JoinTable(name = "project_image", joinColumns = @JoinColumn(name = "project_id"))
    @MapKeyColumn(name = "image_url")
    @Column(name = "image_alt_name", columnDefinition = "TEXT")
    private Map<String, String> images = new HashMap<>();

    @JsonProperty("images")
    public List<ProjectImage> getProjectImages() 
    {
        List<ProjectImage> projectImages = new ArrayList<>();
        for (Map.Entry<String, String> entry : images.entrySet()) 
        {
            String url = entry.getKey();
            String imageAltName = entry.getValue();
            ProjectImage projectImage = new ProjectImage();
            projectImage.setImagePath(url);
            projectImage.setAltName(imageAltName);
            projectImages.add(projectImage);
        }
        return projectImages;
    }

    private transient Long userId;
    private transient String userName;
    private transient String userEmail;
    private transient String userPhone;
    private transient String userProfile;
    private transient Long developerId;
    private transient String developerName;
    private transient String developerUrl;
    private transient String developerLogo;
    private transient Long developerEstiblishedYear;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient List<Floorplan> floorplans;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient List<PaymentPlan> paymentPlans;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient List<Faq> faqs;
    

    public void setProjectDataInJson()
    {
        if(user != null)
        {
            userId = this.user.getId();
            userName = this.user.getFirstName()+" "+ this.getUser().getLastName();
            userEmail = this.user.getEmail();
            userPhone = this.user.getPhone();
            userProfile = this.user.getPhoto();
        }
        if(developer != null)
        {
            developerId = this.developer.getId();
            developerName = this.getDeveloper().getDeveloperName();
            developerUrl = this.getDeveloper().getDeveloperUrl();
            developerLogo = this.getDeveloper().getDeveloperLogo();
            developerEstiblishedYear = this.getDeveloper().getEstablishedYear();
        }
    }

    @JsonGetter("userId")
    public Long getUserId()
    {
        setProjectDataInJson();
        return userId;
    }

    @JsonGetter("userName")
    public String getUserName() 
    {
        setProjectDataInJson();
        return userName;
    }

    @JsonGetter("userEmail")
    public String getUserEmail() 
    {
        setProjectDataInJson();
        return userEmail;
    }

    @JsonGetter("userPhone")
    public String getUserPhone() 
    {
        setProjectDataInJson();
        return userPhone;
    }
    
    @JsonGetter("userProfile")
    public String getUserProfile() 
    {
        setProjectDataInJson();
        return userProfile;
    }

    @JsonGetter("developerId")
    public Long getDeveloperId()
    {
        setProjectDataInJson();
        return developerId;
    }

    @JsonGetter("developerName")
    public String getDeveloperName() 
    {
        setProjectDataInJson();
        return developerName;
    }

    @JsonGetter("developerUrl")
    public String getDeveloperUrl() 
    {
        setProjectDataInJson();
        return developerUrl;
    }

    @JsonGetter("developerLogo")
    public String getDeveloperLogo() 
    {
        setProjectDataInJson();
        return developerLogo;
    }
    
    @JsonGetter("developerEstiblishedYear")
    public Long getDeveloperEstablishedyear() 
    {
        setProjectDataInJson();
        return developerEstiblishedYear;
    }
    
    public void setProjectConfigurations(List<String> projectConfigurations) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.projectConfigurations = objectMapper.writeValueAsString(projectConfigurations);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }
    
    public List<String> getProjectConfigurations() 
    {
        if (this.projectConfigurations == null) 
        {
            return Collections.emptyList(); 
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.projectConfigurations, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return Collections.emptyList(); 
        }
    }

    public void setUsp(List<String> usp) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.usp = objectMapper.writeValueAsString(usp);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }

    public List<String> getUsp() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.usp, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return null;
        }
    }

    public void setProjectSchema(List<String> projectSchema) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.projectSchema = objectMapper.writeValueAsString(projectSchema);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }

    public List<String> getProjectSchema() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.projectSchema, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return null;
        }
    }
    
    public void setMetaKeywords(List<String> metaKeywords) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.metaKeywords = objectMapper.writeValueAsString(metaKeywords);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }
    
    public List<String> getMetaKeywords() 
    {
        if (this.metaKeywords == null) 
        {
            return Collections.emptyList(); 
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.metaKeywords, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return Collections.emptyList(); 
        }
    }


}