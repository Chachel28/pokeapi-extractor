package es.chachel.pokeapi.db;

import javax.persistence.*;

@Entity
@Table
public class Move {

    @Id
    public Integer id;

    public String name;

    public String flavor_text;

    public Integer typeId;

    public Integer power;
    public Integer accuracy;
    public Integer pp;
    public Integer priority;

    @Override
    public String toString() {
        return "Move{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flavor_text='" + flavor_text + '\'' +
                ", typeId=" + typeId +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", pp=" + pp +
                ", priority=" + priority +
                '}';
    }
}
