package examples.spot.margin;

import com.binance.connector.client.impl.SpotClientImpl;
import examples.PrivateConfig;
import java.util.LinkedHashMap;

public final class IsolatedTransfer {
    private IsolatedTransfer() {
    }
    private static final double amount = 1;

    public static void main(String[] args) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        parameters.put("symbol", "BNBUSDT");
        parameters.put("asset", "USDT");
        parameters.put("transFrom", "SPOT");
        parameters.put("transTo", "ISOLATED_MARGIN");
        parameters.put("amount", amount);

        String result = client.createMargin().isolatedTransfer(parameters);
        System.out.println(result);
    }
}
