package com.qtong.afinance.module.pojo.interf;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接口管理表
 */
public class Interface {
	
	private Integer id; //主键
	private String interfaceName;//接口名称
	private String interfaceUrl;//接口URL
	private Integer partnerId;//合作伙伴Id
	private String partnerName;//合作伙伴名称
	private String secretId;//试金石分配账户
	private String secretKey;//试金石分配密钥
	private String afinAccount;//和金融分配客户帐户
	private String afinKey;//和金融分配秘钥
	private String serviceCode;//和金融服务编码
	private String customerNumber;//客户编码(ECID)
	private String customerName;//客户名称
	private String accountType;//类型
	private Integer state;//状态 0在用  1停用
	private String productCode;//产品编码
	
	private Integer value;//新增    返回值类型  0返回值key为产品英文名    1返回值key为产品编码  
	
	private Timestamp createTime;//创建时间
	private List<Map<String,Object>> bindProducts=new ArrayList<>();
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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
	public String getAfinAccount() {
		return afinAccount;
	}
	public void setAfinAccount(String afinAccount) {
		this.afinAccount = afinAccount;
	}
	public String getAfinKey() {
		return afinKey;
	}
	public void setAfinKey(String afinKey) {
		this.afinKey = afinKey;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public int getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

}
