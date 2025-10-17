package com.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Get
{
    public static void main( String[] args ) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient(); // cliente http
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://servicodados.ibge.gov.br/api/v2/cnae/classes/01113"))
                                .GET()
                                .build();

        // envia e captura a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());

    }
}
