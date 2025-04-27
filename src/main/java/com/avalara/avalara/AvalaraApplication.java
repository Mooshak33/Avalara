package com.avalara.avalara;

import com.avalara.avalara.configuration.RestClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RestClientProperties.class})
public class AvalaraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvalaraApplication.class, args);
	}

}
