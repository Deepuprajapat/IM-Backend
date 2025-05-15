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
import com.realestate.invest.Model.Testimonials;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.TestimonialsRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.TestimonialsService;

@Service
public class TestimonialsServiceImpl implements TestimonialsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    @Override
    public Testimonials saveNewTestimonials(Testimonials testimonialsDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Testimonials testimonials = new Testimonials();
        testimonials.setCreatedDate(new Date().getTime());
        testimonials.setName(testimonialsDTO.getName());
        double roundedRating = Math.round(testimonialsDTO.getRating());
        testimonials.setRating(roundedRating);
        testimonials.setType(testimonialsDTO.getType());
        testimonials.setDescription(testimonialsDTO.getDescription());
        testimonials.setApproved(testimonialsDTO.isApproved());
        testimonials.setUser(user);
        return this.testimonialsRepository.save(testimonials);
    }

    @Override
    public Testimonials getById(Long Id) throws Exception 
    {
        Testimonials testimonials = this.testimonialsRepository.findById(Id).orElseThrow(() -> new Exception("Data not found"));
        return testimonials;
    }

    @Override
    public Testimonials updateById(Long Id, Testimonials testimonialsDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Testimonials testimonials = this.testimonialsRepository.findById(Id).orElseThrow(() -> new Exception("Data not found"));

        testimonials.setUpdatedDate(new Date().getTime()); 
        Optional.ofNullable(testimonialsDTO.getDescription()).ifPresent(testimonials::setDescription);
        Optional.ofNullable(testimonialsDTO.getName()).ifPresent(testimonials::setName);
        Optional.ofNullable(testimonialsDTO.getType()).ifPresent(testimonials::setType);
        Optional.ofNullable(testimonialsDTO.getRating())
        .ifPresent(rating -> testimonials.setRating((double) Math.round(rating)));

        Optional.ofNullable(testimonialsDTO.isApproved()).ifPresent(testimonials::setApproved);
        testimonials.setUser(user);
        return this.testimonialsRepository.save(testimonials);
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        Testimonials testimonials = this.testimonialsRepository.findById(Id).orElseThrow(() -> new Exception("Data not found"));
        this.testimonialsRepository.delete(testimonials);
        String message = "Data deleted succesfully";
        return message;
    }

    @Override
    public Testimonials approveById(Long Id, Boolean isApproved) throws Exception 
    {
        Testimonials testimonials = this.testimonialsRepository.findById(Id).orElseThrow(() -> new Exception("Data not found"));
        testimonials.setUpdatedDate(new Date().getTime());
        if(isApproved != null) testimonials.setApproved(isApproved);
        return this.testimonialsRepository.save(testimonials);
    }

    @Override
    public Page<Testimonials> getAll(Boolean isApproved, Long startDate, Long endDate, Pageable pageable) throws Exception 
    {
        List<Testimonials> testimonials = this.testimonialsRepository.findAll();
        List<Testimonials> filteredList = testimonials.stream()
        .filter(tst -> (startDate == null || tst.getCreatedDate() >= startDate) &&
        (endDate == null || tst.getCreatedDate() <= endDate) &&
        (isApproved == null || tst.isApproved() == isApproved))
        .sorted(Comparator.comparing(Testimonials::isApproved)
        .reversed()
        .thenComparing(Comparator.comparing(Testimonials::getCreatedDate).reversed()))
        .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());
        return new PageImpl<>(filteredList.subList(start, end), pageable, filteredList.size());
    }
    
}
