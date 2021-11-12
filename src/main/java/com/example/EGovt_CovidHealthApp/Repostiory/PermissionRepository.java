package com.example.EGovt_CovidHealthApp.Repostiory;

import com.example.EGovt_CovidHealthApp.Model.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Permission repository.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Permission> findByStatus(boolean status);
}
