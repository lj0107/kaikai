package com.qtong.afinance.module.pojo.interf;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口管理表
 */
public class Product {
	
	private Integer id; //主键	
	private String productCode;//产品编码
	private String secretId;//试金石分配账户
	private String secretKey;//试金石分配密钥	
	private String interfaceUrl;//接口URL
	private Timestamp createTime;//创建时间
	private List<Map<String,Object>> bindProducts=new ArrayList<>();
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSecretId() {
		return secretId;
	}
	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public List<Map<String, Object>> getBindProducts() {
		return bindProducts;
	}
	public void setBindProducts(List<Map<String, Object>> bindProducts) {
		this.bindProducts = bindProducts;
	}
	
	
	
	

}
