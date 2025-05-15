package com.realestate.invest.ServiceImpl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.BlogsDTO;
import com.realestate.invest.Model.Blogs;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.BlogsRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.BlogsService;
import com.realestate.invest.Utils.OTPGenerator;

@Service
public class BlogsServiceImpl implements BlogsService 
{

    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Blogs saveNewBlogs(BlogsDTO blogsDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Blogs blogs = new Blogs();
        blogs.setCreatedDate(new Date().getTime());
        blogs.setHeadings(blogsDTO.getHeadings());
        blogs.setSubHeadings(blogsDTO.getSubHeadings());
        blogs.setDescription(blogsDTO.getDescription());
        blogs.setImages(blogsDTO.getImages());
        blogs.setAlt(blogsDTO.getAlt());
        blogs.setCanonical(blogsDTO.getCanonical());
        blogs.setBlogSchema(blogsDTO.getBlogSchema());
        blogs.setMetaTitle(blogsDTO.getMetaTitle());
        blogs.setMetaKeywords(blogsDTO.getMetaKeywords());
        
        String lowerCase = blogs.getHeadings().toLowerCase();
        String blogUrl = lowerCase.replaceAll("\\s", "-");
        Blogs existingUrl = this.blogsRepository.findByBlogUrl(blogUrl);
        String newBlogsUrl = (existingUrl != null) ? (blogUrl + OTPGenerator.generateUUID().toString().substring(13)) : blogUrl;
        
        blogs.setBlogUrl(newBlogsUrl.replace("?", ""));
        blogs.setPriority(blogsDTO.isPriority());
        blogs.setUser(user);
        return this.blogsRepository.save(blogs);
    }

    @Override
    public Blogs getById(Long Id) throws Exception 
    {
        Blogs blogs = this.blogsRepository.findById(Id).orElseThrow(() -> new Exception("Blogs not found"));
        return blogs;
    }

    @Override
    public Blogs updateById(Long Id, BlogsDTO blogsDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Blogs blogs = this.blogsRepository.findById(Id).orElseThrow(() -> new Exception("Blogs not found"));
        blogs.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(blogsDTO.getCreatedDate()).ifPresent(blogs::setCreatedDate);
        Optional.ofNullable(blogsDTO.getSubHeadings()).ifPresent(blogs::setSubHeadings);
        Optional.ofNullable(blogsDTO.getDescription()).ifPresent(blogs::setDescription);
        Optional.ofNullable(blogsDTO.getImages()).ifPresent(blogs::setImages);
        Optional.ofNullable(blogsDTO.getAlt()).ifPresent(blogs::setAlt);
        Optional.ofNullable(blogsDTO.getCanonical()).ifPresent(blogs::setCanonical);
        Optional.ofNullable(blogsDTO.getBlogSchema()).ifPresent(blogs::setBlogSchema);
        Optional.ofNullable(blogsDTO.isPriority()).ifPresent(blogs::setPriority);

        if(blogsDTO.getHeadings() != null && !blogsDTO.getHeadings().equals(blogs.getHeadings()))
        {
            Optional.ofNullable(blogsDTO.getHeadings()).ifPresent(blogs::setHeadings);
            String lowerCase = blogs.getHeadings().toLowerCase();
            String blogUrl = lowerCase.replaceAll("\\s", "-");
            Blogs existingUrl = this.blogsRepository.findByBlogUrl(blogUrl);
            String newBlogsUrl = (existingUrl != null) ? (blogUrl + OTPGenerator.generateUUID().toString().substring(13)) : blogUrl;
            blogs.setBlogUrl(newBlogsUrl.replace("?", ""));
        }

        blogs.setUser(user);
        return this.blogsRepository.save(blogs);
    }

    @Override
    public String deleteBlogsById(Long Id) throws Exception 
    {
        Blogs blogs = this.blogsRepository.findById(Id).orElseThrow(() -> new Exception("Blogs not found"));
        this.blogsRepository.delete(blogs);
        String message = "Blogs deleted successfully";
        return message;
    }

    @Override
    public Blogs setPriorityById(Long Id, Boolean isPriority) throws Exception 
    {
        Blogs blogs = this.blogsRepository.findById(Id).orElseThrow(() -> new Exception("Blogs not found"));
        blogs.setUpdatedDate(new Date().getTime());
        if(isPriority != null) blogs.setPriority(isPriority);
        return this.blogsRepository.save(blogs);
    }

    @Override
    public Blogs getByUrl(String url) throws Exception 
    {
        Blogs blogs = this.blogsRepository.findByBlogUrl(url);
        if(blogs == null) throw new Exception("No data found");
        return blogs;
    }

    @Override
    public Page<Blogs> getAll(Boolean isPriority, Long startDate, Long endDate, Pageable pageable) throws Exception 
    {
        List<Blogs> blogs = this.blogsRepository.findAll();
        List<Blogs> filteredBlogs = blogs.stream()
        .filter(blog -> (isPriority == null || blog.isPriority() == isPriority) &&
        (startDate == null || blog.getCreatedDate() >= startDate) &&
        (endDate == null || blog.getCreatedDate() <= endDate))
        .sorted(Comparator.comparing(Blogs::isPriority)
        .reversed() 
        .thenComparing(Comparator.comparing(Blogs::getCreatedDate).reversed()))  
        .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredBlogs.size());
        return new PageImpl<>(filteredBlogs.subList(start, end), pageable, filteredBlogs.size());
    }
    
}
