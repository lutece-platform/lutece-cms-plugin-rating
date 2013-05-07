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
package fr.paris.lutece.plugins.rating.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 *
 * class ResourceStatDAO
 *
 */
public class ResourceStatDAO implements IResourceStatDAO
{
    private static final String SQL_QUERY_SELECT = "SELECT resource_type, id_resource, vote_count, score_value, download_count, view_count FROM rating_resource_stat WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO rating_resource_stat (resource_type, id_resource, vote_count, score_value, download_count, view_count )VALUES(?,?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE rating_resource_stat SET resource_type=?,id_resource=?, vote_count=?, score_value=?, download_count=?, view_count=? WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM rating_resource_stat WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_GET_DOWNLOAD_COUNT = "SELECT download_count FROM rating_resource_stat WHERE resource_type=? AND id_resource=?";
    private static final String SQL_QUERY_GET_SCORE = "SELECT vote_count, score_value FROM rating_resource_stat WHERE resource_type=? AND id_resource=?";

    /**
     * Load the data of the resourceStat from the table
     *
     * @param strResourceType the type of the resource
     * @param nIdResource the id of the resource
     * @param plugin the Plugin
     * @return an instance of ResourceStat
     */
    public ResourceStat load( String strResourceType, int nIdResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.setInt( 2, nIdResource );

        daoUtil.executeQuery(  );

        ResourceStat resourceStat = null;

        if ( daoUtil.next(  ) )
        {
            resourceStat = new ResourceStat(  );
            resourceStat.setResourceType( daoUtil.getString( 1 ) );
            resourceStat.setIdResource( daoUtil.getInt( 2 ) );
            resourceStat.setVoteCount( daoUtil.getInt( 3 ) );
            resourceStat.setScoreValue( daoUtil.getInt( 4 ) );
            resourceStat.setDownloadCount( daoUtil.getInt( 5 ) );
            resourceStat.setViewCount( daoUtil.getInt( 6 ) );
        }

        daoUtil.free(  );

        return resourceStat;
    }

    /**
    * Insert a new record in the table.
    *
    * @param resourceStat instance of the ResourceStat object to insert
    * @param plugin the plugin
    */
    public void insert( ResourceStat resourceStat, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        daoUtil.setString( 1, resourceStat.getResourceType(  ) );
        daoUtil.setInt( 2, resourceStat.getIdResource(  ) );
        daoUtil.setInt( 3, resourceStat.getVoteCount(  ) );
        daoUtil.setInt( 4, resourceStat.getScoreValue(  ) );
        daoUtil.setInt( 5, resourceStat.getDownloadCount(  ) );
        daoUtil.setInt( 6, resourceStat.getViewCount(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * update record in the table.
     *
     * @param resourceStat instance of the ResourceStat object to update
     * @param plugin the plugin
     */
    public void store( ResourceStat resourceStat, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setString( 1, resourceStat.getResourceType(  ) );
        daoUtil.setInt( 2, resourceStat.getIdResource(  ) );
        daoUtil.setInt( 3, resourceStat.getVoteCount(  ) );
        daoUtil.setInt( 4, resourceStat.getScoreValue(  ) );
        daoUtil.setInt( 5, resourceStat.getDownloadCount(  ) );
        daoUtil.setInt( 6, resourceStat.getViewCount(  ) );
        daoUtil.setString( 7, resourceStat.getResourceType(  ) );
        daoUtil.setInt( 8, resourceStat.getIdResource(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Remove the ResourceStat whose identifier are specified in parameters
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
         * Returns the count of resource download whose resource identifier and resource type are specified in parameters
         *
         * @param strResourceType The type of the resource
         * @param nIdResource the id of the resource
         * @param plugin the Plugin
         * @return the count of resource download
         */
    public int getDownloadCountByResource( String strResourceType, int nIdResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_GET_DOWNLOAD_COUNT, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.setInt( 2, nIdResource );

        daoUtil.executeQuery(  );

        int nDocumentDownlodCount = 0;

        if ( daoUtil.next(  ) )
        {
            nDocumentDownlodCount = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        return nDocumentDownlodCount;
    }

    /**
     * Returns the score of a resource whose resource identifier and resource type are specified in parameters
     *
     * @param strResourceType The type of the resource
     * @param nIdResource the id of the resource
     * @param plugin the Plugin
     * @return the score
     */
    public int getScore( String strResourceType, int nIdResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_GET_SCORE, plugin );

        daoUtil.setString( 1, strResourceType );
        daoUtil.setInt( 2, nIdResource );

        daoUtil.executeQuery(  );

        int nScore = 0;

        if ( daoUtil.next(  ) )
        {
            int nVoteCount = daoUtil.getInt( 1 );
            int nScoreValue = daoUtil.getInt( 2 );

            if ( ( nVoteCount != 0 ) )
            {
                nScore = Math.round( (float) nScoreValue / (float) nVoteCount );
                nScore = nScore + 2;
            }
        }

        daoUtil.free(  );

        return nScore;
    }
}
