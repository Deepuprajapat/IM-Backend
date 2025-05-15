package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.realestate.invest.DTOModel.LeadsDTO;
import com.realestate.invest.Model.Leads;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.LeadsRepository;
import com.realestate.invest.Repository.PropertyRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.LeadsService;
import com.realestate.invest.Utils.LMSUtils;
import com.realestate.invest.Utils.OTPGenerator;
import com.realestate.invest.Utils.OTPValidationUtility;

@Service
public class LeadsServiceImpl implements LeadsService
{

    @Autowired
    private LeadsRepository leadsRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveNewLeads(LeadsDTO leadsDTO) throws Exception
    {
        String message = "OTP Send Successfully";
        Leads leads = this.leadsRepository.findByPhone(leadsDTO.getPhone().trim());
        String otp = OTPGenerator.generateOTP(6);
        if(leads != null)
        {
            List<String> projectName = leads.getProjectName();
            if(leadsDTO.getPropertyId() != null) leads.setProperty(this.propertyRepository.findById(leadsDTO.getPropertyId())
            .orElse(null));

            List<String> updatedDate = leads.getUpdatedDate() != null ? new ArrayList<>(leads.getUpdatedDate()) : new ArrayList<>();
            updatedDate.add(String.valueOf(new Date().getTime()));
            leads.setUpdatedDate(updatedDate);
            leads.setFrequency(leads.getFrequency() + 1);
            projectName.add(leadsDTO.getProjectName());
            leads.setProjectName(projectName);
            leads.setOtp(otp);
            
            List<String> sourceName = leads.getSource() != null ? new ArrayList<>(leads.getSource()) : new ArrayList<>();
            sourceName.add(leadsDTO.getSource());
            leads.setSource(sourceName);
            Optional.ofNullable(leadsDTO.getUserType()).ifPresent(leads::setUserType);
            Leads savedLeads = this.leadsRepository.save(leads);
            OTPValidationUtility.sendSms(savedLeads.getPhone(), otp);
            LMSUtils.sendLeadsToExternalAPI(savedLeads);
            return message;
        }
        leads = new Leads();
        leads.setCreatedDate(new Date().getTime());
        if(leadsDTO.getPropertyId() != null) leads.setProperty(this.propertyRepository.findById(leadsDTO.getPropertyId())
        .orElse(null));
        leads.setName(leadsDTO.getName());
        leads.setPhone(leadsDTO.getPhone());
        leads.setEmail(leadsDTO.getEmail());
        leads.setMessage(leadsDTO.getMessage());
        leads.setProjectName(Arrays.asList(leadsDTO.getProjectName()));
        leads.setSource(Arrays.asList(leadsDTO.getSource()));
        Optional.ofNullable(leadsDTO.getUserType()).ifPresent(leads::setUserType);
        leads.setOtp(otp);
        leads.setFrequency(1L);
        Leads savedLeads = this.leadsRepository.save(leads);
        LMSUtils.sendLeadsToExternalAPI(savedLeads);
        OTPValidationUtility.sendSms(leadsDTO.getPhone(), otp);
        return message;
    }

    @Override
    public Leads getById(Long Id) throws Exception 
    {
        Leads leads = this.leadsRepository.findById(Id).orElseThrow(() -> new Exception("Leads Not Found"));
        return leads;
    }

    @Override
    public Leads updateLeadsById(Long Id, LeadsDTO leadsDTO) throws Exception 
    {
        Leads leads = this.leadsRepository.findById(Id).orElseThrow(() -> new Exception("Leads Not Found"));
        List<String> updatedDate = leads.getUpdatedDate();
        updatedDate.add(String.valueOf(new Date().getTime()));

        if(leadsDTO.getPropertyId() != null) leads.setProperty(this.propertyRepository.findById(leadsDTO.getPropertyId())
        .orElse(null));
        Optional.ofNullable(leadsDTO.getName()).ifPresent(leads::setName);
        Optional.ofNullable(leadsDTO.getPhone()).ifPresent(leads::setPhone);
        Optional.ofNullable(leadsDTO.getEmail()).ifPresent(leads::setEmail);
        Optional.ofNullable(leadsDTO.getProjectName()).ifPresent(projectName -> leads.setProjectName(Arrays.asList(projectName)));
        Optional.ofNullable(leadsDTO.getSource()).ifPresent(sourceName -> leads.setSource(Arrays.asList(sourceName)));
        Optional.ofNullable(leadsDTO.getUserType()).ifPresent(leads::setUserType);
        Optional.ofNullable(leadsDTO.getMessage()).ifPresent(leads::setMessage);
        return this.leadsRepository.save(leads);        
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        Leads leads = this.leadsRepository.findById(Id).orElseThrow(() -> new Exception("Leads Not Found"));
        this.leadsRepository.delete(leads);
        String message = "Leads Deleted Successfully";
        return message;
    }

    @Override
    public Page<Leads> getAll(String project, String phone, Long startDate, Long endDate, Pageable pageable) throws Exception 
    {
        List<Leads> leads = this.leadsRepository.findAll();
        List<Leads> filteredLeads = leads.stream()
        .filter(lead -> project == null || lead.getProjectName().contains(project.toLowerCase()))
        .filter(lead -> phone == null || lead.getPhone().contains(phone))
        .filter(lead ->  (startDate == null || lead.getCreatedDate() >= startDate) &&
        (endDate == null || lead.getCreatedDate() <= endDate))
        .sorted(Comparator.comparing(Leads::getCreatedDate).reversed())
        .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredLeads.size());
        return new PageImpl<>(filteredLeads.subList(start, end), pageable, filteredLeads.size());
    }

    @Override
    public Leads findByPhone(String phone) throws Exception 
    {
        Leads leads = this.leadsRepository.findByPhone(phone);
        if(leads == null) throw new Exception("Leads Not Found");
        return leads;
    }

    @Override
    public String validateOTP(String phone, String OTP) throws Exception
    {
        Leads leads = this.leadsRepository.findByPhoneAndOtp(phone, OTP);
        if(leads == null) throw new Exception("OTP Validation Failed.");
        leads.setOtp(null);
        this.leadsRepository.save(leads);
        String message = "OTP Validated Successfully";
        return message;
    }

    @Override
    public String resendOTP(String phone) throws Exception 
    {
        Leads leads = this.leadsRepository.findByPhone(phone);
        if(leads == null) throw new Exception("Please Fill The Forms Again");
        String otp = OTPGenerator.generateOTP(6);
        leads.setOtp(otp);
        OTPValidationUtility.sendSms(phone, otp);
        this.leadsRepository.save(leads);
        String message = "OTP Send Successfully";
        return message;
    }

    @Override
    public Leads saveNewLeadsWithoutOTP(LeadsDTO leadsDTO) throws Exception 
    {
        Leads leads = this.leadsRepository.findByPhone(leadsDTO.getPhone().trim());
        if(leads != null)
        {
            List<String> projectName = leads.getProjectName();
            
            List<String> updatedDate = leads.getUpdatedDate() != null ? new ArrayList<>(leads.getUpdatedDate()) : new ArrayList<>();
            updatedDate.add(String.valueOf(new Date().getTime()));
            if(leadsDTO.getPropertyId() != null) leads.setProperty(this.propertyRepository.findById(leadsDTO.getPropertyId())
            .orElse(null));
            leads.setFrequency(leads.getFrequency() + 1);
            projectName.add(leadsDTO.getProjectName());
            leads.setProjectName(projectName);
            
            List<String> sourceName = leads.getSource() != null ? new ArrayList<>(leads.getSource()) : new ArrayList<>();
            sourceName.add(leadsDTO.getSource());
            leads.setSource(sourceName);
            Optional.ofNullable(leadsDTO.getUserType()).ifPresent(leads::setUserType);
            return this.leadsRepository.save(leads);
        }
        leads = new Leads();
        leads.setCreatedDate(new Date().getTime());
        if(leadsDTO.getPropertyId() != null) leads.setProperty(this.propertyRepository.findById(leadsDTO.getPropertyId())
        .orElse(null));
        leads.setName(leadsDTO.getName());
        leads.setPhone(leadsDTO.getPhone());
        leads.setEmail(leadsDTO.getEmail());
        leads.setProjectName(Arrays.asList(leadsDTO.getProjectName()));
        leads.setSource(Arrays.asList(leadsDTO.getSource()));

        leads.setMessage(leadsDTO.getMessage());
        Optional.ofNullable(leadsDTO.getUserType()).ifPresent(leads::setUserType);
        leads.setFrequency(1L);
        Leads savedLeads = this.leadsRepository.save(leads);
        LMSUtils.sendLeadsToExternalAPI(savedLeads);
        return savedLeads;
    }

    @Override
    public Page<Leads> getAllLeadsByUser(String search, Long startDate, Long endDate, Pageable pageable) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        List<Leads> leads = this.leadsRepository.findByPropertyUserAndPropertyIsNotNull(user);
        List<Leads> filteredLeads = leads.stream()
        .filter(lead -> search == null || lead.getName().toLowerCase().contains(search.toLowerCase()))
        .filter(lead -> search == null || lead.getPhone().toLowerCase().contains(search.toLowerCase()))
        .filter(lead -> startDate == null || lead.getCreatedDate() >= startDate)
        .filter(lead -> endDate == null || lead.getCreatedDate() <= endDate)
        .sorted(Comparator.comparing(Leads::getCreatedDate).reversed())
        .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredLeads.size());
        return new PageImpl<>(filteredLeads.subList(start, end), pageable, filteredLeads.size());
        
    }
    
}
