package com.jpd.service;

import java.util.Optional;

import org.springframework.stereotype.Service;


import com.jpd.model.Course;
import com.jpd.model.Customer;
import com.jpd.model.CustomerFinishedModule;
import com.jpd.model.Enrollment;
import com.jpd.repository.CourseRepository;
import com.jpd.repository.CustomerFinishedModuleRepository;
import com.jpd.repository.EnrollingRepository;
import com.jpd.repository.ModuleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollService {
private final CustomerFinishedModuleRepository fRe;
private final EnrollingRepository enRe;
private final ModuleRepository mosRe;

public Enrollment retrievedEnrollment(Customer cus,Course course)
{ Enrollment result= this.enRe.findByCustomerAndCourse(cus, course);
if(result!=null) {return result;}
return null;
	}

public void saveProcess(long enrollId, long moduleId) {
	Enrollment enro=this.enRe.findByEnrollId(enrollId);
	com.jpd.model.Module module=this.mosRe.findById(moduleId);
	if(this.fRe.findByEnrollingAndModule(enro,module).isEmpty())
	{CustomerFinishedModule x=new CustomerFinishedModule();
	x.setEnrolling(enro);
	x.setModule(module);
	this.fRe.save(x);
     long length=module.getCourseM().getModule().size();
     double oldPro=(enro.getProgress())+1/(double)length;
     enro.setProgress(oldPro);
     this.enRe.save(enro);
	}
}
 
}
