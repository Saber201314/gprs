package com.shlr.gprs.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.shlr.gprs.domain.Channel;

/**
 * @author Administrator
 */

public class ChannelManager {
	private Map<Integer, Integer> packageMap = new HashMap();
	private Map<Integer, Double> packageDiscountMap = new HashMap();
	private int channelId;
	private int totalAmount;

	public ChannelManager(Channel channel, int totalMount) {
		this.channelId = channel.getId();
		this.totalAmount = totalMount;
		String strPackage = channel.getPackages();
		if (!StringUtils.isEmpty(strPackage)) {
			String[] packages = strPackage.split(",");
			for (String pkg : packages) {
				String[] temp = pkg.split(":");
				this.packageMap.put(Integer.valueOf(temp[0]), Integer.valueOf(temp[2]));
				this.packageDiscountMap.put(Integer.valueOf(temp[0]), Double.valueOf(temp[1]));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(Channel channel) {
		Map map = new HashMap();
		String channelPackage = channel.getPackages();
		if (StringUtils.isEmpty(channelPackage)) {
			String[] packages = channelPackage.split(",");
			for (String pkg : packages) {
				String[] temp = pkg.split(":");
				map.put(Integer.valueOf(temp[0]), Integer.valueOf(temp[2]));
				this.packageDiscountMap.put(Integer.valueOf(temp[0]), Double.valueOf(temp[1]));
			}
		}

		this.packageMap = map;
	}

	public synchronized void addAmount(int amount) {
		this.totalAmount += amount;
	}

	public int getChannelId() {
		return this.channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getPriority(int packageId) {
		return (Integer) this.packageMap.get(Integer.valueOf(packageId));
	}

	public Double getDiscount(int packageId) {
		return Double.valueOf(this.packageDiscountMap.get(Integer.valueOf(packageId)));
	}
}
