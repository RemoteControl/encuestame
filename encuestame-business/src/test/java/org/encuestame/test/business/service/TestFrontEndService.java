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
package org.encuestame.test.business.service;

import java.util.List;

import org.encuestame.business.service.FrontEndService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.web.HashTagBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test for {@link FrontEndService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 12, 2011
 */
public class TestFrontEndService extends AbstractSpringSecurityContext{

    @Autowired
    private IFrontEndService frontEndService;

    /** {@link HashTag} **/
    private HashTag hashTag;

    /** {@link HashTagHits} **/
    private HashTagHits hashTagHit;

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** ip address. **/
    final String ipAddress = "192.168.1.1";

    final String ipAddress2 = "192.168.1.2";

    /** {@link TweetPoll}. **/
    private TweetPoll tweetPoll;

    @Before
    public void initData(){
        this.secondary = createUserAccount("paola", createAccount());
        this.hashTagHit = createHashTagHit(hashTag, this.ipAddress, this.secondary);
        final Question question = createQuestion("Who I am?", "");
        createQuestionAnswer("yes", question, "12345");
        createQuestionAnswer("no", question, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question);
        this.hashTag = createHashTag("hardware",50L);
        final HashTag hashTag2 = createHashTag("programmer",80L);
        this.tweetPoll.getHashTags().add(hashTag);
        this.tweetPoll.getHashTags().add(hashTag2);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        //System.out.println("hashTag ID --->"+ hashTag.getHashTagId());
    }

    /**
     *
     */
    @Test
    public void testCheckPreviousHashTagHit(){
        flushIndexes();
        final Boolean previousRecord = getFrontEndService().checkPreviousHashTagHit(this.ipAddress);
        //System.out.println("Previous record exists? --> "+ previousRecord + "IP" + this.ipAddress);
        final Boolean previousRecord2 = getFrontEndService().checkPreviousHashTagHit(this.ipAddress2);
        //System.out.println("Previous record exists 2? --> "+ previousRecord2 + "IP" + this.ipAddress2);
    }

    /**
     *
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testRegisterHashTagHit() throws EnMeNoResultsFoundException{
        //System.out.println(" previous tag hit --> "+ this.hashTag.getHits());
        final Boolean registerHit = getFrontEndService().registerHashTagHit(this.hashTag, this.ipAddress);
        getFrontEndService().registerHashTagHit(this.hashTag, this.ipAddress2);
    }

    /**
     * Test Get hash tags
     */
    @Test
    public void testGetHashTags(){

        /** Hash Tags **/
        final HashTag hashTag1 = createHashTag("software",50L);
        final HashTag hashTag2 = createHashTag("holidays",70L);
        final HashTag hashTag3 = createHashTag("futboll",80L);
        final HashTag hashTag4 = createHashTag("championsLeague",90L);
        final HashTag hashTag5 = createHashTag("copaAmerica",150L);

        /** Question 2 **/
        final Question question2 = createQuestion("Question 1", "");
        createQuestionAnswer("yes", question2, "12345");
        createQuestionAnswer("no", question2, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question2);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag2);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 3 **/
        final Question question3 = createQuestion("Question 2", "");
        createQuestionAnswer("yes", question3, "12345");
        createQuestionAnswer("no", question3, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question3);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag2);
        this.tweetPoll.getHashTags().add(hashTag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 4 **/
        final Question question4 = createQuestion("Question 3", "");
        createQuestionAnswer("yes", question4, "12345");
        createQuestionAnswer("no", question4, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question4);

        this.tweetPoll.getHashTags().add(hashTag1);
        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        /** Question 5 **/
        final Question question5 = createQuestion("Question 4", "");
        createQuestionAnswer("yes", question5, "12345");
        createQuestionAnswer("no", question5, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question5);

        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        this.tweetPoll.getHashTags().add(hashTag3);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        final Question question6 = createQuestion("Question 5", "");
        createQuestionAnswer("yes", question6, "12345");
        createQuestionAnswer("no", question6, "12346");
        this.tweetPoll = createPublishedTweetPoll(secondary.getAccount(), question6);

        this.tweetPoll.getHashTags().add(hashTag3);
        this.tweetPoll.getHashTags().add(hashTag4);
        this.tweetPoll.getHashTags().add(hashTag5);
        getTweetPoll().saveOrUpdate(this.tweetPoll);

        final List<HashTagBean> hashBean = getFrontEndService().getHashTags(30, 0, "");
        System.out.println(" Hash Bean size --> "+hashBean.size());
        for (HashTagBean hashTagBean : hashBean) {
           // System.out.println(" Hash Bean size --> "+hashTagBean.getSize());
        }
    }

    /**
    * @return the frontEndService
    */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
    * @param frontEndService the frontEndService to set
    */
    public void setFrontEndService(IFrontEndService frontEndService) {
        this.frontEndService = frontEndService;
    }
}