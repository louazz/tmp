package com.temenos.t24;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 This code is made by Louai Zaiter 
**/
public class AppTest {
    public static void main(String[] args)
    {
        //System.out.println(KycGet("url","key"));
        //System.out.println(kycAdd("url", "body","apikey").toString());
    }

    public String KycGet(String url, String apikey) {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                .queryString("apikey", apikey).asJson();

        System.out.println(jsonResponse.getBody());
        System.out.println(jsonResponse.getStatus());
        return jsonResponse.getBody().toString();
    }

    public int KycAdd(String url, String body, String apikey) {

        HttpResponse<String> stringResponse = Unirest.post(url)
                .header("accept", "application/json").queryString("apikey", apikey)
                .body(body).asString();

        return stringResponse.getStatus();
    }
}
