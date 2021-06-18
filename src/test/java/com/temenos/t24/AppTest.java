package com.temenos.t24;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 * This code is made by Louai Zaiter
 **/
public class AppTest {
    private String url;
    private String action;
    private String key;
    private String body;
    public AppTest(String[] args) {
     String[] tmp= args[0].split("#");
     this.url= tmp[0];
     this.action=tmp[1];
     this.key=tmp[2];
     if(tmp[3]!= null){
        this.body=tmp[3];
     }
    
    }
    public  String getAction(){
        return this.action;
    }
    public String getKey(){
        return this.key;
    } 
    public String getBody(){
        return this.body;
    }
    public String getURL(){
        return this.url;
    }
    public static void main(String[] args) {
       AppTest entity= new AppTest(args) ;
       if(entity.getAction()=="ADD"){
          System.out.println(entity.KycAdd(entity.getURL(), entity.getBody(), entity.getKey()));
       }else{
         System.out.println( entity.KycGet(entity.getURL(), entity.getKey()));
       }
        
    }

    public String KycGet(String url, String apikey) {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).header("accept", "application/json")
                .queryString("apikey", apikey).asJson();
        System.out.println(jsonResponse.getBody());
        System.out.println(jsonResponse.getStatus());
        return jsonResponse.getBody().toString();
    }

    public String KycAdd(String url, String body, String apikey) {
        HttpResponse<String> stringResponse = Unirest.post(url).header("accept", "text/xml")
                .queryString("apikey", apikey).body(body).asString();
                System.out.println(stringResponse.getStatus());
        return stringResponse.toString();
    }
}
