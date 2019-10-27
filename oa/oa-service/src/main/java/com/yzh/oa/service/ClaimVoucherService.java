package com.yzh.oa.service;

import java.util.List;

import com.yzh.oa.pojo.ClaimVoucher;
import com.yzh.oa.pojo.ClaimVoucherItem;
import com.yzh.oa.pojo.DealRecord;

public interface ClaimVoucherService {
	void  save(ClaimVoucher claimVoucher,List<ClaimVoucherItem> items);
	
	ClaimVoucher get(int id);
	
	List<ClaimVoucherItem> getItems(int cvid);
	
	List<DealRecord> getRecords(int cvid);
	
	List<ClaimVoucher> getForSelf(String sn);
	
	List<ClaimVoucher> getForDeal(String sn);
	
	void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

	void submit(int id);

	void deal(DealRecord dealRecord);
	
}
