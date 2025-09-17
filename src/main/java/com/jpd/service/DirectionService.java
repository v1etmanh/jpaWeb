package com.jpd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import com.jpd.model.Customer;
import com.jpd.model.RememberWord;
import com.jpd.repository.CustomerRepository;
import com.jpd.repository.RememberWordRepository;

import jakarta.transaction.Transactional;

@Service
public class DirectionService {
	@Autowired
    private RememberWordRepository directionRepository;
    @Autowired CustomerRepository cusRe;
    @Transactional
    public   Customer getUser(String email ) {
    	Customer cus=this.cusRe.findCustomerByEmail(email);
    	if(cus==null)return null;
    	return cus;
    }
    public boolean changeLimit(int delta, Customer customer) {
        int newLimit = customer.getCurrentWords() + delta;
        if (newLimit < 0 || newLimit > 20) return false;
        customer.setCurrentWords(newLimit);
        cusRe.save(customer);
        return true;
    }
    @Transactional
    public long deleteWordById(long rwId, String email) {
        Customer cus = getUser(email);
        if(cus == null) return -1;
        
        // Xóa từ trước
        long result = this.directionRepository.deleteByRwId(rwId);
        
        if(result > 0) {
            // Chỉ giảm count khi xóa thành công
            if(!changeLimit(-1, cus)) {
                // Log error nếu cần
                System.out.println("Warning: Deleted word but couldn't update count");
            }
        }
        
        return result;
    }
    
 
    public boolean upNewWord(RememberWord word, String email) {
        Customer cus = getUser(email);
        if (cus == null) return false;
        if (!changeLimit(1, cus)) return false;
        word.setCustomer(cus);
        this.directionRepository.save(word);
        return true;
    }


    public List<RememberWord> retrieveAllWord(String email){
       Customer cus=getUser(email);
       if(cus==null)return null;
       return this.directionRepository.findByCustomer(cus);
    }

    
}
