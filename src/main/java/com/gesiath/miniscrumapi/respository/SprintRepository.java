package com.gesiath.miniscrumapi.respository;

import com.gesiath.miniscrumapi.entity.Sprint;
import com.gesiath.miniscrumapi.enumerator.SprintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, String> {

    boolean existsByStatus(SprintStatus status);

}
