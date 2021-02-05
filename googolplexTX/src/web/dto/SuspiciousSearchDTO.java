package web.dto;

public class SuspiciousSearchDTO {
	private Long startDate;
	private Long endDate;
	private Integer frequency;
	
	
	public SuspiciousSearchDTO() {
		super();
	}


	/**
	 * @param startDate
	 * @param endDate
	 * @param frequency
	 */
	public SuspiciousSearchDTO(Long startDate, Long endDate, Integer frequency) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.frequency = frequency;
	}


	public Long getStartDate() {
		return startDate;
	}


	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}


	public Long getEndDate() {
		return endDate;
	}


	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}


	public Integer getFrequency() {
		return frequency;
	}


	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}


	@Override
	public String toString() {
		return "SuspiciousSearchDTO [startDate=" + startDate + ", endDate=" + endDate + ", frequency=" + frequency
				+ "]";
	}
	
	
	
}
