package es.chachel.pokeapi.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonMoveRepository extends JpaRepository<PokemonMove, Integer> {
}
