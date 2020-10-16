package es.chachel.pokeapi.rest;

public class ApiTypeReference {
    public String url;

    public int getId() {
        String crop = url.substring(0, url.length() - 1);
        crop = crop.substring(crop.lastIndexOf('/') + 1);
        return Integer.parseInt(crop);
    }
}
