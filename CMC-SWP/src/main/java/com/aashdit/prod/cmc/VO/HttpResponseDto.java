package com.aashdit.prod.cmc.VO;


public class HttpResponseDto {


    private String message;
    private String redirectUrl;
    private Boolean isSuccess;
    private Boolean isCookieNeeded;
    private String cookieName;
    private String cookieValue;
    private Boolean isJwtNeeded;
    private String jwtTokenName;
    private String jwtTokenValue;

    public String errorType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Boolean getCookieNeeded() {
        return isCookieNeeded;
    }

    public void setCookieNeeded(Boolean cookieNeeded) {
        isCookieNeeded = cookieNeeded;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public Boolean getJwtNeeded() {
        return isJwtNeeded;
    }

    public void setJwtNeeded(Boolean jwtNeeded) {
        isJwtNeeded = jwtNeeded;
    }

    public String getJwtTokenName() {
        return jwtTokenName;
    }

    public void setJwtTokenName(String jwtTokenName) {
        this.jwtTokenName = jwtTokenName;
    }

    public String getJwtTokenValue() {
        return jwtTokenValue;
    }

    public void setJwtTokenValue(String jwtTokenValue) {
        this.jwtTokenValue = jwtTokenValue;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public HttpResponseDto(String message, String redirectUrl, Boolean isSuccess, Boolean isCookieNeeded, String cookieName, String cookieValue, Boolean isJwtNeeded, String jwtTokenName, String jwtTokenValue, String errorType) {
        this.message = message;
        this.redirectUrl = redirectUrl;
        this.isSuccess = isSuccess;
        this.isCookieNeeded = isCookieNeeded;
        this.cookieName = cookieName;
        this.cookieValue = cookieValue;
        this.isJwtNeeded = isJwtNeeded;
        this.jwtTokenName = jwtTokenName;
        this.jwtTokenValue = jwtTokenValue;
        this.errorType = errorType;
    }

    public HttpResponseDto() {

    }


}
