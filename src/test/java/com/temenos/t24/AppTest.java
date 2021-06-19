package com.temenos.t24;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * This code is made by Louai Zaiter
 **/
public class AppTest {
    private String url;
    private String action;
    private String key;
    private String body;

    /*
     * public AppTest(String args) { String[] tmp= args.split("#"); this.url=
     * tmp[0]; this.action=tmp[1]; this.key=tmp[2]; if(this.action =="ADD"){
     * this.body=tmp[3]; }
     * 
     * }
     */
    public String getAction() {
        return this.action;
    }

    public String getKey() {
        return this.key;
    }

    public String getBody() {
        return this.body;
    }

    public String getURL() {
        return this.url;
    }

    public static void main(String[] args) throws IOException {
        /*  AppTest entity = new AppTest();
        System.out.println(entity.checker("jjj#GET#1222"));

      
         * AppTest entity= new AppTest(args[0]) ;
         * 
         * if(entity.getAction()=="ADD"){
         * System.out.println(entity.KycAdd(entity.getURL(), entity.getBody(),
         * entity.getKey())); }else{ System.out.println( entity.KycGet(entity.getURL(),
         * entity.getKey())); }
         */
    }

    public String checker(String trame) throws IOException {
        System.out.println("We are checking...");
        String[] tmp = trame.split("#");
        this.url = tmp[0];
        this.action = tmp[1];
        this.key = tmp[2];
        System.out.println("We splitted the String");
        System.out.println(this.action);
        if (this.action.equals("GET")) {
            System.out.println("We are getting KYC info");
            URL urlcon = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) urlcon.openConnection();
            connection.setRequestMethod("GET");
            String apiKey = this.key;
            connection.setRequestProperty("apikey", apiKey);
            System.out.println(connection.getResponseCode());
            return connection.getResponseMessage();
        } else {
            System.out.println("We are adding");
            this.body = tmp[3];
            String result = KycAdd(getURL(), getBody(), getKey());
            System.out.println(result);
            return result;
        }
    }

    public String KycGet(String url, String apikey) throws IOException {

        System.out.println("We are getting KYC info");
        HttpResponse<String> jsonResponse = Unirest.get(url).queryString("apikey", apikey).asString();
        System.out.println(jsonResponse.getBody());
        System.out.println(jsonResponse.getStatus());
        return jsonResponse.getBody().toString();
    }

    public String KycAdd(String url, String body, String apikey) {
        System.out.println("We are adding KYC");
        HttpResponse<String> stringResponse = Unirest.post(url).header("accept", "text/xml")
                .queryString("apikey", apikey).body(body).asString();
        System.out.println(stringResponse.getStatus());
        return stringResponse.toString();
    }
}
