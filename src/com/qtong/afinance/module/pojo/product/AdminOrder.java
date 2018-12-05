package com.qtong.afinance.module.pojo.product;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class AdminOrder implements Serializable{
	
private static final long serialVersionUID = 1L;

    private String productOrderId;//产品包订购关系ID
     
    private String productNumber;//产品包订单号

    private String orderNumber;//订单号

    private String customerNumber;//客户编码

    private String customerName;//客户名称

    private String productName;//产品包名称

    private Timestamp effTime;//订购生效的时间

    private Timestamp expTime;//订购截止的时间

    private Timestamp billEffTime;//订购计费开始时间

    private String contractName;//合同名称
    
    private String contractWordUrl;//合同WordURl 
    
    private String contractPdfUrl;//合同pdfURl  

    private int state;//产品包状态
    
    private int productRatio;//产品包分成比例 1待设置 0已设置
    
    private String parentName;//产品名称
    
    private String parentOrderName;//产品名称
    
    private String proTypeId;//产品类别

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber == null ? null : customerNumber.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Timestamp getEffTime() {
        return effTime;
    }

    public void setEffTime(Timestamp effTime) {
        this.effTime = effTime;
    }

    public Timestamp getExpTime() {
        return expTime;
    }

    public void setExpTime(Timestamp expTime) {
        this.expTime = expTime;
    }

    public Timestamp getBillEffTime() {
        return billEffTime;
    }

    public void setBillEffTime(Timestamp billEffTime) {
        this.billEffTime = billEffTime;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getProTypeId() {
		return proTypeId;
	}

	public void setProTypeId(String proTypeId) {
		this.proTypeId = proTypeId;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getContractWordUrl() {
		return contractWordUrl;
	}

	public void setContractWordUrl(String contractWordUrl) {
		this.contractWordUrl = contractWordUrl;
	}

	public String getContractPdfUrl() {
		return contractPdfUrl;
	}

	public void setContractPdfUrl(String contractPdfUrl) {
		this.contractPdfUrl = contractPdfUrl;
	}

	public int getProductRatio() {
		return productRatio;
	}

	public void setProductRatio(int productRatio) {
		this.productRatio = productRatio;
	}

	public String getParentOrderName() {
		return parentOrderName;
	}

	public void setParentOrderName(String parentOrderName) {
		this.parentOrderName = parentOrderName;
	}

	
 
}