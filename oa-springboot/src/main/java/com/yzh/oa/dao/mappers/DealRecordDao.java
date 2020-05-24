package com.yzh.oa.dao.mappers;

import java.util.List;

import com.yzh.oa.dao.pojo.DealRecord;
import org.springframework.stereotype.Repository;

@Repository("dealRecordDao")
public interface DealRecordDao {
    void insert(DealRecord dealRecord);
    List<DealRecord> selectByClaimVoucher(int cvid);
}

