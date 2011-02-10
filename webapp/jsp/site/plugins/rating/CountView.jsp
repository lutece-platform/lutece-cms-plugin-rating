<%@ page import="fr.paris.lutece.portal.service.util.AppLogService"%><jsp:useBean id="resourceManager" scope="request" class="fr.paris.lutece.plugins.rating.web.ResourceManager" /><%
	try {
		resourceManager.doUpdateViewCount(request, response);
	} catch (Exception e) {
		AppLogService.error(e.getMessage(), e);
	}
	finally {
		response.getOutputStream().flush();
	}
	
%>