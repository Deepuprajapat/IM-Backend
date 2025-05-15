package com.realestate.invest.EnumAndJsons;


public class BackUp 
{
    // Project 
    // String prefixWithSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/";
    // String prefixWithoutSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images";

    // for (Project project : projectsPage.getContent()) 
    // {
    //     String projectLogo = project.getProjectLogo();
        
    //         String newProjectLogo = projectLogo.startsWith("/")
    //                 ? prefixWithoutSlash + projectLogo
    //                 : prefixWithSlash + projectLogo;
    //     Map<String, String> oldImages = project.getImages();
    //     Map<String, String> updatedImages = new HashMap<>();
    //     for (Map.Entry<String, String> entry : oldImages.entrySet()) 
    //     {
    //         String oldKey = entry.getKey();
    //         String value = entry.getValue();
    //         String newKey = oldKey.startsWith("/")
    //                 ? prefixWithoutSlash + oldKey
    //                 : prefixWithSlash + oldKey;
    //         updatedImages.put(newKey.trim(), value);
    //     }
    //     project.setImages(updatedImages);
        // project.setProjectLogo(newProjectLogo);
        // this.projectRepository.save(project);
    // }   

    // Blogs 
    // for(Blogs blog : blogs)
        // {
        //     String prefixWithSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/";
        //     String prefixWithoutSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images";
        //     List<String> images = blog.getImages();
        //     List<String> updatedImages = new ArrayList<>();
        //     for (String image : images) 
        //     {
        //         String newImage = image.startsWith("/")
        //                 ? prefixWithoutSlash + image
        //                 : prefixWithSlash + image;
        //         updatedImages.add(newImage.trim());
        //     }
        //     blog.setImages(updatedImages);
        //     this.blogsRepository.save(blog);
        // }

    // Property
    // for (Property property : properties.getContent()) 
        // {
        //     String prefixWithSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/";
        //     String prefixWithoutSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images";
        //     List<String> images = property.getImages();
        //     List<String> updatedImages = new ArrayList<>();
        //     for (String image : images) 
        //     {
        //         String newImage = image.startsWith("/")
        //                 ? prefixWithoutSlash + image
        //                 : prefixWithSlash + image;
        //         updatedImages.add(newImage.trim());
        //     }

        //     String logoImage = property.getLogoImage();
        //     String newImage = logoImage.startsWith("/")
        //             ? prefixWithoutSlash + logoImage
        //             : prefixWithSlash + logoImage;

        //     String floorPlan2D = property.getFloorImage2D();
        //     String floorPlan2DImage = floorPlan2D.startsWith("/")
        //             ? prefixWithoutSlash + floorPlan2D
        //             : prefixWithSlash + floorPlan2D;

        //     String floorPlan3D = property.getFloorImage3D();
        //     String floorPlan3DImage = floorPlan3D.startsWith("/")
        //             ? prefixWithoutSlash + floorPlan3D
        //             : prefixWithSlash + floorPlan3D;

        //     property.setFloorImage3D(floorPlan3DImage);
        //     property.setFloorImage2D(floorPlan2DImage);
        //     property.setImages(updatedImages);
        //     property.setLogoImage(newImage);
        //     this.propertyRepository.save(property);
        // }

    // Floorplan
     // for(Floorplan floorplan : floorplans)
        // {
        //     String prefixWithSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/";
        //     String prefixWithoutSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images";
                
        //     String oldImage = floorplan.getImgUrl();
        //     if(oldImage.startsWith("/") || oldImage.startsWith("img"))
        //     {
        //         String newImage = oldImage.startsWith("/")
        //                 ? prefixWithoutSlash + oldImage
        //                 : prefixWithSlash + oldImage;
        //         floorplan.setImgUrl(newImage.trim());
        //         this.floorPlanRepository.save(floorplan);
        //     }
        // }

    // Developer 
    // for(Developer developer : developers)
        // {
        //     String prefixWithSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images/";
        //     String prefixWithoutSlash = "https://myimwebsite.s3.ap-south-1.amazonaws.com/images";
        //     String developerImage = developer.getDeveloperLogo();
        //     String newImage = developerImage.startsWith("/")
        //             ? prefixWithoutSlash + developerImage
        //             : prefixWithSlash + developerImage;
        //     developer.setDeveloperLogo(newImage.trim());
        //     this.developerRepository.save(developer);
        // }

    // @GetMapping("/get/by/path/data/{path}")
    // public ResponseEntity<?> getByPathData(@PathVariable String path) 
    // {
    //     try 
    //     {
    //         GenericKeywords genericKeywords = this.genericKeywordsService.getByPath(path);
    //         if (genericKeywords == null) 
    //         {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No match found");
    //         }

    //         String queryParams = genericKeywords.getUrl();
    //         if (queryParams.startsWith("allProjects?")) 
    //         {
    //             queryParams = queryParams.replace("allProjects?", "")
    //             .replace("search", "name")
    //             .replace("locationId", "cityId");
    //         }

    //         String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    //         String apiUrl = baseUrl + "/project/get/all?" + queryParams + "&size=100";
    //         System.out.println("apiUrl: " + apiUrl);

    //         HttpHeaders headers = new HttpHeaders();
    //         headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    //         HttpEntity<String> entity = new HttpEntity<>(headers);
    //         ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

    //         MediaType contentType = response.getHeaders().getContentType();
    //         if (contentType != null && contentType.equals(MediaType.TEXT_HTML)) 
    //         {
    //             return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    //             .body("Unexpected response type: text/html");
    //         }
    //         return ResponseEntity.ok(response.getBody());
    //     } 
    //     catch (HttpClientErrorException | HttpServerErrorException ex) 
    //     {
    //         return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
    //     } 
    //     catch (Exception ex) 
    //     {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //         .body("Failed to fetch data: " + ex.getMessage());
    //     }
    // }

}
