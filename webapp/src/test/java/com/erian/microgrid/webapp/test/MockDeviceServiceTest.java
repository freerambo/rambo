package com.erian.microgrid.webapp.test;

import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.erian.microgrid.webapp.domain.Device;

import com.erian.microgrid.webapp.exception.ResourceNotFoundException;
import com.erian.microgrid.webapp.model.DeviceDetails;
import com.erian.microgrid.webapp.model.DeviceForm;
import com.erian.microgrid.webapp.repository.DeviceRepository;
import com.erian.microgrid.webapp.service.DeviceService;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockDataConfig.class})
public class MockDeviceServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(MockDeviceServiceTest.class);

    @Inject
    private DeviceRepository deviceRepository;

  
    @Inject
    private DeviceService deviceService;

    public MockDeviceServiceTest() {
    }

    @Before
    public void setUp() {
//        deviceService = new DeviceService(deviceRepository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSaveDevice() {
        DeviceForm form = new DeviceForm();
        form.setName("saving name");
        form.setDescription("saving description");

        DeviceDetails details = deviceService.saveDevice(form);

        LOG.debug("device details @" + details);
        assertNotNull("saved device id should not be null@", details.getId());
        assertTrue(details.getId() == 1L);

        Page<DeviceDetails> devices = deviceService.searchDevicesByCriteria("any keyword", Device.Bus.AC, new PageRequest(0, 10));

        assertTrue(devices.getTotalPages() == 1);
        assertTrue(!devices.getContent().isEmpty());
        assertTrue(devices.getContent().get(0).getId() == 1L);
        
//        DeviceForm updatingForm = new DeviceForm();
//        updatingForm.setName("updating name");
//        updatingForm.setDescription("updating description");
//        DeviceDetails updatedDetails = deviceService.updateDevice(1L, updatingForm);
//        
//        assertNotNull(updatedDetails.getId());
//        assertTrue("updating name".equals(updatedDetails.getName()));
//        assertTrue("updating description".equals(updatedDetails.getDescription()));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetNoneExistingDevice() {
        deviceService.findDeviceById(1000L);
    }

}
