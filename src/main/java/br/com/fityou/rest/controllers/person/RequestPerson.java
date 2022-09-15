/**
 * 
 */
package br.com.fityou.rest.controllers.person;

/**
 * @author fpach
 *
 */
public class RequestPerson {

	private String name;
	
	private String phoneNumber;
	
	private Boolean acceptRecieveMessages;

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
}
