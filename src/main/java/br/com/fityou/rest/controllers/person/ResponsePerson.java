/**
 * 
 */
package br.com.fityou.rest.controllers.person;

/**
 * @author fpach
 *
 */
public class ResponsePerson {

	public ResponsePerson() {
		super();
		this.isSuccess = true;
	}

	public ResponsePerson(String message, String technicalMessage, Boolean isSuccess) {
		this.message = message;
		this.technicalMessage = technicalMessage;
		this.isSuccess = isSuccess;
	}

	private Long id;

	private String name;

	private String phoneNumber;

	private Boolean acceptRecieveMessages;

	private String message;

	private String technicalMessage;

	private Boolean isSuccess;

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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the acceptRecieveMessages
	 */
	public Boolean getAcceptRecieveMessages() {
		return acceptRecieveMessages;
	}

	/**
	 * @param acceptRecieveMessages the acceptRecieveMessages to set
	 */
	public void setAcceptRecieveMessages(Boolean acceptRecieveMessages) {
		this.acceptRecieveMessages = acceptRecieveMessages;
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

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
