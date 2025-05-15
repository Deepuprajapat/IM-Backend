package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.User;

public interface FloorPlanRepository extends JpaRepository <Floorplan, Long>
{
    
    List<Floorplan> findByProject(Project project);

    List<Floorplan> findByUser(User user);

}
