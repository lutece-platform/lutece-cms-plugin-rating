/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.rating.service;

import fr.paris.lutece.plugins.rating.business.ResourceConf;
import fr.paris.lutece.plugins.rating.business.ResourceConfHome;
import fr.paris.lutece.plugins.rating.business.ResourceStat;
import fr.paris.lutece.plugins.rating.business.ResourceStatHome;
import fr.paris.lutece.portal.business.mailinglist.Recipient;
import fr.paris.lutece.portal.business.resourceenhancer.IResourceDisplayManager;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.mailinglist.AdminMailingListService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class manages resource display extension
 */
public class ResourceDisplayManager implements IResourceDisplayManager
{
    // Templates
    private static final String TEMPLATE_RESOURCE_VOTE = "/skin/plugins/rating/resource_vote.html";
    private static final String TEMPLATE_VOTE_NOTIFY_MESSAGE = "/skin/plugins/rating/vote_notify_message.html";

    //Marks
    private static final String MARK_RESOURCE_IS_VOTABLE = "is_votable";
    private static final String MARK_RESOURCE_ID = "resource_id";
    private static final String MARK_TEMPLATE_VOTE_CONTENT = "template_vote_content";
    private static final String MARK_PORTLET_ID = "portlet_id";
    private static final String MARK_VOTE_VALUE = "vote_value";
    private static final String MARK_IS_DOWNLOAD_STAT = "is_download_stat";
    private static final String MARK_DOWNLOAD_STAT = "resource_download_stat";
    private static final String MARK_TYPE_RESOURCE = "type_resource";
    private static final String MARK_IS_VIEW_COUNT = "is_view_count";
    private static final String MARK_RESOURCE_VOTE_COUNT = "resource_vote_count";
    private static final String MARK_RESOURCE_SCORE_VALUE = "resource_score_value";
    private static final String MARK_RESOURCE_VIEW_COUNT = "resource_view_stat";

    // Tags
    private static final String TAG_RESOURCE_SCORE = "resource-score";
    private static final String TAG_RESOURCE_IS_VOTABLE = "resource-is-votable";
    private static final String TAG_IS_DOWNLOAD_STAT = "is-download-stat";
    private static final String TAG_DOWNLOAD_STAT = "resource-download-stat";

    //Parameters
    private static final String PARAMETER_VOTE_RESOURCE = "vote";
    private static final String RATING_PLUGIN_NAME = "rating";

    // properties
    private static final String PROPERTY_VOTE_SAVED = "rating.resource_vote.labelVoteSaved";
    private static final String PROPERTY_VOTE_NOTIFY_SUBJECT = "rating.message.notify.subject";
    private static final String PROPERTY_VOTE_NOTIFY_NO_REPLY = "mail.noreply.email";
    private static final String CONSTANT_SPACE = " ";

    /**
     * Add XML datas to the XML resource
     *
     * @param strXml The Xml document
     * @param strResourceType the resource type
     * @param nIdResource The resource id
     */
    public void getXmlAddOn( StringBuffer strXml, String strResourceType, int nIdResource )
    {
        Plugin plugin = PluginService.getPlugin( RATING_PLUGIN_NAME );
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

        if ( resourceConf.isAcceptSiteVotes(  ) == 1 )
        {
            XmlUtil.addElement( strXml, TAG_RESOURCE_SCORE,
                ResourceStatHome.getScore( strResourceType, nIdResource, plugin ) );
            XmlUtil.addElement( strXml, TAG_RESOURCE_IS_VOTABLE, "1" );
        }

        XmlUtil.addElement( strXml, TAG_DOWNLOAD_STAT,
            ResourceStatHome.getDownloadCountByResource( strResourceType, nIdResource, plugin ) );
        XmlUtil.addElement( strXml, TAG_IS_DOWNLOAD_STAT, "1" );
    }

    /**
     * Add datas to the model use by resource template
     * Markers put :
     * <ul>
     * <li>{@link #MARK_RATING_DOWNLOAD_COUNT}
     * <li>{@link #MARK_IS_DOWNLOAD_STAT}
     * <li>{@link #MARK_DOWNLOAD_STAT}
     * <li>{@link #MARK_RESOURCE_VIEW_COUNT}
     * <li>{@link #MARK_RESOURCE_VOTE_COUNT}
     * <li>{@link #MARK_VOTE_VALUE}
     * <li>{@link #MARK_RATING_DOWNLOAD_COUNT}
     * </ul>
     * @param model The model use by resource template
     * @param strResourceType the resource type
     * @param nIdResource The resource id
     * @param strPortletId The portlet ID
     * @param request The HTTP Request
     */
    public void buildPageAddOn( Map<String, Object> model, String strResourceType, int nIdResource,
        String strPortletId, HttpServletRequest request )
    {
        Plugin plugin = PluginService.getPlugin( RATING_PLUGIN_NAME );

        ResourceStat resourceStat = ResourceStatHome.findResourceStat( strResourceType, nIdResource, plugin );

        if ( resourceStat == null )
        {
            resourceStat = new ResourceStat(  );
            resourceStat.setResourceType( strResourceType );
            resourceStat.setIdResource( nIdResource );
            resourceStat.setVoteCount( 0 );
            resourceStat.setScoreValue( 0 );
            resourceStat.setDownloadCount( 0 );
            resourceStat.setViewCount( 0 );
            ResourceStatHome.create( resourceStat, plugin );
        }

        int nResourceDownloadCount = ResourceStatHome.getDownloadCountByResource( strResourceType, nIdResource, plugin );

        model.put( MARK_IS_VIEW_COUNT, true );
        model.put( MARK_RESOURCE_VIEW_COUNT, resourceStat.getViewCount(  ) );

        if ( nResourceDownloadCount > 0 )
        {
            model.put( MARK_IS_DOWNLOAD_STAT, true );
            model.put( MARK_DOWNLOAD_STAT, nResourceDownloadCount );
        }

        ResourceConf resourceConf = ResourceConfHome.findByResource( strResourceType, nIdResource, plugin );

        if ( resourceConf.isAcceptSiteVotes(  ) == 1 )
        {
            String strVote = request.getParameter( PARAMETER_VOTE_RESOURCE );

            // if a vote has been requested
            if ( strVote != null )
            {
                model.put( MARK_TEMPLATE_VOTE_CONTENT,
                    I18nService.getLocalizedString( PROPERTY_VOTE_SAVED, request.getLocale(  ) ) );
            }
            else
            {
                Map<String, Object> voteModel = new HashMap<String, Object>(  );

                voteModel.put( MARK_RESOURCE_ID, nIdResource );
                voteModel.put( MARK_TYPE_RESOURCE, strResourceType );
                voteModel.put( MARK_PORTLET_ID, strPortletId );

                HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_RESOURCE_VOTE, request.getLocale(  ),
                        voteModel );

                model.put( MARK_TEMPLATE_VOTE_CONTENT, template.getHtml(  ) );
            }

            model.put( MARK_RESOURCE_IS_VOTABLE, true );

            // score avg value
            int nScore;

            if ( resourceStat.getVoteCount(  ) > 0 )
            {
                nScore = Math.round( (float) resourceStat.getScoreValue(  ) / (float) resourceStat.getVoteCount(  ) );
                nScore = nScore + 2;
            }
            else
            {
                // no vote yet
                nScore = 0;
            }

            model.put( MARK_RESOURCE_SCORE_VALUE, nScore );
            model.put( MARK_RESOURCE_VOTE_COUNT, resourceStat.getVoteCount(  ) );
        }
    }

    /**
     * Notify list that a vote has been done
     *
     * @param request The HTTP Request
     * @param strResourceType the resource type
     * @param nIdResource The resource id
     * @param nMailingListId the id of the mailing list
     * @param nVoteValue the value of the vote
     *
     */
    public static void sendCommentNotification( HttpServletRequest request, String strResourceType, int nIdResource,
        int nMailingListId, int nVoteValue )
    {
        Collection<Recipient> listRecipients = AdminMailingListService.getRecipients( nMailingListId );

        for ( Recipient recipient : listRecipients )
        {
            //HashMap model = new HashMap(  );
            Map<String, Object> model = new HashMap<String, Object>(  );

            String strSubject = I18nService.getLocalizedString( PROPERTY_VOTE_NOTIFY_SUBJECT, request.getLocale(  ) );

            // Generate the subject of the message
            strSubject += ( CONSTANT_SPACE + strResourceType + CONSTANT_SPACE + nIdResource );

            // Generate the body of the message
            model.put( MARK_TYPE_RESOURCE, strResourceType );
            model.put( MARK_RESOURCE_ID, nIdResource );
            model.put( MARK_VOTE_VALUE, nVoteValue );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_VOTE_NOTIFY_MESSAGE,
                    request.getLocale(  ), model );
            String strBody = template.getHtml(  );

            MailService.sendMailHtml( recipient.getEmail(  ),
                AppPropertiesService.getProperty( PROPERTY_VOTE_NOTIFY_NO_REPLY ),
                AppPropertiesService.getProperty( PROPERTY_VOTE_NOTIFY_NO_REPLY ), strSubject, strBody );
        }
    }
}
