<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Fiche membre</h1>
      </div>
      <div class="row">
      <div class="container">
      <h5>Suppression du membre n�${(id)}</h5> <!-- TODO : remplacer 312 par l'id du membre -->
        <div class="row">
          <p>�tes-vous s�r de vouloir supprimer la fiche de ${(membre.prenom)} ${(membre.nom)} ?</p> <!-- TODO : remplacer prenomDuMembre et nomDuMembre par les valeurs correspondantes -->
	      <form action="/LibraryManager/membre_delete" method="post" class="col s12">
            <input type="hidden" value=${(id)} name="id"> <!-- TODO : remplacer idDuMembre par l'id du membre -->
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit" name="action">Supprimer
	            <i class="material-icons right">delete</i>
	          </button>
	          <a class="btn waves-effect waves-light orange" href=${"/LibraryManager/membre_details?id="+id)}>Annuler</a> <!-- TODO : remplacer idDuMembre par l'id du membre -->
	        </div>
	      </form>
	    </div>	    
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
