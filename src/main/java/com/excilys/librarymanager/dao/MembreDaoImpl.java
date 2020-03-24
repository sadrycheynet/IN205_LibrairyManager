package com.excilys.librarymanager.dao;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Abonnement;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao{
	
	/*!
	 * ATTRIBUTS
	 */
	private static MembreDaoImpl instance;
	
	/*!
	 * CONSTRUCTEUR
	 */
	private MembreDaoImpl(){}
	
	public static MembreDaoImpl getInstance() {
		if(instance==null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}
	
	/*!
	 *	REQUETES
	 */
	
	/*!
	 *	Permet de récupérer un membre par son id passé en paramètre.
	 *	@param id	Id du membre que l'on souhaite récupérer.
	 */
	@Override
	public Membre getById(int id) throws DaoException{
		//On établit la connection à la base
		Connection connection = null;
		Membre selectedMembre = new Membre();
		try {
			connection = ConnectionManager.getConnection();
			//On prépare la requête
			String readQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?";
			PreparedStatement readPreparedStatement = null;
			
			readPreparedStatement = connection.prepareStatement(readQuery);
			readPreparedStatement.setString(1, String.valueOf(id));
			
			//On récupère le membre recherché
			ResultSet rs = readPreparedStatement.executeQuery();
			Abonnement abonnement = Abonnement.valueOf(rs.getString("abonnement"));
			selectedMembre = new Membre(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abonnement);
			
			//On clot le statement
			readPreparedStatement.close();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (NumberFormatException e) {
			throw new DaoException("Problème de parsing");
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
		
		//On retourne le membre récupéré si tout s'est bien déroulé		
		return(selectedMembre);
	}
	
	/*!
	 *	Permet de récupérer la liste de tous les membres inscrits.
	 */
	@Override
	public List<Membre> getList() throws DaoException{
		//On établit la connection à la base
		Connection connection = null;
		ArrayList<Membre> allMembers = new ArrayList<Membre>();
		try {
			connection = ConnectionManager.getConnection();
			//On prépare la requête
			String readQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre";
			PreparedStatement readPreparedStatement = null;
			
			readPreparedStatement = connection.prepareStatement(readQuery);
			
			//On récupère les membres dans la table
			ResultSet rs = readPreparedStatement.executeQuery();
			while(rs.next()) {
				allMembers.add(new Membre(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement"))));
			}
			
			//On clot le statement
			readPreparedStatement.close();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (NumberFormatException e) {
			throw new DaoException("Problème de parsing");
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
		return(allMembers);			
	}
	
	/*!
	 *	Permet de créer un membre à partir des informations le concernant. Renvoie l'id du membre ainsi créé.
	 *	@param nom			Nom du membre à ajouter à la table.
	 *	@param prenom		Prénom du membre à ajouter à la table.
	 *	@param adresse		Adresse du membre à ajouter à la table.
	 *	@param email		Email du membre à ajouter à la table.
	 *	@param telephone	Téléphone du membre à ajouter à la table.
	 *
	 *	@return	Identifiant du membre créé
	 */
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
		Connection connection = null;
		int id = 0;
		try {
			connection = ConnectionManager.getConnection();
			//On bloque le commit
			connection.setAutoCommit(false);
			
			PreparedStatement createStatement = null;
			createStatement = connection.prepareStatement("INSERT INTO membre (nom, prenom, adresse, email, telephone, abonnement) values (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			createStatement.setString(1, nom);
			createStatement.setString(2, prenom);
			createStatement.setString(3, adresse);
			createStatement.setString(4, email);
			createStatement.setString(5, telephone);
			createStatement.setString(6,"BASIC");		//L'abonnement par défaut sera considéré comme le BASIC.
			
			createStatement.executeUpdate();
			
			ResultSet rs = createStatement.getGeneratedKeys();
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			createStatement.close();
			
			//On commit à l'ancienne
			connection.commit();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
		
		return id;
	}
	
	/*!
	 *	Met à jour les informations du membre passé en paramètre.
	 *	@param membre	Membre à modifier.
	 */
	@Override
	public void update(Membre membre) throws DaoException{
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			//On bloque le commit
			connection.setAutoCommit(false);
			
			String updateQuery = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?";
			
			PreparedStatement updateStatement = null;
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setString(1, membre.getNom());
			updateStatement.setString(2, membre.getPrenom());
			updateStatement.setString(3, membre.getAdresse());
			updateStatement.setString(4, membre.getEmail());
			updateStatement.setString(5, membre.getTelephone());
			Abonnement abonnement = membre.getAbonnement();
			updateStatement.setString(6, abonnement.name());
			updateStatement.setString(7, String.valueOf(membre.getId()));
			
			updateStatement.executeUpdate();
			updateStatement.close();
			
			//On commit à l'ancienne
			connection.commit();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
	}
	
	/*!
	 *	Supprime le membre dont l'id est passé en paramètre.
	 *	@param id	Id du membre à supprimer.
	 */
	@Override
	public void delete(int id) throws DaoException{
		Connection connection = null;
		try {
			//On bloque le commit
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			String updateQuery = "DELETE FROM membre WHERE id = ?";
			
			PreparedStatement updateStatement = null;
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.setString(1, String.valueOf(id));
			
			updateStatement.executeUpdate();
			updateStatement.close();
			
			//On commit à l'ancienne
			connection.commit();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
	}
	
	/*!
	 *	Renvoie le nombre de membres de notre librairie.
	 *	@return	Nombre de membres de notre librairie.
	 */
	@Override
	public int count() throws DaoException{
		Connection connection = null;
		int counter = 0;
		try {
			//On bloque le commit
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(false);
			
			String updateQuery = "SELECT COUNT(id) AS count FROM membre";
			
			PreparedStatement updateStatement = null;
			updateStatement = connection.prepareStatement(updateQuery);
			updateStatement.executeUpdate();
			
			ResultSet rs = updateStatement.executeQuery();
			
			if(rs.next()){
				counter = rs.getInt(1);
			}
			
			updateStatement.close();
			
			//On commit à l'ancienne
			connection.commit();
		}
		catch (SQLException e) {;
			throw new DaoException("Problème lors de l'exécution de la requête",e);
		}
		catch (Exception e) {
			throw new DaoException("Erreur de connection",e);
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				throw new DaoException("La connection n'a pas pu être refermée...",e);
			}
		}
		
		return counter;
	}
}
