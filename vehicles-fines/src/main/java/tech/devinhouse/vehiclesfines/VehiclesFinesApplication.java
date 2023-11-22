package tech.devinhouse.vehiclesfines;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehiclesFinesApplication {

	public static void main(String[] args) {SpringApplication.run(VehiclesFinesApplication.class, args);}

	@Bean
	public ModelMapper obterModelMapper() {
		return new ModelMapper();
	}

}
