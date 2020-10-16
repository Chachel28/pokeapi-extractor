package es.chachel.pokeapi.db;

import javax.persistence.*;

@Entity
@Table
public class TypeRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public Integer moveTypeId;
    public Integer pokeTypeId;
    public double multiplier;

    @Override
    public String toString() {
        return "TypeRelation{" +
                "id=" + id +
                ", moveTypeId=" + moveTypeId +
                ", pokeTypeId=" + pokeTypeId +
                ", multiplier=" + multiplier +
                '}';
    }
}
