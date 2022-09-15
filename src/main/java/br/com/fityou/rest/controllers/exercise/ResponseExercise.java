package br.com.fityou.rest.controllers.exercise;

/**
 * @author fpach
 *
 */
public class ResponseExercise {

	public ResponseExercise() {
		
	}
	
	public ResponseExercise(String message, String technicalMessage, Boolean isSuccess) {
		this.message = message;
		this.technicalMessage = technicalMessage;
		this.isSuccess = isSuccess;
	}
	
	private Long id;
	
	private String name;
	
	private String videoLink;
	
	private String message;
	
	private String technicalMessage; 
	
	private Boolean isSuccess;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the videoLink
	 */
	public String getVideoLink() {
		return videoLink;
	}

	/**
	 * @param videoLink the videoLink to set
	 */
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the technicalMessage
	 */
	public String getTechnicalMessage() {
		return technicalMessage;
	}

	/**
	 * @param technicalMessage the technicalMessage to set
	 */
	public void setTechnicalMessage(String technicalMessage) {
		this.technicalMessage = technicalMessage;
	}

	/**
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
