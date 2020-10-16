package es.chachel.pokeapi.db;

import javax.persistence.*;

@Entity
@Table
public class Type {

    @Id
    public Integer id;

    public String name;

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
