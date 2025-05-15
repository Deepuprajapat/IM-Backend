package com.realestate.invest.DTOModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.ProjectImage;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDTO 
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("configurationsTypeId")
    private Long configurationsTypeId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("developerId")
    private Long developerId;

    @JsonProperty("localityId")
    private Long localityId;

    @JsonProperty("name")
    private String projectName;

    @JsonProperty("address")
    private String projectAddress;

    @JsonProperty("rera")
    private String projectRera;

    @JsonProperty("area")
    private String projectArea;

    @JsonProperty("units")
    private String projectUnits;

    @JsonProperty("launchDate")
    private String projectLaunchDate;

    @JsonProperty("possessionDate")
    private String projectPossessionDate;

    @JsonProperty("totalFloor")
    private String totalFloor;

    @JsonProperty("videos")
    private List<String> projectVideos;

    @JsonProperty("videoCount")
    private Long projectVideoCount;

    @JsonProperty("locationUrl")
    private String projectLocationUrl;

    @JsonProperty("siteplanImg")
    private String siteplanImg;

    @JsonProperty("altSitePlanImg")
    private String altSitePlanImg;

    @JsonProperty("reraLink")
    private String reraLink;

    @JsonProperty("availableUnit")
    private String availableUnit;

    @JsonProperty("isPriority")
    private boolean isPriority;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    @JsonProperty("isPremium")
    private boolean isPremium;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @JsonProperty("brochure")
    @Column(columnDefinition = "TEXT")
    private String projectBrochure;

    @JsonProperty("about")
    @Column(columnDefinition = "TEXT")
    private String projectAbout;

    @JsonProperty("metaTitle")
    @Column(columnDefinition = "TEXT")
    private String metaTitle;

    @JsonProperty("metaDesciption")
    @Column(columnDefinition = "TEXT")
    private String metaDesciption;

    @JsonProperty("metaKeywords")
    @Column(columnDefinition = "TEXT")
    private List<String> metaKeywords;

    @JsonProperty("url")
    @Column(columnDefinition = "TEXT")
    private String projectUrl;

    @JsonProperty("shortAddress")
    @Column(columnDefinition = "TEXT")
    private String shortAddress;

    @JsonProperty("schema")
    @Column(columnDefinition = "TEXT")
    private List<String> projectSchema;

    @JsonProperty("description")
    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    @JsonProperty("usps")
    @Column(columnDefinition = "TEXT")
    private List<String> usps;

    @JsonProperty("configurations")
    @Column(columnDefinition = "TEXT")
    private List<String> projectConfigurations;
    
    @JsonProperty("projectAmenities")
    private List<Long> projectAmenities;

    @JsonProperty("overviewPara")
    @Column(columnDefinition = "TEXT")
    private String overviewPara;

    @JsonProperty("floorPara")
    @Column(columnDefinition = "TEXT")
    private String floorPara;

    @JsonProperty("priceListPara")
    @Column(columnDefinition = "TEXT")
    private String priceListPara;

    @JsonProperty("paymentPara")
    @Column(columnDefinition = "TEXT")
    private String paymentPara;

    @JsonProperty("amenitiesPara")
    @Column(columnDefinition = "TEXT")
    private String amenitiesPara;

    @JsonProperty("videoPara")
    @Column(columnDefinition = "TEXT")
    private String videoPara;

    @JsonProperty("siteplanPara")
    @Column(columnDefinition = "TEXT")
    private String siteplanPara;

    @JsonProperty("whyPara")
    @Column(columnDefinition = "TEXT")
    private String whyPara;

    @JsonProperty("locationMap")
    @Column(columnDefinition = "TEXT")
    private String locationMap;

    @JsonProperty("locationPara")
    @Column(columnDefinition = "TEXT")
    private String locationPara;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @JsonProperty("images")
    public List<ProjectImage> projectImages; 

    @JsonProperty("paymentPlans")    
    public List<PaymentPlan> paymentPlans;

    @JsonProperty("faqs")
    public List<Faq> faqs;

    @JsonProperty("totalTowers")
    @Column(columnDefinition = "TEXT")
    private String totalTowers;

    @JsonProperty("projectLogo")
    @Column(columnDefinition = "TEXT")
    private String projectLogo;

    @JsonProperty("altProjectLogo")
    @Column(columnDefinition = "TEXT")
    private String altProjectLogo;

    @JsonProperty("coverPhoto")
    private String coverPhoto;

}
