package es.chachel.pokeapi.rest;

public class ApiType {

    public static class DamageRelations {
        public ApiTypeReference[] double_damage_to;
        public ApiTypeReference[] half_damage_to;
        public ApiTypeReference[] no_damage_to;
    }

    public DamageRelations damage_relations;
    public ApiNameReference[] names;

}
