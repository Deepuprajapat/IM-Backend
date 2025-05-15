package com.realestate.invest.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.LeadsDTO;
import com.realestate.invest.Model.Leads;

@Component
public interface LeadsService 
{
    String saveNewLeads(LeadsDTO leadsDTO) throws Exception;

    Leads getById(Long Id) throws Exception;

    Leads updateLeadsById(Long Id, LeadsDTO leadsDTO) throws Exception;

    String deleteById(Long Id) throws Exception;
    
    Page<Leads> getAll(String project, String phone, Long startDate, Long endDate, Pageable pageable) throws Exception;

    Leads findByPhone(String phone) throws Exception;

    String validateOTP(String phone, String OTP) throws Exception;

    String resendOTP(String phone) throws Exception;

    Leads saveNewLeadsWithoutOTP(LeadsDTO leadsDTO) throws Exception;

    Page<Leads> getAllLeadsByUser(String search, Long startDate, Long endDate, Pageable pageable) throws Exception;

}
