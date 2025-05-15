package com.realestate.invest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ReraInfo;

public interface ReraInfoReposiotry extends JpaRepository<ReraInfo, Long>
{
    ReraInfo findByProjectAndReraNumber(Project project, String reraNumber);

    List<ReraInfo> findByProject(Project project);

}
