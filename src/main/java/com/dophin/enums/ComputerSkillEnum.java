package com.dophin.enums;

/**
 * 计算机技能
 * 
 * @author David.dai
 * 
 */
public enum ComputerSkillEnum {
	
	Word(1, "办公室应用类", "Word"),

	Excel(2, "办公室应用类", "Excel"),

	Powerpoint(3, "办公室应用类", "Powerpoint"),

	Visio(4, "办公室应用类", "Visio"),

	Outlook(5, "办公室应用类", "Outlook"),

	ASP(6, "程序设计类", "ASP"),

	DoNet(7, "程序设计类", ".NET"),

	CSharp(8, "程序设计类", "C#"),

	C(9, "程序设计类", "C"),

	CPlus(10, "程序设计类", "C++"),

	Java(11, "程序设计类", "Java"),

	JSP(12, "程序设计类", "JSP"),

	PHP(13, "程序设计类", "PHP"),

	VB(14, "程序设计类", "VB"),

	VCPlus(15, "程序设计类", "VC++"),

	AJAX(16, "程序设计类", "AJAX"),

	Delphi(17, "程序设计类", "Delphi"),

	Matlab(18, "程序设计类", "Matlab"),

	Python(19, "程序设计类", "Python"),

	Perl(20, "程序设计类", "Perl"),

	Spring(21, "程序设计类", "Spring"),

	SQLServer(22, "数据库类", "SQL Server"),

	Access(23, "数据库类", "Access"),

	MySQL(24, "数据库类", "MySQL"),

	Oracle(25, "数据库类", "Oracle"),

	SPSS(26, "数据库类", "SPSS"),

	CSS(27, "网页设计类", "CSS"),

	Dreamweaver(28, "网页设计类", "Dreamweaver"),

	Fireworks(29, "网页设计类", "Fireworks"),

	HTML(30, "网页设计类", "HTML"),

	Javascript(31, "网页设计类", "Javascript"),

	VBScript(32, "网页设计类", "VBScript"),

	XML(33, "网页设计类", "XML"),

	Linux(34, "操作系统类", "Linux"),

	Windows(35, "操作系统类", "Windows"),

	Unix(36, "操作系统类", "Unix"),

	InDesign(37, "排版软件类", "InDesign"),

	Pagemaker(38, "排版软件类", "Pagemaker"),

	Founder(39, "排版软件类", "方正飞腾"),

	SAP(40, "企业管理类", "SAP"),

	OracleERP(41, "企业管理类", "Oracle ERP"),

	YongYou(42, "企业管理类", "用友"),

	Kingdee(43, "企业管理类", "金蝶"),

	SAS(44, "企业管理类", "SAS"),

	MAX3D(45, "多媒体设计类", "3DMAX"),

	Authorware(46, "多媒体设计类", "Authorware"),

	Flash(47, "多媒体设计类", "Flash"),

	MAYA(48, "多媒体设计类", "MAYA"),

	Painter(49, "多媒体设计类", "Painter"),

	Premiere(50, "多媒体设计类", "Premiere"),

	Photoshop(51, "图像处理类", "Photoshop"),

	CorelDraw(52, "图像处理类", "CorelDraw"),

	Illustrator(53, "图像处理类", "Illustrator"),

	AutoCAD(54, "工程制图类", "AutoCAD"),

	Catia(55, "工程制图类", "Catia"),

	LabVIEW(56, "工程制图类", "LabVIEW"),

	ProAndE(57, "工程制图类", "Pro/E"),

	SolidWorks(58, "工程制图类", "SolidWorks"),

	FPGA(59, "硬件设计类", "FPGA"),

	PLC(60, "硬件设计类", "PLC"),

	Protel(61, "硬件设计类", "Protel"),

	VHDL(62, "硬件设计类", "VHDL");

	private int code;
	private String parent;
	private String desc;

	private ComputerSkillEnum(int code, String parent, String desc) {
		this.code = code;
		this.parent = parent;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static ComputerSkillEnum getComputerSkillEnum(int code) {
		ComputerSkillEnum result = null;
		ComputerSkillEnum[] computerSkillEnums = ComputerSkillEnum.values();
		for (ComputerSkillEnum computerSkillEnum : computerSkillEnums) {
			if (code == computerSkillEnum.getCode()) {
				result = computerSkillEnum;
				break;
			}
		}

		return result;
	}

	public static String getComputerSkillDesc(int code) {
		ComputerSkillEnum result = getComputerSkillEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
