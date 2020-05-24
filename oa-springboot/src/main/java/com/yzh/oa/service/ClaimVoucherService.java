package com.yzh.oa.service;

import com.yzh.oa.dao.pojo.ClaimVoucher;
import com.yzh.oa.dao.pojo.ClaimVoucherItem;
import com.yzh.oa.dao.pojo.DealRecord;

import java.util.List;


public interface ClaimVoucherService {
	void  save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
	
	ClaimVoucher get(int id);
	
	List<ClaimVoucherItem> getItems(int cvid);
	
	List<DealRecord> getRecords(int cvid);
	
	List<ClaimVoucher> getForSelf(String sn);
	
	List<ClaimVoucher> getForDeal(String sn);
	
	void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

	void submit(int id);

	void deal(DealRecord dealRecord);
	
}
