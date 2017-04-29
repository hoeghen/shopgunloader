package com.bitwork.shopgun;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by christianhansen on 23/04/2017.
 */
public class RestHelper {
    private String key = "00ix2uliu8y2tobx8rowrshxwtr03g2b";
    private String secret = "00ix2uliu8w70qfdrafwhe4f1vukngas";
    private String host = "https://api.etilbudsavis.dk";
    private String hash = null;


    public RestHelper() throws NoSuchAlgorithmException {
        hash = generateSHA256(secret);

    }

    static private ObjectMapper objectMapper = new ObjectMapper();
    static private  RestHelper helper;

    public static RestHelper get() throws NoSuchAlgorithmException {
        if(helper == null){
            helper = new RestHelper();
        }
        return helper;
    }

    private Map post(String path,String input) throws java.io.IOException {
        Client client = ClientBuilder.newClient(new ClientConfig());
        System.out.println(path + ":" + input);
        Response response = client.target(host)
                .path(path)
                .request(MediaType.APPLICATION_JSON_TYPE).
                        post(Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));
        String resultString = response.readEntity(String.class);
        return objectMapper.readValue(resultString,HashMap.class);
    }

    private Map get(String path,Map<String,Object> queryParms) throws java.io.IOException {
        Client client = ClientBuilder.newClient(new ClientConfig());

        WebTarget target = client.target(host)
                .path(path);

        Set<String> keys = queryParms.keySet();
        for (String key : keys) {
            Object value = queryParms.get(key);
            target.queryParam(key,value);
        }
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

        String resultString = response.readEntity(String.class);
        return objectMapper.readValue(resultString,HashMap.class);
    }

    public String getToken() throws IOException {
        Map tokenRequest = new HashMap();
        tokenRequest.put("api_key",key);
        return (String) post("v2/sessions",objectMapper.writeValueAsString(tokenRequest)).get("token");
    }


    private  static String generateSHA256(String string) throws NoSuchAlgorithmException {
        MessageDigest digest=null;
        String hash = "";
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.update(string.getBytes());
            byte[] bytes = digest.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return hash;
    }

}
