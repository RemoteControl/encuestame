/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.json;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Link social bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinksSocialBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -466651445270622763L;

    @JsonProperty(value = "link_url")
    private String link;


    @JsonProperty(value = "provider_social")
    private String provider;


    /**
     * @return the link
     */
    @JsonIgnore
    public String getLink() {
        return link;
    }


    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }


    /**
     * @return the provider
     */
    @JsonIgnore
    public String getProvider() {
        return provider;
    }


    /**
     * @param provider the provider to set
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LinksSocialBean [link=" + link + ", provider=" + provider + "]";
    }
}