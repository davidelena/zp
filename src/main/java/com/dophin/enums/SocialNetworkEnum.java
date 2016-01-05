package com.dophin.enums;

public enum SocialNetworkEnum {

	Weibo(1, "微博"),

	Zhihu(2, "知乎"),

	LinkedIn(3, "领英"),

	Lofter(4, "Lofter"),

	Douban(5, "豆瓣"),

	Other(6, "其他");

	private int code;

	private String desc;

	private SocialNetworkEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static SocialNetworkEnum getSocialNetworkEnum(int code) {
		SocialNetworkEnum result = null;
		SocialNetworkEnum[] socialNetworkEnums = SocialNetworkEnum.values();
		for (SocialNetworkEnum socialNetworkEnum : socialNetworkEnums) {
			if (code == socialNetworkEnum.getCode()) {
				result = socialNetworkEnum;
				break;
			}
		}

		return result;
	}

	public static String getSocialNetworkDesc(int code) {
		SocialNetworkEnum result = getSocialNetworkEnum(code);
		return result == null ? "" : result.getDesc();
	}

}
