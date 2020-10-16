package es.chachel.pokeapi.rest;

public class ApiPokemon {

    public static class PokemonTypeReference {
        public int slot;
        public ApiTypeReference type;
    }

    public static class Stat {
        public int base_stat;
        public StatType stat;
    }

    public static class StatType {
        public String name;
    }

    public static class MoveReference {
        public MoveReferenceInternal move;
    }

    public static class MoveReferenceInternal {
        public String url;

        public int getId() {
            String crop = url.substring(0, url.length() - 1);
            crop = crop.substring(crop.lastIndexOf('/') + 1);
            return Integer.parseInt(crop);
        }
    }

    public static class species {
        public String url;
    }

    public PokemonTypeReference[] types;
    public Stat[] stats;
    public MoveReference[] moves;
    public species species;
}
