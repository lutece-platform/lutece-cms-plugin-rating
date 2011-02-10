<jsp:useBean id="resourceManager" scope="request" class="fr.paris.lutece.plugins.rating.web.ResourceManager" />
<%
	resourceManager.doVote(request, response);
%>