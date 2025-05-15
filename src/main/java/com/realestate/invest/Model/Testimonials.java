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

/**
 * @This class represents a Testimonials entity in the application.
 *
 * @Author Abhishek Srivastav
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Testimonials 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long testimonialsId;

    @JsonProperty("createdDate")
    private Long createdDate;

    @JsonProperty("updatedDate")
    private Long updatedDate;

    @JsonProperty("name")
    @Column(columnDefinition = "Text")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("description")
    @Column(columnDefinition = "Text")
    private String description;

    @JsonProperty("isApproved")
    private boolean isApproved;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private transient Long userId;
    private transient String userName;
    private transient String userEmail;
    private transient String userPhone;
    private transient String userProfile;

    public void setBlogsDataInJson()
    {
        if(user != null)
        {
            userId = this.user.getId();
            userName = this.user.getFirstName()+" "+ this.getUser().getLastName();
            userEmail = this.user.getEmail();
            userPhone = this.user.getPhone();
            userProfile = this.user.getPhoto();
        }
    }

    @JsonGetter("userId")
    public Long getUserId()
    {
        setBlogsDataInJson();
        return userId;
    }

    @JsonGetter("userName")
    public String getUserName() 
    {
        setBlogsDataInJson();
        return userName;
    }

    @JsonGetter("userEmail")
    public String getUserEmail() 
    {
        setBlogsDataInJson();
        return userEmail;
    }

    @JsonGetter("userPhone")
    public String getUserPhone() 
    {
        setBlogsDataInJson();
        return userPhone;
    }
    
    @JsonGetter("userProfile")
    public String getUserProfile() 
    {
        setBlogsDataInJson();
        return userProfile;
    }

    public void setRating(Double rating) 
    {
        if (rating < 1 || rating > 5) 
        {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

}