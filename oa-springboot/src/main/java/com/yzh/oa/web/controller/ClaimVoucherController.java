package com.yzh.oa.web.controller;

import com.yzh.oa.global.Contant;
import com.yzh.oa.dao.pojo.DealRecord;
import com.yzh.oa.dao.pojo.Employee;
import com.yzh.oa.service.ClaimVoucherService;
import com.yzh.oa.web.dto.ClaimVoucherInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;



@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

	@Resource
	private ClaimVoucherService claimVoucherServiceImpl;

	/**
	 * 上传items的类别 报销单表对象
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAdd(Map<String, Object> map) {
		map.put("items", Contant.getItems());
		map.put("info", new ClaimVoucherInfo());
		return "claim_voucher_add";
	}

	/**
	 * 得到报销单对象 和登入者信息 将报销单和明细存入数据库 调转处理页面
	 * 
	 * @param session
	 * @param info
	 * @return
	 */
	@RequestMapping("/add")
	public String add(HttpSession session, ClaimVoucherInfo info) {
		// 创建人是当前的员工
		Employee employee = (Employee) session.getAttribute("employee");
		info.getClaimVoucher().setCreateSn(employee.getSn());
		// 保存报销单 和项目明细
		claimVoucherServiceImpl.save(info.getClaimVoucher(), info.getItems());
		return "redirect:deal";
	}

	/**
	 * 接受传来的id(如果形参与传来名字不同则需要添加@Param("id")) 上传
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(int id, Map<String, Object> map) {
		map.put("claimVoucher", claimVoucherServiceImpl.get(id));// 单个
		map.put("items", claimVoucherServiceImpl.getItems(id));// 集合
		map.put("records", claimVoucherServiceImpl.getRecords(id));// 集合
		return "claim_voucher_detail";
	}

	/**
	 * 获取当前用户 有自己创建的表单则 返回报销单信息
	 * 
	 * @param session
	 * @param map
	 * @return 返回个人报销单页面
	 */
	@RequestMapping("/self")
	public String self(HttpSession session, Map<String, Object> map) {
		Employee employee = (Employee) session.getAttribute("employee");
		map.put("list", claimVoucherServiceImpl.getForSelf(employee.getSn()));
		return "claim_voucher_self";
	}

	/**
	 * 有自己需要处理的表单 则 返回报销单信息
	 * 
	 * @param session
	 * @param map
	 * @return 返回待处理报销单页面
	 */
	@RequestMapping("/deal")
	public String deal(HttpSession session, Map<String, Object> map) {
		Employee employee = (Employee) session.getAttribute("employee");
		map.put("list", claimVoucherServiceImpl.getForDeal(employee.getSn()));
		return "claim_voucher_deal";
	}

	/**
	 * 修改报销单前  报销单的所有信息 存入dto中 上传
	 * 
	 * @param id 报销单id
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_update")
	public String toUpdate(int id, Map<String, Object> map) {
		map.put("items", Contant.getItems());
		ClaimVoucherInfo info = new ClaimVoucherInfo();
		info.setClaimVoucher(claimVoucherServiceImpl.get(id));
		info.setItems(claimVoucherServiceImpl.getItems(id));
		map.put("info", info);
		return "claim_voucher_update";
	}

	/**
	 * 获取当前用户属性 将前端的报销单数据传到业务层处理 然后返回待处理报销单页面
	 * 
	 * @param session
	 * @param info
	 * @return
	 */
	@RequestMapping("/update")
	public String update(HttpSession session, ClaimVoucherInfo info) {
		Employee employee = (Employee) session.getAttribute("employee");
		info.getClaimVoucher().setCreateSn(employee.getSn());
		claimVoucherServiceImpl.update(info.getClaimVoucher(), info.getItems());
		return "redirect:deal";
	}

	/**
	 * 提交报销单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/submit")
	public String submit(int id) {
		claimVoucherServiceImpl.submit(id);
		return "redirect:deal";
	}

	/**
	 * 提交后 上级要确认
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_check")
	public String toCheck(int id, Map<String, Object> map) {
		map.put("claimVoucher", claimVoucherServiceImpl.get(id));
		map.put("items", claimVoucherServiceImpl.getItems(id));
		map.put("records", claimVoucherServiceImpl.getRecords(id));
		DealRecord dealRecord = new DealRecord();
		dealRecord.setClaimVoucherId(id);
		map.put("record", dealRecord);
		return "claim_voucher_check";
	}

	/**
	 * 
	 * @param session
	 * @param dealRecord
	 * @return
	 */
	@RequestMapping("/check")
	public String check(HttpSession session, DealRecord dealRecord) {
		Employee employee = (Employee) session.getAttribute("employee");
		dealRecord.setDealSn(employee.getSn());
		claimVoucherServiceImpl.deal(dealRecord);
		return "redirect:deal";
	}
}
