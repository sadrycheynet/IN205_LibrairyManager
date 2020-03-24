package com.excilys.librarymanager.service;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.exception.DaoException;

import com.excilys.librarymanager.model.Livre;

import com.excilys.librarymanager.service.EmpruntServiceImpl;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class LivreServiceImpl implements LivreService {

    private static LivreServiceImpl instance;
	
	private LivreServiceImpl(){}
	
	public static LivreServiceImpl getInstance() {
		if(instance==null) {
			instance = new LivreServiceImpl();
		}
		return instance;
	}

    public List<Livre> getList() throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
		try{
			livres = livreDao.getList();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livres;
    }

	public List<Livre> getListDispo() throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
		try{
			livres = livreDao.getList();
			Iterator<Livre> iter = livres.listIterator();

			while (iter.hasNext()) {
				Livre livre = iter.next();
				if (!empruntService.isLivreDispo(livre.getId())) {
					iter.remove();
				}
			}
				

		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livres;
    }

	public Livre getById(int id) throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        Livre livre = null;
        
		try{
			livre = livreDao.getById(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return livre;
    }
    
	public int create(String titre, String auteur, String isbn) throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int id = 0;
		if (titre == null) {throw new ServiceException("titre du livre vide");}
		
		try{
			id = livreDao.create(titre, auteur, isbn);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return id;
    }
    
	public void update(Livre livre) throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		if (livre.getTitre() == null) {throw new ServiceException("titre du livre vide");}
		
		try{
			livreDao.update(livre);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
    }
    
	public void delete(int id) throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();

		try{
			livreDao.delete(id);
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
    }
    
	public int count() throws ServiceException {
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        int count = 0;
        
		try{
			count = livreDao.count();
		} catch (DaoException e){
			System.out.println(e.getMessage());
		}
		
		return count;
    }
    


}