package com.goldCityWeb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.UpgradeDao;
import com.goldCityWeb.domain.Upgrade;
import com.goldCityWeb.service.UpgradeService;





@Service
public class UpgradeServiceImpl implements UpgradeService {

	@Autowired
	private UpgradeDao upgradeDao;
	
	@Override
	public Upgrade selectUpgradeByos(String os) {
		return upgradeDao.selectUpgradeByos(os);
	}

	@Override
	public void saveUpgrade(Upgrade upgrade) {
		if (upgrade.getId() != null && upgrade.getId().intValue() > 0) {
			upgradeDao.updateUpgrade(upgrade);
		} else {
			upgradeDao.insertUpgrade(upgrade);
		}
			
	}

	@Override
	public List<Upgrade> selectAllUpgrade() {
		return upgradeDao.selectAllUpgrade();
	}
}
