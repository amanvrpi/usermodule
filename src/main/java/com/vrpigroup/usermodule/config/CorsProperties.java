//package com.vrpigroup.usermodule.config;
//import lombok.Getter;
//import lombok.Setter;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@Getter
//@Setter
//@ConfigurationProperties(prefix = "cors")
//public class CorsProperties {
//
//    @Value("${cors.allowedOrigins}")
//    private String allowedOrigins;
//
//    @Value("${cors.allowedMethods}")
//    private String allowedMethods;
//
//    @Value("${cors.allowedHeaders}")
//    private String allowedHeaders;
//
//    @Value("${cors.exposedHeaders}")
//    private String exposedHeaders;
//
//    @Value("${cors.maxAge}")
//    private int maxAge;
//}