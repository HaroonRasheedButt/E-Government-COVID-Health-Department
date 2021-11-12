package com.example.EGovt_CovidHealthApp.Repostiory;

import com.example.EGovt_CovidHealthApp.Model.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Role> findByStatus(boolean status);
}
