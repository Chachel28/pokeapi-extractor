package es.chachel.pokeapi.rest;

public class ApiEvolutionChain {

    public static class ApiChain{

        public static class evolves_to {
            public evolution_details[] evolution_details;
            public species species;
            public evolves_to[] evolves_to;

            public static class evolution_details {
                public int min_level;
            }
            public static class species {
                public String name;
            }
        }
        public evolves_to[] evolves_to;

    }
    public ApiChain chain;

}
