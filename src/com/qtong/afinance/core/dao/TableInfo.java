package com.qtong.afinance.core.dao;


/**
 * 数据库表名静态类
 */
public class TableInfo {
	
	/**
	 * 表名
	 */
	

	public static final String b_report="afin_boss_report";
	public static final String b_report_product="afin_boss_report_product";
	public static final String mas_sending_msg="afin_mas_sending_msg";
	public static final String mas_report="afin_mas_report";
	public static final String afin_ts_usermark_info ="afin_ts_usermark_info";
	public static final String afin_ts_interact_mess ="afin_ts_interact_mess";
    //最新动态表

   

    public static final String afin_news="afin_news";
    
    public static final String afin_admin_pro_ratio="afin_ratio_confirm";

	//项目咨询表
	public static final String afin_consulting="afin_consulting";
	//用户角色权限
	public static final String afin_admin_user ="afin_admin_user";
	public static final String afin_admin_role ="afin_admin_role";
	public static final String afin_admin_resources ="afin_admin_resources";
	public static final String afin_admin_user_role ="afin_admin_user_role";
	public static final String afin_admin_role_res ="afin_admin_role_res";
	
	//产品表
	public static final String afin_admin_product ="afin_product";
	//产品类别表
	public static final String afin_admin_pro_type ="afin_product_type";
	//boss产品表
	public static final String afin_admin_boss_pro ="afin_boss_pro";
	//boss产品关联表
	public static final String afin_admin_boss_pro_rel ="afin_admin_boss_pro_rel";
	//客户产品关联表
	public static final String afin_admin_customer_pro_rel ="afin_admin_customer_pro_rel";

	
	//订单表
	public static final String afin_order ="afin_order";
	//订单子表（产品）
	public static final String afin_order_sub ="afin_order_sub";
	
	//话单表
	public static final String afin_record ="afin_record";
	public static final String afin_datamark_msg_detail ="afin_datamark_msg_detail";
	
	//编码表
	public static final String afin_boss_coding ="afin_boss_coding";

	//产品统计表
	public static final String afin_order_stats ="afin_order_stats";
	//网址卫士统计表
	public static final String afin_urlguard_stats ="afin_urlguard_stats";
	
	
	//客户信息主表
	public static final String afin_boss_customer="afin_boss_customer";
	//客户信息从表x
	public static final String afin_boss_customer_group="afin_boss_customer_group";

	//后台管理-合作伙伴
	public static final String afin_admin_partners="afin_partner";
	//推荐位表
    public static final String afinbusinessAdvert="afin_advert";

    
    //授权核验
    public static final String afin_datamark_msg ="afin_datamark_msg";
    
    //和卫士-白名单
    public static final String afin_lb_whitelist = "afin_lb_whitelist";
    public static final String afin_lb_whitelist_mobile = "afin_lb_whitelist_mobile";
    //位置比对交互信息表
    public static final String afin_lbcmp ="afin_lbcmp";
    //位置比对交互信息详细表
    public static final String afin_lbcmp_detail ="afin_lbcmp_detail";
	
    
    /**

    

	/**

	 * 各个表中状态值
	 */
	
	//0：正常    1：删除
	public static final int tApplicationStateOK=0;
	public static final int tApplicationStateDeleted=1;
	
	
	
	/**
	 * 角色表t_role中不同角色的类型
	 */
	public static final String tRoleSuperAdmin="superAdmin";
	public static final String tRoleDomainAdmin="domainAdmin";
	public static final String tRoleAppAdmin="appAdmin";
	public static final String tRoleOrdinaryUser="ordinaryUser";
	
	/**
	 * 短彩信平台管理员（superAdmin）的帐号t_user表中的user_account
	 */
	public static final String systemSuperAdmin="admin";
	public static boolean isSuperAdmin(String u){
		boolean f=false;
		if(u!=null){
			if(systemSuperAdmin.equals(u)){
				f=true;
			}
		}
		return f;
	}
	
	/**
	 * 平台信息发送核心：云MAS服务地址、云MAS的sdk帐号/密码、云MAS通道编码、企业名称
	 */
	public static final String masServiceURL="http://mas.ecloud.10086.cn/app/sdk/login";
	public static final String masSDKUser="cloudOA";
	public static final String masSDKPwd="Oa123456";
	public static final String masChanelCode="kLMXKMLE";
	public static final String enterpriseName="中国移动办公系统对外服务平台";
}
