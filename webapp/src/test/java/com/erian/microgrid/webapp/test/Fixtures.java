package com.erian.microgrid.webapp.test;

import com.erian.microgrid.webapp.domain.Device;
import com.erian.microgrid.webapp.model.DeviceForm;

/**
 * Fixtures for mockup test case
 * @author zhuyb
 *
 */
public final class Fixtures {

	private Fixtures() {
		throw new InstantiationError("Must not instantiate this class");
	}

	public static Device createDevice(String name, String description) {
		Device device = new Device();
		device.setName(name);
		device.setDescription(description);

		return device;
	}

	public static DeviceForm createDeviceForm(String name, String description) {
		DeviceForm device = new DeviceForm();
		device.setName(name);
		device.setDescription(description);

		return device;
	}

}
