package com.yzh.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yzh.oa.pojo.DealRecord;
@Repository("dealRecordDao")
public interface DealRecordDao {
    void insert(DealRecord dealRecord);
    List<DealRecord> selectByClaimVoucher(int cvid);
}

