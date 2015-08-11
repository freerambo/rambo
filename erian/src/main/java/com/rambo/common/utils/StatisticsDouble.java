package com.rambo.common.utils;

import java.util.List;

public class StatisticsDouble {
	private List<Double> data;
	private int size;
	private double mean;

	public StatisticsDouble(List<Double> data, double mean) {
		this.data = data;
		this.size = data.size();
		this.mean = mean;
	}

	double getVariance() {
		double temp = 0;
		for (double a : data)
			temp += Math.pow(mean - a, 2);
		return temp / size;
	}

	public double getStdDev() {
		return Math.sqrt(getVariance());
	}

	public double getMean() {
		double sum = 0;
		for (double a : data)
			sum += a;
		return sum / size;
	}

}