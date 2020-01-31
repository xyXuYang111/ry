package com.ruoyi.system.dao;

import com.ruoyi.system.domain.SysOperLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysOperLogMongoRepository extends MongoRepository<SysOperLog, String> {

	//根据Id查询
	SysOperLog findSysOperLogByOperId(String operId);

	//根据名称查询
	List<SysOperLog> findSysOperLogByTitleContaining(String title);
	//分页
	Page<SysOperLog> findBy(Pageable pageable);
	//分页----根据名字
	Page<SysOperLog> findSysOperLogByTitleContaining(String title,Pageable pageable);
}
