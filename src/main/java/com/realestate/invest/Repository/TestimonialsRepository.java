package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Testimonials;

public interface TestimonialsRepository extends JpaRepository<Testimonials, Long>
{
    
}
