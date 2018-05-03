/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
import fr.paris.lutece.portal.service.spring.SpringContextService;


/**
*
*class ResourceStatHome
*
*/
public final class ResourceStatHome
{
    // Static variable pointed at the DAO instance
    private static IResourceStatDAO _dao = (IResourceStatDAO) SpringContextService.getPluginBean( "rating",
            "rating.resourceStatDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ResourceStatHome(  )
    {
    }

    /**
     * Creation of an instance of downloadStat
     *
     * @param resourceStat The instance of the ResourceStat which contains the informations to store
     * @param plugin the Plugin
     */
    public static void create( ResourceStat resourceStat, Plugin plugin )
    {
        _dao.insert( resourceStat, plugin );
    }

    /**
         * update record in the table.
         *
         * @param resourceStat instance of the ResourceStat object to update
         * @param plugin the plugin
         */
    public static void update( ResourceStat resourceStat, Plugin plugin )
    {
        _dao.store( resourceStat, plugin );
    }

    /**
     * Remove the ResourceStat whose identifier are specified in parameters
     *
     * @param strResourceType The type of the resource
     * @param nIdResource  the id of the resource
     * @param plugin the Plugin
     */
    public static void remove( String strResourceType, int nIdResource, Plugin plugin )
    {
        _dao.delete( strResourceType, nIdResource, plugin );
    }

    /**
         * Returns an instance of ResourceStat whose resource type and resource identifier are specified in parameters
         *
         * @param strResourceType The type of the resource
         * @param nIdResource  the id of the resource
         * @param plugin the Plugin
         * @return an instance of ResourceStat
         */
    public static ResourceStat findResourceStat( String strResourceType, int nIdResource, Plugin plugin )
    {
        return _dao.load( strResourceType, nIdResource, plugin );
    }

    /**
     * Returns the count of resource download whose resource identifier and resource type are specified in parameters
     *
     * @param strResourceType The type of the resource
     * @param nIdResource the id of the resource
     * @param plugin the Plugin
     * @return the count of resource download
     */
    public static int getDownloadCountByResource( String strResourceType, int nIdResource, Plugin plugin )
    {
        return _dao.getDownloadCountByResource( strResourceType, nIdResource, plugin );
    }

    /**
     * Returns the score of a resource whose resource identifier and resource type are specified in parameters
     *
     * @param strResourceType The type of the resource
     * @param nIdResource the id of the resource
     * @param plugin the Plugin
     * @return the score
     */
    public static int getScore( String strResourceType, int nIdResource, Plugin plugin )
    {
        return _dao.getScore( strResourceType, nIdResource, plugin );
    }
}
