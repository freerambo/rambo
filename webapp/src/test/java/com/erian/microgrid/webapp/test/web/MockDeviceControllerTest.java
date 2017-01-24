package com.erian.microgrid.webapp.test.web;


import com.erian.microgrid.webapp.api.device.DeviceController;
import com.erian.microgrid.webapp.domain.Device;
import com.erian.microgrid.webapp.exception.ResourceNotFoundException;
import com.erian.microgrid.webapp.model.DeviceDetails;
import com.erian.microgrid.webapp.model.DeviceForm;
import com.erian.microgrid.webapp.service.DeviceService;
import com.erian.microgrid.webapp.test.Fixtures;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.hasItem;
import org.junit.AfterClass;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RunWith(MockitoJUnitRunner.class)
public class MockDeviceControllerTest {

    private static final Logger log = LoggerFactory.getLogger(MockDeviceControllerTest.class);

    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private DeviceService deviceService;

    @Mock
    Pageable pageable = mock(PageRequest.class);

    @InjectMocks
    DeviceController deviceController;

    @BeforeClass
    public static void beforeClass() {
        log.debug("==================before class=========================");
    }

    @AfterClass
    public static void afterClass() {
        log.debug("==================after class=========================");
    }

    @Before
    public void setup() {
        log.debug("==================before test case=========================");
        Mockito.reset();
        MockitoAnnotations.initMocks(this);
        mvc = standaloneSetup(deviceController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @After
    public void tearDown() {
        log.debug("==================after test case=========================");
    }

    @Test
    public void saveDevice() throws Exception {
        DeviceForm device = Fixtures.createDeviceForm("First Device", "Description of my first device!");

        when(deviceService.saveDevice(any(DeviceForm.class))).thenAnswer(new Answer<DeviceDetails>() {
            @Override
            public DeviceDetails answer(InvocationOnMock invocation) throws Throwable {
                DeviceForm fm = (DeviceForm) invocation.getArgumentAt(0, DeviceForm.class);

                DeviceDetails result = new DeviceDetails();
                result.setId(1L);
                result.setName(fm.getName());
                result.setDescription(fm.getDescription());
                result.setCreatedDate(new Date());

                return result;
            }
        });
        mvc.perform(post("/api/devices").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated());

        verify(deviceService, times(1)).saveDevice(any(DeviceForm.class));
        verifyNoMoreInteractions(deviceService);
    }

    @Test
    public void retrieveDevices() throws Exception {
        DeviceDetails device1 = new DeviceDetails();
        device1.setId(1L);
        device1.setName("First device");
        device1.setDescription("Description of first device");
        device1.setCreatedDate(new Date());

        DeviceDetails device2 = new DeviceDetails();
        device2.setId(2L);
        device2.setName("Second device");
        device2.setDescription("Description of second device");
        device2.setCreatedDate(new Date());

        when(deviceService.searchDevicesByCriteria(anyString(), any(Device.Bus.class), any(Pageable.class)))
                .thenReturn(new PageImpl(Arrays.asList(device1, device2), new PageRequest(0, 10, Direction.DESC, "createdDate"), 2));

        MvcResult response = mvc.perform(get("/api/devices?q=test&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].id", hasItem(1)))
                .andExpect(jsonPath("$.content[*].title", hasItem("First device")))
                .andReturn();

        verify(deviceService, times(1))
                .searchDevicesByCriteria(anyString(), any(Device.Bus.class), any(Pageable.class));
        verifyNoMoreInteractions(deviceService);
        log.debug("get devices result @" + response.getResponse().getContentAsString());
    }

    @Test
    public void retrieveSingleDevice() throws Exception {

        Device device1 = new Device();
        device1.setId(1L);
        device1.setName("First device");
        device1.setDescription("Description of first device");
        device1.setCreatedDate(new Date());

        when(deviceService.findDeviceById(1L)).thenReturn(device1);

        mvc.perform(get("/api/devices/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").isNumber());

        verify(deviceService, times(1)).findDeviceById(1L);
        verifyNoMoreInteractions(deviceService);
    }


    @Test()
    public void notFound() {
        when(deviceService.findDeviceById(1000L)).thenThrow(new ResourceNotFoundException(1000L));
        try {
            mvc.perform(get("/api/devices/1000").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (Exception ex) {
            log.debug("exception caught @" + ex);
        }
    }

}
