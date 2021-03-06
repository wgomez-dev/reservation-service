package com.example.demo;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.lettuce.core.dynamic.annotation.Param;

@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {
	
	@Bean
	CommandLineRunner commandLineRunner(ReservationRepository reservationRepository){
		return strings ->{
			Stream.of("Wil","Josh", "Pieter", "Tasha", "Alex", "Rick").
				forEach(n -> reservationRepository.save(new Reservation(n)));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

}

@RefreshScope
@RestController
class MessageController {
	@Value("${message}")
	private String message;
	
	@RequestMapping("/message")
	String message() {
		return this.message;
	}
	
}

//Data Access Layer
@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	@RestResource(path="by-name")
	Collection<Reservation> findByReservationName(@Param("rn") String rn);
}

//Model Layer
@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String reservationName;
	
	@Override
	public String toString() {
		return "Reservation {id=" + id + ", reservatonName=" + reservationName + "}";
	}
	
	public Reservation() {
	}
	
	public Reservation(String n) {
		this.reservationName = n;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReservatonName() {
		return reservationName;
	}
	public void setReservatonName(String reservatonName) {
		this.reservationName = reservatonName;
	}
	
	
}