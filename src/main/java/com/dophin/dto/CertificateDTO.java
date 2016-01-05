package com.dophin.dto;

/**
 * 证书
 * 
 * @author dailiwei
 * 
 */
public class CertificateDTO {
	private int id;
	private String diploma;

	public CertificateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CertificateDTO(int id, String diploma) {
		super();
		this.id = id;
		this.diploma = diploma;
	}

	public CertificateDTO(String diploma) {
		super();
		this.diploma = diploma;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	@Override
	public String toString() {
		return "CertificateDTO [id=" + id + ", diploma=" + diploma + "]";
	}

}
