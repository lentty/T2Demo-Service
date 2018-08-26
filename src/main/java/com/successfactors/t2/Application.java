package com.successfactors.t2;

import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;


@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

    @Autowired
    private CacheService cacheService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        cacheService.loadDataOnStartUp();
    }
}

