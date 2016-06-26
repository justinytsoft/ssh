package com.goldCityWeb.service;

import java.util.List;
import java.util.Map;

import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.ProductCover;
import com.goldCityWeb.domain.ProductRecord;
import com.goldCityWeb.util.PageSupport;

public interface IProductService {

	/**
	 * 查询所有商品
	 * @param ps
	 * @param param
	 * @return
	 */
	List<Product> queryProducts(PageSupport ps, Map<String, Object> param);

	/**
	 * 通过ID查询商品
	 * @param id
	 * @return
	 */
	Product queryProductById(Integer id);
	
	public ProductCover queryProductCoverById(Integer id);
	
	public void saveProductRecord(ProductRecord pr);


	public void updateProduct(Product product);
	
	public Product saveProduct(Product product);
	
	
	public void saveProductCover(ProductCover p);

	public List<CommodityType> queryCommodityType();

	/**
	 * 查询商品兑换列表
	 * @param ps
	 * @param param
	 * @return
	 */
	List<ProductRecord> queryProductRecord(PageSupport ps, Map<String, Object> param);

	/**
	 * 查询兑换详情
	 * @param id
	 * @return
	 */
	ProductRecord queryProductRecordById(Integer id);

	/**
	 * 兑换处理 保存快递信息
	 * @param param
	 */
	void updateProductExchangeHandle(Map<String, Object> param);
}
