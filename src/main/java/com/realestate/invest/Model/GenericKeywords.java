package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a Generic Keywords entity in the application.
 *
 * @Author Abhishek Srivastav
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "genericSearch")
public class GenericKeywords 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("keywords")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String searchTerms;

    @JsonProperty("path")
    @Column(columnDefinition = "TEXT")
    private String path;

    @JsonProperty("url")
    @Column(columnDefinition = "TEXT")
    private String url;

}
