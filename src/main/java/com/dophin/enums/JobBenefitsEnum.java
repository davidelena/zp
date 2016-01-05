package com.dophin.enums;

/**
 * 职位福利亮点枚举
 * 
 * @author dailiwei
 * 
 */
public enum JobBenefitsEnum
{

	WuXianYiJin(1, "五险一金"),

	DaiXinNianJia(2, "带薪年假"),

	NianZhongDuoXin(3, "年终多薪"),

	JiXiaoJiangJin(4, "绩效奖金多"),

	JinShengKongJian(5, "晋升空间大"),

	QiQuanJiLi(6, "期权激励"),

	YuanGongChiGu(7, "员工持股"),

	JieJiaRiJinTie(8, "节假日津贴"),

	HangYeLinXianXinChou(9, "行业领先薪酬"),
	
	JieJueHuKou(10, "解决户口"),

	GongSiQianJinHao(11, "公司前景好"),

	BanGongHuanJingHao(12, "办公环境好"),

	TanXingGongZuoZhi(13, "弹性工作制"),

	NeiBuPeiXun(14, "内部培训"),

	YouXiuTuanDui(15, "优秀团队"),

	ShiYongQiQuanXin(16, "试用期全薪"),

	ZhuFangBuTie(17, "住房补贴"),

	HuaFeiBuZhu(18, "话费补助"),

	DaCheBaoXiao(19, "打车报销"),

	MianFeiSanCan(20, "免费三餐"),

	MianFeiTiJian(21, "免费体检"),

	MianFeiBanChe(22, "免费班车"),

	MainFeiJianSheng(23, "免费健身"),

	YuanGongAnMo(24, "员工按摩"),

	YuanGongJuLeBu(25, "员工俱乐部"),

	BuXianLiangShuiGuo(26, "不限量水果零食"),

	BanNianTiaoXin(27, "半年调薪"),

	JiDuTiaoXin(28, "季度调薪"),

	DingQiTuanJian(29, "定期团建"),

	YuanGongFuMuTiJian(30, "员工父母体检");

	private int code;

	private String desc;

	private JobBenefitsEnum(int code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public static JobBenefitsEnum getJobBenefitsEnum(int code)
	{
		JobBenefitsEnum result = null;
		JobBenefitsEnum[] jobBenefitsEnums = JobBenefitsEnum.values();
		for (JobBenefitsEnum jobBenefitsEnum : jobBenefitsEnums)
		{
			if (code == jobBenefitsEnum.getCode())
			{
				result = jobBenefitsEnum;
				break;
			}
		}

		return result;
	}

	public static String getJobBenefitsDesc(int code)
	{
		JobBenefitsEnum result = getJobBenefitsEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
