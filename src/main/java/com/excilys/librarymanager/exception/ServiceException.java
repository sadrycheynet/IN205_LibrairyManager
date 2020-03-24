package com.excilys.librarymanager.exception;

public class ServiceException extends Exception{
	/*!
	 *	CONSTRUCTEURS
	 */
	public ServiceException(){super();}
	
	public ServiceException(String message){super(message);}
	
	public ServiceException(String message, Throwable cause){super(message,cause);}
	
	public ServiceException(Throwable cause){super(cause);}
}