package com.excilys.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {

    private static LivreDaoImpl instance;
    private LivreDaoImpl() {}
    
    public static LivreDaoImpl getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws DaoException {
        Connection connection = null;
        List<Livre> livres = new ArrayList<>();
        String Query = "SELECT id, titre, auteur, isbn FROM livre";
        
		try {
            connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet res = preparedStatement.executeQuery();
            
			while(res.next()) {
				Livre f = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				livres.add(f);
			}
            //System.out.println("GET: " + livres);

            preparedStatement.close();
            res.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des livres", e);
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
		return livres;
    }

	public Livre getById(int id) throws DaoException {
        Connection connection = null;
        Livre livre = new Livre();
        String Query = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?";
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            
			if(res.next()) {
                livre.setId(id);
                livre.setTitre(res.getString("titre"));
                livre.setAuteur(res.getString("auteur"));
                livre.setIsbn(res.getString("isbn"));
			}
            //System.out.println("GET: " + livre);

            preparedStatement.close();
            res.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation d'un livre avec son id", e);
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
		return livre;
    }

	public int create(String titre, String auteur, String isbn) throws DaoException {
        Connection connection = null;
        String Query = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)";
        int id = 0;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            preparedStatement.executeUpdate();
            ResultSet res = preparedStatement.getGeneratedKeys();
            if(res.next()) {
                id = res.getInt(1);
            }
            
            //System.out.println("CREATE: " + String.valueOf(id));

            preparedStatement.close();
            res.close();
            
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
        return id;
    }

	public void update(Livre livre) throws DaoException {
        Connection connection = null;
        String Query = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
            ResultSet res = preparedStatement.executeQuery();
            
            //System.out.println("UPDATE: " + livre);

            preparedStatement.close();
            res.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise à jour d'un livre", e);
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

	public void delete(int id) throws DaoException {
        Connection connection = null;
        String Query = "DELETE FROM livre WHERE id = ?";
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();
            
            //System.out.println("DELETE: " + String.valueOf(id));

            preparedStatement.close();
            res.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la suppression d'un livre", e);
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
        String Query = "SELECT COUNT(id) AS count FROM livre";
        int count = 0;
        
		try {
            connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet res = preparedStatement.executeQuery();
            
            if(res.next()) {
                count = res.getInt("count");
            }

            //System.out.println("COUNT: " + String.valueOf(count));

            preparedStatement.close();
            res.close();
            
		} catch (SQLException e) {
			throw new DaoException("Probleme lors du comptage du nombre de livres", e);
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