<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-tweetpoll-detail web-wrapper-detail">
   <header>
       <h1>${tweetpoll.questionBean.questionName}</h1>
   </header>
   <article class="emne-box">
        <section class="web-tweetpoll-info">
            <div id="info" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail"
                <c:if test="${tweetpoll.limitVotesDate}">
                    date="${tweetpoll.dateToLimit}"
                </c:if>
                 votes="${tweetpoll.totalVotes}"
                 question=" ${tweetpoll.questionBean.questionName}"
                 hits="${tweetpoll.hits}"
                 completed="${tweetpoll.completed}"
                 owner="${tweetpoll.ownerUsername}"
                 tweetPollid="${tweetpoll.id}"></div>
       </section>
        <section class="web-tweetpoll-answer-wrapper web-wrapper-detail-wrapper">
            <div class="web-tweetpoll-answer-chart">
                <div id="chart" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"
                     tweetPollid="${tweetpoll.id}"
                     completed="${tweetpoll.completed}"
                     username="${tweetpoll.ownerUsername}"></div>
            </div>
            <div class="web-tweetpoll-answer-answer">
                <table class="web-tweetpoll-answer-table" cellspacing="0">
                    <thead>
                        <tr class="gradient-black">
                            <th></th>
                            <th>
                                <span class="header-table-answer">
                                	<spring:message code="options.linkToVote"/>
                                </span>
                            </th>
                            <th>
                                <span class="header-table-answer">
                                	<spring:message code="options.votes" />
                                </span>
                            </th>
                            <th>
                                <span class="header-table-answer">
                                	<spring:message code="options.percent" />
                                </span>
                            </th>
                            <th>
                                <span class="header-table-answer"></span>
                            </th>
                        </tr>
                        <tr>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${answers}" var="a">
                            <tr class="answer"
                                dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer"
                                 aId="${a.answers.questionAnswerId}"
                                  color="${a.answers.color}"
                                 label="${a.answers.answer}"
                                  owner="${tweetpoll.ownerUsername}"
                                  completed="${tweetpoll.completed}"
                                  url="${a.shortUrl}">
                             </tr>
                       </c:forEach>
                    </tbody>
                </table>
            </div>
       </section>
       <section class="web-button-wrapper gradient-gray">
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_pie.png">
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_bar.png">
            </div>
       </section>
   </article>
   <article class="emne-box">
       <header>
          <spring:message code="options.links" />
       </header>
       <section>
            <div dojoType="encuestame.org.core.commons.social.LinksPublished"
                 itemId="${tweetpoll.id}" type="TWEETPOLL" class="web-social-links"
                 ></div>
       </section>
    </article>
   <c:if test="${!empty hashtags}">
       <section class="emne-box">
           <header>
           		<spring:message code="options.hashtag" />
           </header>
           <div class="web-tweetpoll-hashtags ">
               <c:forEach items="${hashtags}" var="h">
                       <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
           </div>
       </section>
   </c:if>
   <section class="web-tweetpoll-comments emne-box">
      <header>
            <spring:message code="options.comments" />
      </header>
      <div name="comments" dojoType="encuestame.org.core.comments.Comments" type="tweetpoll" item_id="${tweetpoll.id}"></div>
   </section>
</article>
