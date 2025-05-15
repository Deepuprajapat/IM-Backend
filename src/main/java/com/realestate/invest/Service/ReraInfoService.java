package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.ReraInfo;

@Component
public interface ReraInfoService 
{
    ReraInfo saveNew(ReraInfo reraDTO) throws Exception;

    ReraInfo findById(Long Id) throws Exception;

    ReraInfo updateById(Long Id, ReraInfo reraDTO) throws Exception;

    String deleteById(Long Id) throws Exception;

    List<ReraInfo> getAll(Long projectId, Long startDate, Long endDate) throws Exception;

    List<ReraInfo> findByProject(Long projectId) throws Exception;

}
