package com.realestate.invest.Service;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface DashboardService 
{
    Map<String, Object> getDashboardData(Long startDate, Long endDate) throws Exception;
    
}
