package com.ibge.cnae.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibge.cnae.model.Cnae;

public class CnaeService {

    private static final String BASE_URL = "https://servicodados.ibge.gov.br/api/v2/cnae/classes/";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public CnaeService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public List<Cnae> listarTodos() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(res.body());
            List<Cnae> lista = new ArrayList<>();

            for (JsonNode item : root) {
                String id = item.path("id").asText();
                String desc = item.path("descricao").asText();
                lista.add(new Cnae(id, desc));
            }

            return lista;

        } catch (Exception e) {
            System.out.println("Erro ao consultar CNAEs: " + e.getMessage());
            return List.of();
        }
    }

    public Cnae buscarPorId(String codigo) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + codigo))
                    .GET()
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            JsonNode root = mapper.readTree(res.body());

            // A resposta vem como array
            if (root.isArray() && root.size() > 0) {
                JsonNode node = root.get(0);
                System.out.println(node);
                String id = node.path("id").asText();
                String desc = node.path("descricao").asText();
                return new Cnae(id, desc);
            }

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar CNAE: " + e.getMessage());
            return null;
        }
    }
}
