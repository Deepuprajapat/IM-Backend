package com.realestate.invest.Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

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
    private String images;

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
    private String blogSchema;

    @JsonProperty("metaTitle")
    @Column(columnDefinition = "TEXT")
    private String metaTitle;

    @JsonProperty("metaKeywords")
    @Column(columnDefinition = "TEXT")
    private String metaKeywords;

    @JsonProperty("isPriority")
    private boolean isPriority;
    

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
        if (user != null) 
        {
            userId = this.user.getId();
            userName = this.user.getFirstName() + " " + this.getUser().getLastName();
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

    public void setImages(List<String> images) 
    {
        if (images != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.images = objectMapper.writeValueAsString(images);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.images = null;
        }
    }

    public List<String> getImages() 
    {
        if (this.images != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.images, new TypeReference<List<String>>() {
                });
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }

    public void setBlogSchema(List<String> blogSchema) 
    {
        if (blogSchema != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.blogSchema = objectMapper.writeValueAsString(blogSchema);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } else {
            this.blogSchema = null;
        }
    }

    public List<String> getBlogSchema() 
    {
        if (this.blogSchema != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.blogSchema, new TypeReference<List<String>>() 
                {
                });
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }
    
    public void setMetaKeywords(List<String> metaKeywords) 
    {
        if (metaKeywords != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try 
            {
                this.metaKeywords = objectMapper.writeValueAsString(metaKeywords);
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
            }
        } 
        else 
        {
            this.metaKeywords = null;
        }
    }

    public List<String> getMetakeywords() 
    {
        if (this.metaKeywords != null) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try 
            {
                return objectMapper.readValue(this.metaKeywords, new TypeReference<List<String>>() 
                {
                });
            } 
            catch (JsonProcessingException e) 
            {
                e.getMessage();
                return null;
            }
        }
        return new ArrayList<>();
    }

}
