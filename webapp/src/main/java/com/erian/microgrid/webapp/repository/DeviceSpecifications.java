package com.erian.microgrid.webapp.repository;

import com.erian.microgrid.webapp.domain.Device;
import com.erian.microgrid.webapp.domain.Device_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 *
 */
public final class DeviceSpecifications {
	
	private DeviceSpecifications() {
	      throw new InstantiationError( "Must not instantiate this class" );
	}

	/**
	 * 
	 * @function: dynamic search with filter 
	 * @param keyword
	 * @param status
	 * @return
	 * @author: zhuyuanbo    Sep 8, 2016 5:23:10 PM
	 */
    public static Specification<Device> filterByKeywordAndStatus(
            final String keyword,
            final Device.Bus bus) {
        return (Root<Device> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(keyword)) {
                predicates.add(
                        cb.or(
                                cb.like(root.get(Device_.name), "%" + keyword + "%"),
                                cb.like(root.get(Device_.description), "%" + keyword + "%")
                        )
                );
            }

            if (bus != null) {
                predicates.add(cb.equal(root.get(Device_.bus), bus));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
