import com.bitwork.shopgun.RestHelper;
import com.bitwork.shopgun.ShopGun;

import java.util.Map;

/**
 * Created by cha on 17-04-2017.
 */
public class Loader {

    public static void main(String[] args) {
        try {
            while(notEmpty){
                List<Tilbud> offers = ShopGun.retrieveOffers();
                ShopGun.retrieveShop(tilbud);

                List<convert(offers);
            }


            String token = RestHelper.get().getToken();
            RestHelper.get().
            Map map = RestHelper.get().get("v2/offers",);

            System.out.println("Output from Server .... \n" + map.toString());
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }




}
