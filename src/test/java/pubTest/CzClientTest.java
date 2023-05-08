package pubTest;/**
 *
 */

import com.binance.connector.client.pub.CallResult;
import com.binance.connector.client.pub.CzClient;
import com.binance.connector.client.pub.model.Candlestick;
import com.binance.connector.client.pub.model.Order;

import org.junit.Assert;
import org.junit.Test;

import examples.PrivateConfig;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author yyy
 */
public class CzClientTest {

	private String symbol = "BTCUSDT";
	private BigDecimal qty = BigDecimal.valueOf(0.01);


	CzClient czClient = new CzClient(PrivateConfig.API_KEY,PrivateConfig.SECRET_KEY);


	@Test
	public void ping() {
		Assert.assertEquals(czClient.ping(), "{}");
	}

	@Test
	public void getAvgPrice() {
		System.out.println(czClient.getAvgPrice("BTCUSDT"));
	}

	@Test
	public void listSymbol() {
		System.out.println(czClient.listSymbol());
	}

	@Test
	public void listKline() {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("symbol", "BTCUSDT");
		params.put("interval", "1m");
		params.put("startTime", System.currentTimeMillis() - 1000 * 60 * 60 * 2);
		params.put("endTime", System.currentTimeMillis() - 1000 * 60 * 60);
		List<Candlestick> klines = czClient.listKline(params);
		System.out.println("数据条数：" + klines.size());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("开始时间：" + sdf.format(new Date(klines.get(0).getOpenTime())));
		System.out.println("结束时间：" + sdf.format(new Date(klines.get(klines.size() - 1).getOpenTime())));
	}

	@Test
	public void listKlineOfLastDays() {
		List<Candlestick> klines = czClient.listKlineOfLastDays("BTCUSDT", 1);
		System.out.println("数据条数：" + klines.size());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("开始时间：" + sdf.format(new Date(klines.get(0).getOpenTime())));
		System.out.println("结束时间：" + sdf.format(new Date(klines.get(klines.size() - 1).getOpenTime())));
	}


	@Test
	public void listUserAsset() {
		System.out.println(czClient.listUserAsset());
	}


	@Test
	public void getOrder() {
		Long orderId = 1763779L;
		System.out.println(czClient.getOrder(symbol, orderId));
	}

	@Test
	public void listOrders() {
		System.out.println(czClient.listOrders(symbol));
	}

	@Test
	public void cancelOrder() {
		Long orderId = 20752848116L;
		System.out.println(czClient.cancelOrder(symbol, orderId));
	}

	@Test
	public void cancelOrders() {
		System.out.println(czClient.cancelOrders(symbol));
	}

	@Test
	public void createBuyAndLimitOrder() {
		BigDecimal price = BigDecimal.valueOf(20000);
		Order order = czClient.createBuyOfLimitOrder(symbol, price, qty);
		System.out.println(order.getOrderId()); // 20752829061
	}

	@Test
	public void CancelReplaceOrder() {
		BigDecimal price = BigDecimal.valueOf(20000);
		Long orderId = 20752829061L;
		CallResult<Order> data = czClient.cancelReplaceOrder(symbol, orderId, "BUY", price.add(BigDecimal.ONE), qty);
		if (!data.getSuccess()) {
			System.out.println(data.getMessage());
		} else {
			System.out.println(data.getData().getOrderId()); // 20752848116
		}
	}

}
