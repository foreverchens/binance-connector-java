package com.binance.connector.client.pub;/**
 *
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.connector.client.enums.DefaultUrls;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.binance.connector.client.impl.spot.Trade;
import com.binance.connector.client.impl.spot.Wallet;
import com.binance.connector.client.pub.model.Candlestick;
import com.binance.connector.client.pub.model.Order;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 归纳一些常用的api
 *
 * @author yyy
 */
public class CzClient {

	private SpotClientImpl spotClient;

	private Market market;

	private Trade trade;

	private Wallet wallet;

	public CzClient(String apiKey, String secretKey) {
		spotClient = new SpotClientImpl(apiKey, secretKey, DefaultUrls.PROD_URL);
		market = spotClient.createMarket();
		trade = spotClient.createTrade();
		wallet = spotClient.createWallet();
	}

	public String ping() {
		return market.ping();
	}


	public BigDecimal getAvgPrice(String symbol) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		String jsonStr = market.averagePrice(param);
		return JSON.parseObject(jsonStr).getBigDecimal("price");
	}

	/**
	 * 获取所有可交易的以usdt锚定的币对
	 */
	public List<String> listSymbol() {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();

		String data = market.exchangeInfo(param);
		JSONArray symbols = JSON.parseObject(data).getJSONArray("symbols");

		List<String> result = new ArrayList<>();
		for (int i = 0; i < symbols.size(); i++) {
			JSONObject eleObj = symbols.getJSONObject(i);
			if ("TRADING".equals(eleObj.getString("status")) && "USDT".equals(eleObj.getString("quoteAsset"))) {
				result.add(eleObj.getString("symbol"));
			}
		}
		return result;
	}

	/**
	 * 获取kline集合
	 */
	public List<Candlestick> listKline(LinkedHashMap<String, Object> params) {
		return reqAndRespHandle(params);
	}


	/**
	 * 最近几天的kline
	 */
	public List<Candlestick> listKlineOfLastDays(String symbol, int day) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("symbol", symbol);
		params.put("startTime", System.currentTimeMillis() - day * 24 * 60 * 60 * 1000);
		params.put("interval", "5m");
		return reqAndRespHandle(params);
	}

	@NotNull
	private List<Candlestick> reqAndRespHandle(LinkedHashMap<String, Object> params) {
		params.put("limit", 1000);
		String text = market.klines(params);
		JSONArray jsonArr = JSON.parseArray(text);
		List<Candlestick> result = new ArrayList<>();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONArray item = jsonArr.getJSONArray(i);
			Candlestick element = new Candlestick();
			element.setOpenTime(item.getLong(0));
			element.setOpen(item.getBigDecimal(1));
			element.setHigh(item.getBigDecimal(2));
			element.setLow(item.getBigDecimal(3));
			element.setClose(item.getBigDecimal(4));
			element.setVolume(item.getBigDecimal(5));
			element.setCloseTime(item.getLong(6));
			element.setQuoteAssetVolume(item.getBigDecimal(7));
			element.setNumTrades(item.getInteger(8));
			element.setTakerBuyBaseAssetVolume(item.getBigDecimal(9));
			element.setTakerBuyQuoteAssetVolume(item.getBigDecimal(10));
			element.setIgnore(item.getBigDecimal(11));
			result.add(element);
		}
		return result;
	}

	/**
	 * 获取用户可用资产列表
	 *
	 * @return asset ➡️  free
	 */
	public Map<String, BigDecimal> listUserAsset() {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		String data = wallet.getUserAsset(param);
		JSONArray jsonArr = JSON.parseArray(data);
		Map<String, BigDecimal> result = new HashMap<>();
		for (int i = 0; i < jsonArr.size(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			if (jsonObj.getDouble("btcValuation") < 0.005) {
				// btc估值小于0。005
				continue;
			}
			result.put(jsonObj.getString("asset"), jsonObj.getBigDecimal("free"));
		}
		return result;
	}


	public Order getOrder(String symbol, Long orderId) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		param.put("orderId", orderId);
		String order = trade.getOrder(param);
		return JSON.parseObject(order, Order.class);
	}

	public List<Order> listOrders(String symbol) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		String orders = trade.getOrders(param);
		return JSON.parseArray(orders, Order.class);
	}

	public Order cancelOrder(String symbol, Long orderId) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		param.put("orderId", orderId);
		String order = trade.cancelOrder(param);
		return JSON.parseObject(order, Order.class);
	}


	public List<Order> cancelOrders(String symbol) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		String orders = trade.cancelOpenOrders(param);
		return JSON.parseArray(orders, Order.class);
	}

	public Order createBuyOfLimitOrder(String symbol, BigDecimal price, BigDecimal qty) {
		return createOrder(symbol, "BUY", "LIMIT", price, qty);
	}

	public Order createBuyOfMarketOrder(String symbol, BigDecimal price, BigDecimal qty) {
		return createOrder(symbol, "BUY", "MARKET", price, qty);
	}

	public Order createSellOfLimitOrder(String symbol, BigDecimal price, BigDecimal qty) {
		return createOrder(symbol, "SELL", "LIMIT", price, qty);
	}

	public Order createSellOfMarketOrder(String symbol, BigDecimal price, BigDecimal qty) {
		return createOrder(symbol, "SELL", "MARKET", price, qty);
	}


	public CallResult<Order> cancelReplaceOrder(String symbol, Long orderId, String orderSide, BigDecimal price,
												BigDecimal qty) {

		LinkedHashMap<String, Object> param = new LinkedHashMap<>();

		param.put("symbol", symbol);
		param.put("side", orderSide);
		param.put("type", "LIMIT");
		param.put("cancelReplaceMode", "ALLOW_FAILURE");
		param.put("timeInForce", "GTC");
		param.put("quantity", qty);
		param.put("price", price);
		param.put("cancelOrderId", orderId);
		String data = trade.cancelReplace(param);
		JSONObject jsonObj = JSON.parseObject(data);
		if (!"SUCCESS".equals(jsonObj.getString("newOrderResult"))) {
			return CallResult.failure("new order create fail");
		}
		return CallResult.success(jsonObj.getObject("newOrderResponse", Order.class));
	}


	private Order createOrder(String symbol, String orderSide, String orderType, BigDecimal price, BigDecimal qty) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		param.put("symbol", symbol);
		param.put("side", orderSide);
		param.put("type", orderType);
		param.put("timeInForce", "GTC");
		param.put("quantity", qty);
		param.put("price", price);
		String data = trade.newOrder(param);
		return JSON.parseObject(data, Order.class);
	}
}
