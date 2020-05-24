package com.yzh.oa.dao.mappers;

import java.util.List;

import com.yzh.oa.dao.pojo.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);
    void update(ClaimVoucherItem claimVoucherItem);
    void delete(int id);
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
