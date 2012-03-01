/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.rating.web;

import fr.paris.lutece.plugins.rating.business.ResourceConfHome;
import fr.paris.lutece.plugins.rating.service.RatingPlugin;
import fr.paris.lutece.plugins.rating.service.RatingService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.http.SecurityUtil;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


/**
 * DownloadCountFilter
 */
public class DownloadCountFilter implements Filter
{

    private static final String PARAMETER_RESOURCE_TYPE = "type_resource";
    private static final String PARAMETER_RESOURCE_ID = "id_resource";
    private static final String PARAMETER_DOWNLOAD_URL = "url";
    private static List<String> _listResourceTypes;
    private static Logger _logger = Logger.getLogger( "lutece.security.http" );

    public void init(FilterConfig filterConfig) throws ServletException
    {
        Plugin plugin = PluginService.getPlugin( RatingPlugin.PLUGIN_NAME );
        _listResourceTypes = ResourceConfHome.getResourceTypes( plugin );
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        String strResourceType = request.getParameter(PARAMETER_RESOURCE_TYPE);
        String strResourceId = request.getParameter(PARAMETER_RESOURCE_ID);
        String strUrl = request.getParameter(PARAMETER_DOWNLOAD_URL);

        if (!_listResourceTypes.contains(strResourceType))
        {
            _logger.warn( "rating : invalid resource type" + SecurityUtil.dumpRequest( (HttpServletRequest) request ));
            response.getWriter().print("Invalid resource type" );
            return;
        }
        int nIdResource;
        try
        {
            nIdResource = Integer.parseInt(strResourceId);
        }
        catch( NumberFormatException e )
        {
            _logger.warn( "rating : invalid resource id" + SecurityUtil.dumpRequest( (HttpServletRequest) request ));
            response.getWriter().print("Invalid resource id");
            return;
        }
        RatingService.incrementDownloadCount(strResourceType, nIdResource);
        ((HttpServletResponse) response).sendRedirect(strUrl);
    }

    public void destroy()
    {
    }
}
