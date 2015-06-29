package es.predictia.daemonizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DaemonizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaemonizerApplication.class, args);
    }
    
    @Bean
    public Daemon daemon(){
    	return new Daemon();
    }
    
}
