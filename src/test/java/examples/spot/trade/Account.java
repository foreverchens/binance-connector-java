package examples.spot.trade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.JSONParser;

import examples.PrivateConfig;

import java.util.LinkedHashMap;

public final class Account {
	private Account() {
	}

	public static void main(String[] args) {
		LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

		SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                PrivateConfig.BASE_URL);

		try {
			String result = client.createTrade().account(parameters);
			System.out.println(result);
			JSONArray balances = JSON.parseObject(result).getJSONArray("balances");
			for (int i = 0; i < balances.size(); i++) {
				JSONObject jsonObject = balances.getJSONObject(i);
				if (jsonObject.getDouble("free") > 1) {
					System.out.println(jsonObject);
				}
			}
		} catch (BinanceConnectorException e) {
			System.err.println((String) String.format((String) String.format("fullErrMessage: %s", e.getMessage(),
                    e)));
		} catch (BinanceClientException e) {
			System.err.println((String) String.format("fullErrMessage: %s \nerrMessage: %s \nerrCode: %d " +
                            "\nHTTPStatusCode: %d",
					e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode()));
		}
	}
}
