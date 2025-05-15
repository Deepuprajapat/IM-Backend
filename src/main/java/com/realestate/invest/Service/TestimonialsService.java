package com.realestate.invest.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.Testimonials;

@Component
public interface TestimonialsService 
{
    Testimonials saveNewTestimonials(Testimonials testimonialsDTO) throws Exception;

    Testimonials getById(Long Id) throws Exception;

    Testimonials updateById(Long Id, Testimonials testimonialsDTO) throws Exception;

    String deleteById(Long Id) throws Exception;

    Testimonials approveById(Long Id, Boolean isApproved) throws Exception;

    Page<Testimonials> getAll(Boolean isApproved, Long startDate, Long endDate, Pageable pageable) throws Exception;
}
