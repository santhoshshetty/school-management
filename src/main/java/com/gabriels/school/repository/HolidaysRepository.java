package com.gabriels.school.repository;

import com.gabriels.school.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends JpaRepository<Holiday, String> {
}
