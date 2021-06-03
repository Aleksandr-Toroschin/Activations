package ru.toroschin.ds.activations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.toroschin.ds.activations.shedulers.CheckUpdate;

@SpringBootApplication
public class ActivationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivationsApplication.class, args);
	}

}
