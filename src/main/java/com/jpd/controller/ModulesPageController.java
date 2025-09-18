package com.jpd.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

import com.jpd.model.Course;
import com.jpd.model.Enrollment;
import com.jpd.model.Module;
import com.jpd.model.PdfDocument;
import com.jpd.model.ReModule;
import com.jpd.service.CheckingUserService;
import com.jpd.service.EnrollService;
import com.jpd.service.ModuleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class ModulesPageController {

private final CheckingUserService checkingUserService;
private final EnrollService enR;
private final ModuleService mdService;
private boolean isUserEnrollInCourse(Jwt jwt,long courseId)
{
String email=jwt.getClaimAsString("email");
return checkingUserService.checkIsEnroll(email, courseId)!=null;
}
@GetMapping("/{id}/modules")

public ResponseEntity<	List<ReModule> >retrievedAllModulesByCoures(@PathVariable("id") long courseId,@AuthenticationPrincipal Jwt jwt){
	  String email=jwt.getClaimAsString("email");
	  Enrollment e=checkingUserService.checkIsEnroll(email, courseId);
	   if(e==null) {
		   return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	   }
	   
		   try {
		List<ReModule>result= this.mdService.retrieveCourse(courseId,e);
		return ResponseEntity.ok(result);
		   }catch (Exception e1) {
			// TODO: handle exception
			   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}  
	 
	}
@GetMapping("{id}/module/{module_id}")
public ResponseEntity<Module> retrieveModuleById(@PathVariable("id") long courseId,@PathVariable("module_id") long module_id,@AuthenticationPrincipal Jwt jwt){
	 if(!isUserEnrollInCourse(jwt, courseId))
		 return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		try {Module result= this.mdService.retrieveModuleByid(module_id);
		return ResponseEntity.ok(result);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
@GetMapping("{id}/pdf/{pdf_id}")
public ResponseEntity<?> getMethodName(@PathVariable("id")long id, @PathVariable("pdf_id")long pdf_id,@AuthenticationPrincipal Jwt jwt) {
	  
	  
	   if(isUserEnrollInCourse(jwt, id))
	   {PdfDocument pdf=this.mdService.retrievePdfById(pdf_id);
	    if(pdf!=null)
	    {
	    	Path path= Paths.get(pdf.getDocUrl());
	    	 try {
	    		 byte[] content = Files.readAllBytes(Paths.get(pdf.getDocUrl()));
				  return ResponseEntity.ok()
						.contentType(org.springframework.http.MediaType.APPLICATION_PDF)
		                    .body(content);
			  }catch (Exception e) {
				// TODO: handle exception
				  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .body("Lỗi đọc file: " + e.getMessage());
			}
	    }
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nội dung");
	   }
	     
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you dont have role to access");
           
}

}
