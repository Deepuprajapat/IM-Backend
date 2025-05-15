package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class ProjectImage 
{
    @JsonProperty("imageUrl")
    @Column(columnDefinition = "TEXT")
    private String imagePath;
    
    @JsonProperty("caption")
    @Column(columnDefinition = "TEXT")
    private String altName;

}
