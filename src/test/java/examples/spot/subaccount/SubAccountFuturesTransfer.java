package examples.spot.subaccount;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;

public final class SubAccountFuturesTransfer {
    private SubAccountFuturesTransfer() {
    }
    private static final double amount = 100;
    private static final int type = 2;

    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("email", "");
        parameters.put("asset", "USDT");
        parameters.put("amount", amount);
        parameters.put("type", type);

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createSubAccount().futuresTransfer(parameters);
        System.out.println(result);
    }
}
