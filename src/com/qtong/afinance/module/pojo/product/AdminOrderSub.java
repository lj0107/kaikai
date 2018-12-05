package com.qtong.afinance.module.pojo.product;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class AdminOrderSub implements Serializable{
	
private static final long serialVersionUID = 1L;
    private String subOrderNum;//产品订购关系id

    private String productOrderId;//产品包订购关系ID

    private String productOrderNumber;//产品订单号   

    private String proCode;//产品编码

    private String proName;//产品名称

    private Timestamp effTime;//订购时间

    private Integer state;//订购状态

    private Timestamp updateTime;//更新时间

    private Double price;//单价

    private Double discount;//折扣

    private Double sum;//实收

    private String ratio;//分成比例

    private String reserveRatio;//待审核比例
    
    private String typeName;//上级名称（一级类名）
    
    private String name;//合作伙伴名称
    
    private String productName;//二级类名（产品包名称）
    
    private String contractWordUrl;
    
    private String contractPdfUrl;
    
    private String contractName;
    

    public String getProductOrderNumber() {
        return productOrderNumber;
    }

    public void setProductOrderNumber(String productOrderNumber) {
        this.productOrderNumber = productOrderNumber == null ? null : productOrderNumber.trim();
    }

  
    public String getSubOrderNum() {
        return subOrderNum;
    }

    public void setSubOrderNum(String subOrderNum) {
        this.subOrderNum = subOrderNum == null ? null : subOrderNum.trim();
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode == null ? null : proCode.trim();
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public Date getEffTime() {
        return effTime;
    }

    public void setEffTime(Timestamp effTime) {
        this.effTime = effTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio == null ? null : ratio.trim();
    }

    public String getReserveRatio() {
        return reserveRatio;
    }

    public void setReserveRatio(String reserveRatio) {
        this.reserveRatio = reserveRatio == null ? null : reserveRatio.trim();
    }


	public String getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
}