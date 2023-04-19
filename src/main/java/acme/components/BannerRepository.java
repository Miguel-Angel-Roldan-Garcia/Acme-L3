/*
 * AdvertisementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.components;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.group.Banner;
import acme.framework.helpers.MomentHelper;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.displayPeriodStartDate <= :currentMoment and :currentMoment <= b.displayPeriodEndDate")
	List<Banner> findManyBanners(Date currentMoment);

	default Banner findRandomBanner() {
		Banner result;
		int index;
		ThreadLocalRandom random;
		List<Banner> list;
		Date currentMoment;

		random = ThreadLocalRandom.current();
		currentMoment = MomentHelper.getCurrentMoment();
		list = this.findManyBanners(currentMoment);
		index = random.nextInt(0, list.size());

		result = list.isEmpty() ? null : list.get(index);

		return result;
	}

}
