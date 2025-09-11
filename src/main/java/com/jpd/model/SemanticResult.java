package com.jpd.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class SemanticResult {
    @JsonProperty("match")
    private boolean match;
    
    @JsonProperty("similarity_score")
    private double similarityScore;
    
    @JsonProperty("user_answer")
    private String userAnswer;
    
    @JsonProperty("expected_answer")
    private String expectedAnswer;
    
    @JsonProperty("feedback")
    private String feedback;
    
    @JsonProperty("has_error")
    private boolean hasError;
    
    @JsonProperty("error_message")
    private String errorMessage;
    
    // Constructor thường
    private SemanticResult(Builder builder) {
        this.match = builder.match;
        this.similarityScore = builder.similarityScore;
        this.userAnswer = builder.userAnswer;
        this.expectedAnswer = builder.expectedAnswer;
        this.feedback = builder.feedback;
        this.hasError = builder.hasError;
        this.errorMessage = builder.errorMessage;
    }
    
    // Constructor cho error
    public static SemanticResult error(String errorMessage) {
        return new Builder()
            .hasError(true)
            .errorMessage(errorMessage)
            .build();
    }
    
    // Getters
    public boolean isMatch() { return match; }
    public double getSimilarityScore() { return similarityScore; }
    public String getUserAnswer() { return userAnswer; }
    public String getExpectedAnswer() { return expectedAnswer; }
    public String getFeedback() { return feedback; }
    public boolean isHasError() { return hasError; }
    public String getErrorMessage() { return errorMessage; }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private boolean match;
        private double similarityScore;
        private String userAnswer;
        private String expectedAnswer;
        private String feedback;
        private boolean hasError = false;
        private String errorMessage;
        
        public Builder match(boolean match) {
            this.match = match;
            return this;
        }
        
        public Builder similarityScore(double similarityScore) {
            this.similarityScore = similarityScore;
            return this;
        }
        
        public Builder userAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
            return this;
        }
        
        public Builder expectedAnswer(String expectedAnswer) {
            this.expectedAnswer = expectedAnswer;
            return this;
        }
        
        public Builder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }
        
        public Builder hasError(boolean hasError) {
            this.hasError = hasError;
            return this;
        }
        
        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
        
        public SemanticResult build() {
            return new SemanticResult(this);
        }
    }
}