package com.yzh.oa.dao.mappers;

import java.util.List;

import com.yzh.oa.dao.pojo.ClaimVoucher;
import org.springframework.stereotype.Repository;


@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
	void insert(ClaimVoucher claimVoucher);

	void update(ClaimVoucher claimVoucher);

	void delete(int id);

	ClaimVoucher select(int id);

	List<ClaimVoucher> selectByCreateSn(String csn);

	List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
