package com.excilys.librarymanager.exception;

public class DaoException extends Exception{
	/*!
	 *	CONSTRUCTEURS
	 */
	public DaoException(){super();}
	
	public DaoException(String message){super(message);}
	
	public DaoException(String message, Throwable cause){super(message,cause);}
	
	public DaoException(Throwable cause){super(cause);}
}