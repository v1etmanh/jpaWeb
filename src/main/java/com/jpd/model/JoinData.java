package com.jpd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JoinData {
	  private String jobId;
	    private String audioData;
	    private String audioFilename;
	    private String expectedText;
	    private long createdAt;
}
