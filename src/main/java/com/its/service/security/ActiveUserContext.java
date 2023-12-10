//package com.its.service.security;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import io.jsonwebtoken.Jwt;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Base64;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//@RequiredArgsConstructor
//public class ActiveUserContext {
//
//
//    public String getLoggedInUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        String userId = (String) jwt.getClaims().get("user_id");
//        return userId;
//    }
//
//    public String getLoggedInUserName() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        String userName = (String) jwt.getClaims().get("user_name");
//        return userName;
//    }
//
//    public List<String> getLoggedInUserRoles() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        List<String> roles = (List<String>) jwt.getClaims().get("roles");
//        return roles;
//    }
//
//    public String getLoggedInUserToken() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (Objects.isNull(authentication)) return null;
//        Jwt jwt = (Jwt) authentication.getCredentials();
//
//        return jwt.getTokenValue();
//    }
//
//    public boolean isScrutinizerRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//
//        //List<String> roles = new ArrayList<>();
//        String token = jwt.getTokenValue();
//        String[] chunks = token.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//        //String header = new String(decoder.decode(chunks[0]));
//        String payload = new String(decoder.decode(chunks[1]));
//        JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
//        JsonArray array = jsonObject.get("realm_access").getAsJsonObject().getAsJsonArray("roles");
//        boolean isScrutinizer = false;
//        for (JsonElement a : array) {
//            if ("Scrutinizer".equalsIgnoreCase(String.valueOf(a).substring(1, String.valueOf(a).length() - 1))) {
//                isScrutinizer = true;
//                break;
//            }
//        }
//        return isScrutinizer;
//    }
//
//    public boolean isInstituteHeadRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//
//        //List<String> roles = new ArrayList<>();
//        String token = jwt.getTokenValue();
//        String[] chunks = token.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//        //String header = new String(decoder.decode(chunks[0]));
//        String payload = new String(decoder.decode(chunks[1]));
//        JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
//        JsonArray array = jsonObject.get("realm_access").getAsJsonObject().getAsJsonArray("roles");
//        boolean isScrutinizer = false;
//        for (JsonElement a : array) {
//            if ("InstituteHead".equalsIgnoreCase(String.valueOf(a).substring(1, String.valueOf(a).length() - 1))) {
//                isScrutinizer = true;
//                break;
//            }
//            if ("InstituteAdmin".equalsIgnoreCase(String.valueOf(a).substring(1, String.valueOf(a).length() - 1))) {
//                isScrutinizer = true;
//                break;
//            }
//        }
//        return isScrutinizer;
//    }
//
//    public Long getEmployeeId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        String userId = (String) jwt.getClaims().get("employee_id");
//
//        return Objects.nonNull(userId) ? Long.parseLong(userId) : null;
//    }
//
//    public Long getUserInstituteId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        if (jwt.getClaims().get("institute_id") == null) return -1L;
//        String instituteId = (String) jwt.getClaims().get("institute_id");
//
//        return Long.valueOf(instituteId);
//    }
//
//    public LoggedInUserDto getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getCredentials();
//        Object userId = jwt.getClaims().get("user_id");
//        Object instituteId = jwt.getClaims().get("institute_id");
//        Object employeeId = jwt.getClaims().get("employee_id");
//        Object name = jwt.getClaims().get("name");
//        Object branchId = jwt.getClaims().get("branch_id");
//        Object eiinNo = jwt.getClaims().get("eiin_no");
//        Object instituteTypeId = jwt.getClaims().get("institute_type_id");
//        Object teacherId = jwt.getClaims().get("teacher_id");
//        LoggedInUserDto userDto = new LoggedInUserDto();
//        if (Objects.nonNull(userId)) userDto.setUserId((String) userId);
//        if (Objects.nonNull(name)) userDto.setUserName((String) name);
//        if (Objects.nonNull(instituteId)) userDto.setInstituteId(Long.parseLong((String) instituteId));
//        if (Objects.nonNull(employeeId)) userDto.setEmployeeId(Long.parseLong((String) employeeId));
//        if (Objects.nonNull(branchId)) userDto.setBranchId(Long.parseLong((String) branchId));
//        if (Objects.nonNull(eiinNo)) userDto.setEiinNo((String) eiinNo);
//        if (Objects.nonNull(instituteTypeId)) userDto.setInstituteTypeId(Long.parseLong((String) instituteTypeId));
//        if (Objects.nonNull(teacherId)) userDto.setTeacherId(Long.parseLong((String) teacherId));
//        return userDto;
//    }
//}
