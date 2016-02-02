package com.rambo.common.utils;

import java.util.List;

public class Statistics {
	private List<Float> data;
	private int size;
	private float mean;

	public Statistics(List<Float> data, float mean) {
		this.data = data;
		this.size = data.size();
		this.mean = mean;
	}

	float getVariance() {
		float temp = 0;
		for (float a : data)
			temp += Math.pow(mean - a, 2);
		return temp / size;
	}

	public double getStdDev() {
		return Math.sqrt(getVariance());
	}

	public float getMean() {
		float sum = 0;
		for (float a : data)
			sum += a;
		return sum / size;
	}

}