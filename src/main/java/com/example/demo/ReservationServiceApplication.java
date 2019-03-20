package com.example.demo;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

}

//Insert Data from command line
class DummyDataCLR implements CommandLineRunner{

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Stream.of("Wil","Josh", "Pieter", "Tasha", "Alex", "Rick").forEach(n -> this.reservationRepository.save(new Reservation(n)));
	}
	
}


//Data Access Layer
@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	@RestResource(path="by-name")
	Collection<Reservation> findReservationName(String rn);
}

//Model Layer
@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;
	private String reservatonName;
	
	@Override
	public String toString() {
		return "Reservation {id=" + id + ", reservatonName=" + reservatonName + "}";
	}
	
	public Reservation(String n) {
		this.reservatonName = n;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReservatonName() {
		return reservatonName;
	}
	public void setReservatonName(String reservatonName) {
		this.reservatonName = reservatonName;
	}
	
	
}