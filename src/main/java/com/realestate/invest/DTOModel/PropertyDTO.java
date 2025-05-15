package com.realestate.invest.DTOModel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.invest.EnumAndJsons.Facing;
import com.realestate.invest.EnumAndJsons.FurnishingType;
import com.realestate.invest.EnumAndJsons.ListingType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PropertyDTO 
{
    @JsonProperty("Id")
    private Long Id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("configurationId")
    private Long configurationId;

    @JsonProperty("projectId")
    private Long projectId;
    
    @JsonProperty("developerrId")
    private Long developerId;	
    
    @JsonProperty("localityId")
    private Long localityId;

    @JsonProperty("price")
    private double price;	

    @JsonProperty("propertyName")
    private String propertyName;

    @JsonProperty("propertyAddress")
    private String propertyAddress;

    @JsonProperty("about")
    private String about;

    @JsonProperty("metaTitle")
    private String metaTitle;	

    @JsonProperty("metaDescription")
    private String metaDescription;

    @JsonProperty("url")	
    private String propertyUrl;

    @JsonProperty("metaKeywords")
    private String metaKeywords;

    @JsonProperty("builtupArea")
    private String builtupArea;	

    @JsonProperty("size")
    private String size;	

    @JsonProperty("ageOfProperty")
    private String ageOfProperty;

    @JsonProperty("possessionStatus")
    private String possessionStatus;

    @JsonProperty("productSchema")
    @Column(columnDefinition = "TEXT")	
    private String productSchema;

    @JsonProperty("overviewPara")
    private String overviewPara;

    @JsonProperty("floors")
    private String floors;	

    @JsonProperty("coveredParking")
    @Column(columnDefinition = "TEXT")	
    private String coveredParking;

    @JsonProperty("openParking")	
    private String openParking;

    @JsonProperty("balcony")
    private String balcony;	

    @JsonProperty("bathrooms")
    private String bathrooms;	

    @JsonProperty("bedrooms")	
    private String bedrooms;	

    @JsonProperty("possessionDate")	
    private String possessionDate;

    @JsonProperty("rera")	
    @Column(columnDefinition = "TEXT")	
    private String rera;		

    @JsonProperty("latlong")
    private String latlong;		

    @JsonProperty("amenitiesPara")
    private String amenitiesPara;

    @JsonProperty("locationPara")	
    @Column(columnDefinition = "TEXT")	
    private String locationPara;

    @JsonProperty("locaionMap")	
    @Column(columnDefinition = "TEXT")	
    private String locaionMap;	

    @JsonProperty("floorPara")	
    private String floorPara;	

    @JsonProperty("logoImage")
    private String logoImage;

    @JsonProperty("coverPhoto")
    private String coverPhoto;

    @JsonProperty("floorImage2D")
    private String floorImage2D;

    @JsonProperty("floorImage3D")	
    private String floorImage3D;

    @JsonProperty("videoPara")	
    private List<String> videoPara;	

    @JsonProperty("usp")
    private List<String> usp;	

    @JsonProperty("propertyVideo")	
    private List<String> propertyVideo;

    @JsonProperty("images")	
    @Column(columnDefinition = "TEXT")		
    private List<String> images;		

    @JsonProperty("locationAdvantage")
    private List<String> locationAdvantage;

    @JsonProperty("highlights")
    private List<String> highlights;

    @JsonProperty("isDeleted")
    private Boolean isDeleted;

    @JsonProperty("isFeatured")
    private Boolean isFeatured;

    @Enumerated(EnumType.STRING)
    private Facing facing;
    
    @Enumerated(EnumType.STRING)	
    private FurnishingType furnishingType;
    
    @Enumerated(EnumType.STRING)
    private ListingType	listingType;

    @JsonProperty("propertyAmenities")
    private List<Long> propertyAmenities;

    
}
