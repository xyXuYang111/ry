package com.ruoyi.system.dao;

import com.ruoyi.system.domain.SysMongoDb;
import org.apache.poi.ss.usermodel.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysMongoDbRepository extends MongoRepository<SysMongoDb, String> {

	//根据Id查询
	SysMongoDb findSysMongoDbById(String id);

	//根据名称查询
	List<SysMongoDb> findByNameAndTitleContaining(String name, String title);
	//分页
	Page<SysMongoDb> findBy(Pageable pageable);
	//分页----根据名字
	Page<SysMongoDb> findByNameContaining(String name, Pageable pageable);
}
