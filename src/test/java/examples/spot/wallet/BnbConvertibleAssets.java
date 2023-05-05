package examples.spot.wallet;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;

import java.util.LinkedHashMap;

/**
 * 获取可以转换成BNB的小额资产
 */
public final class BnbConvertibleAssets {
    private BnbConvertibleAssets() {
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createWallet().bnbConvertableAssets(parameters);
        System.out.println(result);
    }
}
