package es.chachel.pokeapi.rest;

public class ApiSpecie {

    public ApiNameReference[] names;

    public ApiFlavorTextReference[] flavor_text_entries;

    public ApiEvolutionChainInternal evolution_chain;

    public static class ApiEvolutionChainInternal {
        public String url;
    }
}
