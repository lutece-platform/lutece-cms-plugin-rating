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
package fr.paris.lutece.plugins.rating.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *class  ResourceConfDAO
 *
 */
public class ResourceConfDAO implements IResourceConfDAO
{
    private static final String SQL_QUERY_INSERT = "INSERT INTO rating_resource_conf (resource_type, id_resource, accept_site_votes, is_email_notified_vote, id_mailinglist_vote )VALUES(?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE rating_resource_conf SET resource_type=?, id_resource=?, accept_site_votes=?, is_email_notified_vote=?, id_mailinglist_vote=? WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_SELECT = "SELECT resource_type, id_resource, accept_site_votes, is_email_notified_vote, id_mailinglist_vote FROM rating_resource_conf WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM rating_resource_conf WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_SELECT_RESOURCE_TYPES = "SELECT resource_type FROM rating_resource_types";
    /**
     * Insert a new record in the table.
     *
     * @param resourceConf instance of the ResourceConf object to insert
     * @param plugin the plugin
     */
    public void insert( ResourceConf resourceConf, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setString( 1, resourceConf.getResourceType(  ) );
        daoUtil.setInt( 2, resourceConf.getIdResource(  ) );
        daoUtil.setInt( 3, resourceConf.isAcceptSiteVotes(  ) );
        daoUtil.setInt( 4, resourceConf.isEmailNotifiedVotes(  ) );
        daoUtil.setInt( 5, resourceConf.getIdMailingListVotes(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the resource conf by the resource type and  the resource identifier
     *
     * @param strResourceType the type of the resource
     * @param nIdResource the id of the resource
     * @param plugin the Plugin
     * @return an instance of resourceConf
     */
    public ResourceConf load( String strResourceType, int nIdResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.setInt( 2, nIdResource );

        daoUtil.executeQuery(  );

        ResourceConf resourceConf = null;

        if ( daoUtil.next(  ) )
        {
            resourceConf = new ResourceConf(  );
            resourceConf.setResourceType( daoUtil.getString( 1 ) );
            resourceConf.setIdResource( daoUtil.getInt( 2 ) );
            resourceConf.setAcceptSiteVotes( daoUtil.getInt( 3 ) );
            resourceConf.setEmailNotifiedVotes( daoUtil.getInt( 4 ) );
            resourceConf.setIdMailingListVotes( daoUtil.getInt( 5 ) );
        }

        daoUtil.free(  );

        return resourceConf;
    }

    /**
     * update record in the table.
     *
     * @param resourceConf instance of the ResourceConf object to update
     * @param plugin the plugin
     */
    public void store( ResourceConf resourceConf, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, resourceConf.getResourceType(  ) );
        daoUtil.setInt( 2, resourceConf.getIdResource(  ) );
        daoUtil.setInt( 3, resourceConf.isAcceptSiteVotes(  ) );
        daoUtil.setInt( 4, resourceConf.isEmailNotifiedVotes(  ) );
        daoUtil.setInt( 5, resourceConf.getIdMailingListVotes(  ) );
        daoUtil.setString( 6, resourceConf.getResourceType(  ) );
        daoUtil.setInt( 7, resourceConf.getIdResource(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Remove the ResourceConf whose identifier are specified in parameters
     *
     * @param strResourceType The type of the resource
     * @param nIdResource  the id of the resource
     * @param plugin the Plugin
     */
    public void delete( String strResourceType, int nIdResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.setInt( 2, nIdResource );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }


    /**
     * {@inheritDoc }
     */

    public List<String> getResourceTypes(Plugin plugin)
    {
        List<String> list = new ArrayList<String>();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_TYPES, plugin );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            list.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free(  );

        return list;
    }
}
