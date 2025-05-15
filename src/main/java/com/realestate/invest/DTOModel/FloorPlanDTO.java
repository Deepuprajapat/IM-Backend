package com.realestate.invest.DTOModel;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FloorPlanDTO 
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("projectConfigurationId")
    private Long propertyConfigurationId;

    @JsonProperty("projectId")
    private Long projectId;

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
    private Boolean isSoldOut;
    
}
