package es.chachel.pokeapi.db;

import javax.persistence.*;

@Entity
@Table
public class Pokemon {

    @Id
    public Integer id;

    public String name;
    public String flavor_text;

    public Integer type1Id;
    public Integer type2Id;

    public int baseHp;
    public int baseAtk;
    public int baseDef;
    public int baseSpAtk;
    public int baseSpDef;
    public int baseSpd;

    public Integer evolutionLVL;
    public String nextEvolution;

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flavor_text='" + flavor_text + '\'' +
                ", type1Id=" + type1Id +
                ", type2Id=" + type2Id +
                ", baseHp=" + baseHp +
                ", baseAtk=" + baseAtk +
                ", baseDef=" + baseDef +
                ", baseSpAtk=" + baseSpAtk +
                ", baseSpDef=" + baseSpDef +
                ", baseSpd=" + baseSpd +
                '}';
    }
}
