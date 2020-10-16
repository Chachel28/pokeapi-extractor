package es.chachel.pokeapi.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PokemonMove {

    @Id
    @GeneratedValue
    public Integer id;

    public Integer pokemonId;
    public Integer moveId;

    @Override
    public String toString() {
        return "PokemonMove{" +
                "id=" + id +
                ", pokemonId=" + pokemonId +
                ", moveId=" + moveId +
                '}';
    }
}
