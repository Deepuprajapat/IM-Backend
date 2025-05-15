package com.realestate.invest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.DTOModel.BlogsDTO;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Blogs;
import com.realestate.invest.Service.BlogsService;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blogs")
public class BlogsController 
{

    @Autowired
    private BlogsService blogsService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewBlogs(@RequestBody BlogsDTO blogsDTO) 
    {
        try 
        {
            Blogs blogs = blogsService.saveNewBlogs(blogsDTO);
            return new ResponseEntity<>(blogs, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> getById(@PathVariable Long Id) 
    {
        try 
        {
            Blogs blogs = blogsService.getById(Id);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateById(@PathVariable Long Id, @RequestBody BlogsDTO blogsDTO) 
    {
        try 
        {
            Blogs blogs = blogsService.updateById(Id, blogsDTO);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteBlogsById(@PathVariable Long Id) 
    {
        try 
        {
            SuccessResponse successResponse = new SuccessResponse(blogsService.deleteBlogsById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/set/priority/by/id/{Id}")
    public ResponseEntity<?> setPriorityById(@PathVariable Long Id, @NotNull @RequestParam Boolean isPriority) 
    {
        try 
        {
            Blogs blogs = blogsService.setPriorityById(Id, isPriority);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable String url) 
    {
        try 
        {
            Blogs blogs = blogsService.getByUrl(url);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllBlogs(@RequestParam(required = false) Boolean isPriority,
    @RequestParam(required = false) Long startDate,
    @RequestParam(required = false) Long endDate,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size) 
    {
        try 
        {
            Page<Blogs> blogsPage = blogsService.getAll(isPriority, startDate, endDate, PageRequest.of(page, size));
            return new ResponseEntity<>(blogsPage, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


}

