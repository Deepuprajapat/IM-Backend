package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.PropertyDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Amenities;
import com.realestate.invest.Model.Locality;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ProjectConfiguration;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.AmenitiesRepository;
import com.realestate.invest.Repository.LocalityRepository;
import com.realestate.invest.Repository.ProjectConfigurationRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.PropertyRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.PropertyService;
import com.realestate.invest.Utils.OTPGenerator;
import com.realestate.invest.Utils.PropertyUtility;

@Service
public class PropertyServiceImpl implements PropertyService
{

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectConfigurationRepository configurationRepository;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired 
    private ProjectRepository projectRepository;

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Override
    public Property saveNewProperty(PropertyDTO propertyDTO) throws Exception 
    { 
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Please login again");

        Property property = new Property();
        property.setCreatedDate(new Date().getTime());

        if(propertyDTO.getConfigurationId() != null)
        {
            ProjectConfiguration propertyConfiguration = this.configurationRepository.findById(propertyDTO.getConfigurationId())
            .orElseThrow(() -> new Exception("Configuation Not Found"));
            property.setConfiguration(propertyConfiguration);
        }

        if(propertyDTO.getLocalityId() != null)
        {
            Locality locality = this.localityRepository.findById(propertyDTO.getLocalityId())
            .orElseThrow(() -> new Exception("Locality Not Found"));
            property.setLocality(locality);
        }

        List<Amenities> validAmenities = propertyDTO.getPropertyAmenities().stream()
        .map(amenitiesRepository::findById) 
        .filter(Optional::isPresent) 
        .map(Optional::get) 
        .collect(Collectors.toList());

        if (!validAmenities.isEmpty()) 
        {
            property.setPropertyAmenities(new ArrayList<>(validAmenities));
        }

        property.setPropertyName(propertyDTO.getPropertyName());

        String lowerCase = property.getPropertyName().toLowerCase();
        String PropertyUrl = lowerCase.replaceAll("\\s", "-");
        Property existingUrl = this.propertyRepository.findByPropertyUrl(PropertyUrl);
        String newPropertyUrl = (existingUrl != null) ? (PropertyUrl + OTPGenerator.generateUUID().substring(13)) : PropertyUrl;
        property.setPropertyUrl(newPropertyUrl);
        property.setAbout(propertyDTO.getAbout());
        property.setAgeOfProperty(propertyDTO.getAgeOfProperty());
        property.setAmenitiesPara(propertyDTO.getAmenitiesPara());
        property.setBalcony(propertyDTO.getBalcony());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setBuiltupArea(propertyDTO.getBuiltupArea());
        property.setSize(propertyDTO.getSize());
        property.setPossessionStatus(propertyDTO.getPossessionStatus());
        property.setPrice(propertyDTO.getPrice());
        property.setPropertyAddress(propertyDTO.getPropertyAddress());
        property.setMetaTitle(propertyDTO.getMetaTitle());
        property.setMetaDescription(propertyDTO.getMetaDescription());
        property.setMetaKeywords(propertyDTO.getMetaKeywords());
        property.setOverviewPara(propertyDTO.getOverviewPara());
        property.setFloors(propertyDTO.getFloors());
        property.setCoveredParking(propertyDTO.getCoveredParking());
        property.setOpenParking(propertyDTO.getOpenParking());
        property.setPossessionDate(propertyDTO.getPossessionDate());
        property.setRera(propertyDTO.getRera());
        property.setUsp(propertyDTO.getUsp());
        property.setLatlong(propertyDTO.getLatlong());
        property.setLocationPara(propertyDTO.getLocationPara());
        property.setLocaionMap(propertyDTO.getLocaionMap());
        property.setFloorPara(propertyDTO.getFloorPara());
        property.setLogoImage(propertyDTO.getLogoImage());
        property.setCoverPhoto(propertyDTO.getCoverPhoto());
        property.setFloorImage2D(propertyDTO.getFloorImage2D());
        property.setFloorImage3D(propertyDTO.getFloorImage3D());
        property.setVideoPara(propertyDTO.getVideoPara());
        property.setPropertyVideo(propertyDTO.getPropertyVideo());
        property.setImages(propertyDTO.getImages());
        property.setLocationAdvantage(propertyDTO.getLocationAdvantage());
        property.setDeleted(false);
        property.setFeatured(propertyDTO.getIsFeatured());
        property.setFacing(propertyDTO.getFacing());
        property.setHighlights(propertyDTO.getHighlights());
        property.setFurnishingType(propertyDTO.getFurnishingType());
        property.setListingType(propertyDTO.getListingType());

        if(propertyDTO.getProjectId() != null)
        {
            Project project = this.projectRepository.findById(propertyDTO.getProjectId())
            .orElseThrow(() -> new Exception("Project Not Found"));
            property.setProject(project);
            property.setDeveloper(project.getDeveloper());
            property.setPropertyAmenities(new ArrayList<>(project.getProjectAmenities()));
            property.setAmenitiesPara(project.getAmenitiesPara() != null ? project.getAmenitiesPara() : propertyDTO.getAmenitiesPara());
            property.setPropertyAddress(project.getProjectAddress() != null ? project.getProjectAddress() : propertyDTO.getPropertyAddress());
            property.setFloorPara(project.getFloorPara() != null ? project.getFloorPara() : propertyDTO.getFloorPara());
            property.setAbout(project.getProjectAbout() != null ? project.getProjectAbout() : propertyDTO.getAbout());
            property.setRera(project.getProjectRera() != null ? project.getProjectRera() : propertyDTO.getRera());
            property.setVideoPara(project.getVideoPara() != null ? Arrays.asList(project.getVideoPara()) : propertyDTO.getVideoPara());
            property.setPropertyVideo(propertyDTO.getPropertyVideo() != null ? propertyDTO.getPropertyVideo() : (project.getProjectVideos() != null ? project.getProjectVideos() : null));
            property.setLocality(project.getLocality());
            property.setLocationPara(project.getLocationPara() != null ? project.getLocationPara() : propertyDTO.getLocationPara());
            property.setMetaDescription(project.getMetaDesciption() != null ? project.getMetaDesciption() : propertyDTO.getMetaDescription());
            property.setMetaKeywords(project.getMetaKeywords() != null ? String.join(",", project.getMetaKeywords()) : propertyDTO.getMetaKeywords());
            property.setMetaTitle(project.getMetaTitle() != null ? project.getMetaTitle() : propertyDTO.getMetaTitle());
            property.setProductSchema(project.getProjectSchema() != null ? String.join(",", project.getProjectSchema()) : propertyDTO.getProductSchema());
            property.setOverviewPara(project.getOverviewPara() != null ? project.getOverviewPara() : propertyDTO.getOverviewPara());
            property.setUsp(project.getUsp() != null ? project.getUsp() : propertyDTO.getUsp());
        }

        property.setUser(user); 
        return propertyRepository.save(property);
    }

    @Override
    public Property getById(Long Id) throws Exception 
    {
        Property property = this.propertyRepository.findById(Id).orElseThrow(() -> new Exception("Property Not Found"));
        return property;
    }

    @Override
    public Property updateById(Long Id, PropertyDTO propertyDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Please login again");

        Property property = this.propertyRepository.findById(Id).orElseThrow(() -> new Exception("Property Not Found"));
        property.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(propertyDTO.getPropertyName()).ifPresent(property::setPropertyName);
        Optional.ofNullable(propertyDTO.getAbout()).ifPresent(property::setAbout);
        Optional.ofNullable(propertyDTO.getAgeOfProperty()).ifPresent(property::setAgeOfProperty);
        Optional.ofNullable(propertyDTO.getAmenitiesPara()).ifPresent(property::setAmenitiesPara);
        Optional.ofNullable(propertyDTO.getBalcony()).ifPresent(property::setBalcony);
        Optional.ofNullable(propertyDTO.getBathrooms()).ifPresent(property::setBathrooms);
        Optional.ofNullable(propertyDTO.getBedrooms()).ifPresent(property::setBedrooms);
        Optional.ofNullable(propertyDTO.getBuiltupArea()).ifPresent(property::setBuiltupArea);
        Optional.ofNullable(propertyDTO.getSize()).ifPresent(property::setSize);
        Optional.ofNullable(propertyDTO.getPossessionStatus()).ifPresent(property::setPossessionStatus);
        Optional.ofNullable(propertyDTO.getPrice()).ifPresent(property::setPrice);
        Optional.ofNullable(propertyDTO.getPropertyAddress()).ifPresent(property::setPropertyAddress);
        Optional.ofNullable(propertyDTO.getMetaTitle()).ifPresent(property::setMetaTitle);
        Optional.ofNullable(propertyDTO.getMetaDescription()).ifPresent(property::setMetaDescription);
        Optional.ofNullable(propertyDTO.getMetaKeywords()).ifPresent(property::setMetaKeywords);
        Optional.ofNullable(propertyDTO.getOverviewPara()).ifPresent(property::setOverviewPara);
        Optional.ofNullable(propertyDTO.getFloors()).ifPresent(property::setFloors);
        Optional.ofNullable(propertyDTO.getCoveredParking()).ifPresent(property::setCoveredParking);
        Optional.ofNullable(propertyDTO.getOpenParking()).ifPresent(property::setOpenParking);
        Optional.ofNullable(propertyDTO.getPossessionDate()).ifPresent(property::setPossessionDate);
        Optional.ofNullable(propertyDTO.getRera()).ifPresent(property::setRera);
        Optional.ofNullable(propertyDTO.getUsp()).ifPresent(property::setUsp);
        Optional.ofNullable(propertyDTO.getLatlong()).ifPresent(property::setLatlong);
        Optional.ofNullable(propertyDTO.getLocationPara()).ifPresent(property::setLocationPara);
        Optional.ofNullable(propertyDTO.getLocaionMap()).ifPresent(property::setLocaionMap);
        Optional.ofNullable(propertyDTO.getFloorPara()).ifPresent(property::setFloorPara);
        Optional.ofNullable(propertyDTO.getLogoImage()).ifPresent(property::setLogoImage);
        Optional.ofNullable(propertyDTO.getCoverPhoto()).ifPresent(property::setCoverPhoto);
        Optional.ofNullable(propertyDTO.getFloorImage2D()).ifPresent(property::setFloorImage2D);
        Optional.ofNullable(propertyDTO.getFloorImage3D()).ifPresent(property::setFloorImage3D);
        Optional.ofNullable(propertyDTO.getVideoPara()).ifPresent(property::setVideoPara);
        Optional.ofNullable(propertyDTO.getPropertyVideo()).ifPresent(property::setPropertyVideo);
        Optional.ofNullable(propertyDTO.getImages()).ifPresent(property::setImages);
        Optional.ofNullable(propertyDTO.getLocationAdvantage()).ifPresent(property::setLocationAdvantage);
        Optional.ofNullable(propertyDTO.getIsFeatured()).ifPresent(property::setFeatured);
        Optional.ofNullable(propertyDTO.getFacing()).ifPresent(property::setFacing);
        Optional.ofNullable(propertyDTO.getFurnishingType()).ifPresent(property::setFurnishingType);
        Optional.ofNullable(propertyDTO.getListingType()).ifPresent(property::setListingType);
        Optional.ofNullable(propertyDTO.getHighlights()).ifPresent(property::setHighlights);

        if(propertyDTO.getProjectId() != null)
        {
            Project project = this.projectRepository.findById(propertyDTO.getProjectId())
            .orElseThrow(() -> new Exception("Project Not Found"));
            property.setProject(project);
            property.setDeveloper(project.getDeveloper() != null ? project.getDeveloper() : null);
        }

        if(propertyDTO.getConfigurationId() != null)
        {
            ProjectConfiguration propertyConfiguration = this.configurationRepository.findById(propertyDTO.getConfigurationId())
            .orElseThrow(() -> new Exception("Configuation Not Found"));
            property.setConfiguration(propertyConfiguration);
        }

        if(propertyDTO.getLocalityId() != null)
        {
            Locality locality = this.localityRepository.findById(propertyDTO.getLocalityId())
            .orElseThrow(() -> new Exception("Locality Not Found"));
            property.setLocality(locality);
        }

        Optional.ofNullable(propertyDTO.getPropertyAmenities()).ifPresent(amenitiesIds -> 
        {
            List<Amenities> validAmenities = amenitiesIds.stream()
                    .map(amenitiesRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            property.setPropertyAmenities(validAmenities);
        });
        property.setUpdatedById(user.getId());
        return propertyRepository.save(property);
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        Property property = this.propertyRepository.findById(Id).orElseThrow(() -> new Exception("Property Not Found"));
        this.propertyRepository.delete(property);
        String message = "Property Deleted Successfully";
        return message;
    }

    @Override
    public Page<Property> findAll(Long cityId, PropertyType propertyType, String configTypeName, String configurationName, Long startDate, Long endDate, Long userId, String name, Long projectId,
    Long localityId, Boolean isDeleted, Pageable pageable) throws Exception 
    {
        Specification<Property> specification = PropertyUtility.filterByCriteria(cityId, propertyType, configTypeName, configurationName, startDate, endDate, userId, name, 
        projectId, localityId, isDeleted);
        
        Page<Property> properties = this.propertyRepository.findAll(specification, pageable);
        return new PageImpl<>(properties.getContent(), pageable, properties.getTotalElements());
    }

    @Override
    public Property findByUrl(String url) throws Exception 
    {
        Property property = this.propertyRepository.findByPropertyUrl(url);
        if(property == null) throw new Exception("Property Not Found");
        return property;
    }

    @Override
    public Page<Property> findByUser(Long userId, Pageable pageable) throws Exception 
    {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new Exception("User Not Found"));
        List<Property> properties = this.propertyRepository.findByUser(user);
        if(properties == null || properties.isEmpty()) throw new Exception("Properties Not Found");
        
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), properties.size());
        return new PageImpl<>(properties.subList(start, end), pageable, properties.size());
    }

    @Override
    public Page<Property> findByProject(Long projectId, Pageable pageable) throws Exception 
    {
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project Not Found"));
        List<Property> properties = this.propertyRepository.findByProject(project);
        if(properties == null || properties.isEmpty()) throw new Exception("No Property Belongs To "+project.getProjectName());
        
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), properties.size());
        return new PageImpl<>(properties.subList(start, end), pageable, properties.size());
    }
    
}
