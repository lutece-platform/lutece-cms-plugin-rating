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

import fr.paris.lutece.plugins.rating.business.ResourceConf;
import fr.paris.lutece.plugins.rating.business.ResourceConfHome;
import fr.paris.lutece.plugins.rating.business.ResourceStatHome;
import fr.paris.lutece.plugins.rating.service.RatingPlugin;
import fr.paris.lutece.plugins.rating.service.RatingService;
import fr.paris.lutece.plugins.rating.service.ResourceDisplayManager;
import fr.paris.lutece.portal.business.resourceenhancer.IResourceManager;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.url.UrlItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This class manages resource : vote, view and download count.
 */
public class ResourceManager implements IResourceManager
{
    //Marks
    private static final String MARK_RESOURCE_IS_VOTABLE = "is_votable";
    private static final String MARK_ACCEPT_SITE_VOTES = "accept_site_votes";
    private static final String MARK_IS_EMAIL_NOTIFIED_VOTE = "is_email_notified_vote";
    private static final String MARK_MAILING_LIST_DEFAULT_VALUE = "mailing_list_default_value";

    //Parameters
    private static final String PARAMETER_ACCEPT_SITE_VOTES = "accept_site_votes";
    private static final String PARAMETER_IS_EMAIL_NOTIFIED_VOTE = "is_email_notified_vote";
    private static final String PARAMETER_MAILING_ID = "mailing_vote";
    private static final String PARAMETER_TYPE_RESOURCE = "type_resource";
    private static final String PARAMETER_ID_RESOURCE = "id_resource";
    private static final String PARAMETER_VOTE_VALUE = "vote_value";
    private static final String PARAMETER_HTTP_REFERER = "referer";
    private static final String PARAMETER_VOTE = "vote";
    private static final String CONSTANT_VALUE_VOTE_PARAMETER = "1";
    private static final String PROPERTY_WHITE_IMAGE_ADDRESS = "/images/local/skin/plugins/rating/white_pixel.jpg";
    private static final String PROPERTY_WHITE_IMAGE_TYPE = "jpg";
    private static byte[] _imageContent;
    private static String _strImageName;

    static
    {
        putImageToCache(  );
    }

    /**
     * Reads image file and saves it.
     */
    private static void putImageToCache(  )
    {
        FileInputStream fileInputStream = null;

        try
        {
            File file = new File( AppPathService.getWebAppPath(  ) + PROPERTY_WHITE_IMAGE_ADDRESS );

            final long length = file.length(  );

            byte[] fileContent = new byte[(int) length];

            fileInputStream = new FileInputStream( file );

            int nOff = 0;
            int nLen = fileContent.length;
            int nRead;

            do
            {
                nRead = fileInputStream.read( fileContent, nOff, nLen );

                if ( nRead > 0 )
                {
                    nOff += nRead;
                    nLen -= nRead;
                }
            }
            while ( ( nRead >= 0 ) && ( nLen > 0 ) );

            _imageContent = fileContent;
            _strImageName = file.getName(  );
        }
        catch ( IOException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }
        finally
        {
            if ( fileInputStream != null )
            {
                try
                {
                    fileInputStream.close(  );
                }
                catch ( IOException e )
                {
                    AppLogService.error( e.getMessage(  ), e );
                }
            }
        }
    }

    /**
    * Add datas to the create document model use in the template
    * @param model The model use by create document template
    */
    public void getCreateResourceModelAddOn( Map<String, Object> model )
    {
        model.put( MARK_RESOURCE_IS_VOTABLE, true );
    }

    /**
    * Perform actions associated to the document creation
    * @param request The HTTP request
    * @param strResourceType the resource type
    * @param nIdResource The resource id
    */
    public void doCreateResourceAddOn( HttpServletRequest request, String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );
        ResourceConf resourceConf = new ResourceConf(  );
        resourceConf.setResourceType( strResourceType );
        resourceConf.setIdResource( nIdResource );

        String strIsModeratedVote = ( request.getParameter( PARAMETER_ACCEPT_SITE_VOTES ) != null )
            ? request.getParameter( PARAMETER_ACCEPT_SITE_VOTES ) : "0";
        String strIsEmailNotifiedVote = ( request.getParameter( PARAMETER_IS_EMAIL_NOTIFIED_VOTE ) != null )
            ? request.getParameter( PARAMETER_IS_EMAIL_NOTIFIED_VOTE ) : "0";
        String strMailingId = ( request.getParameter( PARAMETER_MAILING_ID ) != null )
            ? request.getParameter( PARAMETER_MAILING_ID ) : "0";

        resourceConf.setAcceptSiteVotes( Integer.parseInt( strIsModeratedVote ) );
        resourceConf.setEmailNotifiedVotes( Integer.parseInt( strIsEmailNotifiedVote ) );
        resourceConf.setIdMailingListVotes( Integer.parseInt( strMailingId ) );

        ResourceConfHome.create( resourceConf, plugin );
    }

    /**
    * Add datas to the modify document model use in the template
    * @param model the map use in the template
    * @param strResourceType the resource type
    * @param nIdResource The resource id
    */
    public void getModifyResourceModelAddOn( Map<String, Object> model, String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );

        ResourceConf resourceConf = ResourceConfHome.findByResource( strResourceType, nIdResource, plugin );

        if ( resourceConf == null )
        {
            resourceConf = new ResourceConf(  );
            resourceConf.setResourceType( strResourceType );
            resourceConf.setIdResource( nIdResource );
            resourceConf.setAcceptSiteVotes( 0 );
            resourceConf.setEmailNotifiedVotes( 0 );
            resourceConf.setIdMailingListVotes( 0 );
            ResourceConfHome.create( resourceConf, plugin );
        }

        model.put( MARK_RESOURCE_IS_VOTABLE, true );
        model.put( MARK_ACCEPT_SITE_VOTES, resourceConf.isAcceptSiteVotes(  ) );
        model.put( MARK_IS_EMAIL_NOTIFIED_VOTE, resourceConf.isEmailNotifiedVotes(  ) );
        model.put( MARK_MAILING_LIST_DEFAULT_VALUE, resourceConf.getIdMailingListVotes(  ) );
    }

    /**
    * Perform actions associated to the document modification
    * @param request The HTTP request
    * @param strResourceType the resource type
    * @param nIdResource The resource id
    */
    public void doModifyResourceAddOn( HttpServletRequest request, String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );
        ResourceConf resourceConf = new ResourceConf(  );
        resourceConf.setResourceType( strResourceType );
        resourceConf.setIdResource( nIdResource );

        String strIsModeratedVote = ( request.getParameter( PARAMETER_ACCEPT_SITE_VOTES ) != null )
            ? request.getParameter( PARAMETER_ACCEPT_SITE_VOTES ) : "0";
        String strIsEmailNotifiedVote = ( request.getParameter( PARAMETER_IS_EMAIL_NOTIFIED_VOTE ) != null )
            ? request.getParameter( PARAMETER_IS_EMAIL_NOTIFIED_VOTE ) : "0";
        String strMailingId = ( request.getParameter( PARAMETER_MAILING_ID ) != null )
            ? request.getParameter( PARAMETER_MAILING_ID ) : "0";

        resourceConf.setAcceptSiteVotes( Integer.parseInt( strIsModeratedVote ) );
        resourceConf.setEmailNotifiedVotes( Integer.parseInt( strIsEmailNotifiedVote ) );
        resourceConf.setIdMailingListVotes( Integer.parseInt( strMailingId ) );

        ResourceConfHome.update( resourceConf, plugin );
    }

    /**
    * Perform actions associated to the resource download
    * @param request The HTTP request
    * @param strResourceType the resource type
    * @param nIdResource the resource id
    */
    public void doDownloadResourceAddOn( HttpServletRequest request, String strResourceType, int nIdResource )
    {
        RatingService.incrementDownloadCount( strResourceType, nIdResource );
    }

    /**
     * Perform actions associated to the resource deletion
     * @param request The HTTP request
     * @param strResourceType the resource type
     * @param nIdResource the resource id
     */
    public void doDeleteResourceAddOn( HttpServletRequest request, String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );

        ResourceStatHome.remove( strResourceType, nIdResource, plugin );
        ResourceConfHome.remove( strResourceType, nIdResource, plugin );
    }

    /**
     * Update the view count and return an image
     * @param request The HTTP request
     * @param response The HTTP response
     * @throws IOException the io exception
     */
    public void doUpdateViewCount( HttpServletRequest request, HttpServletResponse response )
        throws IOException
    {
        String strResourceType = request.getParameter( PARAMETER_TYPE_RESOURCE );
        String strIDResource = request.getParameter( PARAMETER_ID_RESOURCE );
        int nIdResource = Integer.parseInt( strIDResource );

        RatingService.incrementViewCount( strResourceType, nIdResource );

        // use cached image
        response.setHeader( "Content-Disposition", "attachment;filename=\"" + _strImageName + "\"" );
        response.setContentType( PROPERTY_WHITE_IMAGE_TYPE );
        response.setHeader( "Cache-Control", "must-revalidate" );
        response.getOutputStream(  ).write( _imageContent );
    }

    /**
     * Update the vote value an count
     * @param request The HTTP request
     * @param response The HTTP response
     * @throws IOException the io exception
     */
    public void doVote( HttpServletRequest request, HttpServletResponse response )
        throws IOException
    {
        Plugin plugin = getPlugin(  );
        String strResourceType = request.getParameter( PARAMETER_TYPE_RESOURCE );
        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE );
        int nIdResource = Integer.parseInt( strIdResource );
        String strVoteValue = request.getParameter( PARAMETER_VOTE_VALUE );

        ResourceConf resourceConf = ResourceConfHome.findByResource( strResourceType, nIdResource, plugin );

        int nVoteValue = Integer.parseInt( strVoteValue );
        RatingService.incrementVote( strResourceType, nIdResource, nVoteValue );

        if ( resourceConf.isEmailNotifiedVotes(  ) == 1 )
        {
            ResourceDisplayManager.sendCommentNotification( request, strResourceType, nIdResource,
                resourceConf.getIdMailingListVotes(  ), Integer.parseInt( strVoteValue ) );
        }

        String strReferer = request.getHeader( PARAMETER_HTTP_REFERER );

        if ( strReferer != null )
        {
            UrlItem urlItem = new UrlItem( strReferer );
            urlItem.addParameter( PARAMETER_VOTE, CONSTANT_VALUE_VOTE_PARAMETER );
            response.sendRedirect( urlItem.getUrl(  ) );
        }
        else
        {
            response.sendRedirect( AppPathService.getPortalUrl(  ) );
        }
    }

    /**
     * Gets the plugin
     * @return the plugin
     */
    private Plugin getPlugin(  )
    {
        return PluginService.getPlugin( RatingPlugin.PLUGIN_NAME );
    }
}
