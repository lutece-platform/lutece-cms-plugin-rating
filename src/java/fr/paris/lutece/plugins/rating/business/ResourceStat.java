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


/**
 *
 * class ResourceStat
 *
 */
public class ResourceStat
{
    private String _strResourceType;
    private int _nIdResource;
    private int _nVoteCount;
    private int _nScoreValue;
    private int _nDownloadCount;
    private int _nViewCount;

    /**
    * set the resource type
    * @param strResourceType the resource type of the statistic
    */
    public void setResourceType( String strResourceType )
    {
        _strResourceType = strResourceType;
    }

    /**
    *
    * @return the resource type of the statistic
    */
    public String getResourceType(  )
    {
        return _strResourceType;
    }

    /**
    * set the Id of the resource
    * @param nIdResource the id of the resource
    */
    public void setIdResource( int nIdResource )
    {
        _nIdResource = nIdResource;
    }

    /**
    *
    * @return the Id of the resource
    */
    public int getIdResource(  )
    {
        return _nIdResource;
    }

    /**
     * set the download count
     * @param nDownloadCount the count of download
     */
    public void setDownloadCount( int nDownloadCount )
    {
        _nDownloadCount = nDownloadCount;
    }

    /**
    *
    * @return the download count
    */
    public int getDownloadCount(  )
    {
        return _nDownloadCount;
    }

    /**
     * set the vote count
     * @param nVoteCount the count of vote
     */
    public void setVoteCount( int nVoteCount )
    {
        _nVoteCount = nVoteCount;
    }

    /**
    *
    * @return the vote count
    */
    public int getVoteCount(  )
    {
        return _nVoteCount;
    }

    /**
     * set the view count
     * @param nViewCount the count of view
     */
    public void setViewCount( int nViewCount )
    {
        _nViewCount = nViewCount;
    }

    /**
    *
    * @return the view count
    */
    public int getViewCount(  )
    {
        return _nViewCount;
    }

    /**
     * set the score value
     * @param nScoreValue the value of the score
     */
    public void setScoreValue( int nScoreValue )
    {
        _nScoreValue = nScoreValue;
    }

    /**
    *
    * @return the score value
    */
    public int getScoreValue(  )
    {
        return _nScoreValue;
    }
}
