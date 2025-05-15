package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Faq 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("question")
    @Column(columnDefinition = "TEXT")
    private String question;

    @JsonProperty("answer")
    @Column(columnDefinition = "TEXT")
    private String answer;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    private transient Long projectId;
    private transient String projectName;
    
    public void setProjectDataInJson()
    {
        if(project != null)
        {
            projectId = project.getId();
            projectName = project.getProjectName();
        }
    }
    

    @JsonGetter("projectId")
    public Long getProjectId()
    {
        setProjectDataInJson();
        return projectId;
    }

    @JsonGetter("projectName")
    public String getProjectName() 
    {
        setProjectDataInJson();
        return projectName;
    }
}
