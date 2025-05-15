package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.invest.EnumAndJsons.PropertyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ProjectConfigurationType 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("configurationTypeName")
    private String configurationTypeName;

    @JsonProperty("propertyType")
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    
}
