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
        <h1 class="page-announce-text valign">Liste des membres</h1>
      </div>
      <div class="row">
	    <div class="col s12">
	      <table class="striped no-padding">
            <thead>
              <tr>
                <th>Nom</th>
                <th>Pr�nom</th>
                <th class="hide-on-small-only">Adresse</th>
                <th class="hide-on-small-only">E-mail</th>
                <th class="hide-on-small-only">T�l�phone</th>
                <th>D�tails</th>
              </tr>
            </thead>
            <tbody>

			<c:forEach var="membre" items="${membres}">
              <tr>
                <td>${(membre.nom)}</td>
                <td>${(membre.prenom)}</td>
                <td class="hide-on-small-only">${(membre.adresse)}</td>
                <td class="hide-on-small-only">${(membre.email)}</td>
                <td class="hide-on-small-only">${(membre.telephone)}</td>
                <td class="center"><a href=${("membre_details?id="+(membre.id.toString()))}><ion-icon class="details" name="information-circle-outline"></ion-icon></a></td>
              </tr>
			</c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
