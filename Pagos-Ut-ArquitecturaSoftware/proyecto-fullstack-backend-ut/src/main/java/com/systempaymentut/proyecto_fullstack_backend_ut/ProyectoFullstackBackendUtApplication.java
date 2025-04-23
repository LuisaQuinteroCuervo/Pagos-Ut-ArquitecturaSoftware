package com.systempaymentut.proyecto_fullstack_backend_ut;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.PagoRepository;

@SpringBootApplication
public class ProyectoFullstackBackendUtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFullstackBackendUtApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository) {
		return args -> {
			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Daniela")
					.apellido("Martinez")
					.codigo("1234")
					.programaId("ISI123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Carlos")
					.apellido("Martínez")
					.codigo("1235")
					.programaId("ISI123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Fernando")
					.apellido("Rodriguez")
					.codigo("1236")
					.programaId("ISI123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Juan")
					.apellido("Perez")
					.codigo("1237")
					.programaId("ISI123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Maria")
					.apellido("Garcia")
					.codigo("1238")
					.programaId("ISI123")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Luis")
					.apellido("Sanchez")
					.codigo("1239")
					.programaId("ISI123")
					.build());

			// tipos de pago
			TypePago tiposPago[] = TypePago.values();

			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {

				for (int i = 0; i < 10; i++) {
					int index = random.nextInt(tiposPago.length);

					// construir un objeto

					Pago pago = Pago.builder()
							.cantidad(1000 + (int) (Math.random() * 20000))
							.type(tiposPago[index])
							.status(PagoStatus.CREADO)
							.fecha(LocalDate.now())
							.estudiante(estudiante)
							.build();
					pagoRepository.save(pago);

				}

			});

		};
	}

}
