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
import com.realestate.invest.EnumAndJsons.Facing;
import com.realestate.invest.EnumAndJsons.FurnishingType;
import com.realestate.invest.EnumAndJsons.ListingType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class Property 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    private Long Id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("updatedById")
    private Long updatedById;

    @JsonProperty("price")
    private double price;	

    @JsonProperty("propertyName")
    @Column(columnDefinition = "TEXT")
    private String propertyName;

    @JsonProperty("propertyAddress")
    @Column(columnDefinition = "TEXT")
    private String propertyAddress;

    @JsonProperty("about")
    @Column(columnDefinition = "TEXT")
    private String about;

    @JsonProperty("metaTitle")
    @Column(columnDefinition = "TEXT")
    private String metaTitle;	

    @JsonProperty("metaDescription")
    @Column(columnDefinition = "TEXT")
    private String metaDescription;

    @JsonProperty("url")	
    @Column(columnDefinition = "TEXT")
    private String propertyUrl;

    @JsonProperty("metaKeywords")
    @Column(columnDefinition = "TEXT")
    private String metaKeywords;

    @JsonProperty("builtupArea")
    @Column(columnDefinition = "TEXT")
    private String builtupArea;	

    @JsonProperty("size")
    @Column(columnDefinition = "TEXT")
    private String size;	

    @JsonProperty("ageOfProperty")
    @Column(columnDefinition = "TEXT")
    private String ageOfProperty;

    @JsonProperty("possessionStatus")
    @Column(columnDefinition = "TEXT")
    private String possessionStatus;

    @JsonProperty("productSchema")
    @Column(columnDefinition = "TEXT")	
    private String productSchema;

    @JsonProperty("overviewPara")
    @Column(columnDefinition = "TEXT")
    private String overviewPara;

    @JsonProperty("floors")
    @Column(columnDefinition = "TEXT")
    private String floors;	

    @JsonProperty("coveredParking")
    @Column(columnDefinition = "TEXT")	
    private String coveredParking;

    @JsonProperty("openParking")	
    @Column(columnDefinition = "TEXT")
    private String openParking;

    @JsonProperty("balcony")
    @Column(columnDefinition = "TEXT")
    private String balcony;	

    @JsonProperty("bathrooms")
    @Column(columnDefinition = "TEXT")
    private String bathrooms;	

    @JsonProperty("bedrooms")	
    @Column(columnDefinition = "TEXT")
    private String bedrooms;	

    @JsonProperty("possessionDate")	
    @Column(columnDefinition = "TEXT")
    private String possessionDate;

    @JsonProperty("rera")	
    @Column(columnDefinition = "TEXT")	
    private String rera;	

    @JsonProperty("usp")
    @Column(columnDefinition = "TEXT")
    private String usp;		

    @JsonProperty("latlong")
    @Column(columnDefinition = "TEXT")
    private String latlong;		

    @JsonProperty("amenitiesPara")
    @Column(columnDefinition = "TEXT")
    private String amenitiesPara;

    @JsonProperty("locationPara")	
    @Column(columnDefinition = "TEXT")	
    private String locationPara;

    @JsonProperty("locationMap")	
    @Column(columnDefinition = "TEXT")	
    private String locaionMap;	

    @JsonProperty("floorPara")	
    @Column(columnDefinition = "TEXT")
    private String floorPara;	

    @JsonProperty("logoImage")
    @Column(columnDefinition = "TEXT")
    private String logoImage;

    @JsonProperty("coverPhoto")
    @Column(columnDefinition = "TEXT")
    private String coverPhoto;

    @JsonProperty("floorImage2D")
    @Column(columnDefinition = "TEXT")
    private String floorImage2D;

    @JsonProperty("floorImage3D")	
    @Column(columnDefinition = "TEXT")
    private String floorImage3D;

    @JsonProperty("videoPara")	
    @Column(columnDefinition = "TEXT")
    private String videoPara;	

    @JsonProperty("propertyVideo")	
    @Column(columnDefinition = "TEXT")
    private String propertyVideo;

    @JsonProperty("images")	
    @Column(columnDefinition = "TEXT")		
    private String images;		

    @JsonProperty("locationAdvantage")
    @Column(columnDefinition = "TEXT")
    private String locationAdvantage;

    @JsonProperty("highlights")
    @Column(columnDefinition = "TEXT")
    private String highlights;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    @Enumerated(EnumType.STRING)
    private Facing facing;
    
    @Enumerated(EnumType.STRING)	
    private FurnishingType furnishingType;
    
    @Enumerated(EnumType.STRING)
    private ListingType	listingType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "confiuration_id")
    private ProjectConfiguration configuration;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "locality_id")
    private Locality locality;	
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "developer_id")
    private Developer developer;	
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;	

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "property_amenities",
    joinColumns = @JoinColumn(name = "property_id"),
    inverseJoinColumns = @JoinColumn(name = "amenities_id"))
    private List<Amenities> propertyAmenities = new ArrayList<>();

    private transient Long userId;
    private transient String userName;
    private transient String userEmail;
    private transient String userPhone;
    private transient String userProfile;
    private transient Long projectId;
    private transient String projectName;
    private transient String projectUrl;
    private transient String projectLogo;
    private transient Long developerId;
    private transient String developerName;
    private transient String developerUrl;
    private transient String developerLogo;
    private transient Long developerEstiblishedYear;
    

    public void setProjectDataInJson() 
    {
        if (user != null) 
        {
            userId = this.user.getId();
            userName = (this.user.getFirstName() != null ? this.user.getFirstName() : "") + " " + 
            (this.user.getLastName() != null ? this.user.getLastName() : "");
            userEmail = (this.user.getEmail() != null) ? this.user.getEmail() : "";
            userPhone = (this.user.getPhone() != null) ? this.user.getPhone() : "";
            userProfile = (this.user.getPhoto() != null) ? this.user.getPhoto() : "";
        }
        if (developer != null) 
        {
            developerId = this.developer.getId();
            developerName = (this.developer.getDeveloperName() != null) ? this.developer.getDeveloperName() : "";
            developerUrl = (this.developer.getDeveloperUrl() != null) ? this.developer.getDeveloperUrl() : "";
            developerLogo = (this.developer.getDeveloperLogo() != null) ? this.developer.getDeveloperLogo() : "";
            developerEstiblishedYear = (this.developer.getEstablishedYear() != null) ? this.developer.getEstablishedYear() : 0;
        }
        if (project != null) 
        {
            projectId =  this.project.getId();
            projectName = (this.project.getProjectName() != null) ? this.project.getProjectName() : "";
            projectUrl = (this.project.getProjectUrl() != null) ? this.project.getProjectUrl() : "";
            projectLogo = (this.project.getProjectLogo() != null) ? this.project.getProjectLogo() : "";
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
    
    @JsonGetter("projectId")
    public Long getProjectId() 
    {
        setProjectDataInJson();
        return projectId;
    }
    
    @JsonGetter("projectUrl")
    public String getProjectUrl() 
    {
        setProjectDataInJson();
        return projectUrl;
    }
    
    @JsonGetter("projectName")
    public String getProjectName() 
    {
        setProjectDataInJson();
        return projectName;
    }
    
    @JsonGetter("projectLogo")
    public String getProjectLogo() 
    {
        setProjectDataInJson();
        return projectLogo;
    }


    public void setUsp(List<String> usp) 
    {
        if (usp != null) 
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
        else 
        {
            this.usp = null;
        }
    }
    
    public List<String> getUsp() 
    {
        if (this.usp != null) 
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
        return new ArrayList<>(); 
    }
    
    public void setVideoPara(List<String> videoPara) 
    {
        if (videoPara != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.videoPara = objectMapper.writeValueAsString(videoPara);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.videoPara = null;
        }
    }
    
    public List<String> getVideoPara() 
    {
        if (this.videoPara != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.videoPara, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setPropertyVideo(List<String> propertyVideo) 
    {
        if (propertyVideo != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.propertyVideo = objectMapper.writeValueAsString(propertyVideo);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.propertyVideo = null;
        }
    }
    
    public List<String> getPropertyVideo() 
    {
        if (this.propertyVideo != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.propertyVideo, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setImages(List<String> images) 
    {
        if (images != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.images = objectMapper.writeValueAsString(images);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.images = null;
        }
    }
    
    public List<String> getImages() 
    {
        if (this.images != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.images, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setLocationAdvantage(List<String> locationAdvantage) 
    {
        if (locationAdvantage != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.locationAdvantage = objectMapper.writeValueAsString(locationAdvantage);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.locationAdvantage = null;
        }
    }
    
    public List<String> getLocationAdvantage() 
    {
        if (this.locationAdvantage != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.locationAdvantage, new TypeReference<List<String>>() {});
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    

    public void setHighlights(List<String> highlights) 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try 
        {
            this.highlights = objectMapper.writeValueAsString(highlights);
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
        }
    }

    public List<String> getHighlights() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try 
        {
            return objectMapper.readValue(this.highlights, new TypeReference<List<String>>() {});
        } 
        catch (JsonProcessingException e) 
        {
            e.getMessage();
            return null;
        }
    }


}
