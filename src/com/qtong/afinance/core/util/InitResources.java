package com.qtong.afinance.core.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.qtong.afinance.module.dao.admin.AdminResourcesDao;
import com.qtong.afinance.module.dao.admin.AdminRoleDao;
import com.qtong.afinance.module.dao.admin.AdminUserDao;
import com.qtong.afinance.module.pojo.admin.AdminResources;
import com.qtong.afinance.module.pojo.admin.AdminRole;
import com.qtong.afinance.module.pojo.admin.AdminUser;

/**
 * 初始化权限信息
 * 
 * 
 */
@Component
public class InitResources {
	@Resource
	private AdminResourcesDao resourcesDao;

	@Resource
	private AdminRoleDao roleDao;

	@Resource
	private AdminUserDao userDao;

	// 参数：名称、菜单层级、类型(0页面1功能)、上级资源id、排序、路径、创建人、描述、标识、创建时间、更新时间、状态

	/**
	 * 说明：标识mark,表示和前台页面对应，有几个表示和代办相关
	 * 
	 * 991:待审核恶意网址 992：待下发恶意网址 993：待拦截恶意网址 994：待审核误报网址 995：待下发误报网址 996：待解除误报网址
	 * 4：待创建账号客户 998：待关联产品 999：待审批事项 990：待设置分成比例的订单
	 * 
	 * 
	 * 说明：前台样式标识 首页：0 乾坤大数据：1 和位士：2 网址卫士：3 客户管理：4 产品管理：5 订单管理：6 账单管理：7 合作伙伴：8
	 * 统计分析：9 系统管理：10 门户管理：11 
	 */

	@Transactional
	public void init() {

		// 保存权限数据
		AdminResources menu, menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, menu11, menu12;
		Integer id, id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12;

		// ###########################首页##################
		// 一级权限
		menu = new AdminResources("首页", 1, String.valueOf(0),
				String.valueOf(0), 1, "admin_index", "李元芳", "", 0,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);

		// ###########################乾坤大数据##################
		// 一级权限
		menu = new AdminResources("乾坤大数据", 1, String.valueOf(1),
				String.valueOf(0), 2, null, "李元芳", "乾坤大数据，数据有乾坤", 1,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("数据标签", 2, String.valueOf(1),
				String.valueOf(id), 1, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("智能位置", 2, String.valueOf(0),
				String.valueOf(id), 2, "javascript:;", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 1);
		menu3 = new AdminResources("商业咨询", 2, String.valueOf(0),
				String.valueOf(id), 3, "javascript:;", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 1);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);
		// 三级权限
		menu4 = new AdminResources("授权核验", 3, String.valueOf(0),
				String.valueOf(id1), 1, "bigData/bigLabel", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id4 = resourcesDao.insertReturnId(menu4);

		// ###########################和位士##################
		// 一级权限
		menu = new AdminResources("和位士", 1, String.valueOf(1),
				String.valueOf(0), 3, null, "李元芳", "和位士，描述字段", 2,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("和位士", 2, String.valueOf(1),
				String.valueOf(id), 1, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		// 三级权限
		menu2 = new AdminResources("白名单", 3, String.valueOf(0),
				String.valueOf(id1), 1, "andGuards/whiteList", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu3 = new AdminResources("授权核验", 3, String.valueOf(0),
				String.valueOf(id1), 2, "andGuards/andLabel", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);

		// ###########################网址卫士##################
		// 一级权限
		menu = new AdminResources("网址卫士", 1, String.valueOf(1),
				String.valueOf(0), 4, null, "李元芳", "网址卫士，描述字段", 3,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("恶意网址待办", 2, String.valueOf(1),
				String.valueOf(id), 1, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("误报网址待办", 2, String.valueOf(1),
				String.valueOf(id), 2, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu3 = new AdminResources("网址查询", 2, String.valueOf(0),
				String.valueOf(id), 3, "websiteGuard/websiteQuery", "李元芳", "",
				null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu4 = new AdminResources("安全网址库", 2, String.valueOf(0),
				String.valueOf(id), 4, "websiteGuard/goodWebsiteLibrary",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu5 = new AdminResources("恶意网址库", 2, String.valueOf(0),
				String.valueOf(id), 5, "websiteGuard/badWebsiteLibrary",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);
		id4 = resourcesDao.insertReturnId(menu4);
		id5 = resourcesDao.insertReturnId(menu5);
		// 三级权限
		menu6 = new AdminResources("待审核", 3, String.valueOf(0),
				String.valueOf(id1), 1, "websiteGuard/badWebUpcoming", "李元芳",
				"", 991, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu7 = new AdminResources("待下发", 3, String.valueOf(0),
				String.valueOf(id1), 2, "websiteGuard/badWebIssued", "李元芳", "",
				992, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu8 = new AdminResources("待拦截", 3, String.valueOf(0),
				String.valueOf(id1), 3, "websiteGuard/badWebIntercepted",
				"李元芳", "", 993, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu9 = new AdminResources("待审核", 3, String.valueOf(0),
				String.valueOf(id2), 1, "websiteGuard/wrongWebUpcoming", "李元芳",
				"", 994, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu10 = new AdminResources("待下发", 3, String.valueOf(0),
				String.valueOf(id2), 2, "websiteGuard/wrongWebIssued", "李元芳",
				"", 995, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu11 = new AdminResources("待解除", 3, String.valueOf(0),
				String.valueOf(id2), 3, "websiteGuard/wrongWebIntercepted",
				"李元芳", "", 996, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id6 = resourcesDao.insertReturnId(menu6);
		id7 = resourcesDao.insertReturnId(menu7);
		id8 = resourcesDao.insertReturnId(menu8);
		id9 = resourcesDao.insertReturnId(menu9);
		id10 = resourcesDao.insertReturnId(menu10);
		id11 = resourcesDao.insertReturnId(menu11);

		// ###########################客户管理##################
		// 一级权限
		menu = new AdminResources("客户管理", 1, String.valueOf(0),
				String.valueOf(0), 5, "customerManagement/customerManagement",
				"李元芳", "为客户提供优质服务", 4, Timestamp.valueOf(DateUtil.toStr(
						new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);

		// ###########################产品管理##################
		// 一级权限
		menu = new AdminResources("产品管理", 1, String.valueOf(1),
				String.valueOf(0), 6, null, "李元芳", "轻松管理产品", 5,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("产品类别管理", 2, String.valueOf(0),
				String.valueOf(id), 1, "productManagement/product_sort", "李元芳",
				"", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("产品信息管理", 2, String.valueOf(0),
				String.valueOf(id), 2, "productManagement/inforProduct", "李元芳",
				"", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu3 = new AdminResources("Boss产品信息", 2, String.valueOf(0),
				String.valueOf(id), 3, "productManagement/bossProduct", "李元芳",
				"", 998, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);

		// ###########################订单管理##################
		// 一级权限
		menu = new AdminResources("订单管理", 1, String.valueOf(1),
				String.valueOf(0), 7, null, "李元芳", "订单管理，描述字段", 6,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("订单列表", 2, String.valueOf(0),
				String.valueOf(id), 1, "bill/order_manage", "李元芳", "", 990,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("分成比例审批", 2, String.valueOf(1),
				String.valueOf(id), 2, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		// 三级权限
		menu3 = new AdminResources("待审批", 3, String.valueOf(0),
				String.valueOf(id2), 1, "approval/approvalPending", "李元芳", "",
				999, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu4 = new AdminResources("已审批", 3, String.valueOf(0),
				String.valueOf(id2), 2, "approval/approvalHave", "李元芳", "",
				null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id3 = resourcesDao.insertReturnId(menu3);
		id4 = resourcesDao.insertReturnId(menu4);

		// ###########################账单管理##################
		// 一级权限
		menu = new AdminResources("账单管理", 1, String.valueOf(0),
				String.valueOf(0), 8, "bill/bill_manage", "李元芳", "账单管理，描述字段",
				7, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);

		// ###########################合作伙伴##################
		// 一级权限
		menu = new AdminResources("合作伙伴", 1, String.valueOf(0),
				String.valueOf(0), 9, "partner", "李元芳", "合作伙伴，描述字段", 8,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);

		// ###########################统计分析##################
		// 一级权限
		menu = new AdminResources("统计分析", 1, String.valueOf(1),
				String.valueOf(0), 10, null, "李元芳", "精准统计，精准分析", 9,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("业务金额", 2, String.valueOf(0),
				String.valueOf(id), 1, "businessStatistics/businessMoney",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("订购客户数", 2, String.valueOf(0),
				String.valueOf(id), 2, "businessStatistics/clientNumber",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu3 = new AdminResources("合作伙伴分成", 2, String.valueOf(0),
				String.valueOf(id), 3, "businessStatistics/partnerSharing",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);

		// ###########################系统管理##################
		// 一级权限
		menu = new AdminResources("系统管理", 1, String.valueOf(1),
				String.valueOf(0), 11, null, "李元芳", "轻松管理后台用户", 10,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("后台用户管理", 2, String.valueOf(1),
				String.valueOf(id), 1, null, "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		menu2 = new AdminResources("接口管理", 2, String.valueOf(0),
				String.valueOf(id), 2, "backgroundUserManagement/InterfaceManagement", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
						Timestamp.valueOf(DateUtil.toStr(new Date(),
								DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id2 = resourcesDao.insertReturnId(menu2);
		
		// 三级权限
		menu3 = new AdminResources("管理员管理", 3, String.valueOf(0),
				String.valueOf(id1), 1,
				"backgroundUserManagement/htguanliyuanlist", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu4 = new AdminResources("角色权限管理", 3, String.valueOf(0),
				String.valueOf(id1), 2,
				"backgroundUserManagement/htguanlijsqx", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id3 = resourcesDao.insertReturnId(menu3);
		id4 = resourcesDao.insertReturnId(menu4);

		// ###########################门户管理##################
		// 一级权限
		menu = new AdminResources("门户管理", 1, String.valueOf(1),
				String.valueOf(0), 12, null, "李元芳", "轻松管理门户", 11,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id = resourcesDao.insertReturnId(menu);
		// 二级权限
		menu1 = new AdminResources("推荐位管理", 2, String.valueOf(0),
				String.valueOf(id), 1, "portalOperationManagement/list", "李元芳",
				"", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu2 = new AdminResources("动态管理", 2, String.valueOf(0),
				String.valueOf(id), 2, "portalOperationManagement/dynamic",
				"李元芳", "", null, Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		menu3 = new AdminResources("项目咨询", 2, String.valueOf(0),
				String.valueOf(id), 3, "consult", "李元芳", "", null,
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)),
				Timestamp.valueOf(DateUtil.toStr(new Date(),
						DateUtil.YYYY_MM_DD_HH_MM_SS)), 0);
		id1 = resourcesDao.insertReturnId(menu1);
		id2 = resourcesDao.insertReturnId(menu2);
		id3 = resourcesDao.insertReturnId(menu3);

		// *************************************为了测试方便创建一个角色，拥有所有权限，并创建一个用户拥有该权限

		// 创建角色
		AdminRole role = new AdminRole();
		role.setName("超级管理员");
		role.setDescription("拥有一切权限，测试使用，请勿修改");
		role.setCreator("李元芳");
		role.setCreateTime(Timestamp.valueOf(DateUtil.toStr(new Date(),
				DateUtil.YYYY_MM_DD_HH_MM_SS)));
		role.setState(0);
		Integer roelId = roleDao.insertRoleReturnId(role);

		// 创建用户
		AdminUser user = new AdminUser();
		user.setMobile("17648888888");
		user.setPassword(DigestUtils.md5DigestAsHex("dwZaC*T:C}".getBytes()));
		user.setName("李元芳");
		user.setMail("rewr@qq.com");
		user.setJob("项目经理");
		user.setDepartment("金融行业解决方案部");
		user.setDescription("管理。。。。");
		user.setCreator("李元芳");
		user.setState(0);
		// mobile,password,name,mail,job,department,description,creator,create_time,state

		Integer userId = userDao.insertUserReturnId(user);

		// 绑定关系
		// ==》角色----权限
		List<AdminResources> list = resourcesDao.selAll();
		List<Integer> resIds = new ArrayList<Integer>();
		for (AdminResources resource : list) {
			resIds.add(resource.getId());
		}
		roleDao.addRoleRes(roelId.toString(), resIds);
		// ==》用户----角色
		roleDao.addUserRoleRel(userId.toString(), roelId.toString());
	}

	public static void main(String[] args) {
		// 需要自己获取bean
		ApplicationContext ac = new FileSystemXmlApplicationContext(
				"WebContent/WEB-INF/applicationContext*.xml");
		InitResources initResources = (InitResources) ac
				.getBean("initResources");
		initResources.init();
	}
}
