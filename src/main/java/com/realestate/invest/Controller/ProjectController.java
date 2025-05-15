package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.DTOModel.ProjectDTO;
import com.realestate.invest.EnumAndJsons.ProjectInfo;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Service.ProjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
public class ProjectController 
{
    
    @Autowired
    private ProjectService projectService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewProject(@RequestBody ProjectDTO projectDTO) 
    {
        try 
        {
            Project project = this.projectService.saveNewProject(projectDTO);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long Id) 
    {
        try 
        {
            Project project = this.projectService.findById(Id);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/name")
    public ResponseEntity<?> getProjectsByName(@RequestParam String name) 
    {
        try 
        {
            List<Project> projects = this.projectService.findByName(name);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long Id, @RequestBody ProjectDTO projectDto) 
    {
        try 
        {
            Project project = this.projectService.updateProjectById(Id, projectDto);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long Id) 
    {
        try 
        {
            String result = this.projectService.deleteProjectById(Id);
            SuccessResponse successResponse = new SuccessResponse(result);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllProjects(@RequestParam(required = false) Boolean isPriority,
    @RequestParam(required = false) Boolean isPremium,
    @RequestParam(required = false) Boolean isFeatured,
    @RequestParam(required = false, defaultValue = "false") Boolean isDeleted,
    @RequestParam(required = false) ProjectStatus status,
    @RequestParam(required = false) Long developerId,
    @RequestParam(required = false) Long cityId,
    @RequestParam(required = false) Long localityId, 
    @RequestParam(required = false) String name,
    @RequestParam(required = false) PropertyType type,
    @RequestParam(required = false) List<String> configurations,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)
    {
        try 
        {
            Page<Project> projects = this.projectService.getAllProjects(isPriority, isPremium, isFeatured, isDeleted, status, developerId, cityId, 
            localityId, name, type, configurations, PageRequest.of(page, size));
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> getProjectByUrl(@PathVariable String url) 
    {
        try 
        {
            Project project = this.projectService.findByurl(url);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/compare")
    public ResponseEntity<?> findByIdIn(@RequestParam List<Long> projectIds) 
    {
        try 
        {
            List<Project> projects = this.projectService.findByIdIn(projectIds);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getProjectInfos(@RequestParam(required = false, defaultValue = "false") Boolean isDeleted) 
    {
        try 
        {
            List<ProjectInfo> projectInfos = this.projectService.getProjectInfos(isDeleted);
            return new ResponseEntity<>(projectInfos, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/payment-plans")
    public ResponseEntity<?> getAllPayPlans(@RequestParam(required = false) Long projectId) 
    {
        try 
        {
            List<PaymentPlan> payPlans = this.projectService.getAllPayPlans(projectId);
            return new ResponseEntity<>(payPlans, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all/faqs")
    public ResponseEntity<?> getAllFaqs(@RequestParam(required = false) Long projectId) 
    {
        try 
        {
            List<Faq> faqs = this.projectService.getAllFaqs(projectId);
            return new ResponseEntity<>(faqs, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


}
