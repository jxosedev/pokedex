package com.webflux.pokedex;

import com.webflux.pokedex.Repository.PokemonRepository;
import com.webflux.pokedex.model.Pokemon;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);}

		@Bean
	CommandLineRunner init (ReactiveMongoRepository operations, PokemonRepository repository) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon(null, "Pikachu", "Eletrico", "Choque do Trovão", 5.0),
					new Pokemon(null, "Bubassauro", "Planta", "Bomba de Semente", 10.0),
					new Pokemon(null, "Dragonite", "Dragão", "Ondado Trovão", 225.5))
					.flatMap(repository::save);

			pokemonFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
		}

}
