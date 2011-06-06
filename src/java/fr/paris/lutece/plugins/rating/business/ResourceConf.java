/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
 * class ResourceConf
 *
 */
public class ResourceConf
{
    private String _strResourceType;
    private int _nIdResource;
    private int _nIsAcceptSiteVotes;
    private int _nIsEmailNotifiedVotes;
    private int _nIdMailingListVotes;

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
     * Sets the nIsAcceptVote
     * @param nIsAcceptSiteVotes The nIsAcceptVote
     */
    public void setAcceptSiteVotes( int nIsAcceptSiteVotes )
    {
        _nIsAcceptSiteVotes = nIsAcceptSiteVotes;
    }

    /**
     * Returns the nIsAcceptVote
     * @return The nIsAcceptVote
     */
    public int isAcceptSiteVotes(  )
    {
        return _nIsAcceptSiteVotes;
    }

    /**
     * Sets the nIsEmailNotifiedVotes
     * @param nIsEmailNotifiedVotes The nIsEmailNotifiedVotes;
     */
    public void setEmailNotifiedVotes( int nIsEmailNotifiedVotes )
    {
        _nIsEmailNotifiedVotes = nIsEmailNotifiedVotes;
    }

    /**
     * Returns the nIsEmailNotifiedVotes
     * @return The nIsEmailNotifiedVotes
     */
    public int isEmailNotifiedVotes(  )
    {
        return _nIsEmailNotifiedVotes;
    }

    /**
     * set the Id of the mailing list
     * @param nIdMailingListVotes the id of the mailing list
     */
    public void setIdMailingListVotes( int nIdMailingListVotes )
    {
        _nIdMailingListVotes = nIdMailingListVotes;
    }

    /**
    *
    * @return the Id of the mailing list
    */
    public int getIdMailingListVotes(  )
    {
        return _nIdMailingListVotes;
    }
}
