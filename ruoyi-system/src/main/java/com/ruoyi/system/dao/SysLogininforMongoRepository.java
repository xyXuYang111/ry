package com.ruoyi.system.dao;

import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.domain.SysMongoDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysLogininforMongoRepository extends MongoRepository<SysLogininfor, String> {

	//根据Id查询
	SysLogininfor findSysLogininforByInfoId(String infoId);

	//根据名称查询
	List<SysLogininfor> findByIpaddrAndLoginNameAndStatusContaining(String ipaddr, String loginName, String status);
	//分页
	Page<SysLogininfor> findBy(Pageable pageable);
	//分页----根据名字
	Page<SysLogininfor> findByIpaddrContaining(String ipaddr, Pageable pageable);
}
