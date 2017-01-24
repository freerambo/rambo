package com.erian.microgrid.webapp.api.device;

import com.erian.microgrid.webapp.Constants;
import com.erian.microgrid.webapp.model.*;
import com.erian.microgrid.webapp.exception.InvalidRequestException;

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
import org.springframework.util.CollectionUtils;
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
@RequestMapping(value = Constants.URI_API + Constants.URI_DEVICE_API)
public class DeviceController2 {

	private static final Logger log = LoggerFactory.getLogger(DeviceController2.class);
	private static RestTemplate restTemplate = new RestTemplate();


	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<Device> getAllDevices() {

		log.warn("get all devices of q@");
//		"http://172.21.76.189/MicrogridApi/devices"
		ResponseEntity<List<Device>> rateResponse =
		        restTemplate.exchange(Constants.BASE_URL + Constants.URI_DEVICE_API,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Device>>() {
		            });
		List<Device> devices = rateResponse.getBody();
		
		if(!CollectionUtils.isEmpty(devices)){
			log.debug("get devices size @" + devices.size());
		}
			

		return devices;
	}

	
	
	/* Response as below
	 * {
		  "name" : "chroma_63211_dc_load",
		  "typeName" : "DC Load",
		  "location" : "Lab at level 5",
		  "className" : "Load",
		  "id" : 0,
		  "description" : "DC LOAD chroma_63211_dc_load",
		  "model" : "chroma_63211",
		  "portNumber" : "3030",
		  "typeID" : 4,
		  "classID" : 1,
		  "microgridID" : 1,
		  "busID" : 2,
		  "microgridName" : "Lab Level 5 Microgrid",
		  "vendor" : "chroma",
		  "isConnected" : 1,
		  "isProgrammable" : 1
		}*/
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Device> getPost(@PathVariable("id") Integer id) {

		log.debug("get devicesinfo by id @" + id);
		


		Device device = restTemplate.getForObject(Constants.BASE_URL + Constants.URI_DEVICE_API+ "/" + id, Device.class, "");
		// mapping problem, o as not found
		device.setID(id);
		
		log.debug("get device @" + device);

		return new ResponseEntity<>(device, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseMessage> createDevice(@RequestBody @Valid Device device,
			BindingResult errResult) {

		log.debug("create a new device");
		if (errResult.hasErrors()) {
			throw new InvalidRequestException(errResult);
		}


//		result = restTemplate.postForObject(deviceStatusUrl, null, String.class, url, action);

		
		Device saved =  restTemplate.postForObject(Constants.BASE_URL + Constants.URI_DEVICE_API, device, Device.class);
		
		log.debug("saved device id is " + saved.getID());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(Constants.URI_API + Constants.URI_DEVICES + "/{id}").buildAndExpand(saved.getID()).toUri());

		return new ResponseEntity<>(ResponseMessage.success("device.created"), headers, HttpStatus.CREATED);

	}
	
	/**
	 * 
	 * @param args  
	 * @description    Test for the APIS
	 * @version currentVersion  
	 * @author Yuanbo Zhu  
	 * @createtime 20 Sep 2016 3:45:15 pm
	 */
	public static void main(String args[]){
		
/*		ResponseEntity<List<Device>> rateResponse =
		        restTemplate.exchange(Constants.BASE_URL + Constants.URI_DEVICE_API,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Device>>() {
		            });
		List<Device> devices = rateResponse.getBody();
		
		log.debug("get devices size @" + devices.size());*/
		
	  Device device = restTemplate.getForObject("http://172.21.76.189/MicrogridApi/devices/1", Device.class);
	  
		
		log.debug("get device @" + device);

	}

}
