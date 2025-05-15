package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "developer")
public class Developer 
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

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("overview")
    @Column(columnDefinition = "TEXT")
    private String overview;

    @JsonProperty("establishedYear")
    private Long establishedYear;

    @JsonProperty("totalProjects")
    private Long projectDoneNo;

    @JsonProperty("city")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_name")
    private City city;

}
