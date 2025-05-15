package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Amenities 
{   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;    

    @JsonIgnore
    private Long createdDate;

    @JsonIgnore
    private Long updatedDate;
    
    @JsonProperty("category")
    private String amenitiesCategory;

    @JsonProperty("name")
    private String amenitiesName;

    @JsonProperty("url")
    private String amenitiesUrl;

    public void setAmenitiesCategory(String amenitiesCategory) 
    {
        this.amenitiesCategory = amenitiesCategory != null ? amenitiesCategory.toUpperCase() : null;
    }
    
}
