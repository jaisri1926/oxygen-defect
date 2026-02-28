package com.hospital.oxygen_defect.controller;

import com.hospital.oxygen_defect.model.Cylinder;
import com.hospital.oxygen_defect.service.CylinderService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CsvUploadController {

    @Autowired
    private CylinderService cylinderService;

    // Show upload page
    @GetMapping("/upload")
    public String showUploadPage(HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        return "upload";
    }

    // Process CSV upload
    @PostMapping("/upload")
    public String processUpload(@RequestParam("file") MultipartFile file,
                                HttpSession session,
                                Model model) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }

        List<Cylinder> uploadedCylinders = new ArrayList<>();
        int successCount = 0;
        int defectiveCount = 0;

        try {
            CSVReader csvReader = new CSVReader(
                new InputStreamReader(file.getInputStream()));

            String[] line;
            boolean isHeader = true;

            while ((line = csvReader.readNext()) != null) {
                // Skip header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Read each column
                Cylinder cylinder = new Cylinder();
                cylinder.setCylinderNo(line[0].trim());
                cylinder.setPressure(Double.parseDouble(line[1].trim()));
                cylinder.setWeight(Double.parseDouble(line[2].trim()));
                cylinder.setTemperature(Double.parseDouble(line[3].trim()));
                cylinder.setValveCondition(line[4].trim());
                cylinder.setStatus(line[5].trim());

                // Detect defect
                String defectStatus = cylinderService.detectDefect(cylinder);
                cylinder.setDefectStatus(defectStatus);

                // Save to database
                cylinderService.saveCylinder(cylinder);
                uploadedCylinders.add(cylinder);

                if ("DEFECTIVE".equals(defectStatus)) {
                    defectiveCount++;
                } else {
                    successCount++;
                }
            }
            csvReader.close();

            model.addAttribute("uploadedCylinders", uploadedCylinders);
            model.addAttribute("successCount", successCount);
            model.addAttribute("defectiveCount", defectiveCount);
            model.addAttribute("totalCount", uploadedCylinders.size());
            model.addAttribute("success", "CSV uploaded successfully!");

        } catch (Exception e) {
            model.addAttribute("error", "Error reading CSV file: " + e.getMessage());
        }

        return "upload-result";
    }
}