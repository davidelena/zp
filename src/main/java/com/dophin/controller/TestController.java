package com.dophin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dophin.dto.CompanyInfoDTO;
import com.dophin.dto.MemberDTO;
import com.dophin.dto.RecruitInfoDTO;
import com.dophin.enums.MemberSourceEnum;
import com.dophin.enums.PositionStatusEnum;
import com.dophin.service.CompanyMemberService;
import com.dophin.service.ConstantsService;
import com.dophin.service.MemberRecruitService;
import com.dophin.service.MemberService;
import com.dophin.service.RecruitInfoService;
import com.dophin.service.ResumeInfoService;
import com.dophin.service.biz.CompanyBusinessService;

/**
 * 测试上传下载以及图片切割的controller类
 * 
 * @author dailiwei
 * 
 */
@Controller
@RequestMapping("/test")
public class TestController {

	private static Logger logger = Logger.getLogger(TestController.class);
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private CompanyMemberService companyMemberService;

	@Autowired
	private RecruitInfoService recruitInfoService;

	@Autowired
	private ResumeInfoService resumeInfoService;

	@Autowired
	private MemberRecruitService memberRecruitService;

	@Autowired
	private CompanyBusinessService companyBusinessService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView upload() {
		ModelAndView mav = new ModelAndView("uploadTest");
		return mav;
	}

	@RequestMapping(value = "/jcrop", method = RequestMethod.GET)
	public ModelAndView jcrop() {
		ModelAndView mav = new ModelAndView("jcropTest");
		return mav;
	}

	@RequestMapping(value = "/sliderbar", method = RequestMethod.GET)
	public ModelAndView sliderbar() {
		ModelAndView mav = new ModelAndView("sliderbar");
		return mav;
	}

	@RequestMapping(value = "/test_qiniu", method = RequestMethod.GET)
	public ModelAndView testQiniu() {
		ModelAndView mav = new ModelAndView("test_qiniu");
		logger.info("execute test_qiniu view...");
		return mav;
	}

	@RequestMapping(value = "/test_slider", method = RequestMethod.GET)
	public ModelAndView testSlider() {
		ModelAndView mav = new ModelAndView("test_slider");
		return mav;
	}
	
	@RequestMapping(value = "/company_view", method = RequestMethod.GET)
	public ModelAndView companyView(String id, Integer cid) {
		logger.info(String.format("execute company_view view, id: %s, cid: %d", id, cid));
		id = id == null ? "" : id;
		cid = cid == null ? 0 : cid;

		MemberDTO memberInfo = memberService.queryMemberInfo(id);
		CompanyInfoDTO companyInfo = null;
		List<RecruitInfoDTO> recruitInfoDTOs = null;

		if (memberInfo != null) {
			if (memberInfo.getSource() == MemberSourceEnum.Enterprise.getCode()) {
				companyInfo = companyMemberService.queryCompanyInfo(memberInfo.getCompanyId());
			} else if (memberInfo.getSource() == MemberSourceEnum.Student.getCode()) {
				if (cid > 0) {
					companyInfo = companyMemberService.queryCompanyInfo(cid);
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("companyId", cid);
					queryMap.put("positionStatus", PositionStatusEnum.Online.getCode());
					queryMap.put("start", 0);
					queryMap.put("size", 5);

					recruitInfoDTOs = recruitInfoService.queryRecruitInfos(queryMap);
				} else {
					companyInfo = new CompanyInfoDTO();
					recruitInfoDTOs = new ArrayList<RecruitInfoDTO>();
				}
			}
		} else {
			memberInfo = new MemberDTO();
			companyInfo = new CompanyInfoDTO();
		}

		ModelAndView mav = new ModelAndView("company_view_test");
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("memberInfo", memberInfo);
		mav.addObject("recruitInfos", recruitInfoDTOs);

		return mav;
	}
}
