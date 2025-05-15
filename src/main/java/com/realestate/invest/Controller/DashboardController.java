package com.realestate.invest.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.Service.DashboardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController 
{

    @Autowired
    private DashboardService dashboardService;


    @GetMapping("/data")
    public ResponseEntity<?> getDashboardData(@RequestParam(required = false) Long startDate, 
    @RequestParam(required = false) Long endDate)
    {
        try
        {
            Map<String, Object> dashboardData = this.dashboardService.getDashboardData(startDate, endDate);
            return new ResponseEntity<>(dashboardData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
