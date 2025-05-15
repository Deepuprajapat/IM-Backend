package com.realestate.invest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Utils.MigrationService;

/**
 * @Controller class responsible for handling requests related to data migration.
 * 
 * <p>This controller provides REST endpoints to trigger data migration processes
 * managed by the {@link MigrationService}.</p>
 * 
 * @author Abhishek Srivastav
 */
@RestController
@RequestMapping("/data-migration")
public class MigrationController 
{
    @Autowired
    private MigrationService migrationService;

    /**
     * @Endpoint to trigger the migration of projects from an external database.
     * 
     * <p>This method calls the {@link MigrationService#migrateProjects()} method to 
     * perform the migration process and returns the status of the operation.</p>
     * 
     * @return A {@link ResponseEntity} containing the response message and HTTP status:
     *   - {@code HttpStatus.CREATED} if migration is successful.
     *   - {@code HttpStatus.INTERNAL_SERVER_ERROR} if an error occurs during migration.
     */
    @PostMapping("/save/project")
    public ResponseEntity<?> migrateProjects()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateProjects());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/save/developer")
    public ResponseEntity<?> migrateDevelopers()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateDevelopers());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/properties")
    public ResponseEntity<?> migrateProperties()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateProperties());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/blogs")
    public ResponseEntity<?> migrateBlogs()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateBlogs());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/amenities")
    public ResponseEntity<?> migrateAmenities()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateAmenities());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/property/amenities")
    public ResponseEntity<?> migrateAmenitiesInProperty()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateAmenitiesInProperty());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/property/locality")
    public ResponseEntity<?> saveLocalityInProperty()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.saveLocalityInProperty());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/project/keywords-usps")
    public ResponseEntity<?> migrateUspsAndMetaKeywords()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateUspsAndMetaKeywords());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/project/project-status")
    public ResponseEntity<?> migrateStatusFromProject()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateStatusFromProject());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/rera")
    public ResponseEntity<?> migrateProjectReraInformation()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateProjectReraInformation());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/faqs")
    public ResponseEntity<?> migrateProjectFaqs()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateProjectFaqs());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/save/para")
    public ResponseEntity<?> migrateParagraph()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateParagraph());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/developer")
    public ResponseEntity<?> migrateUpdateOfDevelopers()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateUpdateOfDevelopers());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/project/schema")
    public ResponseEntity<?> migrateProjectSchema()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateProjectSchema());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/blog/subheadings")
    public ResponseEntity<?> migrateBlogDescription()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migrateBlogDescription());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/project/payment-plan")
    public ResponseEntity<?> migratePaymentPlans()
    {
        try
        {
            SuccessResponse migratedDataResponse = new SuccessResponse(this.migrationService.migratePaymentPlans());
            return new ResponseEntity<>(migratedDataResponse, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
