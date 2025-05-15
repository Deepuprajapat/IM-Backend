package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.StaticSiteData;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.StaticSiteDataRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.StaticSiteDataService;

@Service
public class StaticSiteDataServiceImpl implements StaticSiteDataService
{
    @Autowired
    private StaticSiteDataRepository staticSiteDataRepository ;

    @Autowired 
    private UserRepository userRepository;

    @Override
    public StaticSiteData saveNewStaticSiteData(StaticSiteData siteData, Long userId) throws Exception 
    {
        if (!siteData.isExclusive()) 
        {
            throw new Exception("You cannot save multiple things at a time");
        }
        User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new Exception("User not found"));

        boolean isAdmin = user.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("GP_ADMIN"));
        if(!isAdmin)
        {
            throw new Exception("You are not an authorize user");
        }

        StaticSiteData staticSiteData1 = new StaticSiteData();
        if(siteData.getPrivacyPolicy() != null)
        {
            staticSiteData1.setPrivacyPolicy(siteData.getPrivacyPolicy());
        }
        else if(siteData.getRefundPolicy() != null)
        {
            staticSiteData1.setRefundPolicy(siteData.getRefundPolicy());
        }
        else if(siteData.getTermOfServices() != null)
        {
            staticSiteData1.setTermOfServices(siteData.getTermOfServices());
        }
        else if(siteData.getAboutUs() != null)
        {
            staticSiteData1.setAboutUs(siteData.getAboutUs());
        }
        else
        {
            throw new Exception("Please enter a valid data");
        }
        staticSiteData1.setUser(user);
        staticSiteData1.setCreatedOn(new Date().getTime());
        return this.staticSiteDataRepository.save(staticSiteData1);
        
    }

    @Override
    public StaticSiteData updateStaticSiteDataById(StaticSiteData siteData, Long id) throws Exception 
    {
        if (!siteData.isExclusive()) 
        {
            throw new Exception("You cannot save multiple things at a time");
        }
        StaticSiteData existingSiteData= this.staticSiteDataRepository.findById(id)
        .orElseThrow(() -> new Exception("Data not found"));

        if(existingSiteData.getPrivacyPolicy() != null)
        {
            existingSiteData.setPrivacyPolicy(siteData.getPrivacyPolicy());
        }
        else if(existingSiteData.getRefundPolicy() != null)
        {
            existingSiteData.setRefundPolicy(siteData.getRefundPolicy());
        }
        else if(existingSiteData.getTermOfServices() != null)
        {
            existingSiteData.setTermOfServices(siteData.getTermOfServices());
        }
        else if(siteData.getAboutUs() != null)
        {
            existingSiteData.setAboutUs(siteData.getAboutUs());
        }
        else
        {
            throw new Exception("Please enter a valid data");
        }
        existingSiteData.setUpdatedOn(new Date().getTime());
        return this.staticSiteDataRepository.save(existingSiteData);
    }

    @Override
    public StaticSiteData findAllRefundPolicies() 
    {
        List<StaticSiteData> refundPolicies = this.staticSiteDataRepository.findAllRefundPolicies();
        return refundPolicies.isEmpty() ? null : refundPolicies.get(0);
    }

    @Override
    public StaticSiteData findAllTermsOfServices() 
    {
        List<StaticSiteData> termAndConditions = this.staticSiteDataRepository.findAllTermsOfServices();
        return termAndConditions.isEmpty() ? null : termAndConditions.get(0);
    }

    @Override
    public StaticSiteData findAllPrivacyPolicies() 
    {
        List<StaticSiteData> privacyPoilicies = this.staticSiteDataRepository.findAllPrivacyPolicies();
        return privacyPoilicies.isEmpty() ? null : privacyPoilicies.get(0);
    }

    @Override
    public List<StaticSiteData> getAllStaticSiteDatas() 
    {
        return this.staticSiteDataRepository.findAll();
    }

    @Override
    public StaticSiteData getStaticSiteDataById(Long dataId) throws Exception
    {
        StaticSiteData staticSiteData = this.staticSiteDataRepository.findById(dataId)
        .orElseThrow(() -> new Exception("Data not found"));
        
        return staticSiteData;
    }

    @Override
    public void deleteStaticSiteDataById(Long dataId) throws Exception 
    {
        StaticSiteData existingData = this.staticSiteDataRepository.findById(dataId).orElse(null);
        if(existingData == null)
        {
            throw new Exception("Data not found");
        }
        this.staticSiteDataRepository.deleteById(dataId);
    }

    @Override
    public List<StaticSiteData> getStaticSiteDataByUserId(Long userId) throws Exception
    {
        this.userRepository.findById(userId)
        .orElseThrow(() -> new Exception("User not found"));
        
        List<StaticSiteData> staticSiteDatas = this.staticSiteDataRepository.getStaticSiteDataByUserId(userId);
        return staticSiteDatas;
    }

    @Override
    public StaticSiteData findAboutUs() 
    {
        List<StaticSiteData> aboutusData =  this.staticSiteDataRepository.findAboutUs();
        return aboutusData.isEmpty() ? null : aboutusData.get(0);
    }
    
}
