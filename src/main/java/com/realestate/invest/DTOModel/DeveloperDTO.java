package com.realestate.invest.DTOModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DeveloperDTO 
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("cityId")
    private Long cityId;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("name")
    private String developerName;

    @JsonProperty("logo")
    private String developerLogo;

    @JsonProperty("altLogo")
    private String altDeveloperLogo;

    @JsonProperty("legalName")
    private String developerLegalName;

    @JsonProperty("url")
    private String developerUrl;
    @Column(columnDefinition = "TEXT")

    @JsonProperty("address")
    private String developerAddress;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("isVerified")
    private Boolean isVerified;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("disclaimer")
    @Column(columnDefinition = "TEXT")
    private String disclaimer;

    @JsonProperty("about")
    @Column(columnDefinition = "TEXT")
    private String about;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("establishedYear")
    private Long establishedYear;

    @JsonProperty("totalProjects")
    private Long projectDoneNo;

}
