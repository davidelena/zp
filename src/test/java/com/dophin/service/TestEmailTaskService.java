package com.dophin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dophin.dto.EmailTaskDTO;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestEmailTaskService extends TestBaseService {

	@Autowired
	private EmailTaskService emailTaskService;

	private EmailTaskDTO getEmailTaskDTO() {
		EmailTaskDTO emailTaskDTO = new EmailTaskDTO();
		emailTaskDTO.setMailto("123@qq.com");
		emailTaskDTO.setTitle("邮箱激活邮件");
		emailTaskDTO.setContent("www.baidu.com,相关内容");
		return emailTaskDTO;
	}

	@Test
	public void testInsertEmailTaskInfo() {
		int id = emailTaskService.insertEmailTaskInfo(getEmailTaskDTO());
		System.err.println(id);
	}

	@Test
	public void testUpdateEmailTaskInfo() {
		EmailTaskDTO emailTaskDTO = emailTaskService.queryEmailTaskInfo(6);
		System.err.println(emailTaskDTO);
		emailTaskDTO.setContent("更新内容");
		int count = emailTaskService.updateEmailTaskInfo(emailTaskDTO);
		System.err.println("update " + count + " record.");
		EmailTaskDTO updatEmailTaskDTO = emailTaskService.queryEmailTaskInfo(6);
		System.err.println(updatEmailTaskDTO);
	}

	@Test
	public void testDeleteEmailTaskInfo() {
		int count = emailTaskService.deleteEmailTaskInfo(5);
		System.out.println(count);
	}

	@Test
	public void testQueryEmailTaskInfo() {
		EmailTaskDTO emailTaskDTO = emailTaskService.queryEmailTaskInfo(16);
		System.err.println(emailTaskDTO);
	}

}
