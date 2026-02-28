package com.hospital.oxygen_defect.repository;

import com.hospital.oxygen_defect.model.Cylinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CylinderRepository extends JpaRepository<Cylinder, Integer> {
}