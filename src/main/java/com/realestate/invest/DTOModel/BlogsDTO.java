package com.realestate.invest.DTOModel;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class BlogsDTO 
{
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("createdDate")
    private Long createdDate;
    
    @JsonProperty("updatedDate")
    private Long updatedDate;
    
    @JsonProperty("headings")
    @Column(columnDefinition = "TEXT")
    private String headings;
    
    @JsonProperty("subHeadings")
    @Column(columnDefinition = "TEXT")
    private String subHeadings;
    
    @JsonProperty("description")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @JsonProperty("images")
    @Column(columnDefinition = "TEXT")
    private List<String> images;
    
    @JsonProperty("blogUrl")
    @Column(columnDefinition = "TEXT")
    private String blogUrl;
    
    @JsonProperty("alt")
    @Column(columnDefinition = "TEXT")
    private String alt;
    
    @JsonProperty("canonical")
    @Column(columnDefinition = "TEXT")
    private String canonical;
    
    @JsonProperty("schema")
    @Column(columnDefinition = "TEXT")
    private List<String> blogSchema;
    
    @JsonProperty("metaTitle")
    @Column(columnDefinition = "TEXT")
    private String metaTitle;
    
    @JsonProperty("metaKeywords")
    @Column(columnDefinition = "TEXT")
    private List<String> metaKeywords;
    
    @JsonProperty("isPriority")
    private boolean isPriority;


}
