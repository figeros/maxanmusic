package com.example.maxanmusic_webservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class maxanmusic_webserviceApplication {


    public static void main(String[] args) {
        SpringApplication.run(maxanmusic_webserviceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
