package br.com.victor.realmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.victor.realmeet.config.propeties")
public class RealmeetApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealmeetApplication.class, args);
	}

}
