package com.temenos.t24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
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
        AppTest entity = new AppTest();
        System.out.println(entity.KycGet(""));

        /*
         * AppTest entity= new AppTest(args[0]) ;
         * 
         * if(entity.getAction()=="ADD"){
         * System.out.println(entity.KycAdd(entity.getURL(), entity.getBody(),
         * entity.getKey())); }else{ System.out.println( entity.KycGet(entity.getURL(),
         * entity.getKey())); }
         */
    }

    public String checker(String trame) throws IOException {
        System.out.println(KycGet(trame));
        System.out.println("We are checking...");
        String[] tmp = trame.split("#");
        this.url = tmp[0];
        this.action = tmp[1];
        this.key = tmp[2];
        System.out.println("We splitted the String");
        System.out.println(this.action);
        if (this.action.equals("GET")) {
            System.out.println("We are getting KYC info");
            String https_url = this.url;
            try {
                URL urlcon = new URL(https_url);
                HttpsURLConnection connection = (HttpsURLConnection) urlcon.openConnection();
                connection.setRequestMethod("GET");
                String apiKey = this.key;
                System.out.println("Your key: " + apiKey);
                connection.setRequestProperty("apikey", apiKey);
                System.out.println(connection.getResponseCode());
                print_https_cert(connection);
                print_content(connection);
                
                return connection.getResponseMessage();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
           
          
        } else {
            System.out.println("We are adding");
            this.body = tmp[3];
            String result = KycAdd(getURL(), getBody(), getKey());
            System.out.println(result);
            return result;
        }
    }

    public int KycGet(String trame) throws IOException {
        String[] tmp = trame.split("#");
        this.url = tmp[0];
        this.key = tmp[2];
        System.out.println("We are getting KYC info");
        /*
         * HttpResponse<String> jsonResponse =
         * Unirest.get(this.url).queryString("apikey", this.key).asString();
         * System.out.println(jsonResponse.getBody());
         * System.out.println(jsonResponse.getStatus()); return
         * jsonResponse.getBody().toString();
         */
        Unirest.config().verifySsl(false);
        return Unirest.get(this.url).header("apikey", this.key).asString().getStatus();
    }

    public String KycAdd(String url, String body, String apikey) {
        Unirest.config().verifySsl(false);
        System.out.println("We are adding KYC");
        HttpResponse<String> stringResponse = Unirest.post(url).header("accept", "text/xml")
                .queryString("apikey", apikey).body(body).asString();
        System.out.println(stringResponse.getStatus());
        return stringResponse.toString();
    }

    private void print_https_cert(HttpsURLConnection con) {

        if (con != null) {

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                java.security.cert.Certificate[] certs = con.getServerCertificates();
                for (java.security.cert.Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void print_content(HttpsURLConnection con) {
        if (con != null) {

            try {

                System.out.println("****** Content of the URL ********");
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
