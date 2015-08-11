package com.rambo.common.params;

import java.util.List;

import com.google.common.collect.Lists;

public class MeterConstants {
	// meters for spms
	// public static int[] METER_IDS_OF_SPMS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
	// 11, 12 };

	public final static List<Integer> METER_IDS_OF_SPMS = Lists.newArrayList(1,
			2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

	public final static List<String> LIST_OF_METERs_OF_NMS = Lists
			.newArrayList("KVARH_EXP_INT", "KVARH_IMP_INT", "KWH_EXP_INT",
					"KWH_IMP_INT");

	public final static List<String> LOCATIONS_OF_METERs_OF_NMS = Lists
			.newArrayList("NTU Laboratory", "Nanyang Crescent", "Nanyang Terrace",
					"Nanyang Meadow", "Nanyang Valley", "NIEO");

	public enum TYPES_OF_METERs_OF_NMS {
		KVARH_EXP_INT, KVARH_IMP_INT, KWH_EXP_INT, KWH_IMP_INT,

		CUR_ACT_CREDIT_REG, KVARH_EXP_REG, KVARH_IMP_REG, KWH_IMP_REG, KWH_EXP_REG

	};
}
