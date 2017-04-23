
import org.glassfish.jersey.client.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by cha on 17-04-2017.
 */
public class Loader {

    public static void main(String[] args) {
    String key = "00ix2uliu8y2tobx8rowrshxwtr03g2b";
    String secret = "00ix2uliu8w70qfdrafwhe4f1vukngas";
    String host = "api.etilbudsavis.dk";

        try {
            String hash = generateSHA256(secret);


            Client client = ClientBuilder.newClient(new ClientConfig());

            String input = "{\"api_key\": \""+key+"\"}";

            Response response = client.target("https://api.etilbudsavis.dk")
                    .path("v2/sessions")
                    .request(MediaType.APPLICATION_JSON_TYPE).
                            post(Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));


            System.out.println("Output from Server .... \n");
            System.out.println(response.readEntity(String.class));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    public static String generateSHA256(String string) throws NoSuchAlgorithmException {
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
