package com.goldCityWeb.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldCityWeb.dao.BaseDao;
import com.goldCityWeb.dao.IProductDao;
import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.ProductCover;
import com.goldCityWeb.domain.ProductRecord;
import com.goldCityWeb.service.IProductService;
import com.goldCityWeb.util.AbstractModuleSuport;
import com.goldCityWeb.util.PageSupport;

@Service
public class ProductServiceImpl extends AbstractModuleSuport implements IProductService {

	@Autowired
	private IProductDao productDao;
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Product> queryProducts(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.IProductDao.queryProducts", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.IProductDao.queryProducts", "com.goldCityWeb.dao.IProductDao.queryProductsTotal", param, ps);
		}
	}
	
	@Override
	public Product queryProductById(Integer id) {
		return productDao.queryProductById(id);
	}
	
	@Override
	public ProductCover queryProductCoverById(Integer id){
		return productDao.queryProductCovers(id);
	}
	@Override
	public void updateProduct(Product product) {
		productDao.updateProduct(product);
	}
	
	@Override
	public Product saveProduct(Product product) {
		if(product.getId() != null && product.getId().intValue() > 0) {
			productDao.updateProduct(product);
			if(product.getCovers() != null && product.getCovers().size() > 0) {
				List<ProductCover> pcList = productDao.queryProductCoverByPid(product.getId());
				ProductCover pc = product.getCovers().get(0);
				pc.setPid(product.getId());
				if(pcList == null || pcList.size() == 0) {
					productDao.insetyProductCover(pc);
				} else {
					productDao.updateProductCover(pc);
				}
			}
			return product;
		} else {
			productDao.saveProduct(product);
			if(product.getCovers() != null && product.getCovers().size() > 0) {
				ProductCover pc=product.getCovers().get(0);
				pc.setPid(product.getId());
				productDao.insetyProductCover(pc);
			}
			return product;
		}
	}
	
	public void saveProductCover(ProductCover p){
		 productDao.updateProductCover(p);
	}
	@Override
	public List<CommodityType> queryCommodityType() {
		return productDao.queryCommodityType();
	}

	@Override
	public void saveProductRecord(ProductRecord pr) {
		productDao.saveProductRecord(pr);
		baseDao.updateUserFreezeGold(pr.getU_id(), pr.getGold().floatValue());
		baseDao.updateUserGold(pr.getU_id(), pr.getGold().floatValue());
		if(pr.getType() == 0){
			productDao.updateUserCard(pr.getU_id(),pr.getP_id(),pr.getCount());
			
		}
	}

	@Override
	public List<ProductRecord> queryProductRecord(PageSupport ps, Map<String, Object> param) {
		if(ps==null){
			return this.getList("com.goldCityWeb.dao.IProductDao.queryProductRecord", param);
		} else {
			return this.getListPageSupportByManualOperation("com.goldCityWeb.dao.IProductDao.queryProductRecord", "com.goldCityWeb.dao.IProductDao.queryProductRecordTotal", param, ps);
		}
	}
	
	@Override
	public ProductRecord queryProductRecordById(Integer id) {
		return productDao.queryProductRecordById(id);
	}
	
	@Override
	public void updateProductExchangeHandle(Map<String, Object> param) {
		productDao.updateProductExchangeHandle(param);
	}
}
