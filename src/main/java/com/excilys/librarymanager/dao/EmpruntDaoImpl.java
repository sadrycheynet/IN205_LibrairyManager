package com.excilys.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {

    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl() {}
    
    public static EmpruntDaoImpl getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    public List<Emprunt> getList() throws DaoException {
        Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();
        String Query = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
        + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
        + "FROM emprunt AS e "
        + "INNER JOIN membre ON membre.id = e.idMembre "
        + "INNER JOIN livre ON livre.id = e.idLivre "
        + "ORDER BY dateRetour DESC";
        
		try {
            connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet rs = preparedStatement.executeQuery();
            
			while(rs.next()) {
                Livre l = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre m = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), Abonnement.valueOf(rs.getString("abonnement")));
				Emprunt f = new Emprunt(rs.getInt("id"), l, m, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
				emprunts.add(f);
			}
            //System.out.println("GET: " + emprunts);

            preparedStatement.close();
            rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
				throw new DaoException("La connection n'a pas pu être refermée...");
			}
		}
		return emprunts;
    }

	public List<Emprunt> getListCurrent() throws DaoException {
        Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();

        String Query = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
            + "FROM emprunt AS e"
            + "INNER JOIN membre ON membre.id = e.idMembre"
            + "INNER JOIN livre ON livre.id = e.idLivre"
            + "WHERE dateRetour IS NULL";

        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Livre l = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre m = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), rs.getObject("abonnement", Abonnement.class));
				Emprunt f = new Emprunt(rs.getInt("id"), l, m, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                emprunts.add(f);
            }
            //System.out.println("GET: " + emprunts);

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
                throw new DaoException("La connection n'a pas pu être refermée...");
            }
        }
        return emprunts;

    }

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();

        String Query = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
            + "FROM emprunt AS e"
            + "INNER JOIN membre ON membre.id = e.idMembre"
            + "INNER JOIN livre ON livre.id = e.idLivre"
            + "WHERE dateRetour IS NULL AND membre.id = ?";
        
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, idMembre);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Livre l = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre m = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), rs.getObject("abonnement", Abonnement.class));
				Emprunt f = new Emprunt(rs.getInt("id"), l, m, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                emprunts.add(f);
            }
            //System.out.println("GET: " + emprunts);

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
                throw new DaoException("La connection n'a pas pu être refermée...");
            }
        }
        return emprunts;
    }

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        Connection connection = null;
        List<Emprunt> emprunts = new ArrayList<>();

        String Query = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
            + "FROM emprunt AS e"
            + "INNER JOIN membre ON membre.id = e.idMembre"
            + "INNER JOIN livre ON livre.id = e.idLivre"
            + "WHERE dateRetour IS NULL AND livre.id = ?";
        
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, idLivre);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Livre l = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre m = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), rs.getObject("abonnement", Abonnement.class));
				Emprunt f = new Emprunt(rs.getInt("id"), l, m, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
                emprunts.add(f);
            }
            //System.out.println("GET: " + emprunts);

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
                throw new DaoException("La connection n'a pas pu être refermée...");
            }
        }
        return emprunts;
    }

	public Emprunt getById(int id) throws DaoException{
        Connection connection = null;
        Emprunt emprunt = null;

        String Query = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
            + "FROM emprunt AS e"
            + "INNER JOIN membre ON membre.id = e.idMembre"
            + "INNER JOIN livre ON livre.id = e.idLivre"
            + "WHERE e.id = ?";
        
        try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                Livre l = new Livre(rs.getInt("idLivre"), rs.getString("titre"), rs.getString("auteur"), rs.getString("isbn"));
                Membre m = new Membre(rs.getInt("idMembre"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("email"), rs.getString("telephone"), rs.getObject("abonnement", Abonnement.class));
                Emprunt f = new Emprunt(rs.getInt("idEmprunt"), l, m, rs.getDate("dateEmprunt").toLocalDate(), rs.getDate("dateRetour").toLocalDate());
            }  
            //System.out.println("GET: " + emprunt);

            preparedStatement.close();
            rs.close();
            
        } catch (SQLException e) {
            throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
                throw new DaoException("La connection n'a pas pu être refermée...");
            }
        }
        return emprunt;
    }

	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        Connection connection = null;
        String Query = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            preparedStatement.setDate(4, java.sql.Date.valueOf("NULL"));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();

            preparedStatement.close();
            rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la création d'un livre", e);
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
				throw new DaoException("La connection n'a pas pu être refermée...");
			}
		}
    }

	public void update(Emprunt emprunt) throws DaoException{
        Connection connection = null;
        String Query = "UPDATE emprunt "
        + "SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? "
        + "WHERE id = ? ";
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            Livre l = emprunt.getLivre();
            Membre m = emprunt.getMembre();
            preparedStatement.setInt(1, m.getId());
            preparedStatement.setInt(2, l.getId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();

            preparedStatement.close();
            rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la création d'un livre", e);
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
				throw new DaoException("La connection n'a pas pu être refermée...");
			}
		}
    }

	public int count() throws DaoException {
        Connection connection = null;
        String Query = "SELECT COUNT(id) AS count FROM emprunt";

        int count = 0;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }

            preparedStatement.close();
            rs.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la création d'un livre", e);
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
				throw new DaoException("La connection n'a pas pu être refermée...");
			}
        }
        return count;
    }

}