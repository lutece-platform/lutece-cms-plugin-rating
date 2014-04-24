/*
 * Copyright (c) 2002-2014, Mairie de Paris
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

import fr.paris.lutece.plugins.rating.business.ResourceStat;
import fr.paris.lutece.plugins.rating.business.ResourceStatHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;


/**
 * RatingService
 */
public class RatingService
{
    private static Plugin _plugin;

    /**
     * Increments download counter
     * @param strResourceType the resource type
     * @param nIdResource the resource id
     */
    public static void incrementDownloadCount( String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );

        ResourceStat resourceStat = ResourceStatHome.findResourceStat( strResourceType, nIdResource, plugin );

        if ( resourceStat == null )
        {
            resourceStat = createResourceStat( strResourceType, nIdResource );
        }

        resourceStat.setDownloadCount( resourceStat.getDownloadCount(  ) + 1 );
        ResourceStatHome.update( resourceStat, plugin );
    }

    /**
     * Increments view counter
     * @param strResourceType the resource type
     * @param nIdResource the resource id
     */
    public static void incrementViewCount( String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );

        ResourceStat resourceStat = ResourceStatHome.findResourceStat( strResourceType, nIdResource, plugin );

        if ( resourceStat == null )
        {
            resourceStat = createResourceStat( strResourceType, nIdResource );
        }

        resourceStat.setViewCount( resourceStat.getViewCount(  ) + 1 );
        ResourceStatHome.update( resourceStat, plugin );
    }

    /**
     * Increments vote data
     * @param strResourceType the resource type
     * @param nIdResource the resource id
     * @param nVoteValue the vote value
     */
    public static void incrementVote( String strResourceType, int nIdResource, int nVoteValue )
    {
        Plugin plugin = getPlugin(  );

        ResourceStat resourceStat = ResourceStatHome.findResourceStat( strResourceType, nIdResource, plugin );

        if ( resourceStat == null )
        {
            resourceStat = new ResourceStat(  );
            resourceStat.setResourceType( strResourceType );
            resourceStat.setIdResource( nIdResource );
            ResourceStatHome.create( resourceStat, plugin );
        }

        resourceStat.setVoteCount( resourceStat.getVoteCount(  ) + 1 );
        resourceStat.setScoreValue( resourceStat.getScoreValue(  ) + nVoteValue );
        ResourceStatHome.update( resourceStat, plugin );
    }

    private static ResourceStat createResourceStat( String strResourceType, int nIdResource )
    {
        Plugin plugin = getPlugin(  );

        ResourceStat resourceStat = new ResourceStat(  );
        resourceStat.setResourceType( strResourceType );
        resourceStat.setIdResource( nIdResource );
        ResourceStatHome.create( resourceStat, plugin );

        return ResourceStatHome.findResourceStat( strResourceType, nIdResource, plugin );
    }

    private static Plugin getPlugin(  )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( RatingPlugin.PLUGIN_NAME );
        }

        return _plugin;
    }
}
