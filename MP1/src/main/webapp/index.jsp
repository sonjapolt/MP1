<%@ page import="data.*"%>
<%@ page import="main.*"%>
<%
	Controller controller = new Controller();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>Start Page</title>
<link href="map.css" rel="stylesheet">
</head>
<body>
	<div>
		<h1>MP1 - Gruppe 4</h1>
		<h3>LinkedGeoData & Schema.org</h3>
		<a href="index.jsp">Homepage</a>
	</div>
	<br />
	<form action="index.jsp" method="GET">
		<div>
			Choose a category: <select name="category">
				<%
					if (request.getParameter("category") != null) {
						controller.setSelectedCategory(request.getParameter("category"));
					}
					for (String category : controller.getCategories()) {
						if (category.equals(controller.getSelectedCategory())) {
				%>
				<option selected="selected" value="<%=category%>"><%=category%></option>
				<%
					} else {
				%>
				<option value="<%=category%>"><%=category%></option>
				<%
					}
				%>
				<%
					}
				%>
			</select>
		</div>
		<div>
			<%
				if (request.getParameter("limit") != null) {
					controller.setLimit(Integer.parseInt(request.getParameter("limit")));
				}
			%>
			Limit: <input type="text" name="limit"
				value="<%=controller.getLimit()%>">
		</div>
		<div>
			<input type="submit" value="Submit" />
		</div>
	</form>
	<br />
	<div id="map"></div>
	<script>
		function initMap() {

			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 1.5,
				center : {
					lat : 0,
					lng : 0
				}
			});

			var markers = locations.map(function(location, i) {
				return new google.maps.Marker({
					position : location,
					label : labels[i]
				});
			});

			var markerCluster = new MarkerClusterer(
					map,
					markers,
					{
						imagePath : 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'
					});
		}
		var locations = [];
		var labels = [];
	<%for (MapPoint mapPoint : controller.getMapPoints()) {%>
		locations.push({
			lat :
	<%=mapPoint.getLatitude()%>
		,
			lng :
	<%=mapPoint.getLongitude()%>
		});
		labels.push("<%= mapPoint.getLabel()%>");
	<%}%>
		
	</script>
	<script
		src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
		
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAuTGyR0hVhQlh4US2vjYIUoypOouKYlNI&callback=initMap">
		
	</script>
</body>
</html>