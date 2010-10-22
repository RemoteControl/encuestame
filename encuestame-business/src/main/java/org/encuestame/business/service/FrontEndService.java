/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.core.exception.EnMeSearchException;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.dao.SearchPeriods;
import org.encuestame.persistence.dao.SearchSurveyPollTweetItem;
import org.encuestame.utils.web.frontEnd.UnitSearchItem;
import org.springframework.util.Assert;

/**
 * Front End Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:38 AM
 * @version $Id:$
 */
public class FrontEndService extends AbstractBaseService implements IFrontEndService {

    /** Front End Service Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Max Results. **/
    private final Integer MAX_RESULTS = 15;

    /**
     * Search Items By Keyword.
     * @param keyword keyword.
     * @param maxResults limit of results to return.
     * @return result of the search.
     * @throws EnMeSearchException search exception.
     */
    public List<UnitSearchItem> searchItemsByKeyword(
                final String keyword,
                final String period,
                Integer maxResults)
           throws EnMeSearchException{
        final SearchSurveyPollTweetItem searchItems = new SearchSurveyPollTweetItem();
        if(maxResults == null){
            maxResults = this.MAX_RESULTS;
        }
        log.debug("Max Results "+maxResults);
        final List<TweetPoll> items = new ArrayList<TweetPoll>();
        final List<Poll> polls = new ArrayList<Poll>();
        //final List<Surveys> surveys = new ArrayList<Surveys>();
        if(period == null || keyword == null){
            throw new EnMeSearchException("search params required.");
        } else {
            final SearchPeriods periodSelected = SearchPeriods.getPeriodString(period);
            if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(maxResults));
                polls.addAll(getFrontEndDao().getPollFrontEndLast24(maxResults));
                //surveys.addAll(getFrontEndDao(), maxResults);
            } else if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(maxResults));
                polls.addAll(getFrontEndDao().getPollFrontEndLast24(maxResults));
                //surveys.addAll(getFrontEndDao(), maxResults);
            } else if(periodSelected.equals(SearchPeriods.SEVENDAYS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast7Days(maxResults));
                polls.addAll(getFrontEndDao().getPollFrontEndLast7Days(maxResults));
                //surveys.addAll(getFrontEndDao(), maxResults);
            } else if(periodSelected.equals(SearchPeriods.THIRTYDAYS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast30Days(maxResults));
                polls.addAll(getFrontEndDao().getPollFrontEndLast30Days(maxResults));
                //surveys.addAll(getFrontEndDao(), maxResults);
            } else if(periodSelected.equals(SearchPeriods.ALLTIME)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndAllTime(maxResults));
                polls.addAll(getFrontEndDao().getPollFrontEndAllTime(maxResults));
                //surveys.addAll(getFrontEndDao(), maxResults);
            }
            Assert.notNull(searchItems);
            searchItems.setPolls(polls);
            searchItems.setTweetPolls(items);
            searchItems.setSurveys(null);
            log.debug("Poll "+polls.size());
            log.debug("TweetPoll "+items.size());
        }
        return null;
    }
}