/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Question;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.survey.QuestionAnswer;
import org.encuestame.persistence.domain.survey.TweetPoll;
import org.encuestame.persistence.domain.survey.TweetPollFolder;
import org.encuestame.persistence.domain.survey.TweetPollResult;
import org.encuestame.persistence.domain.survey.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.persistence.domain.survey.TweetPollSavedPublishedStatus.Type;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Tweet Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 02, 2010
 * @version $Id: $
 */
public class TweetPollService extends AbstractSurveyService implements ITweetPollService{


    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Answer Poll Path.
     */
    private String answerPollPath;

    /**
     * TweetPoll Result Path.
     */
    private String tweetPollResultsPath;

    /**
     * Tweet Path.
     **/
    private String tweetPath;

    /**
     * Twitter Domain.
     */
    private String twitterDomain;

    private final Long TOTALVOTE = 0L ;

    /**
     * Get Tweet Polls by User Id.
     * @param username username.
     * @return list of Tweet polls bean
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitTweetPoll> getTweetsPollsByUserName(final String username,
            final Integer maxResults,
            final Integer start) throws EnMeDomainNotFoundException{
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(getPrimaryUser(username), maxResults, start);
         log.info("tweetPoll size "+tweetPolls.size());
        return this.setTweetPollListAnswers(tweetPolls);
    }

    /**
     * Set List Answer.
     * @param listTweetPolls List of {@link TweetPoll}
     * @return
     * @throws EnMeExpcetion
     */
    private List<UnitTweetPoll> setTweetPollListAnswers(final List<TweetPoll> listTweetPolls){
        final List<UnitTweetPoll> tweetPollsBean = new ArrayList<UnitTweetPoll>();
        for (TweetPoll tweetPoll : listTweetPolls) {
            final UnitTweetPoll unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             if (unitTweetPoll.getId() != null) {
                 unitTweetPoll.setTotalVotes(getTweetPollDao().getTotalVotesByTweetPollId(unitTweetPoll.getId()));
             } else {
                 unitTweetPoll.setTotalVotes(this.TOTALVOTE);
             }
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Search {@link TweetPoll} by Keyword.
     * @param username username session
     * @param keyword keyword.
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsByKeyWord(final String username,
                               final String keyword,
                               final Integer maxResults, final Integer start) throws EnMeExpcetion{
        log.info("search keyword tweetPoll  "+keyword);
        List<TweetPoll> tweetPolls  = new ArrayList<TweetPoll>();
        if(keyword == null){
           throw new EnMeExpcetion("keyword is missing");
        } else {
            tweetPolls = getTweetPollDao().retrieveTweetsByQuestionName(keyword, getPrimaryUser(username), maxResults, start);
        }
        log.info("search keyword tweetPoll size "+tweetPolls.size());
        return this.setTweetPollListAnswers(tweetPolls);
    }

    /**
     * Search Tweet Polls Today.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveTweetPollToday(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Tweet Polls Last Week.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveTweetPollLastWeek(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Favourites TweetPolls.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollFavourites(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveFavouritesTweetPoll(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Scheduled TweetsPoll.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollScheduled(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveScheduledTweetPoll(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Create Tweet Poll.
     * @param tweetPollBean tweet poll bean.
     * @throws EnMeExpcetion exception
     */
    public void createTweetPoll(final UnitTweetPoll tweetPollBean, final Question question) throws EnMeExpcetion {
        try{
            final TweetPoll tweetPollDomain = new TweetPoll();
            log.debug("question found "+question);
            if(question == null){
                throw new EnMeExpcetion("question not found");
            }
            tweetPollDomain.setQuestion(question);
            tweetPollDomain.setCloseNotification(tweetPollBean.getCloseNotification());
            tweetPollDomain.setCompleted(Boolean.FALSE);
            tweetPollDomain.setCaptcha(tweetPollBean.getCaptcha());
            tweetPollDomain.setAllowLiveResults(tweetPollBean.getAllowLiveResults());
            tweetPollDomain.setLimitVotes(tweetPollBean.getLimitVotes());
            tweetPollDomain.setTweetOwner(getAccountDao().getUserById(tweetPollBean.getUserId()));
            tweetPollDomain.setResultNotification(tweetPollBean.getResultNotification());
            tweetPollDomain.setPublishTweetPoll(tweetPollBean.getPublishPoll());
            tweetPollDomain.setCreateDate(new Date());
            tweetPollDomain.setAllowLiveResults(tweetPollBean.getAllowLiveResults());
            tweetPollDomain.setScheduleTweetPoll(tweetPollBean.getSchedule());
            tweetPollDomain.setScheduleDate(tweetPollBean.getScheduleDate());
            this.getTweetPollDao().saveOrUpdate(tweetPollDomain);
            final List<QuestionAnswer> answers = this.getQuestionDao().getAnswersByQuestionId(question.getQid());
            for (QuestionAnswer questionsAnswers : answers) {
                final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
                tPollSwitch.setAnswers(questionsAnswers);
                tPollSwitch.setTweetPoll(tweetPollDomain);
                tPollSwitch.setCodeTweet(questionsAnswers.getUniqueAnserHash());
                getTweetPollDao().saveOrUpdate(tPollSwitch);
                createNotification(NotificationEnum.TWEETPOL_CREATED, question.getQuestion() , question.getAccountQuestion());
            }
            //Save Hash Tags for this tweetPoll.
            log.debug("HashTag Size"+tweetPollBean.getHashTags().size());
            for (UnitHashTag unitHashTag : tweetPollBean.getHashTags()) {
                HashTag hashTag = getHashTagDao().getHashTagByName(unitHashTag.getHashTagName().toLowerCase());
                //If is null, create new hashTag.
                if(hashTag == null){
                    log.debug("Create HashTag "+unitHashTag.getHashTagName().toLowerCase());
                    hashTag = createHashTag(unitHashTag.getHashTagName().toLowerCase());
                }
                tweetPollDomain.getHashTags().add(hashTag);
            }
            //Update TweetPoll.
            if( tweetPollBean.getHashTags().size() > 0){
                log.debug("Update Hash Tag");
                getTweetPollDao().saveOrUpdate(tweetPollDomain);
            }
            tweetPollBean.setId(tweetPollDomain.getTweetPollId());
        }
        catch (Exception e) {
            log.error("Error creating TweetlPoll "+e);
            e.printStackTrace();
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    public String generateTweetPollText(final UnitTweetPoll tweetPoll, final String url) throws EnMeExpcetion{
        String tweetQuestionText = "";
        try{
            final TweetPoll tweetPollDomain = getTweetPollDao().getTweetPollById(tweetPoll.getId());
            log.debug("generateTweetPollText");
            log.debug("TweetPoll ID: "+tweetPollDomain.getTweetPollId());
            tweetQuestionText = tweetPollDomain.getQuestion().getQuestion();
            log.debug("Question text: "+tweetQuestionText);
            //Build Answers.
            final List<QuestionAnswer> answers = getQuestionDao().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            for (final QuestionAnswer questionsAnswers : answers) {
                 log.debug("Answer ID: "+questionsAnswers.getQuestionAnswerId());
                 log.debug("Answer Question: "+questionsAnswers.getAnswer());
                 tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+buildUrlAnswer(questionsAnswers, url);
            }
            //Build Hash Tag.
            for (final HashTag tag : tweetPollDomain.getHashTags()) {
                 log.debug("Hash Tag ID: "+tag.getHashTagId());
                 log.debug("Tag Name "+tag.getHashTag());
                 final StringBuffer buffer = new StringBuffer(tweetQuestionText);
                 buffer.append(" ");
                 buffer.append("#");
                 buffer.append(tag.getHashTag());
                 tweetQuestionText = buffer.toString();
            }
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        log.debug("Question Generated: "+tweetQuestionText);
        return tweetQuestionText;
    }



    /**
     * Build Url Answer.
     * @param anwer answer
     * @throws IOException exception
     * @throws HttpException exception
     */
    private String buildUrlAnswer(final QuestionAnswer answer, final String domain) throws HttpException, IOException{
        final StringBuffer stringBuffer = new StringBuffer(domain);
        stringBuffer.append(this.getTweetPath());
        stringBuffer.append(answer.getUniqueAnserHash());
        return getTwitterService().getTinyUrl(stringBuffer.toString());
    }

    /**
     * Public Multiples Tweet Accounts.
     * @param twitterAccounts List of {@link SocialAccount}.
     * @param tweetPoll {@link TweetPoll}.
     * @param tweetText tweet text.
     */
    public String[] publicMultiplesTweetAccounts(
            final List<UnitTwitterAccountBean> twitterAccounts,
            final Long tweetPollId,
            final String tweetText){
            final String accountsResult[] = {};
            log.debug("publicMultiplesTweetAccounts "+twitterAccounts.size());
            for (UnitTwitterAccountBean unitTwitterAccountBean : twitterAccounts) {
                final String account[] = {};
                final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
                final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
                final SocialAccount socialTwitterAccounts = getAccountDao().getTwitterAccount(unitTwitterAccountBean.getAccountId());
                publishedStatus.setApiType(Type.TWITTER);
                if(socialTwitterAccounts != null && tweetPoll != null){
                    log.debug("secUserTwitterAccounts Account"+socialTwitterAccounts.getSocialAccountName());
                    publishedStatus.setTweetPoll(tweetPoll);
                    publishedStatus.setTwitterAccount(socialTwitterAccounts);
                    try {
                        log.debug("Publishing...");
                        final Status status = this.publicTweetPoll(tweetText, socialTwitterAccounts);
                        publishedStatus.setTweetId(status.getId());
                        publishedStatus.setPublicationDateTweet(status.getCreatedAt());
                        publishedStatus.setStatus(TweetPollSavedPublishedStatus.Status.SUCCESS);
                        createNotification(NotificationEnum.TWEETPOLL_PUBLISHED,
                                buildTwitterItemView(socialTwitterAccounts.getSocialAccountName(), String.valueOf(status.getId())),
                                socialTwitterAccounts.getSecUsers());
                    } catch (Exception e) {
                        log.error("Error publish tweet "+e.getMessage());
                        publishedStatus.setStatus(TweetPollSavedPublishedStatus.Status.FAILED);
                        publishedStatus.setDescriptionStatus(e.getMessage().substring(254));
                    }
                    getTweetPollDao().saveOrUpdate(publishedStatus);
                }
                else{
                    log.warn("Twitter Account Not Found [Id:"+unitTwitterAccountBean.getAccountId()+"]");
                }
            }
            return accountsResult;
    }

    /**
     * Build Twitter Url.
     * @param username
     * @param id
     * @return
     */
    public String buildTwitterItemView(final String username, final String id){
        final StringBuilder builder = new  StringBuilder(getTwitterDomain());
        builder.append(username);
        builder.append("/status/");
        builder.append(id);
        return builder.toString();
    }

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    public void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip){
        final TweetPollResult pollResult = new TweetPollResult();
        pollResult.setIpVote(ip.trim());
        pollResult.setTweetPollSwitch(pollSwitch);
        pollResult.setTweetResponseDate(new Date());
        getTweetPollDao().saveOrUpdate(pollResult);
    }

    /**
     * Get Results By {@link TweetPoll}.
     * @param tweetPollId tweetPoll Id
     * @return list of {@link UnitTweetPollResult}
     * @throws EnMeDomainNotFoundException
     */
    //FIXME: this service don' retrieve data if answer never was voted.
    public List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId) throws EnMeDomainNotFoundException{
        log.debug("getResultsByTweetPollId "+tweetPollId);
        final List<UnitTweetPollResult> pollResults = new ArrayList<UnitTweetPollResult>();
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        log.debug("tweetPoll "+tweetPoll);
        if(tweetPoll == null){
            throw new EnMeDomainNotFoundException("tweetPoll not found");
        } else {
            for (QuestionAnswer questionsAnswers : getQuestionDao().getAnswersByQuestionId(tweetPoll.getQuestion().getQid())) {
                  log.debug("Question Name "+tweetPoll.getQuestion().getQuestion());
                  log.debug("Answers Size "+tweetPoll.getQuestion().getQuestionsAnswers().size());
                  final List<Object[]> result = getTweetPollDao().getResultsByTweetPoll(tweetPoll, questionsAnswers);
                  log.debug("result getResultsByTweetPollId- "+result.size());
                  for (Object[] objects : result) {
                      log.debug("objects 1- "+objects[0]);
                      log.debug("objects 2- "+objects[1]);
                      final UnitTweetPollResult tweetPollResult = new UnitTweetPollResult();
                      tweetPollResult.setResults(Long.valueOf(objects[1].toString()));
                      tweetPollResult.setAnswersBean(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
                      pollResults.add(tweetPollResult);
                  }
            }
        }
        return pollResults;
    }

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    public TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll){
        return getTweetPollDao().validateVoteIP(ipVote, tweetPoll);
    }

    /**
     * Validate User Twitter Account.
     * @param username username logged.
     * @return if user have twitter account
     * @throws EnMeDomainNotFoundException
     * @throws TwitterException
     */
    @Deprecated
    public Boolean validateUserTwitterAccount(final String username) throws EnMeDomainNotFoundException{
        final Account users = getUserAccount(username).getAccount();
        Boolean validate = false;
     // TODO: Removed by ENCUESTAME-43
    /*    log.info(users.getTwitterAccount());
        if(!users.getTwitterAccount().isEmpty() && !users.getTwitterPassword().isEmpty()){
            log.info(users.getTwitterPassword());
            try{
                final User user = getTwitterService().verifyCredentials(users.getTwitterAccount(), users.getTwitterPassword());
                log.info(user);
                validate = Integer.valueOf(user.getId()) != null ? true : false;
            }
            catch (Exception e) {
                log.error("Error Validate Twitter Account "+e.getMessage());
            }
        }*/
        log.info(validate);
        return validate;
    }

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public UnitFolder createTweetPollFolder(final String folderName, final String username) throws EnMeDomainNotFoundException{
        final TweetPollFolder tweetPollFolderDomain = new TweetPollFolder();
        tweetPollFolderDomain.setUsers(getUserAccount(username).getAccount());
        tweetPollFolderDomain.setCreatedAt(new Date());

        tweetPollFolderDomain.setFolderName(folderName);
        this.getTweetPollDao().saveOrUpdate(tweetPollFolderDomain);
        return ConvertDomainBean.convertFolderToBeanFolder(tweetPollFolderDomain);

    }

    /**
     * Update Tweet Poll Folder.
     * @throws EnMeDomainNotFoundException
     */
    public UnitFolder updateTweetPollFolder(final Long folderId, final String folderName, final String username) throws EnMeDomainNotFoundException{
        final TweetPollFolder tweetPollFolder = this.getTweetPollFolder(folderId);
        if(tweetPollFolder == null) {
            throw new EnMeDomainNotFoundException("Tweet Poll Folder not found");
        }
        else{
            tweetPollFolder.setFolderName(folderName);
            getTweetPollDao().saveOrUpdate(tweetPollFolder);
        }
         return ConvertDomainBean.convertFolderToBeanFolder(tweetPollFolder);
     }

     /**
     * Remove TweetPoll Folder.
     * @param TweetPoll folderId
     * @throws EnMeDomainNotFoundException
     */
    public void deleteTweetPollFolder(final Long folderId) throws EnMeDomainNotFoundException{
        final TweetPollFolder tweetPollfolder = this.getTweetPollFolder(folderId);
        if(tweetPollfolder != null){
            getTweetPollDao().delete(tweetPollfolder);
        } else {
            throw new EnMeDomainNotFoundException("TweetPoll folder not found");
        }
    }

    /**
     * Get Tweet Poll Folder.
     * @param id
     * @return
     */
    private TweetPollFolder getTweetPollFolder(final Long folderId){
        return this.getTweetPollDao().getTweetPollFolderById(folderId);
    }

    /**
     * Get Tweet Poll Folder by User and FolderId.
     * @param id
     * @return
     */
    private TweetPollFolder getTweetPollFolderByFolderIdandUser(final Long folderId, final Long userId){
        return this.getTweetPollDao().getTweetPollFolderByIdandUser(folderId, userId);
    }

    /**
     * Add {@link TweetPoll} to Folder.
     * @param folderId
     * @throws EnMeDomainNotFoundException
     */
    public void addTweetPollToFolder(final Long folderId, final String username, final Long tweetPollId) throws EnMeDomainNotFoundException{
        final TweetPollFolder tpfolder = this.getTweetPollFolderByFolderIdandUser(folderId, getPrimaryUser(username));
         if(tpfolder!=null) {
             final TweetPoll tpoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
             tpoll.setTweetPollFolder(tpfolder);
             getTweetPollDao().saveOrUpdate(tpoll);
         } else {
             throw new EnMeDomainNotFoundException("TweetPoll folder not found");
         }
    }

    /**
     * Change Status {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    public void changeStatusTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setCloseNotification(Boolean.TRUE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
               throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /**
     * Set Favourite TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    public void setFavouriteTweetPoll(final Long tweetPollId, final String username) throws
           EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setFavourites(tweetPoll.getFavourites() == null ? false : !tweetPoll.getFavourites());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
               throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }


    /**
     * Change Allow Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    public void changeAllowLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setAllowLiveResults(tweetPoll.getAllowLiveResults() == null ? false : !tweetPoll.getAllowLiveResults());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Allow Live Results Operation");
        }
    }

    /**
     * Change Allow Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    public void changeAllowCaptchaTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
             tweetPoll.setCaptcha(tweetPoll.getCaptcha() == null ? false : !tweetPoll.getCaptcha());
             getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Allow Captcha Operation");
        }
    }

    /**
     * Change Resume Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     */
    public void changeResumeLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setResumeLiveResults(tweetPoll.getResumeLiveResults() == null ? false : !tweetPoll.getResumeLiveResults());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Resume Live Results Operation");
        }
    }

    /**
     * Get TweetPoll.
     * @param tweetPollId
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    private TweetPoll getTweetPoll(final Long tweetPollId, final String username) throws EnMeDomainNotFoundException{
        return getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
    }

    /**
     * Change Allow Repeated TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    public void changeAllowRepeatedTweetPoll(final Long tweetPollId, final String username)
                throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = this.getTweetPoll(tweetPollId, username);
        if (tweetPoll != null){
            tweetPoll.setAllowRepatedVotes(tweetPoll.getAllowRepatedVotes() == null ? false : !tweetPoll.getAllowRepatedVotes());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
            throw new EnmeFailOperation("Fail Change Allow Repeated Operation");
        }
    }

    /**
     * Change Close Notification.
     * @param tweetPollId
     * @param username
     * @throws EnMeDomainNotFoundException
     * @throws EnmeFailOperation
     */
    public void changeCloseNotificationTweetPoll(final Long tweetPollId, final String username)
           throws EnMeDomainNotFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = this.getTweetPoll(tweetPollId, username);
        if (tweetPoll != null){
            tweetPoll.setCloseNotification(tweetPoll.getCloseNotification() == null
                      ? false : !tweetPoll.getCloseNotification());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
            throw new EnmeFailOperation("Fail Change Allow Repeated Operation");
        }
    }

    /**
     * @return the answerPollPath
     */
    public String getAnswerPollPath() {
        return answerPollPath;
    }

    /**
     * @param answerPollPath the answerPollPath to set
     */
    public void setAnswerPollPath(final String answerPollPath) {
        this.answerPollPath = answerPollPath;
    }

    /**
     * @return the tweetPollResultsPath
     */
    public String getTweetPollResultsPath() {
        return tweetPollResultsPath;
    }

    /**
     * @param tweetPollRgetTweetPollResultsPathesultsPath the tweetPollResultsPath to set
     */
    public void setTweetPollResultsPath(final String tweetPollResultsPath) {
        this.tweetPollResultsPath = tweetPollResultsPath;
    }

    /**
     * @return the tweetPath
     */
    public String getTweetPath() {
        return tweetPath;
    }

    /**
     * @param tweetPath the tweetPath to set
     */
    public void setTweetPath(final String tweetPath) {
        this.tweetPath = tweetPath;
    }

    /**
     * @return the twitterDomain
     */
    public String getTwitterDomain() {
        return twitterDomain;
    }

    /**
     * @param twitterDomain the twitterDomain to set
     */
    public void setTwitterDomain(String twitterDomain) {
        this.twitterDomain = twitterDomain;
    }
}
