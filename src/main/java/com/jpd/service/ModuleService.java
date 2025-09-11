package com.jpd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jpd.model.Course;
import com.jpd.model.CustomerFinishedModule;
import com.jpd.model.Enrollment;
import com.jpd.model.Module;
import com.jpd.model.PdfDocument;
import com.jpd.model.ReModule;
import com.jpd.repository.CourseRepository;
import com.jpd.repository.CustomerFinishedModuleRepository;
import com.jpd.repository.ModuleRepository;
import com.jpd.repository.PdfDocumentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModuleService {
ModuleRepository moduleRepository;
CourseRepository courseRe;
PdfDocumentRepository pdfDocumentRepository;
CustomerFinishedModuleRepository fRe;
public List<Module> retrieveAllModuleByCourse(long courseId) {
	
	Course course=this.courseRe.findByCourseId(courseId);
	return this.moduleRepository.findByCourseM(course);
}
public Module retrieveModuleByid(long id){
	return this.moduleRepository.findById(id);
}
public List<ReModule> retrieveCourse(long courseId,Enrollment e) {
	List<CustomerFinishedModule> cusF=this.fRe.findByEnrolling(e);
    return retrieveAllModuleByCourse(courseId).stream()
            .map(module -> new ReModule(module.getId(), module.getModuleType().getModuleName(),cusF.stream().anyMatch(x -> x.getModule().getId() == module.getId())))
            
            .toList();
}
public PdfDocument retrievePdfById(long pdf_id) {
	return this.pdfDocumentRepository.findByDocId(pdf_id);
}

}
