/**
 * 
 */
package com.goldCityWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldCityWeb.domain.Upgrade;
import com.goldCityWeb.service.UpgradeService;





/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/admin")
public class UpgradeController {
	@Autowired
	private UpgradeService upgradeService;
	
	@RequestMapping(value = "/upgrade/check", method = RequestMethod.GET)
	public String upgradeCheck(Model model) {
		return "pages/upgrade/check";
	}
	
	@RequestMapping(value = "/upgrade/load", method = RequestMethod.GET)
	public @ResponseBody Upgrade upgradeLoad(Model model, @RequestParam String os) {
		Upgrade upgrade = upgradeService.selectUpgradeByos(os);
		if (upgrade != null) {
			return upgrade;
		}
		return null;
	}
	
	
	@RequestMapping(value = "/upgrade/save", method = RequestMethod.POST)
	public @ResponseBody String videoEdit(Model model, @RequestParam(required=false) Integer id, 
			@RequestParam String os, @RequestParam String version,@RequestParam Integer must_update,@RequestParam String message,
			@RequestParam String update_path) {
		Upgrade upgrade = new Upgrade();
		upgrade.setOs(os);
		upgrade.setVersion(version);
		upgrade.setUpdate_path(update_path);
		upgrade.setMust_update(must_update);
		upgrade.setMessage(message);
		
		if (id != null && id.intValue() > 0) {
			upgrade.setId(id);
		}
		upgradeService.saveUpgrade(upgrade);
		return "ok";
	}
}
