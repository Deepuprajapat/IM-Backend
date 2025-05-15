package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
public class ProjectConfiguration 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("configurationName")
    private String projectConfigurationName;

    @JsonProperty("configurationType")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "configuration_type_id")
    private ProjectConfigurationType configurationType;
    
}
