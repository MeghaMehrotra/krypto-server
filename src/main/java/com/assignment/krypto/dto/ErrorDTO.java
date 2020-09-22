package com.assignment.krypto.dto;


import java.util.List;

public class ErrorDTO {
	private List<String> message;
	private MessageType type;

	public ErrorDTO() {
		super();
	}

	/**
	 * @param message
	 * @param type
	 */
	public ErrorDTO(List<String> message, MessageType type) {
		super();
		this.message = message;
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public List<String> getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(List<String> message) {
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorDTO [message=" + message + ", type=" + type + "]";
	}

}