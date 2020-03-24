package com.excilys.librarymanager.model;

public class Membre{
	/*!
	 *	CHAMPS DE LA TABLE MEMBRE
	 */
	private int id;
	private String nom;
	private String prenom;
	private String adresse;
	private String email;
	private String telephone;
	private Abonnement	abonnement;
	
	/*!
	 *	CONSTRUCTEURS
	 */
	public Membre(){
		id = 0;
		nom = "";
		prenom = "";
		adresse = "";
		email="";
		telephone = "";
		abonnement = Abonnement.BASIC;
	}
	
	public Membre(int newId, String newNom, String newPrenom, String newAdresse, String newEmail, String newTelephone, Abonnement newAbonnement){
		id = newId;
		nom = newNom;
		prenom = newPrenom;
		adresse = newAdresse;
		email = newEmail;
		telephone = newTelephone;
		abonnement = newAbonnement;
	}
	
	/*!
	 *	ACCESSEURS
	 */
	
	/*!
	 *	Retourne l'id du membre.
	 */
	public int getId(){
		return id;
	}
	
	/*!
	 *	Retourne le nom du membre.
	 */
	public String getNom(){
		return nom;
	}
	
	/*!
	 *	Retourne le prénom du membre.
	 */
	public String getPrenom(){
		return prenom;
	}
	
	/*!
	 *	Retourne l'adresse du membre.
	 */
	public String getAdresse(){
		return adresse;
	}
	
	/*!
	 *	Retourne l'email du membre.
	 */
	public String getEmail(){
		return email;
	}
	
	/*!
	 *	Retourne le numéro de téléphone du membre.
	 */
	public String getTelephone(){
		return telephone;
	}
	
	/*!
	 *	Retourne le type d'abonnement du membre.
	 */
	public Abonnement getAbonnement(){
		return abonnement;
	}
	
	/*!
	 *	MODIFIEURS
	 */
	
	/*!
	 *	Modifie l'id du membre.
	 *	@param newId	Nouvel identifiant du membre.
	 */
	public void setId(int newId){
		this.id = newId;
	}
	
	/*!
	 *	Modifie le nom du membre.
	 *	@param newNom	Nouveau nom du membre.
	 */
	public void setNom(String newNom){
		this.nom = newNom;
	}
	
	/*!
	 *	Modifie le prénom du membre.
	 *	@param newPrenom	Nouveau prénom du membre.
	 */
	public void setPrenom(String newPrenom){
		this.prenom = newPrenom;
	}
	
	/*!
	 *	Modifie l'adresse du membre.
	 *	@param newAdresse	Nouvelle adresse du membre.
	 */
	public void setAdresse(String newAdresse){
		this.adresse = newAdresse;
	}
	
	/*!
	 *	Modifie l'email du membre.
	 *	@param newEmail	Nouvel email du membre.
	 */
	public void setEmail(String newEmail){
		this.email = newEmail;
	}
	
	/*!
	 *	Modifie le numéro de téléphone du membre.
	 *	@param newTelephone	Nouveau numéro de téléphone du membre.
	 */
	public void setTelephone(String newTelephone){
		this.telephone = newTelephone;
	}
	
	/*!
	 *	Modifie le type d'abonnement du membre.
	 *	@param newAbonnement	Nouveau type d'abonnement du membre.
	 */
	public void setAbonnement(Abonnement newAbonnement){
		this.abonnement = newAbonnement;
	}
	
	/*!
	 *	DESCRIPTEUR
	 */
	public String toString(){
		String description = "Id : "+String.valueOf(this.id)+"\n";
		description = description+"Nom : "+this.nom+"\n";
		description = description+"Prenom : "+this.prenom+"\n";
		description = description+"Adresse : "+this.adresse+"\n";
		description = description+"Email : "+this.email+"\n";
		description = description+"Telephone : "+this.telephone+"\n";
		description = description+"Abonnement : "+this.abonnement.name();
		
		return description;
	}
}