package com.goldCityWeb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goldCityWeb.domain.CommodityType;
import com.goldCityWeb.domain.Product;
import com.goldCityWeb.domain.ProductCover;
import com.goldCityWeb.domain.ProductRecord;

public interface IProductDao {

	Product queryProductById(Integer id);
	
	ProductCover queryProductCovers(Integer id);

	public void updateProduct(Product product);
	
	public void saveProduct(Product product);
	
	public List<CommodityType> queryCommodityType();
	
	public void saveProductRecord(ProductRecord pr);
	
	public void updateProductCover(ProductCover pc);
	
	public void updateUserCard(@Param(value="user_id") Integer id,@Param(value="p_id") Integer p_id,@Param(value="count") Integer count);
	
	public void insetyProductCover(ProductCover pc);
	
	public List<ProductCover> queryProductCoverByPid(@Param(value="pid") Integer pid);

	ProductRecord queryProductRecordById(@Param("id") Integer id);

	void updateProductExchangeHandle(@Param("param") Map<String, Object> param);
}
