package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Static Site Data entity in the application.
 *
 * @Author Abhishek Srivastav
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaticSiteData 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long dataId;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String privacyPolicy;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String termOfServices;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String refundPolicy;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String aboutUs;

    private long createdOn;
    private long updatedOn;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isExclusive() 
    {
        return (privacyPolicy != null ? 1 : 0) + (termOfServices != null ? 1 : 0) + (refundPolicy != null ? 1 : 0) + (aboutUs != null ? 1 : 0)  <= 1;
    }


}
