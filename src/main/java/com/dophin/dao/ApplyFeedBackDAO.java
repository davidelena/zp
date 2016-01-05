package com.dophin.dao;

import java.util.List;
import java.util.Map;

import com.dophin.dto.ApplyFeedBackDTO;

/**
 * 查看反馈信息列表
 * @author David.dai
 *
 */
public interface ApplyFeedBackDAO
{
	List<ApplyFeedBackDTO> queryApplyFeedBackInfos(Map<String, Object> queryMap);
}
