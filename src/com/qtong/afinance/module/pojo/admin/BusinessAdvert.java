package com.qtong.afinance.module.pojo.admin;

public class BusinessAdvert {
	private String id;// 推荐位id
	private String advert_model;// 推荐位模块
	private String advert_address;// 推荐位位置
	private String advert_size;// 推荐位图片长和高
	private String advert_title;// 推荐位标题
	private String advert_pic;// 推荐位图片存放的位置
	private String advert_url;// 推荐位图片链接
	private String advert_sort;// 推荐位排序标识符，1代表优先，数越大越靠后
	private String advert_states;// 推荐位上下线状态，1：上线。0：下线
	private String create_time;// 发布时间
	private String online_time;// 上线时间
	private String update_time;// 更新时间
	private String custom_time;//自定义时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdvert_model() {
		return advert_model;
	}
	public void setAdvert_model(String advert_model) {
		this.advert_model = advert_model;
	}
	public String getAdvert_address() {
		return advert_address;
	}
	public void setAdvert_address(String advert_address) {
		this.advert_address = advert_address;
	}
	public String getAdvert_size() {
		return advert_size;
	}
	public void setAdvert_size(String advert_size) {
		this.advert_size = advert_size;
	}
	public String getAdvert_title() {
		return advert_title;
	}
	public void setAdvert_title(String advert_title) {
		this.advert_title = advert_title;
	}
	public String getAdvert_pic() {
		return advert_pic;
	}
	public void setAdvert_pic(String advert_pic) {
		this.advert_pic = advert_pic;
	}
	public String getAdvert_url() {
		return advert_url;
	}
	public void setAdvert_url(String advert_url) {
		this.advert_url = advert_url;
	}
	public String getAdvert_sort() {
		return advert_sort;
	}
	public void setAdvert_sort(String advert_sort) {
		this.advert_sort = advert_sort;
	}
	public String getAdvert_states() {
		return advert_states;
	}
	public void setAdvert_states(String advert_states) {
		this.advert_states = advert_states;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOnline_time() {
		return online_time;
	}
	public void setOnline_time(String online_time) {
		this.online_time = online_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCustom_time() {
		return custom_time;
	}
	public void setCustom_time(String custom_time) {
		this.custom_time = custom_time;
	}
	@Override
	public String toString() {
		return "BusinessAdvert [id=" + id + ", advert_model=" + advert_model + ", advert_address=" + advert_address
				+ ", advert_size=" + advert_size + ", advert_title=" + advert_title + ", advert_pic=" + advert_pic
				+ ", advert_url=" + advert_url + ", advert_sort=" + advert_sort + ", advert_states=" + advert_states
				+ ", create_time=" + create_time + ", online_time=" + online_time + ", update_time=" + update_time
				+ ", custom_time=" + custom_time + "]";
	}
	
	
}
