package com.hospital.oxygen_defect.controller;

import com.hospital.oxygen_defect.model.Cylinder;
import com.hospital.oxygen_defect.service.CylinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CylinderController {

    @Autowired
    private CylinderService cylinderService;

    // Home page
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        List<Cylinder> cylinders = cylinderService.getAllCylinders();
        long defectiveCount = cylinders.stream()
                .filter(c -> "DEFECTIVE".equals(c.getDefectStatus()))
                .count();
        long noDefectCount = cylinders.stream()
                .filter(c -> "NO DEFECT".equals(c.getDefectStatus()))
                .count();
        model.addAttribute("cylinders", cylinders);
        model.addAttribute("totalCount", cylinders.size());
        model.addAttribute("defectiveCount", defectiveCount);
        model.addAttribute("noDefectCount", noDefectCount);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "index";
    }

    // Show add cylinder form
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("cylinder", new Cylinder());
        return "add-cylinder";
    }

    // Save cylinder and detect defect
    @PostMapping("/save")
    public String saveCylinder(@ModelAttribute Cylinder cylinder,
                               HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        String defectStatus = cylinderService.detectDefect(cylinder);
        cylinder.setDefectStatus(defectStatus);
        cylinderService.saveCylinder(cylinder);
        return "redirect:/";
    }

    // View cylinder details
    @GetMapping("/view/{id}")
    public String viewCylinder(@PathVariable int id, Model model,
                               HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        Cylinder cylinder = cylinderService.getCylinderById(id);
        model.addAttribute("cylinder", cylinder);
        return "view-cylinder";
    }

    // Delete cylinder
    @GetMapping("/delete/{id}")
    public String deleteCylinder(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        cylinderService.deleteCylinder(id);
        return "redirect:/";
    }
 // Dashboard page
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }
        List<Cylinder> cylinders = cylinderService.getAllCylinders();
        long defectiveCount = cylinders.stream()
                .filter(c -> "DEFECTIVE".equals(c.getDefectStatus()))
                .count();
        long noDefectCount = cylinders.stream()
                .filter(c -> "NO DEFECT".equals(c.getDefectStatus()))
                .count();

        // Valve condition counts
        long goodValve = cylinders.stream()
                .filter(c -> "good".equals(c.getValveCondition()))
                .count();
        long badValve = cylinders.stream()
                .filter(c -> "bad".equals(c.getValveCondition()))
                .count();
        long damagedValve = cylinders.stream()
                .filter(c -> "damaged".equals(c.getValveCondition()))
                .count();

        model.addAttribute("totalCount", cylinders.size());
        model.addAttribute("defectiveCount", defectiveCount);
        model.addAttribute("noDefectCount", noDefectCount);
        model.addAttribute("goodValve", goodValve);
        model.addAttribute("badValve", badValve);
        model.addAttribute("damagedValve", damagedValve);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "dashboard";
    }
}