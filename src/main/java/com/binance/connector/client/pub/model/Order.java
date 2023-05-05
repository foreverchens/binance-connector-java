package com.binance.connector.client.pub.model;


import java.math.BigDecimal;

public class Order {

	private String clientOrderId;

	private BigDecimal cumQuote;

	private BigDecimal executedQty;

	private Long orderId;

	private BigDecimal origQty;

	private BigDecimal price;
	// 平均成交价格
	private BigDecimal avgPrice;


	private Boolean reduceOnly;

	private String side;

	private String positionSide;

	private String status;

	private BigDecimal stopPrice;

	private String symbol;

	private String timeInForce;

	private String type;

	private Long time;


	private Long updateTime;

	private String workingType;

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	public BigDecimal getCumQuote() {
		return cumQuote;
	}

	public void setCumQuote(BigDecimal cumQuote) {
		this.cumQuote = cumQuote;
	}

	public BigDecimal getExecutedQty() {
		return executedQty;
	}

	public void setExecutedQty(BigDecimal executedQty) {
		this.executedQty = executedQty;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOrigQty() {
		return origQty;
	}

	public void setOrigQty(BigDecimal origQty) {
		this.origQty = origQty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getReduceOnly() {
		return reduceOnly;
	}

	public void setReduceOnly(Boolean reduceOnly) {
		this.reduceOnly = reduceOnly;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getPositionSide() {
		return positionSide;
	}

	public void setPositionSide(String positionSide) {
		this.positionSide = positionSide;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getStopPrice() {
		return stopPrice;
	}

	public void setStopPrice(BigDecimal stopPrice) {
		this.stopPrice = stopPrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(String timeInForce) {
		this.timeInForce = timeInForce;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getWorkingType() {
		return workingType;
	}

	public void setWorkingType(String workingType) {
		this.workingType = workingType;
	}

	@Override
	public String toString() {
		return "Order{" + "clientOrderId='" + clientOrderId + '\'' + ", cumQuote=" + cumQuote + ", executedQty=" + executedQty + ", orderId=" + orderId + ", origQty=" + origQty + ", price=" + price + ", avgPrice=" + avgPrice + ", reduceOnly=" + reduceOnly + ", side='" + side + '\'' + ", positionSide='" + positionSide + '\'' + ", status='" + status + '\'' + ", stopPrice=" + stopPrice + ", symbol='" + symbol + '\'' + ", timeInForce='" + timeInForce + '\'' + ", type='" + type + '\'' + ", time=" + time + ", updateTime=" + updateTime + ", workingType='" + workingType + '\'' + '}';
	}
}
