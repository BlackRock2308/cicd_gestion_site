package sn.ept.git.seminaire.cicd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CicdApplication extends SpringBootServletInitializer{
@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(CicdApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(CicdApplication.class, args);
	}

}
