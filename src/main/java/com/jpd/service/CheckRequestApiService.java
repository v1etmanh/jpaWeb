package com.jpd.service;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jpd.model.Customer;
import com.jpd.model.RequestSpeaking;
import com.jpd.repository.CustomerRepository;
import com.jpd.repository.RequestSpeakingRepository;

@Service
public class CheckRequestApiService {
@Autowired
private RequestSpeakingRepository rsRe;
@Autowired
private CustomerRepository cusRe;
public RequestSpeaking isSendRequest(String email) {
    Customer cus = this.cusRe.findCustomerByEmail(email);
    if(cus == null) return null;
    
    RequestSpeaking rq = this.rsRe.findByCustomer(cus);
    if(rq == null) return null;
    
    Date today = new Date();
    java.sql.Date sqlToday = new java.sql.Date(today.getTime());
    
    // Kiểm tra nếu hôm nay là ngày reset request
    if(rq.getNextDate() != null && 
       rq.getNextDate().compareTo(sqlToday) <= 0) {
        
        // Reset số lượng request và set ngày tiếp theo
        rq.setNowCurrentDate(sqlToday);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        rq.setNextDate(new java.sql.Date(cal.getTimeInMillis()));
        rq.setAvaiableRequest(6);
        
        this.rsRe.save(rq); // Lưu thay đổi
        return rq;
    }
    
    // Kiểm tra còn request không
    if(rq.getAvaiableRequest() <= 0) return null;
    
    return rq;
}
public void decreaseAvaiableRequest(RequestSpeaking rq)
{rq.setAvaiableRequest(rq.getAvaiableRequest()-1);
this.rsRe.save(rq);
	}

}
