/**
 * 
 */
package com.goldCityWeb.service;

import java.util.List;

import com.goldCityWeb.domain.Upgrade;





/**
 * @author Administrator
 * 
 */
public interface UpgradeService {
	public Upgrade selectUpgradeByos(String os);

	public void saveUpgrade(Upgrade upgrade);
	
	public List<Upgrade> selectAllUpgrade();
}
