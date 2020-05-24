package com.yzh.oa.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yzh.oa.global.Contant;
import com.yzh.oa.dao.mappers.ClaimVoucherDao;
import com.yzh.oa.dao.mappers.ClaimVoucherItemDao;
import com.yzh.oa.dao.mappers.DealRecordDao;
import com.yzh.oa.dao.mappers.EmployeeDao;
import com.yzh.oa.dao.pojo.ClaimVoucher;
import com.yzh.oa.dao.pojo.ClaimVoucherItem;
import com.yzh.oa.dao.pojo.DealRecord;
import com.yzh.oa.dao.pojo.Employee;
import org.springframework.stereotype.Service;


import com.yzh.oa.service.ClaimVoucherService;
@Service("cliamVoucherBiz")
public class ClaimVoucherServiceImpl implements ClaimVoucherService {
    @Resource
    private ClaimVoucherDao claimVoucherDao;
    @Resource
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Resource
    private DealRecordDao dealRecordDao;
    @Resource
    private EmployeeDao employeeDao;

    /**
     * 保存保险单对象和项目明细
     */
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
    	//创建时间为当前时间
        claimVoucher.setCreateTime(new Date());
        //下一个执行人为当前创建人  因为没有提交
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //状态为已创建
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        //执行插入
        claimVoucherDao.insert(claimVoucher);
        //循环遍历明细  明细中的报销单id为从传来报销单中获取
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    /**
     * 通过报销单id选择出详细的报销单信息
     */
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    /**
     * 通过报销单id选出该报销单的所有项目明细
     */
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    /**
     * 获取处理明细 包括处理人的信息
     */
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    /**
     * 通过创建人的sn获取报销单
     */
	@Override
	public List<ClaimVoucher> getForSelf(String sn) {
		return claimVoucherDao.selectByCreateSn(sn);
	}

	/**
	 * 通过下一个处理人的sn查找并返回下一个处理人
	 */
	@Override
	public List<ClaimVoucher> getForDeal(String sn) {
		return claimVoucherDao.selectByNextDealSn(sn);
	}

	/**
	 * 更新报销单
	 * 
	 * 更新原来就有的  插入原来没有的
	 */
	@Override
	public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);
        
        //查找某个报销单的项目明细
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        //用已存在的项目明细 去比较 传过来的项目明细  判断有无冲突 有则删除
        for(ClaimVoucherItem old : olds){
        	boolean isHave = false;
        	//遍历前端传来的表单 判断数据库中是否已存在
        	for(ClaimVoucherItem item: items){
        		if(item.getId().equals(old.getId())){
        			isHave=true;
        			break;
        		}
        	}
        	//删除前端传来  后端不存在的
        	if(!isHave){
        		claimVoucherItemDao.delete(old.getId());
        	}
        }
        for(ClaimVoucherItem item : items){
        	//设置项目所在的报销单id
        	item.setClaimVoucherId(claimVoucher.getId());
        	int id = item.getId() == null?0:item.getId();
        	if(id>0){
        		claimVoucherItemDao.update(item);
        	}else{
        		claimVoucherItemDao.insert(item);
        	}
        }
        
	}

	/**
	 * 提交
	 * 
	 * 员工创建报销单提交到部门经理 状态变为提交
	 * 
	 * 部门经理创建报销单提交到总经理 
	 * 
	 * 总经理创建报销单直接提交财务
	 */
	@Override
	public void submit(int id) {
		ClaimVoucher claimVoucher = claimVoucherDao.select(id);
		Employee employee = employeeDao.select(claimVoucher.getCreateSn());
		claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
		//财务报销
		if(employee.getPost().equals(Contant.POST_CASHIER)){
			claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());
			claimVoucherDao.update(claimVoucher);
		}else if(employee.getPost().equals(Contant.POST_GM)){
			//总经理
			claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());
			claimVoucherDao.update(claimVoucher);
		}else{
			//财务部报销
			if(employee.getDepartmentSn().equals("财务部")){
				claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_CASHIER).get(0).getSn());
				claimVoucherDao.update(claimVoucher);
			}
			//其他部门
			else{
				claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
				claimVoucherDao.update(claimVoucher);
			}
		}
		//新记录
		DealRecord dealRecord = new DealRecord();
		dealRecord.setDealWay(Contant.DEAL_SUBMIT);
		dealRecord.setDealSn(employee.getSn());
		dealRecord.setClaimVoucherId(id);
		dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
		dealRecord.setDealTime(new Date());
		dealRecord.setComment("无");
		dealRecordDao.insert(dealRecord);
	}

	/**
	 * 审核
	 * 
	 * 分为不同的状态 分别判断
	 */
	@Override
	public void deal(DealRecord dealRecord) {
		ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
		Employee employee = employeeDao.select(dealRecord.getDealSn());
		dealRecord.setDealTime(new Date());
		/* 通过的话：
		 如果是总经理或者额度没有超过限制地话可以直接通过，下一个处理人为财务，否则的话要交给总经理复审
		 设置报销单状态和下一个处理人为总经理
		 */
		if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
			//没超过限制或是总经理
			if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){
				claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
				claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());
				
				dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
			}else{
				claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
				claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());
				
				dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
			}
			
		}
		 /* 打回的话：
		  	设置报销单状态为打回
		  	设置下一个处理人为创建人
		  */
		else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
			claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
			claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
			dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
		}
		/*	
		 	拒绝：
			设置状态拒绝，下一个处理人空
		*/
		else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
			claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
			claimVoucher.setNextDealSn(null);
			
			dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
		}
		/*
		 * 已打款：
			设置下一个为空，状态设置已打款
		*/
		else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
			claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
			claimVoucher.setNextDealSn(null);
			
			dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
		}
		//修改数据库数据 每个人操作完都是一条记录所以是添加
		claimVoucherDao.update(claimVoucher);
		dealRecordDao.insert(dealRecord);
	}

}
