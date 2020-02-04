package com.ruoyi.system.elasticsearch;

import com.ruoyi.system.domain.SysOperLogElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysOperLogElasticRepository extends ElasticsearchRepository<SysOperLogElastic, String> {

	//根据Id查询
	SysOperLogElastic findSysOperLogElasticElasticByOperId(String operId);

	//根据名称查询
	List<SysOperLogElastic> findSysOperLogElasticElasticByTitleContaining(String title);
	//分页
	Page<SysOperLogElastic> findBy(Pageable pageable);
	//分页----根据名字
	Page<SysOperLogElastic> findSysOperLogElasticElasticByTitleContaining(String title, Pageable pageable);
}
