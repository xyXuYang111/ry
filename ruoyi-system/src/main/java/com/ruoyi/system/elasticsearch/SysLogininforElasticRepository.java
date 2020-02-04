package com.ruoyi.system.elasticsearch;

import com.ruoyi.system.domain.SysLogininforElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SysLogininforElasticRepository extends ElasticsearchRepository<SysLogininforElastic, String> {

	//根据Id查询
	SysLogininforElastic findSysLogininforElasticElasticByInfoId(String infoId);

	//根据名称查询
	List<SysLogininforElastic> findByIpaddrAndLoginNameAndStatus(String ipaddr, String loginName, String status);
	//分页
	Page<SysLogininforElastic> findBy(Pageable pageable);
	//分页----根据名字
	Page<SysLogininforElastic> findByIpaddrContaining(String ipaddr, Pageable pageable);
}
