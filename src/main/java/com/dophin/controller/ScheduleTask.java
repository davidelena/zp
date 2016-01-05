package com.dophin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dophin.dto.EsBulkCondition;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.service.RecruitInfoService;
import com.dophin.utils.CommonUtils;
import com.dophin.utils.ElasticSearchUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 后台定时调度任务，每天1点检测所有的职位是否已经过期，过期则更改状态
 * 
 * @author David.dai
 * 
 */
@Controller
public class ScheduleTask extends BaseController implements InitializingBean {

	@Autowired
	private RecruitInfoService recruitInfoService;
	
	private static long ONE_DAY = 24 * 60 * 60 * 1000;

	@Override
	public void afterPropertiesSet() throws Exception {
		executeCheckRecruitInfoTask();
	}
	
	private static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void executeCheckRecruitInfoTask(){
		ScheduledExecutorService task = Executors.newScheduledThreadPool(1);
		long oneDay = ONE_DAY;
		long initDay = getTimeMillis(CommonUtils.TASK_TIME) - System.currentTimeMillis();
		initDay = initDay > 0 ? initDay : oneDay + initDay;
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("positionStatus", PositionStatusEnum.Online.getCode());
		List<RecruitInfoDTO> recruitInfoDTOs = recruitInfoService.queryRecruitInfos(queryMap);
		
		CheckRecruitInfoDataTask checkTask = new CheckRecruitInfoDataTask(recruitInfoDTOs, recruitInfoService);
		task.scheduleAtFixedRate(checkTask, initDay, oneDay, TimeUnit.MILLISECONDS);
	}
	
}

class CheckRecruitInfoDataTask implements Runnable {

	protected static final String RECRUIT_INFO = "recruitinfo";

	private static ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();

	private static ObjectMapper mapper = new ObjectMapper();

	private static Logger logger = Logger.getLogger(ScheduleTask.class);

	private List<RecruitInfoDTO> recruitInfoDTOs;

	private RecruitInfoService recruitInfoService;

	public CheckRecruitInfoDataTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckRecruitInfoDataTask(List<RecruitInfoDTO> recruitInfoDTOs, RecruitInfoService recruitInfoService) {
		super();
		this.recruitInfoDTOs = recruitInfoDTOs;
		this.recruitInfoService = recruitInfoService;
	}

	@Override
	public void run() {
		Date today = new Date();
		today = DateUtils.truncate(today, Calendar.DATE);
		List<EsBulkCondition> esBulkConditions = new ArrayList<EsBulkCondition>();
		for (RecruitInfoDTO item : recruitInfoDTOs) {
			try {
				// 如果职位过期，则更新相关职位信息，自动下线
				if (item.getValidityTime().compareTo(today) < 0) {
					item.setPositionStatus(PositionStatusEnum.Offline.getCode());
					int count = recruitInfoService.updateRecruitInfo(item);
					logger.info("update recruitinfo for count: " + count);
					esBulkConditions.add(new EsBulkCondition(item.getId(), mapper.writeValueAsString(item)));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				continue;
			}
		}
		//批量更新ES
		BulkResponse response = elasticSearchUtils.doBulkUpdateAction(CommonUtils.ES_INDEX, RECRUIT_INFO,
				esBulkConditions);
		logger.info(response);
	}

}
