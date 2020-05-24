package com.yzh.oa.web.dto;

import com.yzh.oa.dao.pojo.ClaimVoucher;
import com.yzh.oa.dao.pojo.ClaimVoucherItem;

import java.util.List;


/**
 * 收集浏览器传来的数据用dto
 * 
 * 因为保险单填写页面包含报销单和项目
 * @author listener
 *
 */
public class ClaimVoucherInfo {
	private ClaimVoucher claimVoucher;
	private List<ClaimVoucherItem> items;
	public ClaimVoucher getClaimVoucher() {
		return claimVoucher;
	}
	public void setClaimVoucher(ClaimVoucher claimVoucher) {
		this.claimVoucher = claimVoucher;
	}
	public List<ClaimVoucherItem> getItems() {
		return items;
	}
	public void setItems(List<ClaimVoucherItem> items) {
		this.items = items;
	}
}
