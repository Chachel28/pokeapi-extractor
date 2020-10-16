package es.chachel.pokeapi;

import es.chachel.pokeapi.db.*;
import es.chachel.pokeapi.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/";

    private static final String POKEAPI_TYPE_URL = POKEAPI_URL + "type/";
    private static final int FIRST_TYPE = 1;
    private static final int LAST_TYPE = 18;

    private static final String POKEAPI_MOVE_URL = POKEAPI_URL + "move/";
    private static final int FIRST_MOVE = 1;
    private static final int LAST_MOVE = 728;


    private static final String POKEAPI_POKEMON_URL = POKEAPI_URL + "pokemon/";
    private static final int FIRST_POKEMON = 1;
    private static final int LAST_POKEMON = 151;

    private final RestTemplate restTemplate;
    private final HttpEntity<Object> request;

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TypeRelationRepository typeRelationRepository;
    @Autowired
    private MoveRepository moveRepository;
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private PokemonMoveRepository pokemonMoveRepository;

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).build().run(args);
    }

    public App() {
        restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "el chacho");
        request = new HttpEntity<>(headers);
    }

    @Override
    public void run(String... args) {
        importTypes();
        importMoves();
        importPokemon();
    }

    private void importTypes() {
        for (int i = FIRST_TYPE; i <= LAST_TYPE; i++) {
            String url = POKEAPI_TYPE_URL + i;
            try {
                ApiType apiType = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        ApiType.class).getBody();

                Type type = new Type();
                type.id = i;
                if (apiType != null) {
                    for (int j = 0; j < apiType.names.length; j++) {
                        if (apiType.names[j].language.name.equalsIgnoreCase("es")) {
                            type.name = apiType.names[j].name;
                        }
                    }

                    type = typeRepository.save(type);
                    System.out.println(type);

                    for (ApiTypeReference apiTypeReference : apiType.damage_relations.double_damage_to) {
                        TypeRelation typeRelation = new TypeRelation();
                        typeRelation.moveTypeId = i;
                        typeRelation.pokeTypeId = apiTypeReference.getId();
                        typeRelation.multiplier = 2;

                        typeRelation = typeRelationRepository.save(typeRelation);
                        System.out.println(typeRelation);
                    }

                    for (ApiTypeReference apiTypeReference : apiType.damage_relations.half_damage_to) {
                        TypeRelation typeRelation = new TypeRelation();
                        typeRelation.moveTypeId = i;
                        typeRelation.pokeTypeId = apiTypeReference.getId();
                        typeRelation.multiplier = 0.5;

                        typeRelation = typeRelationRepository.save(typeRelation);
                        System.out.println(typeRelation);
                    }

                    for (ApiTypeReference apiTypeReference : apiType.damage_relations.no_damage_to) {
                        TypeRelation typeRelation = new TypeRelation();
                        typeRelation.moveTypeId = i;
                        typeRelation.pokeTypeId = apiTypeReference.getId();
                        typeRelation.multiplier = 0;

                        typeRelation = typeRelationRepository.save(typeRelation);
                        System.out.println(typeRelation);
                    }
                }
            } catch (HttpClientErrorException e) {
                System.err.println("Error retrieving type number " + i);
            }
        }
    }

    private void importMoves() {
        for (int i = FIRST_MOVE; i <= LAST_MOVE; i++) {
            String url = POKEAPI_MOVE_URL + i;
            try {
                ApiMove apiMove = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        ApiMove.class).getBody();

                Move move = new Move();
                move.id = i;
                if (apiMove != null) {
                    for (int j = 0; j < apiMove.names.length; j++) {
                        if (apiMove.names[j].language.name.equalsIgnoreCase("es")) {
                            move.name = apiMove.names[j].name;
                        }
                    }
                    for (int j = 0; j < apiMove.flavor_text_entries.length; j++) {
                        if (apiMove.flavor_text_entries[j].language.name.equalsIgnoreCase("es")) {
                            move.flavor_text = apiMove.flavor_text_entries[j].flavor_text;
                        }
                    }

                    move.typeId = apiMove.type.getId();
                    move.power = apiMove.power;
                    move.accuracy = apiMove.accuracy;
                    move.pp = apiMove.pp;
                    move.priority = apiMove.priority;

                    move = moveRepository.save(move);
                    System.out.println(move);
                }
            } catch (HttpClientErrorException e) {
                System.err.println("Error retrieving move number " + i);
            }
        }
    }

    private void importPokemon() {
        for (int i = FIRST_POKEMON; i <= LAST_POKEMON; i++) {
            String url = POKEAPI_POKEMON_URL + i;
            try {
                ApiPokemon apiPokemon = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        ApiPokemon.class).getBody();
                if (apiPokemon != null) {
                    ApiSpecie apiSpecie = restTemplate.exchange(
                            apiPokemon.species.url,
                            HttpMethod.GET,
                            request,
                            ApiSpecie.class).getBody();

                    Pokemon pokemon = new Pokemon();
                    pokemon.id = i;

                    if (apiSpecie != null) {
                        for (int j = 0; j < apiSpecie.names.length; j++) {
                            if (apiSpecie.names[j].language.name.equalsIgnoreCase("es")) {
                                pokemon.name = apiSpecie.names[j].name;
                            }
                        }
                        for (int j = 0; j < apiSpecie.flavor_text_entries.length; j++) {
                            if (apiSpecie.flavor_text_entries[j].language.name.equalsIgnoreCase("es")) {
                                pokemon.flavor_text = apiSpecie.flavor_text_entries[j].flavor_text;
                            }
                        }


                        ApiEvolutionChain apiEvolutionChain = restTemplate.exchange(
                                apiSpecie.evolution_chain.url,
                                HttpMethod.GET,
                                request,
                                ApiEvolutionChain.class).getBody();
                        if (apiEvolutionChain != null) {
                            switch (apiEvolutionChain.chain.evolves_to.length) {
                                case 1:
                                case 2:
                                case 3:
                                    switch (apiEvolutionChain.chain.evolves_to[0].evolves_to.length) {
                                        case 0:
                                            if (pokemon.name.equalsIgnoreCase(apiEvolutionChain.chain.evolves_to[0].species.name)) {
                                                pokemon.nextEvolution = null;
                                                pokemon.evolutionLVL = null;
                                            } else {
                                                pokemon.nextEvolution = apiEvolutionChain.chain.evolves_to[0].species.name;
                                                pokemon.evolutionLVL = apiEvolutionChain.chain.evolves_to[0].evolution_details[0].min_level;
                                            }
                                            break;
                                        case 1:
                                        case 2:
                                            if (pokemon.name.equalsIgnoreCase(apiEvolutionChain.chain.evolves_to[0].species.name)) {
                                                pokemon.nextEvolution = apiEvolutionChain.chain.evolves_to[0].evolves_to[0].species.name;
                                                pokemon.evolutionLVL = apiEvolutionChain.chain.evolves_to[0].evolves_to[0].evolution_details[0].min_level;
                                            } else if (pokemon.name.equalsIgnoreCase(apiEvolutionChain.chain.evolves_to[0].evolves_to[0].species.name)) {
                                                pokemon.nextEvolution = null;
                                                pokemon.evolutionLVL = null;
                                            } else {
                                                pokemon.nextEvolution = apiEvolutionChain.chain.evolves_to[0].species.name;
                                                pokemon.evolutionLVL = apiEvolutionChain.chain.evolves_to[0].evolution_details[0].min_level;
                                            }
                                            break;
                                        default:
                                            System.err.println("TE BAÑASTE WE");
                                    }
                                    break;
                                case 8:
                                    pokemon.nextEvolution = "Jolteon / Flareon / Vaporeon";
                                    pokemon.evolutionLVL = null;
                                    break;
                                default:
                                    pokemon.nextEvolution = null;
                                    pokemon.evolutionLVL = null;
                            }
                        }

                        for (ApiPokemon.PokemonTypeReference typeReference : apiPokemon.types) {
                            switch (typeReference.slot) {
                                case 1:
                                    pokemon.type1Id = typeReference.type.getId();
                                    break;
                                case 2:
                                    pokemon.type2Id = typeReference.type.getId();
                                    break;
                                default:
                                    System.err.println("TE BAÑASTE WE");
                            }
                        }
                        for (ApiPokemon.Stat stat : apiPokemon.stats) {
                            switch (stat.stat.name) {
                                case "hp":
                                    pokemon.baseHp = stat.base_stat;
                                    break;
                                case "attack":
                                    pokemon.baseAtk = stat.base_stat;
                                    break;
                                case "defense":
                                    pokemon.baseDef = stat.base_stat;
                                    break;
                                case "special-attack":
                                    pokemon.baseSpAtk = stat.base_stat;
                                    break;
                                case "special-defense":
                                    pokemon.baseSpDef = stat.base_stat;
                                    break;
                                case "speed":
                                    pokemon.baseSpd = stat.base_stat;
                                    break;
                                default:
                                    System.err.println("TE BAÑASTE WE");
                            }
                        }
                        pokemon = pokemonRepository.save(pokemon);
                        System.out.println(pokemon);

                        for (ApiPokemon.MoveReference moveReference : apiPokemon.moves) {
                            PokemonMove pokemonMove = new PokemonMove();
                            pokemonMove.pokemonId = i;
                            pokemonMove.moveId = moveReference.move.getId();

                            pokemonMove = pokemonMoveRepository.save(pokemonMove);
                            System.out.println(pokemonMove);
                        }
                    }
                }
            } catch (HttpClientErrorException e) {
                System.err.println("Error retrieving move number " + i);
            }
        }
    }
}
