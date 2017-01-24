package com.erian.microgrid.webapp.api.device;

import com.erian.microgrid.webapp.Constants;
import com.erian.microgrid.webapp.domain.*;
import com.erian.microgrid.webapp.exception.InvalidRequestException;

import com.erian.microgrid.webapp.model.DeviceDetails;
import com.erian.microgrid.webapp.model.DeviceForm;
import com.erian.microgrid.webapp.model.ResponseMessage;
import com.erian.microgrid.webapp.service.DeviceService;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 
 * function description：Device Controller
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  Sep 14, 2016 11:42:31 PM
 */
@RestController
@RequestMapping(value = Constants.URI_API + Constants.URI_DEVICES)
public class DeviceController {

	private static final Logger log = LoggerFactory.getLogger(DeviceController.class);
	private static RestTemplate restTemplate = new RestTemplate();

	private DeviceService deviceService;

	@Inject
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<DeviceDetails>> getAllDevices(
			@RequestParam(value = "q", required = false) String keyword, //
			@RequestParam(value = "bus", required = false) Device.Bus bus, //
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable page) {

		log.warn("get all devices of q@" + keyword + ", bus @" + bus + ", page@" + page);

		Page<DeviceDetails> devices = deviceService.searchDevicesByCriteria(keyword, bus, page);

		log.debug("get devices size @" + devices.getTotalElements());

		return new ResponseEntity<>(devices, HttpStatus.OK);
	}

	
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Device> getPost(@PathVariable("id") Long id) {

		log.debug("get devicesinfo by id @" + id);
		
		ResponseEntity<List<Device>> rateResponse =
		        restTemplate.exchange("https://bitpay.com/api/rates",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Device>>() {
		            });
//		List<Device> device = rateResponse.getBody();

//		restTemplate.getfor.getForEntity(url, responseType, urlVariables);
		
		Device device = deviceService.findDeviceById(id);

		log.debug("get device @" + device);

		return new ResponseEntity<>(device, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseMessage> createDevice(@RequestBody @Valid DeviceForm device,
			BindingResult errResult) {

		log.debug("create a new device");
		if (errResult.hasErrors()) {
			throw new InvalidRequestException(errResult);
		}

		DeviceDetails saved = deviceService.saveDevice(device);

		log.debug("saved device id is @" + saved.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(Constants.URI_API + Constants.URI_DEVICES + "/{id}").buildAndExpand(saved.getId()).toUri());

		return new ResponseEntity<>(ResponseMessage.success("device.created"), headers, HttpStatus.CREATED);

	}

}
