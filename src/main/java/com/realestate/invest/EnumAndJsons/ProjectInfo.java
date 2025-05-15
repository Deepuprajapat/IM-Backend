package com.realestate.invest.EnumAndJsons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProjectInfo 
{
    @JsonProperty("id")
    private Long Id;

    @JsonProperty("name")
    private String projectName;

    @JsonProperty("url")
    private String projectUrl;

    @JsonProperty("projectLogo")
    private String projectLogo;
    
}
