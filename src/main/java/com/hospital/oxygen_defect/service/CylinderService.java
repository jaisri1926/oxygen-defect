package com.hospital.oxygen_defect.service;

import com.hospital.oxygen_defect.model.Cylinder;
import com.hospital.oxygen_defect.repository.CylinderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CylinderService {

    @Autowired
    private CylinderRepository cylinderRepository;

    // Save cylinder
    public Cylinder saveCylinder(Cylinder cylinder) {
        return cylinderRepository.save(cylinder);
    }

    // Get all cylinders
    public List<Cylinder> getAllCylinders() {
        return cylinderRepository.findAll();
    }

    // Get cylinder by ID
    public Cylinder getCylinderById(int id) {
        return cylinderRepository.findById(id).orElse(null);
    }

    // Delete cylinder
    public void deleteCylinder(int id) {
        cylinderRepository.deleteById(id);
    }

    // Detect defect using DCCA logic
    public String detectDefect(Cylinder cylinder) {
        double pressure = cylinder.getPressure();
        double weight = cylinder.getWeight();
        double temperature = cylinder.getTemperature();
        String valve = cylinder.getValveCondition();

        // DCCA defect detection logic
        if (pressure < 30 || pressure > 200) {
            return "DEFECTIVE";
        }
        if (weight < 5 || weight > 100) {
            return "DEFECTIVE";
        }
        if (temperature < 0 || temperature > 50) {
            return "DEFECTIVE";
        }
        if (valve.equalsIgnoreCase("bad") || valve.equalsIgnoreCase("damaged")) {
            return "DEFECTIVE";
        }
        return "NO DEFECT";
    }
}