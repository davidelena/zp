package com.dophin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.dophin.enums.ComputerSkillEnum;
import com.dophin.utils.CommonUtils;

public class TestCommonUtils {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadConfiguration() {
		// System.err.println(CommonUtils.ES_HOST);
		// System.err.println(CommonUtils.ES_PORT);
		// System.err.println(CommonUtils.ES_INDEX);
		Date validityTime = DateUtils.truncate(new Date(), Calendar.DATE);
		validityTime = DateUtils.setHours(validityTime, 23);
		validityTime = DateUtils.setMinutes(validityTime, 59);
		validityTime = DateUtils.setSeconds(validityTime, 59);

		System.err.println(validityTime);
	}

	@Test
	public void testRegexMobileAndEmail() {
		String email = "123@qq.com";
		System.err.println(CommonUtils.checkEmail(email));

		String mobile = "13111111111";
		System.err.println(CommonUtils.checkMobile(mobile));
	}

	private Map<String, List<ComputerSkillEnum>> getComputerSkillMap() {
		Map<String, List<ComputerSkillEnum>> resultMap = new HashMap<>();
		ComputerSkillEnum[] computerSkillEnums = ComputerSkillEnum.values();
		for (ComputerSkillEnum computerSkillEnum : computerSkillEnums) {
			if (!resultMap.containsKey(computerSkillEnum.getParent())) {
				resultMap.put(computerSkillEnum.getParent(), new ArrayList<ComputerSkillEnum>());
			}

			resultMap.get(computerSkillEnum.getParent()).add(computerSkillEnum);
		}

		return resultMap;
	}

	@Test
	public void getCompanyEmailSuffix() {
		String account = "daviddai@hupu.com";
		String companyEmailSuffix = account.contains("@") ? StringUtils.substringAfterLast(account, "@") : "";
		System.err.println(companyEmailSuffix);
	}

	@Test
	public void testPassword() {
		System.err.println(DigestUtils.md5Hex("12345678"));
	}

	@Test
	public void testComputerSkillMap() {
		Map<String, List<ComputerSkillEnum>> resultMap = new HashMap<String, List<ComputerSkillEnum>>();
		List<ComputerSkillEnum> computerSkillEnums = Arrays.asList(ComputerSkillEnum.values());
		for (ComputerSkillEnum computerSkillEnum : computerSkillEnums) {
			if (!resultMap.containsKey(computerSkillEnum.getParent())) {
				resultMap.put(computerSkillEnum.getParent(), new ArrayList<ComputerSkillEnum>());
			}

			resultMap.get(computerSkillEnum.getParent()).add(computerSkillEnum);
		}

		for (Entry<String, List<ComputerSkillEnum>> kv : resultMap.entrySet()) {
			System.err.println("=============" + kv.getKey() + "======================");
			for (ComputerSkillEnum item : kv.getValue()) {
				System.err.println(item);
			}
		}
	}
}
