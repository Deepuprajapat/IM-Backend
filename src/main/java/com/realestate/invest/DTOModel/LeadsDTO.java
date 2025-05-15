package com.realestate.invest.DTOModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class LeadsDTO 
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("propertyId")
    private Long propertyId;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("name")
    @Column(columnDefinition = "TEXT")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    @Column(columnDefinition = "TEXT")
    private String email;

    @JsonProperty("projectName")
    @Column(columnDefinition = "TEXT")
    private String projectName;

    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;

    @JsonProperty("source")
    @Column(columnDefinition = "TEXT")
    private String source;

    @JsonProperty("userType")
    @Column(columnDefinition = "TEXT")
    private String userType;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("frequency")
    private Long frequency;
    
    
}
