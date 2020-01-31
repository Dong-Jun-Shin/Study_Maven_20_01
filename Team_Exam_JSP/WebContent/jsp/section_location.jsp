<%@page import="java.io.File"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.io.FileReader"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	JSONParser parser = new JSONParser();
	JSONObject jo = (JSONObject)parser.parse(new FileReader(request.getRealPath("/") + "/json/api_key.json"));
	JSONArray jsonArray = (JSONArray)jo.get("api_key");
	JSONObject key = (JSONObject)jsonArray.get(0);
	
	String api_key = (String)(key.get("googleMap"));
%>
<link rel="stylesheet" type="text/css" href="../css/location.css">

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=<%=api_key %>&callback=initMap" async defer></script>
<script type="text/javascript" src="../js/location.js">	</script>

<div class="container">
	<h1>예술의 전당: 탄자니아 본점</h1>
	<div id="map"></div>
</div>