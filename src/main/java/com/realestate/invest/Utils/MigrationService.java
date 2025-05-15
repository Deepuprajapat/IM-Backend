package com.realestate.invest.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.EnumAndJsons.Facing;
import com.realestate.invest.EnumAndJsons.FurnishingType;
import com.realestate.invest.EnumAndJsons.ListingType;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Amenities;
import com.realestate.invest.Model.Blogs;
import com.realestate.invest.Model.City;
import com.realestate.invest.Model.Developer;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Model.Locality;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ProjectConfiguration;
import com.realestate.invest.Model.ProjectConfigurationType;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Model.ReraInfo;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.AmenitiesRepository;
import com.realestate.invest.Repository.BlogsRepository;
import com.realestate.invest.Repository.CityRepository;
import com.realestate.invest.Repository.DeveloperRepository;
import com.realestate.invest.Repository.FaqRepository;
import com.realestate.invest.Repository.FloorPlanRepository;
import com.realestate.invest.Repository.LocalityRepository;
import com.realestate.invest.Repository.PaymentPlanRepository;
import com.realestate.invest.Repository.ProjectConfigurationRepository;
import com.realestate.invest.Repository.ProjectConfigurationTypeRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.PropertyRepository;
import com.realestate.invest.Repository.ReraInfoReposiotry;
import com.realestate.invest.Repository.UserRepository;
import jakarta.transaction.Transactional;

/**
 * @Service class responsible for migrating project data from an external database
 * into the application.
 * 
 * <p>This service connects to an external MySQL database, retrieves data from the
 * "project" table, and processes it for further use.</p>
 * 
 * @author Abhishek Srivastav
 */
@Service
public class MigrationService 
{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectConfigurationRepository projectConfigurationRepository;

    @Autowired 
    private ProjectConfigurationTypeRepository pcTypeRepository;

    @Autowired
    private FloorPlanRepository floorPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired 
    private PropertyRepository propertyRepository;

    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    private ReraInfoReposiotry reraInfoReposiotry;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private FaqRepository faqRepository;

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/imdb1";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    /**
     * @Migrates project data from an external MySQL database by connecting
     * using JDBC, retrieving data from the "project" table, and converting it
     * into {@link Project} objects for further processing.
     * 
     * @return A success message indicating the completion of the migration process.
     * @throws Exception If any error occurs during database connectivity or data retrieval.
     */
    @Transactional
    public String migrateProjects() throws Exception
    {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery())
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByMobilePhone(userDetails.getUsername());
            if(user == null) throw new Exception("Authentication failed");

            System.out.println("Database Connected Successfully"); 
            while (resultSet.next()) 
            {
                System.out.println("Data Intercepted Successfully");
                Project project = new Project();
                Long id = resultSet.getLong("id");
                System.out.println("id : "+id);
                Long developerId = resultSet.getLong("developer_id");

                project.setCreatedDate(new Date().getTime());
                project.setProjectName(resultSet.getString("project_name"));
                project.setProjectAddress(resultSet.getString("project_address"));
                project.setProjectRera(resultSet.getString("project_rera"));
                project.setProjectArea(resultSet.getString("project_area"));
                project.setProjectUnits(resultSet.getString("project_units"));
                project.setTotalFloor(resultSet.getString("total_floor"));
                String projectVideo = resultSet.getString("project_video");
                if (projectVideo != null) 
                {
                    project.setProjectVideos(Arrays.asList(projectVideo.split(",")));
                } 
                else 
                {
                    project.setProjectVideos(Collections.emptyList()); 
                }
                project.setProjectVideoCount(resultSet.getLong("video_sn"));
                project.setProjectLocationUrl(resultSet.getString("project_location"));
                project.setSiteplanImg(resultSet.getString("siteplan_img"));
                project.setAltSitePlanImg(resultSet.getString("siteplan_img"));
                project.setReraLink(resultSet.getString("rera_link"));
                project.setAvailableUnit(resultSet.getString("available_unit"));
                project.setProjectBrochure(resultSet.getString("pdf"));
                project.setProjectAbout(resultSet.getString("project_about"));
                project.setMetaTitle(resultSet.getString("meta_title"));
                project.setMetaDesciption(resultSet.getString("meta_des"));
                project.setProjectUrl(resultSet.getString("post_url"));
                project.setShortAddress(resultSet.getString("short_address"));
                project.setAltProjectLogo(resultSet.getString("alt_logo"));
                project.setProjectLogo(resultSet.getString("logo_img"));
                project.setTotalTowers(resultSet.getString("total_tower"));

                String possessionDate = resultSet.getString("project_possession_date").trim();
                possessionDate = possessionDate.replaceAll("\\s+", " ");
                possessionDate = possessionDate.replaceAll(",\\s*", ",");
                possessionDate = possessionDate.replaceFirst("^\\s+", "");
                possessionDate = possessionDate.replaceAll("(?<=[a-zA-Z]) (?=[a-zA-Z])", ", ");
                possessionDate = possessionDate.trim();
                System.out.println(possessionDate);
                project.setProjectPossessionDate(possessionDate);

                String launchDate = resultSet.getString("project_launch_date").trim();
                if (!launchDate.equalsIgnoreCase("coming soon")) 
                {
                    launchDate = launchDate.replaceAll("\\s+", " ");
                    launchDate = launchDate.replaceAll(",\\s*", ",");
                    launchDate = launchDate.replaceFirst("^\\s+", "");
                    launchDate = launchDate.replaceAll("(?<=[a-zA-Z]) (?=[a-zA-Z])", ", ");
                    launchDate = launchDate.trim();
                }
                System.out.println(launchDate);                
                project.setProjectLaunchDate(launchDate);

                String projectSchema = resultSet.getString("schema");
                if (projectSchema != null) 
                {
                    project.setProjectSchema(Arrays.asList(projectSchema.split("</script>,")));
                } 
                else 
                {
                    project.setProjectSchema(Collections.emptyList()); 
                }
                project.setProjectDescription(resultSet.getString("content"));
                List<String> usps = new ArrayList<>();
                usps.add(resultSet.getString("usp1"));
                usps.add(resultSet.getString("usp2"));
                usps.add(resultSet.getString("usp3"));
                usps.add(resultSet.getString("usp4"));
                usps.add(resultSet.getString("usp5"));
                usps.add(resultSet.getString("usp6"));
                usps = usps.stream().filter(Objects::nonNull).filter(s -> !s.isEmpty()).collect(Collectors.toList());
                project.setUsp(usps);
                System.out.println("Usps Done");

                String imageQuery = "SELECT img1, img2, img3, img4, img5, alt_img1, alt_img2, alt_img3, alt_img4, alt_img5 " +
                "from project where id = ?";
                try (PreparedStatement imageStmt = connection.prepareStatement(imageQuery)) 
                {
                    imageStmt.setLong(1, id);
                    try (ResultSet imageSet = imageStmt.executeQuery()) 
                    {
                        Map<String, String> images = new HashMap<>();
                        while (imageSet.next()) {
                            for (int i = 1; i <= 5; i++) 
                            { 
                                String question = imageSet.getString("img" + i);
                                String answer = imageSet.getString("alt_img" + i);
                                if (question != null && answer != null) 
                                {
                                    images.put(question, answer);
                                }
                            }
                        }                        
                        project.setImages(images);
                    }
                }
                System.out.println("Images Fetched");

                String paraQuery = "SELECT * FROM project_con WHERE project_id = ?";
                try (PreparedStatement parasmt = connection.prepareStatement(paraQuery)) 
                {
                    parasmt.setLong(1, id);
                    try (ResultSet paraResultSet = parasmt.executeQuery()) 
                    {
                        if (paraResultSet.next()) 
                        { 
                            project.setOverviewPara(paraResultSet.getString("overview_para"));
                            project.setFloorPara(paraResultSet.getString("floor_para"));
                            project.setPriceListPara(paraResultSet.getString("price_list_para"));
                            project.setPaymentPara(paraResultSet.getString("payment_para"));
                            project.setAmenitiesPara(paraResultSet.getString("amenities_para"));
                            project.setVideoPara(paraResultSet.getString("video_para"));
                            project.setSiteplanPara(paraResultSet.getString("siteplan_para"));
                            project.setWhyPara(paraResultSet.getString("why_para"));
                            project.setLocationMap(paraResultSet.getString("location_map"));
                            project.setLocationPara(paraResultSet.getString("location_para"));
                        } 
                        else 
                        {
                            System.out.println("No data found in project_con for project_id: " + id);
                        }
                    }
                }
                System.out.println("Para Fetched");
                
                if (resultSet.getString("short_address") != null) 
                {
                    String fullString = resultSet.getString("short_address").toLowerCase();
                    String cityName = "";
                    String localityName = "";
                    String stateName = "";
                
                    if (fullString.contains("noida")) 
                    {
                        cityName = "Noida";
                        stateName = "Uttar Pradesh";
                        if (fullString.contains("sector 150")) 
                        {
                            localityName = "Sector 150";
                        } 
                        else if (fullString.contains("sector 128")) 
                        {
                            localityName = "Sector 128";
                        } 
                        else if (fullString.contains("sector 124")) 
                        {
                            localityName = "Sector 124";
                        } 
                        else if (fullString.contains("sector 152")) 
                        {
                            localityName = "Sector 152";
                        } 
                        else if (fullString.contains("sector 107")) 
                        {
                            localityName = "Sector 107";
                        } 
                        else if (fullString.contains("sector 143")) 
                        {
                            localityName = "Sector 143";
                        } 
                        else if (fullString.contains("sector 144")) 
                        {
                            localityName = "Sector 144";
                        } 
                        else if (fullString.contains("sector 79")) 
                        {
                            localityName = "Sector 79";
                        } 
                        else if (fullString.contains("sector 75")) 
                        {
                            localityName = "Sector 75";
                        } 
                        else if (fullString.contains("sector 131")) 
                        {
                            localityName = "Sector 131";
                        } 
                        else if (fullString.contains("sector 94")) 
                        {
                            localityName = "Sector 94";
                        } 
                        else if (fullString.contains("greater noida west")) 
                        {
                            localityName = "greater noida west";
                        } 
                        else if (fullString.contains("yamuna Expressway")) 
                        {
                            localityName = "Yamuna Expressway";
                        } 
                        else if (fullString.contains("Techzone 4 Greater Noida West".toLowerCase())) 
                        {
                            localityName = "Techzone 4 Greater Noida West";
                        } 
                        else if (fullString.contains("Sector Zeta 1 Greater Noida".toLowerCase())) 
                        {
                            localityName = "Sector Zeta 1 Greater Noida";
                        } 
                        else if (fullString.contains("Sector Alpha II Greater Noida".toLowerCase())) 
                        {
                            localityName = "Sector Alpha II Greater Noida";
                        } 
                        else if (fullString.contains("Sector 4 Greater Noida West".toLowerCase())) 
                        {
                            localityName = "Sector 4 Greater Noida West";
                        } 
                        else if (fullString.contains("Sector Chi 5 Greater Noida".toLowerCase())) 
                        {
                            localityName = "Sector Chi 5 Greater Noida";
                        } 
                        else if (fullString.contains("Sector 2 Greater Noida West".toLowerCase())) 
                        {
                            localityName = "Sector 2 Greater Noida West";
                        } 
                        else if (fullString.contains("Sector 120 Noida".toLowerCase())) 
                        {
                            localityName = "Sector 120";
                        } 
                        else 
                        {
                            localityName = "Unknown Noida Sector";
                        }
                    } 
                    else if (fullString.contains("ghaziabad")) 
                    {
                        cityName = "Ghaziabad";
                        stateName = "Uttar Pradesh";
                        if (fullString.contains("indirapuram".toLowerCase())) 
                        {
                            localityName = "Indirapuram";
                        } 
                        else if (fullString.contains("siddhartha vihar".toLowerCase())) 
                        {
                            localityName = "Siddhartha Vihar";
                        } 
                        else if (fullString.contains("Vasundhara".toLowerCase())) 
                        {
                            localityName = "Vasundhara";
                        } 
                        else if (fullString.contains("Raj Nagar Extension".toLowerCase())) 
                        {
                            localityName = "Raj Nagar Extension";
                        } 
                        else if (fullString.contains("Crossing Republik".toLowerCase())) 
                        {
                            localityName = "Crossing Republik";
                        } 
                        else 
                        {
                            localityName = "Unknown Ghaziabad Area";
                        }
                    } 
                    else if (fullString.contains("delhi")) 
                    {
                        cityName = "Delhi";
                        stateName = "Delhi";
                        if (fullString.contains("kirti nagar west".toLowerCase())) 
                        {
                            localityName = "Kirti Nagar West";
                        } 
                        else if (fullString.contains("chattarpur south delhi")) 
                        {
                            localityName = "Chattarpur South Delhi";
                        } 
                        else if (fullString.contains("moti nagar west delhi")) 
                        {
                            localityName = "Moti Nagar";
                        } 
                        else if (fullString.contains("okhla south delhi")) 
                        {
                            localityName = "Okhla";
                        } 
                        else if (fullString.contains("connaught place new delhi")) {
                            localityName = "Connaught Place";
                        } 
                        else if (fullString.contains("Crossing Republik".toLowerCase())) 
                        {
                            localityName = "Crossing Republik";
                        } 
                        else 
                        {
                            localityName = "Unknown Delhi Area";
                        }
                    } 
                    else if (fullString.contains("gurugram")) 
                    {
                        cityName = "Gurugram";
                        stateName = "Haryana";
                        if (fullString.contains("sector 63a")) 
                        {
                            localityName = "Sector 63A";
                        } 
                        else if (fullString.contains("sector 81")) 
                        {
                            localityName = "Sector 81";
                        } 
                        else if (fullString.contains("sector 85")) 
                        {
                            localityName = "Sector 85";
                        } 
                        else if (fullString.contains("sector 79")) 
                        {
                            localityName = "Sector 79";
                        } 
                        else if (fullString.contains("sector 93")) 
                        {
                            localityName = "Sector 93";
                        } 
                        else if (fullString.contains("sector 112")) 
                        {
                            localityName = "Sector 112";
                        } 
                        else if (fullString.contains("sector 106")) 
                        {
                            localityName = "Sector 106";
                        } 
                        else if (fullString.contains("sector 107")) 
                        {
                            localityName = "Sector 107";
                        } 
                        else if (fullString.contains("Sector 36A".toLowerCase())) 
                        {
                            localityName = "Sector 36A";
                        } 
                        else if (fullString.contains("Sector 103, Dwarka Expressway".toLowerCase())) 
                        {
                            localityName = "Sector 103, Dwarka Expressway";
                        } 
                        else if (fullString.contains("Sector 36 Sohna Road".toLowerCase())) 
                        {
                            localityName = "Sector 36 Sohna Road";
                        } 
                        else if (fullString.contains("Sector 85, Dwarka Expressway".toLowerCase())) 
                        {
                            localityName = "Sector 85, Dwarka Expressway";
                        } 
                        else if (fullString.contains("Sector 77, Gurugram".toLowerCase())) 
                        {
                            localityName = "Sector 107";
                        } 
                        else 
                        {
                            localityName = "Unknown Gurugram Sector";
                        }
                    } 
                    else if (fullString.contains("mumbai")) 
                    {
                        cityName = "Mumbai";
                        stateName = "Mumbai";
                        if (fullString.contains("worli")) 
                        {
                            localityName = "Worli";
                        } 
                        else if (fullString.contains("kandivali east")) 
                        {
                            localityName = "Kandivali East";
                        } 
                        else if (fullString.contains("mahalaxmi")) 
                        {
                            localityName = "Mahalaxmi";
                        } 
                        else if (fullString.contains("akurli rd kandivali east")) 
                        {
                            localityName = "Akurli Rd Kandivali East";
                        } 
                        else if (fullString.contains("matunga")) 
                        {
                            localityName = "Matunga";
                        } 
                        else if (fullString.contains("jogeshwari vikhroli link road")) 
                        {
                            localityName = "Jogeshwari Vikhroli Link Road";
                        } 
                        else if (fullString.contains("Nirmal Nagar".toLowerCase())) 
                        {
                            localityName = "Nirmal Nagar";
                        } 
                        else 
                        {
                            localityName = "Unknown Mumbai Area";
                        }
                    } 
                    else if (fullString.contains("bangalore")) 
                    {
                        cityName = "Bangalore";
                        stateName = "Karnataka";
                        if (fullString.contains("bannerghatta road")) 
                        {
                            localityName = "Bannerghatta Road";
                        } 
                        else if (fullString.contains("kanakapura road")) 
                        {
                            localityName = "Kanakapura Road";
                        } 
                        else if (fullString.contains("manyata tech park")) 
                        {
                            localityName = "Manyata Tech Park";
                        } 
                        else if (fullString.contains("singasandra")) 
                        {
                            localityName = "Singasandra";
                        } 
                        else 
                        {
                            localityName = "Unknown Bangalore Area";
                        }
                    } 
                    else if (fullString.contains("pune")) 
                    {
                        cityName = "Pune";
                        stateName = "Mumbai";
                        if (fullString.contains("hinjewadi")) 
                        {
                            localityName = "Hinjewadi";
                        } 
                        else if (fullString.contains("gahunjhe")) 
                        {
                            localityName = "Gahunje";
                        } 
                        else if (fullString.contains("manjari")) 
                        {
                            localityName = "Manjari";
                        } 
                        else if (fullString.contains("mahalunge")) 
                        {
                            localityName = "Mahalunge";
                        } 
                        else if (fullString.contains("baner")) 
                        {
                            localityName = "Baner";
                        } 
                        else 
                        {
                            localityName = "Unknown Pune Area";
                        }
                    } 
                    else 
                    {
                        cityName = "Unknown";
                        localityName = "Unknown";
                        stateName = "Unknown";
                    }
                    Locality locality = this.localityRepository.findByLocalityNameAndCityCityName(localityName, cityName);
                    if(locality == null)
                    {
                        City city = this.cityRepository.findByCityNameAndStateName(cityName, stateName);
                        if(city == null)
                        {
                            city = new City();
                            city.setCreatedDate(new Date().getTime());
                            city.setStateName(stateName.toUpperCase());
                            city.setCityName(cityName.toUpperCase());
                            city.setActive(true);
                            
                            String lowerCase = city.getCityName().toLowerCase()+ " "+city.getStateName().toLowerCase();
                            String cityUrl = lowerCase.replaceAll("\\s", "-");
                            City existingUrl = this.cityRepository.findByCityUrl(cityUrl);
                            String newcityUrl = (existingUrl != null) ? (cityUrl + OTPGenerator.generateUUID().substring(13)) : cityUrl;
                            city.setCityUrl(newcityUrl);
                            this.cityRepository.save(city);
                        }
                        locality = new Locality();
                        locality.setCreatedDate(new Date().getTime());
                        locality.setLocalityName(localityName.toUpperCase());
                        locality.setCity(city);
                        String lowerCase = locality.getLocalityName().toLowerCase();
                        String localityUrl = lowerCase.replaceAll("\\s", "-");
                        Locality existingUrl = this.localityRepository.findByLocalityUrl(localityUrl);
                        String newLocalityUrl = (existingUrl != null) ? (localityUrl + OTPGenerator.generateUUID().substring(13)) : localityUrl;
                        locality.setLocalityUrl(newLocalityUrl);
                        this.localityRepository.save(locality);
                    }
                    project.setLocality(locality);
                }

                if(resultSet.getString("project_bubble").toLowerCase() != null)
                {
                    String statusData = resultSet.getString("project_bubble").toLowerCase();
                    ProjectStatus projectStatus;
                    if(statusData == "Ready To Move".toLowerCase())
                    {
                        projectStatus = ProjectStatus.READY_TO_MOVE;
                    }
                    else if(statusData == "Under Construction".toLowerCase())
                    {
                        projectStatus = ProjectStatus.UNDER_CONSTRUCTION;
                    }
                    else if(statusData == "OC Received".toLowerCase())
                    {
                        projectStatus = ProjectStatus.READY_TO_MOVE;
                    }
                    else if(statusData == "New Launch".toLowerCase())
                    {
                        projectStatus = ProjectStatus.NEW_LAUNCH;
                    }
                    else
                    {
                        projectStatus = ProjectStatus.PRE_LAUNCH;
                    }
                    project.setStatus(projectStatus);
                }

                String developerQuery = "select project_developers.developer_id, project_developers.developer_name, project.id, "+
                "project.project_name from project_developers, project where project.developer_id = project_developers.developer_id "+
                "and project_developers.developer_id= ? ";
                try (PreparedStatement devstmt = connection.prepareStatement(developerQuery)) 
                {
                    devstmt.setLong(1, developerId);
                    try (ResultSet devSet = devstmt.executeQuery()) 
                    {
                        while (devSet.next()) 
                        {
                            Developer developer = this.developerRepository.findByDeveloperName(devSet.getString("developer_name"));
                            if(developer != null) project.setDeveloper(developer);
                        }
                    }
                }
                System.out.println("Developer Fetched");

                project.setUser(user);
                projects.add(project);
                Project project2 = this.projectRepository.save(project);
                System.out.println("Project Added");

                String configQuery = "SELECT project_floorplan.project_id, project.project_name, project_floorplan.title, " +
                                    "project_floorplan.stitle, project_floorplan.price, project_floorplan.img, project_floorplan.size, " +
                                    "project_floorplan.content, project.property_type FROM project_floorplan, project " +
                                    "WHERE project_floorplan.project_id = project.id AND project_floorplan.project_id = ?";

                try (PreparedStatement confstmt = connection.prepareStatement(configQuery)) 
                {
                    confstmt.setLong(1, id);
                    try (ResultSet confResultSet = confstmt.executeQuery()) 
                    {
                        while (confResultSet.next()) 
                        {
                            ProjectConfiguration configuration;
                            Floorplan floorplan = new Floorplan();
                            String title = confResultSet.getString("title");
                            String configurationName = confResultSet.getString("stitle").trim().toUpperCase();
                            String configurationTypeName;

                            if (title != null && title.contains("Apartment")) 
                            {
                                configurationTypeName = "Apartment";
                            } 
                            else 
                            {
                                configurationTypeName = title;
                            }
                            configuration = this.projectConfigurationRepository.findByProjectConfigurationNameAndConfigurationTypeConfigurationTypeName
                            (configurationName, configurationTypeName);
                            if(configuration == null)
                            {
                                configurationTypeName.trim().toUpperCase();
                                PropertyType propertyType;
                                if(confResultSet.getString("property_type").toLowerCase().equals(PropertyType.RESIDENTIAL.toString().toLowerCase()))
                                {
                                    propertyType = PropertyType.RESIDENTIAL;
                                }
                                else
                                {
                                    propertyType = PropertyType.COMMERCIAL;
                                }
                                
                                configuration = new ProjectConfiguration();
                                configuration.setCreatedDate(new Date().getTime());
                                ProjectConfigurationType configType; 
                                configType = this.pcTypeRepository.findByConfigurationTypeNameAndPropertyType(configurationTypeName, 
                                propertyType);
                                if(configType == null)
                                {
                                    configType = new ProjectConfigurationType();
                                    configType.setCreatedDate(new Date().getTime());
                                    configType.setPropertyType(propertyType);
                                    configType.setConfigurationTypeName(configurationTypeName);
                                    this.pcTypeRepository.save(configType);
                                }
                                configuration.setConfigurationType(configType);
                                configuration.setProjectConfigurationName(configurationName);
                                this.projectConfigurationRepository.save(configuration);
                            }
                            floorplan.setCreatedDate(new Date().getTime());
                            floorplan.setConfiguration(configuration);
                            floorplan.setTitle(confResultSet.getString("title"));
                            
                            floorplan.setSize(confResultSet.getString("size") != null && !confResultSet.getString("size").trim().isEmpty() 
                            ? Long.parseLong(confResultSet.getString("size").trim()) : null); 
                            String priceString = confResultSet.getString("price");
                            if (priceString != null && priceString.trim().matches("-?\\d+(\\.\\d+)?")) floorplan.setPrice(Double.parseDouble(priceString.trim()));

                            floorplan.setImgUrl(confResultSet.getString("img"));
                            floorplan.setProject(project2);
                            floorplan.setUser(user);
                            Floorplan floorplan2 = this.floorPlanRepository.save(floorplan);
                            System.out.println("Floor Plan Data : "+floorplan2.getId()+" Project Name : "+floorplan2.getProjectName());
                        }
                    }
                }
                System.out.println("Floor Plans Fetched");

                List<Floorplan> floorplans = this.floorPlanRepository.findByProject(project2);
                if (floorplans != null) 
                {
                    Set<String> uniqueConfigurations = new HashSet<>();
                    for (Floorplan flr : floorplans) 
                    {
                        String configName = flr.getConfiguration().getProjectConfigurationName();
                        uniqueConfigurations.add(configName);
                        project2.setConfigurationsType(flr.getConfiguration().getConfigurationType());
                    }
                    List<String> configurations = new ArrayList<>(uniqueConfigurations);
                    project2.setProjectConfigurations(configurations);
                    this.projectRepository.save(project2);
                }
                System.out.println("Configuration Saved");

            }

        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            throw new Exception(e.getLocalizedMessage());
        }
        String message = "Data Saved Successfully";
        return message;
    }

    public String migrateDevelopers() throws Exception
    {
        List<Developer> developers = new ArrayList<>();String sql = "SELECT * FROM project_developers";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery())
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByMobilePhone(userDetails.getUsername());
            if(user == null) throw new Exception("Authentication failed");

            System.out.println("Database Connected Successfully"); 
            while (resultSet.next()) 
            {
                System.out.println("Data Intercepted Successfully");
                Developer developer = new Developer();
                Long id = resultSet.getLong("developer_id");
                System.out.println("id : "+id);
                
                developer.setCreatedDate(new Date().getTime());
                developer.setDeveloperName(resultSet.getString("developer_name"));
                developer.setDisclaimer(resultSet.getString("dev_heading"));
                developer.setOverview(resultSet.getString("dev_desc"));
                developer.setAbout(resultSet.getString("about_developer"));
                developer.setDeveloperLegalName(resultSet.getString("developer_name"));
                developer.setDeveloperLogo(resultSet.getString("dev_img"));
                developer.setAltDeveloperLogo(resultSet.getString("logo_image_alt"));
                
                String totalProjects = resultSet.getString("dev_total_project");
                if (totalProjects != null && !totalProjects.trim().isEmpty()) 
                {
                    totalProjects = totalProjects.trim().replaceAll("[^\\d]", "");
                    developer.setProjectDoneNo(!totalProjects.isEmpty() ? Long.parseLong(totalProjects) : null);
                } 
                else 
                {
                    developer.setProjectDoneNo(null);
                }

                // Parse 'dev_established' safely
                String establishedYear = resultSet.getString("dev_established");
                if (establishedYear != null && !establishedYear.trim().isEmpty()) 
                {
                    establishedYear = establishedYear.trim().replaceAll("[^\\d]", "");
                    developer.setEstablishedYear(!establishedYear.isEmpty() ? Long.parseLong(establishedYear) : null);
                } 
                else 
                {
                    developer.setEstablishedYear(null);
                }
                String lowerCase = developer.getDeveloperName().toLowerCase();
                String developerUrl = lowerCase.replaceAll("\\s", "-");
                Developer existingUrl = this.developerRepository.findByDeveloperUrl(developerUrl);
                String newDeveloperUrl = (existingUrl != null) ? (developerUrl + OTPGenerator.generateUUID().substring(13)) : developerUrl;
                developer.setDeveloperUrl(newDeveloperUrl);

                developers.add(developer);
                Developer developer2 = this.developerRepository.save(developer);
                System.out.println("Saved Developers : "+developer2.getId());
            }
            String message = "Developer saved successfully";
            return message;
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            throw new Exception(e.getLocalizedMessage());
        }
    }


    @Transactional
    public String migrateProperties() throws Exception 
    {
        List<Property> properties = new ArrayList<>();
        String sql = "SELECT * FROM property_table";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) 
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByMobilePhone(userDetails.getUsername());
            if (user == null) throw new Exception("Authentication failed");

            System.out.println("Database Connected Successfully");

            while (resultSet.next()) 
            {
                System.out.println("Data Intercepted Successfully");
                Property property = new Property();
                Long developerId = resultSet.getLong("property_developers_id");

                property.setCreatedDate(new Date().getTime());
                property.setPropertyName(resultSet.getString("property_name"));
                property.setPropertyAddress(resultSet.getString("property_address"));
                property.setAbout(resultSet.getString("property_about"));
                property.setMetaTitle(resultSet.getString("meta_title"));
                property.setMetaDescription(resultSet.getString("meta_des"));
                property.setPropertyUrl(resultSet.getString("post_url"));
                property.setMetaKeywords(resultSet.getString("meta_keywords"));
                property.setPrice(resultSet.getLong("price"));
                property.setBuiltupArea(resultSet.getString("built_up_area"));
                property.setSize(resultSet.getString("size"));
                property.setAgeOfProperty(resultSet.getString("age_of_property"));
                property.setPossessionStatus(resultSet.getString("possession_status"));
                property.setProductSchema(resultSet.getString("product_schema"));
                property.setOverviewPara(resultSet.getString("overview_para"));
                property.setFloors(resultSet.getString("floors"));
                if(resultSet.getString("facing") != null)
                {
                    String facingText = resultSet.getString("facing"); 
                    Facing facing;
                    System.out.println("facingText : "+facingText);
                    if(facingText.contains("North Facing"))
                    {
                        facing = Facing.NORTH;
                    }
                    else if(facingText.contains("North East"))
                    {
                        facing = Facing.NORTH_EAST;
                    }
                    else 
                    {
                        facing = Facing.NORTH;
                    }
                    property.setFacing(facing);
                }
                
                if(resultSet.getString("furnishing") != null)
                {
                    String furnishingText = resultSet.getString("furnishing");
                    FurnishingType furnishingType;
                    if(furnishingText.contains("Semi-furnished") || furnishingText.contains("Semi-Furnished"))
                    {
                        furnishingType = FurnishingType.SEMI_FURNISHED;
                    }
                    else
                    {
                        furnishingType = FurnishingType.UN_FURNISHED;
                    }
                    property.setFurnishingType(furnishingType);
                }
                property.setCoveredParking(resultSet.getString("covered_parking"));
                property.setOpenParking(resultSet.getString("open_parking"));
                property.setBalcony(resultSet.getString("balcony"));
                property.setBathrooms(resultSet.getString("bathrooms"));
                property.setBedrooms(resultSet.getString("bedrooms"));
                property.setPossessionDate(resultSet.getString("possession_date"));
                property.setRera(resultSet.getString("rera_id"));
                List<String> usps = new ArrayList<>();
                usps.add(resultSet.getString("usp1"));
                usps.add(resultSet.getString("usp2"));
                usps.add(resultSet.getString("usp3"));
                usps.add(resultSet.getString("usp4"));
                usps.add(resultSet.getString("usp5"));
                usps.add(resultSet.getString("usp6"));
                property.setUsp(usps);
                property.setAmenitiesPara(resultSet.getString("amenities_para"));
                property.setLocationPara(resultSet.getString("location_para"));
                property.setLocaionMap(resultSet.getString("property_location"));
                String videoPara = resultSet.getString("video_para");
                if(videoPara != null)
                {
                    property.setVideoPara(Arrays.asList(videoPara.split("</p>")));
                }
                String propertyVideo = resultSet.getString("project_video");
                if(propertyVideo != null)
                {
                    property.setPropertyVideo(Arrays.asList(propertyVideo));
                }
                List<String> images = new ArrayList<>();
                images.add(resultSet.getString("img1"));
                images.add(resultSet.getString("img2"));
                images.add(resultSet.getString("img3"));
                images.add(resultSet.getString("img4"));
                images.add(resultSet.getString("img5"));
                property.setImages(images);
                property.setFloorPara(resultSet.getString("floor_para"));
                property.setFloorImage2D(resultSet.getString("floor_image_2D"));
                property.setFloorImage3D(resultSet.getString("floor_image_3D"));
                property.setLogoImage(resultSet.getString("logo_img"));
                property.setListingType(ListingType.SALE);
                List<String> highlights = new ArrayList<>();
                highlights.add("Sportsfacilities "+resultSet.getString("sports_facilities"));
                highlights.add("Road connectivity "+resultSet.getString("road_connectivity"));
                highlights.add("Public Transport "+resultSet.getString("public_transport"));
                highlights.add("Medical Facilities "+resultSet.getString("medical_facilities"));
                highlights.add("Entertainment Hub "+resultSet.getString("entertainment_hub"));
                highlights.add("Educational Institutions "+resultSet.getString("educational_institutions"));
                property.setHighlights(highlights);
                String configurationName =  resultSet.getString("configuration");
                if(configurationName != null) 
                {
                    List<ProjectConfiguration> projectConfigurations = this.projectConfigurationRepository
                    .findByConfigurationTypeConfigurationTypeName(configurationName);
                    if (projectConfigurations != null && !projectConfigurations.isEmpty()) 
                    {
                        ProjectConfiguration projectConfiguration = projectConfigurations.get(0);
                        if(projectConfiguration != null) 
                        {
                            property.setConfiguration(projectConfiguration);
                        }
                    }
                }
                

                String developerQuery = "select project_developers.developer_id, project_developers.developer_name, " +
                "property_table.property_id, property_table.property_name from project_developers, property_table where " +
                "property_table.property_developers_id = project_developers.developer_id and project_developers.developer_id = ?";
                try (PreparedStatement devstmt = connection.prepareStatement(developerQuery)) 
                {
                    devstmt.setLong(1, developerId);
                    try (ResultSet devSet = devstmt.executeQuery()) 
                    {
                        while (devSet.next()) 
                        {
                            Developer developer = this.developerRepository.findByDeveloperName(devSet.getString("developer_name"));
                            if(developer != null) property.setDeveloper(developer);
                        }
                    }
                }
                String projectName = resultSet.getString("property_name");
                if(projectName != null)
                {
                    Project project = this.projectRepository.findByProjectName(projectName);
                    if(project != null)
                    {
                        System.out.println("Project Name : "+project.getProjectName());
                        property.setProject(project);
                        property.setDeveloper(project.getDeveloper());
                    }
                }
                System.out.println("Developer Attached");

                property.setUser(user);
                properties.add(property);
                Property savedproperty = this.propertyRepository.save(property);
                System.out.println("Property Id : "+savedproperty.getId());
            }
        }
        String message = "Properties migrated successfully!";
        return message;
    }


    @Transactional
    public String migrateBlogs() throws Exception 
    {
        List<Blogs> blogs = new ArrayList<>();
        String sql = "SELECT * FROM blogs";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) 
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByMobilePhone(userDetails.getUsername());
            if (user == null) throw new Exception("Authentication failed");

            System.out.println("Database Connected Successfully");

            while (resultSet.next()) 
            {
                System.out.println("Data Intercepted Successfully");
                Blogs blog = new Blogs();
                blog.setCreatedDate(new Date().getTime());
                blog.setHeadings(resultSet.getString("title"));
                blog.setSubHeadings(resultSet.getString("blog_des"));
                blog.setDescription(resultSet.getString("des"));
                blog.setBlogUrl(resultSet.getString("url"));
                blog.setPriority(true);
                blog.setCanonical(resultSet.getString("canonical"));
                String blogSchema = resultSet.getString("blog_schema");
                if(blogSchema != null)
                {
                    blog.setBlogSchema(Arrays.asList(blogSchema.split("</script>")));
                }
                blog.setAlt(resultSet.getString("alt"));
                String images = resultSet.getString("img");
                if(images != null)
                {
                    blog.setImages(Arrays.asList(images));
                }
                blog.setUser(user);
                System.out.println("Blogs Saved Successfully");
                blogs.add(blog);
                Blogs savedBlogs = this.blogsRepository.save(blog);
                System.out.println("Saved Blog Id : "+savedBlogs.getId());
            }
        }
        String message = "Blogs Saved Successfully";
        return message;
    }

    public String migrateAmenities() throws Exception
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for(Project project : allProjects)
        {
            if(project != null)
            {
                String query = "select project_amenities.project_id, project.id, project.project_name from project_amenities, "+
                "project where project_amenities.project_id = project.id and project.project_name = ? ";
                
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            Map<String, String> amenitiesRow = new HashMap<>();
                            List<Amenities> amenitiesList = new ArrayList<>();
                            
                            String amenitiesQuery = "SELECT * FROM project_amenities WHERE project_id = ?";
                            try (Connection amenityConn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                            PreparedStatement amstmt = amenityConn.prepareStatement(amenitiesQuery)) 
                            {
                                amstmt.setInt(1, resultSet.getInt("project_id"));
                                try (ResultSet rs = amstmt.executeQuery()) 
                                {
                                    while (rs.next()) 
                                    {
                                        int columnCount = rs.getMetaData().getColumnCount();
                                        for (int i = 1; i <= columnCount; i++) 
                                        {
                                            String columnName = rs.getMetaData().getColumnName(i);
                                            amenitiesRow.put(columnName, rs.getString(columnName));
                                        }
                                    }
                                }
                            }
                            List<String> sports = Arrays.asList("gymnasium", "swimming_pool", "kids_pool", "badminton_court", "tennis_court",
                                    "cricket", "skating_rink", "golf_putting", "golf_course", "golf_simulator",
                                    "squash_court", "kids_play_areas-sand_pits", "basketball", "volly_ball",
                                    "yoga_areas", "rappelling_rock_climbing", "jogging_cycle_track", "table_tennis",
                                    "play_courts", "rock_climbing_wall", "pickle_ball_courts", "sports_lawns", "pavilion_with_seating",
                                    "activity_room", "box_football", "toddler_pool", "children_play_area");

                            List<String> convenience = Arrays.asList("power_backup", "treated_water_supply", "24x7_water_supply", "high_speed_elevators",
                                    "high_speed_escalators", "atm", "medical_facility", "day_care_center", "driver_area",
                                    "restaurant", "home_automation", "attached_market", "lift",
                                    "multipurpose_lawn", "main_entrance", "tower_drop_off", "senior_citizen_zone", "business_center",
                                    "co_working_space", "car_parking", "car_wash_area", "laundry", "grocery_shop", "shopping_centre", "dth_television",
                                    "entrance_lobby", "cafeteria", "school");

                            List<String> safety = Arrays.asList("24x7_security", "cctv_video_surveillance", "fire_fighting_systems",
                                    "emergency_rescue_alarms", "entertainment_zone", "burglar_alarm",
                                    "smart_card_access", "intercom_facility", "video_phone", "smoke_heat_sensors",
                                    "swipe_card_access", "multi_tier_security_system", "pedestrian_and_vehicular_traffic_control",
                                    "security_cabin", "gated_community", "internal_street_lights");

                            List<String> leisure = Arrays.asList("sauna", "party_hall", "indoor_games", "Club House", "spa", "mini_theatre",
                                    "multi_brand_retail", "hypermarket", "dance_room", "luxurious_clubhouse", "jacuzzi",
                                    "multiplex", "cards_room", "cafe_coffee_bar", "conference_room", "amphitheater",
                                    "reading_room_library", "food_court", "party_hall_with_outdoor_party_area",
                                    "cafe_and_verandah_lounge", "club_deck", "seating_orchard", "orchard_cafe",
                                    "fragrance_garden", "reflexology_path", "card_room", "waiting_lounge", "fountain", "steam_room");

                            List<String> environment = Arrays.asList("rain_water_harvesting", "sewage_treatment_plant", "eco_friendly",
                                    "large_green_area", "normal_park_central_green", "tree_plaza", "pet_park",
                                    "seating_orchard", "future_development", "signature_seatings", "open_space",
                                    "landscape_garden");

                            List<String> entertainment = Arrays.asList("amphitheatre", "theatre", "bar_chill_out_lounge");

                            List<String> lifestyle = Arrays.asList("pergola", "gazebo", "flower_garden", "terrace_garden", "sun_deck", "Lounge");

                            List<String> sportsAmenities = filterAmenities(sports, amenitiesRow);
                            List<String> convenienceAmenities = filterAmenities(convenience, amenitiesRow);
                            List<String> safetyAmenities = filterAmenities(safety, amenitiesRow);
                            List<String> leisureAmenities = filterAmenities(leisure, amenitiesRow);
                            List<String> environmentAmenities = filterAmenities(environment, amenitiesRow);
                            List<String> entertainmentAmenities = filterAmenities(entertainment, amenitiesRow);
                            List<String> lifestyleAmenities = filterAmenities(lifestyle, amenitiesRow);
                            System.out.println("ProjectId : "+project.getId());
                            System.out.println("Sports Amenities: " + sportsAmenities);
                            System.out.println("Convenience Amenities: " + convenienceAmenities);
                            System.out.println("Safety Amenities: " + safetyAmenities);
                            System.out.println("Leisure Amenities: " + leisureAmenities);
                            System.out.println("Environment Amenities: " + environmentAmenities);
                            System.out.println("Entertainment Amenities: " + entertainmentAmenities);
                            System.out.println("Lifestyle Amenities: " + lifestyleAmenities);
                            

                            List<String> pngAmenityNames = Arrays.asList(
                            "activity_room", "Amphitheatre", "box_football", "business_centre", "cafe_and_verandah_lounge",
                            "Cafeteria", "Car-Parking", "Car-Wash-Area", "Card-Room", "Children's-Play-Area", "club_deck",
                            "co_working_space", "DTH-Television", "Entrance-Lobby", "Flower-Garden", "Fountain", "fragrance_garden",
                            "gate", "Gated-Community", "gazebo", "Gazebo", "Golf-Course", "golf", "Grocery-Shop", "ice-skating-silhouette",
                            "Internal-Street-Lights", "Landscape-Garden", "Laundry", "Lounge", "main_entrance", "multi_tier_security_system",
                            "multipurpose_lawn", "Open-Space", "orchard_cafe", "party_hall", "pavilion_with_seating",
                            "pedestrian_and_vehicular_traffic_control", "Pergola", "pet_park", "pickleball_courts", "plant", "play_courts",
                            "plaza", "raised-bed", "rajpath", "ramp", "reflexology_path", "road", "rock_climbing_wall", "School",
                            "Seating-Orchard", "seating", "Security-Cabin", "senior_citizen_zone", "Shopping-Centre", "signature_seatings",
                            "sports_lawns", "stadium", "stones", "Stream-Room", "Sun-Deck", "swipe_card", "Terrace-garden", "Theatre",
                            "Toddler-Pool", "tower_drop_off", "tree_plaza", "Waiting-Lounge"
                            );
                            String url = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/update/img/icon/";

                            for (String sportsAmenity : sportsAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName("Sports", sportsAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(sportsAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+sportsAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Sports");       
                                    amenity.setAmenitiesName(sportsAmenity);   
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }

                            for (String convenienceAmenity : convenienceAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Convenience", convenienceAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(convenienceAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+convenienceAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Convenience");   
                                    amenity.setAmenitiesName(convenienceAmenity);  
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }

                            for (String safetyAmenity : safetyAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Safety", safetyAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(safetyAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+safetyAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Safety");        
                                    amenity.setAmenitiesName(safetyAmenity);  
                                    amenity.setAmenitiesUrl(fullUrl);   
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String leisureAmenity : leisureAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Leisure", leisureAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(leisureAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+leisureAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Leisure");        
                                    amenity.setAmenitiesName(leisureAmenity);   
                                    amenity.setAmenitiesUrl(fullUrl);  
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String environmentAmenity : environmentAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Environment", environmentAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(environmentAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+environmentAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Environment");        
                                    amenity.setAmenitiesName(environmentAmenity); 
                                    amenity.setAmenitiesUrl(fullUrl);    
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String entertainmentAmenity : entertainmentAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Entertainment", entertainmentAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(entertainmentAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+entertainmentAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Entertainment");        
                                    amenity.setAmenitiesName(entertainmentAmenity); 
                                    amenity.setAmenitiesUrl(fullUrl);    
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String lifestyleAmenity : lifestyleAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Lifestyle", lifestyleAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(lifestyleAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+lifestyleAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Lifestyle");        
                                    amenity.setAmenitiesName(lifestyleAmenity);     
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            project.setProjectAmenities(amenitiesList);
                            this.projectRepository.save(project);
                            System.out.println("Project Amenities : "+project.getProjectAmenities().toString());
                        }
                    }
                }
                catch(SQLException e)
                {
                    throw new Exception(e.getMessage());
                }
            }
        }
        String message = "Amenities Saved Succesfully";
        return message;
    }


    private static List<String> filterAmenities(List<String> category, Map<String, String> amenitiesRow) 
    {
        List<String> filteredAmenities = new ArrayList<>();
        for (String amenity : category)
        {
            if (amenitiesRow.containsKey(amenity) && amenitiesRow.get(amenity) != null) 
            {
                filteredAmenities.add(amenity);
            }
        }
        return filteredAmenities;
    }

    public String migrateAmenitiesInProperty() throws Exception
    {
        List<Property> allProperties = this.propertyRepository.findAll();
        for(Property property : allProperties)
        {
            if(property != null)
            {
                String query = "SELECT pa.* FROM property_table pt JOIN property_amenities pa ON " +
                "pt.property_amenities_id = pa.id WHERE pt.property_name = ? ";
                
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, property.getPropertyName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            Map<String, String> amenitiesRow = new HashMap<>();
                            List<Amenities> amenitiesList = new ArrayList<>();
                            
                            String amenitiesQuery = "SELECT * FROM property_amenities WHERE id = ?";
                            try (Connection amenityConn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                            PreparedStatement amstmt = amenityConn.prepareStatement(amenitiesQuery)) 
                            {
                                amstmt.setInt(1, resultSet.getInt("id"));
                                try (ResultSet rs = amstmt.executeQuery()) 
                                {
                                    while (rs.next()) 
                                    {
                                        int columnCount = rs.getMetaData().getColumnCount();
                                        for (int i = 1; i <= columnCount; i++) 
                                        {
                                            String columnName = rs.getMetaData().getColumnName(i);
                                            amenitiesRow.put(columnName, rs.getString(columnName));
                                        }
                                    }
                                }
                            }
                            List<String> sports = Arrays.asList("gymnasium", "swimming_pool", "kids_pool", "badminton_court", "tennis_court",
                                    "cricket", "skating_rink", "golf_putting", "golf_course", "golf_simulator",
                                    "squash_court", "kids_play_areas-sand_pits", "basketball", "volly_ball",
                                    "yoga_areas", "rappelling_rock_climbing", "jogging_cycle_track", "table_tennis",
                                    "play_courts", "rock_climbing_wall", "pickle_ball_courts", "sports_lawns", "pavilion_with_seating",
                                    "activity_room", "box_football", "toddler_pool", "children_play_area");

                            List<String> convenience = Arrays.asList("power_backup", "treated_water_supply", "24x7_water_supply", "high_speed_elevators",
                                    "high_speed_escalators", "atm", "medical_facility", "day_care_center", "driver_area",
                                    "restaurant", "home_automation", "attached_market", "lift",
                                    "multipurpose_lawn", "main_entrance", "tower_drop_off", "senior_citizen_zone", "business_center",
                                    "co_working_space", "car_parking", "car_wash_area", "laundry", "grocery_shop", "shopping_centre", "dth_television",
                                    "entrance_lobby", "cafeteria", "school");

                            List<String> safety = Arrays.asList("24x7_security", "cctv_video_surveillance", "fire_fighting_systems",
                                    "emergency_rescue_alarms", "entertainment_zone", "burglar_alarm",
                                    "smart_card_access", "intercom_facility", "video_phone", "smoke_heat_sensors",
                                    "swipe_card_access", "multi_tier_security_system", "pedestrian_and_vehicular_traffic_control",
                                    "security_cabin", "gated_community", "internal_street_lights");

                            List<String> leisure = Arrays.asList("sauna", "party_hall", "indoor_games", "Club House", "spa", "mini_theatre",
                                    "multi_brand_retail", "hypermarket", "dance_room", "luxurious_clubhouse", "jacuzzi",
                                    "multiplex", "cards_room", "cafe_coffee_bar", "conference_room", "amphitheater",
                                    "reading_room_library", "food_court", "party_hall_with_outdoor_party_area",
                                    "cafe_and_verandah_lounge", "club_deck", "seating_orchard", "orchard_cafe",
                                    "fragrance_garden", "reflexology_path", "card_room", "waiting_lounge", "fountain", "steam_room");

                            List<String> environment = Arrays.asList("rain_water_harvesting", "sewage_treatment_plant", "eco_friendly",
                                    "large_green_area", "normal_park_central_green", "tree_plaza", "pet_park",
                                    "seating_orchard", "future_development", "signature_seatings", "open_space",
                                    "landscape_garden");

                            List<String> entertainment = Arrays.asList("amphitheatre", "theatre", "bar_chill_out_lounge");

                            List<String> lifestyle = Arrays.asList("pergola", "gazebo", "flower_garden", "terrace_garden", "sun_deck", "Lounge");

                            List<String> sportsAmenities = filterAmenities(sports, amenitiesRow);
                            List<String> convenienceAmenities = filterAmenities(convenience, amenitiesRow);
                            List<String> safetyAmenities = filterAmenities(safety, amenitiesRow);
                            List<String> leisureAmenities = filterAmenities(leisure, amenitiesRow);
                            List<String> environmentAmenities = filterAmenities(environment, amenitiesRow);
                            List<String> entertainmentAmenities = filterAmenities(entertainment, amenitiesRow);
                            List<String> lifestyleAmenities = filterAmenities(lifestyle, amenitiesRow);
                            System.out.println("PropertyId : "+property.getId());
                            System.out.println("Sports Amenities: " + sportsAmenities);
                            System.out.println("Convenience Amenities: " + convenienceAmenities);
                            System.out.println("Safety Amenities: " + safetyAmenities);
                            System.out.println("Leisure Amenities: " + leisureAmenities);
                            System.out.println("Environment Amenities: " + environmentAmenities);
                            System.out.println("Entertainment Amenities: " + entertainmentAmenities);
                            System.out.println("Lifestyle Amenities: " + lifestyleAmenities);
                            

                            List<String> pngAmenityNames = Arrays.asList(
                            "activity_room", "Amphitheatre", "box_football", "business_centre", "cafe_and_verandah_lounge",
                            "Cafeteria", "Car-Parking", "Car-Wash-Area", "Card-Room", "Children's-Play-Area", "club_deck",
                            "co_working_space", "DTH-Television", "Entrance-Lobby", "Flower-Garden", "Fountain", "fragrance_garden",
                            "gate", "Gated-Community", "gazebo", "Gazebo", "Golf-Course", "golf", "Grocery-Shop", "ice-skating-silhouette",
                            "Internal-Street-Lights", "Landscape-Garden", "Laundry", "Lounge", "main_entrance", "multi_tier_security_system",
                            "multipurpose_lawn", "Open-Space", "orchard_cafe", "party_hall", "pavilion_with_seating",
                            "pedestrian_and_vehicular_traffic_control", "Pergola", "pet_park", "pickleball_courts", "plant", "play_courts",
                            "plaza", "raised-bed", "rajpath", "ramp", "reflexology_path", "road", "rock_climbing_wall", "School",
                            "Seating-Orchard", "seating", "Security-Cabin", "senior_citizen_zone", "Shopping-Centre", "signature_seatings",
                            "sports_lawns", "stadium", "stones", "Stream-Room", "Sun-Deck", "swipe_card", "Terrace-garden", "Theatre",
                            "Toddler-Pool", "tower_drop_off", "tree_plaza", "Waiting-Lounge"
                            );
                            String url = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/update/img/icon/";

                            for (String sportsAmenity : sportsAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName("Sports", sportsAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(sportsAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+sportsAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Sports");       
                                    amenity.setAmenitiesName(sportsAmenity);   
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }

                            for (String convenienceAmenity : convenienceAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Convenience", convenienceAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(convenienceAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+convenienceAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Convenience");   
                                    amenity.setAmenitiesName(convenienceAmenity);  
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }

                            for (String safetyAmenity : safetyAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Safety", safetyAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(safetyAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+safetyAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Safety");        
                                    amenity.setAmenitiesName(safetyAmenity);  
                                    amenity.setAmenitiesUrl(fullUrl);   
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String leisureAmenity : leisureAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Leisure", leisureAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(leisureAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+leisureAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Leisure");        
                                    amenity.setAmenitiesName(leisureAmenity);   
                                    amenity.setAmenitiesUrl(fullUrl);  
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String environmentAmenity : environmentAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Environment", environmentAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(environmentAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+environmentAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Environment");        
                                    amenity.setAmenitiesName(environmentAmenity); 
                                    amenity.setAmenitiesUrl(fullUrl);    
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String entertainmentAmenity : entertainmentAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Entertainment", entertainmentAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(entertainmentAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+entertainmentAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Entertainment");        
                                    amenity.setAmenitiesName(entertainmentAmenity); 
                                    amenity.setAmenitiesUrl(fullUrl);    
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            
                            for (String lifestyleAmenity : lifestyleAmenities) 
                            {
                                Amenities amenity = this.amenitiesRepository.findByAmenitiesCategoryAndAmenitiesName
                                ("Lifestyle", lifestyleAmenity);
                                if(amenity == null)
                                {
                                    String extension = pngAmenityNames.contains(lifestyleAmenity) ? ".png" : ".svg";
                                    String fullUrl = url+lifestyleAmenity+extension;

                                    amenity = new Amenities();
                                    amenity.setCreatedDate(new Date().getTime());  
                                    amenity.setAmenitiesCategory("Lifestyle");        
                                    amenity.setAmenitiesName(lifestyleAmenity);     
                                    amenity.setAmenitiesUrl(fullUrl);
                                    this.amenitiesRepository.save(amenity);
                                }
                                amenitiesList.add(amenity);
                            }
                            property.setPropertyAmenities(amenitiesList);
                            this.propertyRepository.save(property);
                            System.out.println("Property Amenities : "+property.getPropertyAmenities().toString());
                        }
                    }
                }
                catch(SQLException e)
                {
                    throw new Exception(e.getMessage());
                }
            }
        }
        String message = "Property Amenities Saved Succesfully";
        return message;
    }
    
    public String saveLocalityInProperty() 
    {
        List<Property> properties = this.propertyRepository.findAll();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) 
        {
            String sql = "SELECT property_address FROM property_table WHERE property_name = ?";
            for (Property property : properties) 
            {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) 
                {
                    preparedStatement.setString(1, property.getPropertyName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            String propertyAddress = resultSet.getString("property_address");
                            if (propertyAddress != null) 
                            {
                                String[] parts = propertyAddress.split(",", 3); 
                                String sectorPart = parts[0].trim(); 
                                String cityPart = (parts.length > 1) ? parts[1].trim() : ""; 
                                String extensionCityPart = (parts.length > 2) ? parts[2].trim() : "";

                                Locality locality = this.localityRepository.findByLocalityNameIgnoringCase(sectorPart);
                                if (locality == null) 
                                {
                                    locality = this.localityRepository.findByLocalityNameIgnoringCase(cityPart);
                                    if(locality == null)
                                    {
                                        locality = this.localityRepository.findByLocalityNameIgnoringCase(extensionCityPart);
                                    }
                                }
                                if (locality != null) 
                                {
                                    property.setLocality(locality);
                                }
                            }
                        }
                    }
                } 
            }
            this.propertyRepository.saveAll(properties);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return "Error while saving localities: " + e.getMessage();
        }
        return "Locality Saved Successfully";
    }

    public String migrateUspsAndMetaKeywords() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "select * from project where project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            List<String> usps = new ArrayList<>();
                            usps.add(resultSet.getString("usp1"));
                            usps.add(resultSet.getString("usp2"));
                            usps.add(resultSet.getString("usp3"));
                            usps.add(resultSet.getString("usp4"));
                            usps.add(resultSet.getString("usp5"));
                            usps.add(resultSet.getString("usp6"));
                            usps = usps.stream().filter(Objects::nonNull).filter(s -> !s.isEmpty()).collect(Collectors.toList());
                            project.setUsp(usps);
                            List<String> metaKeywordsList = new ArrayList<>();
                            String metaKeywords = resultSet.getString("meta_keyword");
                            if (metaKeywords != null) 
                            {
                                metaKeywordsList.addAll(Arrays.asList(metaKeywords.split(","))); 
                                project.setMetaKeywords(metaKeywordsList); 
                            }
                            
                            String launchDate = resultSet.getString("project_launch_date").trim();
                            if (!launchDate.equalsIgnoreCase("coming soon")) 
                            {
                                launchDate = launchDate.replaceAll("\\s+", " ");
                                launchDate = launchDate.replaceAll(",\\s*", ",");
                                launchDate = launchDate.replaceFirst("^\\s+", "");
                                launchDate = launchDate.replaceAll("(?<=[a-zA-Z]) (?=[a-zA-Z])", ", ");
                                launchDate = launchDate.trim();
                            }
                            System.out.println(launchDate);                
                            project.setProjectLaunchDate(launchDate);
                            
                            String possessionDate = resultSet.getString("project_possession_date").trim();
                            possessionDate = possessionDate.replaceAll("\\s+", " ");
                            possessionDate = possessionDate.replaceAll(",\\s*", ",");
                            possessionDate = possessionDate.replaceFirst("^\\s+", "");
                            possessionDate = possessionDate.replaceAll("(?<=[a-zA-Z]) (?=[a-zA-Z])", ", ");
                            possessionDate = possessionDate.trim();
                            System.out.println(possessionDate);
                            project.setProjectPossessionDate(possessionDate);
                            project.setProjectAbout(resultSet.getString("project_about"));
                            project.setProjectRera(resultSet.getString("project_rera"));
                            project.setProjectUnits(resultSet.getString("project_units"));
                            project.setTotalFloor(resultSet.getString("total_floor"));
                            project.setTotalTowers(resultSet.getString("total_tower"));
                            project.setProjectArea(resultSet.getString("project_area"));
                            this.projectRepository.save(project);
                        }
                    }
                }
            }
        }
        return "USPs and Meta Keywords migrated successfully!";
    }

    public String migrateStatusFromProject() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "select * from project where project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            if(resultSet.getString("project_bubble") != null)
                            {
                                String statusData = resultSet.getString("project_bubble");
                                ProjectStatus projectStatus;
                                if(statusData.toLowerCase().contains("Ready To Move".toLowerCase()))
                                {
                                    projectStatus = ProjectStatus.READY_TO_MOVE;
                                }
                                else if(statusData.toLowerCase().contains("Under Construction".toLowerCase()))
                                {
                                    projectStatus = ProjectStatus.UNDER_CONSTRUCTION;
                                }
                                else if(statusData.toLowerCase().contains("OC Received".toLowerCase()))
                                {
                                    projectStatus = ProjectStatus.READY_TO_MOVE;
                                }
                                else if(statusData.toLowerCase().contains("New Launch".toLowerCase()))
                                {
                                    projectStatus = ProjectStatus.NEW_LAUNCH;
                                }
                                else
                                {
                                    projectStatus = ProjectStatus.PRE_LAUNCH;
                                }
                                project.setStatus(projectStatus);
                            }
                            this.projectRepository.save(project);
                        }
                    }
                }
            }
        }
        return "Project Status Migrated successfully!";
    }

    public String migrateProjectReraInformation() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
    
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "SELECT project_rera.id, project_rera.phase, project_rera.status, " +
                "project_rera.project_rera_name, project_rera.rera_number, project.id, " +
                "project.project_name FROM project_rera, project " +
                "WHERE project_rera.project_id = project.id;"; 
    
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) 
                {
                    while (resultSet.next()) 
                    { 
                        String projectName = resultSet.getString("project_name");
    
                        if (project.getProjectName().equals(projectName)) 
                        { 
                            System.out.println("RunningTime");
                            String projectReraName = resultSet.getString("project_rera_name");
                            String reraNumber = resultSet.getString("rera_number");
                            String phase = resultSet.getString("phase");
                            String status = resultSet.getString("status");
    
                            ReraInfo reraInfo = new ReraInfo();
                            reraInfo.setCreatedOn(new Date().getTime());
                            reraInfo.setReraNumber(reraNumber);
                            reraInfo.setProjectReraName(projectReraName);
                            reraInfo.setPhase(Arrays.asList(phase));
                            reraInfo.setStatus(status);
                            reraInfo.setProject(project);
                            reraInfo.setUser(this.userRepository.findById(1L)
                                .orElseThrow(() -> new Exception("User not found")));
                            this.reraInfoReposiotry.save(reraInfo);
                        }
                    }
                }
            }
        }
        return "Rera migrated successfully!";
    }

    public String migrateProjectFaqs() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "SELECT project_faqs.question, project_faqs.answer, project.project_name, project.id " +
                "FROM project_faqs, project " +
                "WHERE project.id = project_faqs.project_id AND project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        while (resultSet.next()) 
                        {
                            String projectName = resultSet.getString("project_name");
                            if (project.getProjectName().equals(projectName)) 
                            {
                                String question = resultSet.getString("question");
                                String answer = resultSet.getString("answer");
                                if (question != null && answer != null) 
                                {
                                    Faq faq = new Faq();
                                    faq.setQuestion(question);
                                    faq.setAnswer(answer);
                                    faq.setProject(project);
                                    this.faqRepository.save(faq);
                                    System.out.println("ProjectName : "+projectName + " Question : "+question + " Answer : "+answer);
                                } 
                                else 
                                {
                                    System.out.println("Question or Answer is null for project: " + projectName);
                                }
                            }
                        }
                    }
                }
            }
        }
        return "FAQs migrated successfully!";
    }
    

    public String migrateParagraph() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "SELECT project_con.*, project.project_name FROM project_con JOIN project ON project.id = project_con.project_id WHERE project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            project.setOverviewPara(resultSet.getString("overview_para"));
                            project.setFloorPara(resultSet.getString("floor_para"));
                            project.setPriceListPara(resultSet.getString("price_list_para"));
                            project.setPaymentPara(resultSet.getString("payment_para"));
                            project.setAmenitiesPara(resultSet.getString("amenities_para"));
                            project.setVideoPara(resultSet.getString("video_para"));
                            project.setSiteplanPara(resultSet.getString("siteplan_para"));
                            project.setWhyPara(resultSet.getString("why_para"));
                            project.setLocationMap(resultSet.getString("location_map"));
                            project.setLocationPara(resultSet.getString("location_para"));
                            System.out.println("project id : "+project.getId());
                            this.projectRepository.save(project);
                        }
                    }
                }
            }
        }
        return "Paras migrated successfully!";
    }

    public String migrateUpdateOfDevelopers() throws Exception 
    {
        List<Developer> allDevelopers = this.developerRepository.findAll();
        for (Developer developer : allDevelopers) 
        {
            if (developer != null) 
            {
                String query = "SELECT * from project_developers where developer_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, developer.getDeveloperName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            String totalProjects = resultSet.getString("dev_total_project");
                            if (totalProjects != null && !totalProjects.trim().isEmpty()) 
                            {
                                totalProjects = totalProjects.trim().replaceAll("[^\\d]", "");
                                developer.setProjectDoneNo(!totalProjects.isEmpty() ? Long.parseLong(totalProjects) : null);
                            } 

                            System.out.println("Developer Name : "+developer.getDeveloperName() + "Toal Projects : "+ developer.getProjectDoneNo());
                            String establishedYear = resultSet.getString("dev_established");
                            if (establishedYear != null && !establishedYear.trim().isEmpty()) 
                            {
                                establishedYear = establishedYear.trim().replaceAll("[^\\d]", "");
                                developer.setEstablishedYear(!establishedYear.isEmpty() ? Long.parseLong(establishedYear) : null);
                            } 
                            this.developerRepository.save(developer);
                        }
                    }
                }
            }
        }
        return "Developer updates migrated successfully!";
    }

    public String migrateProjectSchema() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "select * from project where project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {String projectSchema = resultSet.getString("product_schema");
                        if (projectSchema != null) 
                        {
                            project.setProjectSchema(Arrays.asList(projectSchema.split("</script>,")));
                        } 
                        else 
                        {
                            project.setProjectSchema(Collections.emptyList()); 
                        }
                            this.projectRepository.save(project);
                        }
                    }
                }
            }
        }
        return "Schema migrated successfully!";
    }

    public String migrateBlogDescription() throws Exception 
    {
        List<Blogs> allBlogs = this.blogsRepository.findAll();
        for (Blogs blog : allBlogs) 
        {
            if (blog != null) 
            {
                String query = "select * from blogs where url = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, blog.getBlogUrl());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        if (resultSet.next()) 
                        {
                            String subHeading = resultSet.getString("blog_des");
                            if (subHeading != null) 
                            {
                                blog.setSubHeadings(subHeading);
                            } 
                            else 
                            {
                                blog.setSubHeadings("null"); 
                            }
                            System.out.println("SubHeadings : "+blog.getSubHeadings());
                            this.blogsRepository.save(blog);
                        }
                    }
                }
            }
        }
        return "Blogs migrated successfully!";
    }
    

    public String migratePaymentPlans() throws Exception 
    {
        List<Project> allProjects = this.projectRepository.findAll();
        for (Project project : allProjects) 
        {
            if (project != null) 
            {
                String query = "select text,plan from project_rooms, project where project_rooms.project_id = project.id and project.project_name = ?";
                try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) 
                {
                    preparedStatement.setString(1, project.getProjectName());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) 
                    {
                        while (resultSet.next()) 
                        {
                            String planName = resultSet.getString("text");
                            String details = resultSet.getString("plan");
                            if (planName != null && details != null) 
                            {
                                System.out.println("Plan Name : "+planName + "Details : "+details);
                                PaymentPlan payPlan = new PaymentPlan();
                                payPlan.setPaymentPlanName(planName);
                                payPlan.setPaymentPlanValue(details);
                                payPlan.setProject(project);
                                this.paymentPlanRepository.save(payPlan);
                            } 
                            else 
                            {
                                System.out.println("planName or details is null for project: " + project.getProjectName());
                            }
                        }
                    }
                }
            }
        }
        return "Schema migrated successfully!";
    }

}
