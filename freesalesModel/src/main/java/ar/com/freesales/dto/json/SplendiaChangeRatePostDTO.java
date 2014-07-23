package ar.com.freesales.dto.json;

@Deprecated
public class SplendiaChangeRatePostDTO {
	
	private String iMinStay;
	private String iOldMinStay;
	private Double fPrice;
	private Double fOldPrice;
	
	public SplendiaChangeRatePostDTO(String iMinStay, String iOldMinStay, Double fPrice, Double fOldPrice) {
		this.iMinStay = iMinStay;
		this.iOldMinStay = iOldMinStay;
		this.fPrice = fPrice;
		this.fOldPrice = fOldPrice;
	}

	public String getiMinStay() {
		return iMinStay;
	}

	public void setiMinStay(String iMinStay) {
		this.iMinStay = iMinStay;
	}

	public String getiOldMinStay() {
		return iOldMinStay;
	}

	public void setiOldMinStay(String iOldMinStay) {
		this.iOldMinStay = iOldMinStay;
	}

	public Double getfPrice() {
		return fPrice;
	}

	public void setfPrice(Double fPrice) {
		this.fPrice = fPrice;
	}

	public Double getfOldPrice() {
		return fOldPrice;
	}

	public void setfOldPrice(Double fOldPrice) {
		this.fOldPrice = fOldPrice;
	}
}
