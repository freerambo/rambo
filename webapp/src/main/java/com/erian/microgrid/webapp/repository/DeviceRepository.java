package com.erian.microgrid.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;

import com.erian.microgrid.webapp.domain.*;

public interface DeviceRepository extends JpaRepository<Device, Long>,//
        JpaSpecificationExecutor<Device>{

	
//    @Procedure
//    Device getDevice(Integer id);
}
