package com.erian.microgrid.webapp.service;

import com.erian.microgrid.webapp.DTOUtils;
import com.erian.microgrid.webapp.domain.*;
import com.erian.microgrid.webapp.domain.Device;
import com.erian.microgrid.webapp.exception.ResourceNotFoundException;

import com.erian.microgrid.webapp.model.*;
import com.erian.microgrid.webapp.repository.*;


import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 */
@Service
@Transactional
public class DeviceService {

	private static final Logger log = LoggerFactory.getLogger(DeviceService.class);

	private DeviceRepository deviceRepository;

//	@Autowired
//	public void setDeviceRepository(DeviceRepository deviceRepository) {
//		this.deviceRepository = deviceRepository;
//	}
	
	@Inject
	public DeviceService(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;

	}

	public Page<DeviceDetails> searchDevicesByCriteria(String q, Device.Bus bus, Pageable page) {

		log.debug("search devices by keyword@" + q + ", page @" + page);

		Page<Device> devices = deviceRepository.findAll(DeviceSpecifications.filterByKeywordAndStatus(q, bus), page);

		log.debug("get devices size @" + devices.getTotalElements());

		return DTOUtils.mapPage(devices, DeviceDetails.class);
	}

	/**
	 * 
	 * @function:find one device with ID
	 * @param id
	 * @return
	 * @author: zhuyuanbo Sep 13, 2016 3:06:02 PM
	 */
	public Device findDeviceById(Long id) {

		Assert.notNull(id, "device id can not be null");

		log.debug("find device by id@" + id);

		Device device = deviceRepository.findOne(id);

		if (device == null) {
			throw new ResourceNotFoundException(id);
		}

//		return DTOUtils.map(device, DeviceDetails.class);
		return device;
	}

	
	/**
	 * 
	 * @function: save a new device
	 * @param form
	 * @return
	 * @author: zhuyuanbo    Sep 13, 2016 3:11:15 PM
	 */
	public DeviceDetails saveDevice(DeviceForm form) {

		log.debug("save device @" + form);

		Device device = DTOUtils.map(form, Device.class);

		Device saved = deviceRepository.save(device);

		log.debug("saved device is @" + saved);

		return DTOUtils.map(saved, DeviceDetails.class);

	}

}
