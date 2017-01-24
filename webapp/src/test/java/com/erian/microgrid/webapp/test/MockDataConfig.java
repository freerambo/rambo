package com.erian.microgrid.webapp.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.erian.microgrid.webapp.domain.Device;
import com.erian.microgrid.webapp.exception.ResourceNotFoundException;
import com.erian.microgrid.webapp.repository.DeviceRepository;
import com.erian.microgrid.webapp.service.DeviceService;



@Configuration
public class MockDataConfig {

    @Bean
    public DeviceRepository deviceRepository() {
        final Device device = createDevice();
        DeviceRepository devices = mock(DeviceRepository.class);
        when(devices.save(any(Device.class))).thenAnswer((InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            Device result = (Device) args[0];
            result.setId(device.getId());
            result.setName(device.getName());
            result.setDescription(device.getDescription());
            result.setCreatedDate(device.getCreatedDate());
            return result;
        });

        when(devices.findOne(1000L)).thenThrow(new ResourceNotFoundException(1000L));
        when(devices.findOne(1L)).thenReturn(device);
        when(devices.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl(Arrays.asList(device), new PageRequest(0, 10), 1L));
        when(devices.findAll()).thenReturn(Arrays.asList(device));
        return devices;
    }

    
    @Bean
    public DeviceService deviceService(DeviceRepository repo){
        return new DeviceService(repo);
    }

    @Bean
    public Device createDevice() {
        Device device = new Device();
        device.setCreatedDate(new Date());
        device.setId(1L);
        device.setName("First device");
        device.setDescription("description of my first device!");
        device.setCreatedDate(new Date());

        return device;
    }
}
