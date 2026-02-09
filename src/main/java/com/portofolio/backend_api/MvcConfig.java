package com.portofolio.backend_api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // MANTRA AJAIB:
        // "Kalau ada yang minta URL /foto-tamu/..., tolong ambilkan dari folder 'foto-tamu' di luar aplikasi."
        
        registry.addResourceHandler("/foto-tamu/**")
                .addResourceLocations("file:./foto-tamu/");
    }
}