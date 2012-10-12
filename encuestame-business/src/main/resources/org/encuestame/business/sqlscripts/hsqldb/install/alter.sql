alter table access_rate add constraint FKC2760EDB546D76C9 foreign key (comments_commentId) references comments
alter table access_rate add constraint FKC2760EDB63976E9 foreign key (poll_poll_id) references poll
alter table access_rate add constraint FKC2760EDBE4669675 foreign key (user_uid) references userAccount
alter table access_rate add constraint FKC2760EDB51153812 foreign key (survey_sid) references surveys
alter table access_rate add constraint FKC2760EDB953C854B foreign key (tweetPoll_tweet_poll_id) references tweetPoll
alter table application add constraint FK5CA405505ECE45A2 foreign key (account_uid) references account
alter table application_connection add constraint FK73D5D2D27E933D7 foreign key (account_uid) references userAccount
alter table application_connection add constraint FK73D5D2D4402BE26 foreign key (application_application_id) references application
alter table attachment add constraint FK8AF75923225A055 foreign key (project_id) references project
alter table client add constraint FKAF12F3CB225A055 foreign key (project_id) references project
alter table comments add constraint FKDC17DDF4F44558E9 foreign key (uid) references userAccount
alter table comments add constraint FKDC17DDF4793D9E77 foreign key (sid) references surveys
alter table comments add constraint FKDC17DDF4CE12CAE8 foreign key (pollId) references poll
alter table comments add constraint FKDC17DDF4D9AA8E98 foreign key (tweetPollId) references tweetPoll
alter table dashboard add constraint FKC18AEA949229BCA5 foreign key (userBoard_uid) references userAccount
alter table email add constraint FK5C24B9CED78E617 foreign key (id_list) references emailList
alter table emailList add constraint FK7E5F425A2B2A6AB4 foreign key (uid) references account
alter table emailSubscribe add constraint FK4B85010EED78E617 foreign key (id_list) references emailList
alter table emailSubscribe add constraint FK4B85010EE824035 foreign key (email_id) references email
alter table gadget add constraint FKB549144CB975B5F9 foreign key (dashboard_dashboardId) references dashboard
alter table gadget_properties add constraint FK866B6706369F8B2C foreign key (userAccount_uid) references userAccount
alter table gadget_properties add constraint FK866B670629091B05 foreign key (gadget_gadgetId) references gadget
alter table geoPoint add constraint FK6C73C0BFBD91661D foreign key (loc_id_type) references geoPoint_type
alter table geoPoint add constraint FK6C73C0BF5ECE45A2 foreign key (account_uid) references account
alter table geoPoint add constraint FK6C73C0BF34EF9A43 foreign key (geoPointFolder_locate_folder_id) references geoPoint_folder
alter table geoPoint_folder add constraint FKF4A1D3EE2B2A6AB4 foreign key (uid) references account
alter table geoPoint_folder add constraint FKF4A1D3EE6EF241E9 foreign key (createdBy_uid) references userAccount
alter table geoPoint_folder add constraint FKF4A1D3EE6E4ED46D foreign key (subLocationFolder_locate_folder_id) references geoPoint_folder
alter table geoPoint_type add constraint FK514326BA4075E3FD foreign key (users_uid) references account
alter table group_permission add constraint FK362E6F8F45895AFF foreign key (sec_id_group) references groups
alter table group_permission add constraint FK362E6F8F43ADB63D foreign key (sec_id_permission) references permission
alter table groups add constraint FKB63DD9D45ECE45A2 foreign key (account_uid) references account
alter table groups_permission add constraint FK7F1951A45895AFF foreign key (sec_id_group) references groups
alter table groups_permission add constraint FK7F1951A43ADB63D foreign key (sec_id_permission) references permission
alter table hits add constraint FK30DF40369F8B2C foreign key (userAccount_uid) references userAccount
alter table hits add constraint FK30DF4019AA125 foreign key (hashTag_hash_tag_id) references hash_tags
alter table hits add constraint FK30DF4063976E9 foreign key (poll_poll_id) references poll
alter table hits add constraint FK30DF4051153812 foreign key (survey_sid) references surveys
alter table hits add constraint FK30DF40953C854B foreign key (tweetPoll_tweet_poll_id) references tweetPoll
alter table notification add constraint FK237A88EB2B2A6AB4 foreign key (uid) references account
alter table poll add constraint FK3497BF89452CCA foreign key (poll_folder) references poll_folder
alter table poll add constraint FK3497BF50FE71F5 foreign key (qid) references questions
alter table poll add constraint FK3497BFA64FB606 foreign key (editor) references userAccount
alter table poll add constraint FK3497BF8E4A448B foreign key (owner_id) references account
alter table poll_folder add constraint FKC5911CEE2B2A6AB4 foreign key (uid) references account
alter table poll_folder add constraint FKC5911CEE6EF241E9 foreign key (createdBy_uid) references userAccount
alter table poll_hashtags add constraint FK9D199EA7DA98FFE1 foreign key (hastag_id) references hash_tags
alter table poll_hashtags add constraint FK9D199EA7F0ED6769 foreign key (poll_id) references poll
alter table poll_result add constraint FKD981C89DDDD118B5 foreign key (q_answer_id) references questions_answers
alter table poll_result add constraint FKD981C89DF0ED6769 foreign key (poll_id) references poll
alter table project add constraint FKED904B19514C1986 foreign key (lead_uid) references userAccount
alter table project add constraint FKED904B194075E3FD foreign key (users_uid) references account
alter table project_geoPoint add constraint FK2599132584536452 foreign key (cat_id_project) references project
alter table project_geoPoint add constraint FK2599132535313189 foreign key (cat_id_loc) references geoPoint
alter table project_group add constraint FKC7652DD945895AFF foreign key (sec_id_group) references groups
alter table project_group add constraint FKC7652DD984536452 foreign key (cat_id_project) references project
alter table project_locations add constraint FK242951B884536452 foreign key (cat_id_project) references project
alter table project_locations add constraint FK242951B835313189 foreign key (cat_id_loc) references geoPoint
alter table question_category_questions add constraint FK2FFE1845B10E79BE foreign key (question_category_qCategory) references question_category
alter table question_category_questions add constraint FK2FFE18457A068CB foreign key (questionLibrary_qid) references questions
alter table question_collection add constraint FKB4097C972B2A6AB4 foreign key (uid) references account
alter table question_dependence_survey add constraint FKBB424D49793D9E77 foreign key (sid) references surveys
alter table question_relations add constraint FK217954DE893521DA foreign key (id_q_colection) references question_collection
alter table question_relations add constraint FK217954DE8A76A0BD foreign key (question_id) references questions
alter table questions add constraint FK95C5414D2B2A6AB4 foreign key (uid) references account
alter table questions add constraint FK95C5414D39E97991 foreign key (section_ssid) references survey_section
alter table questions_answers add constraint FK539703837E6C7BBC foreign key (id_question_answer) references questions
alter table questions_dependencies add constraint FK92E86ADBDDD118B5 foreign key (q_answer_id) references questions_answers
alter table social_account add constraint FK50078B5B5ECE45A2 foreign key (account_uid) references account
alter table social_account add constraint FK50078B5BF2F411F2 foreign key (userOwner_uid) references userAccount
alter table survey_folder add constraint FK7EF958F32B2A6AB4 foreign key (uid) references account
alter table survey_folder add constraint FK7EF958F36EF241E9 foreign key (createdBy_uid) references userAccount
alter table survey_group_format add constraint FKB4DF867C310E993C foreign key (sg_id) references survey_group
alter table survey_group_format add constraint FKB4DF867CB1A6912C foreign key (id_sid_format) references survey_format
alter table survey_group_project add constraint FKFD028D3484536452 foreign key (cat_id_project) references project
alter table survey_group_project add constraint FKFD028D34B75F3482 foreign key (id_sid_format) references survey_group
alter table survey_pagination add constraint FKBEC9A99F793D9E77 foreign key (sid) references surveys
alter table survey_pagination add constraint FKBEC9A99F1359B877 foreign key (ssid) references survey_section
alter table survey_result add constraint FK92EA04A246BF7A1C foreign key (question_qid) references questions
alter table survey_result add constraint FK92EA04A2496009B4 foreign key (answer_q_answer_id) references questions_answers
alter table survey_result add constraint FK92EA04A251153812 foreign key (survey_sid) references surveys
alter table survey_section add constraint FKFE5AD30051153812 foreign key (survey_sid) references surveys
alter table surveys add constraint FK91914459A3C7A06A foreign key (survey_folder) references survey_folder
alter table surveys add constraint FK9191445973FF13B foreign key (project_project_id) references project
alter table surveys add constraint FK91914459A64FB606 foreign key (editor) references userAccount
alter table surveys add constraint FK919144598E4A448B foreign key (owner_id) references account
alter table tweetPoll add constraint FKA65B1D02B2A6AB4 foreign key (uid) references account
alter table tweetPoll add constraint FKA65B1D0D9BA7E54 foreign key (tweetPollFolderId) references tweetPoll_Folder
alter table tweetPoll add constraint FKA65B1D050FE71F5 foreign key (qid) references questions
alter table tweetPoll add constraint FKA65B1D0A64FB606 foreign key (editor) references userAccount
alter table tweetPoll_Folder add constraint FKA027A9DD2B2A6AB4 foreign key (uid) references account
alter table tweetPoll_Folder add constraint FKA027A9DD6EF241E9 foreign key (createdBy_uid) references userAccount
alter table tweetPoll_save_published_status add constraint FKD499A4B65239D117 foreign key (socialAccount_social_account_id) references social_account
alter table tweetPoll_save_published_status add constraint FKD499A4B663976E9 foreign key (poll_poll_id) references poll
alter table tweetPoll_save_published_status add constraint FKD499A4B651153812 foreign key (survey_sid) references surveys
alter table tweetPoll_save_published_status add constraint FKD499A4B6953C854B foreign key (tweetPoll_tweet_poll_id) references tweetPoll
alter table tweetpoll_hashtags add constraint FKF8C717D6286705D7 foreign key (tweetpoll_id) references tweetPoll
alter table tweetpoll_hashtags add constraint FKF8C717D6DA98FFE1 foreign key (hastag_id) references hash_tags
alter table tweetpoll_result add constraint FK8749C18CB9D39F98 foreign key (tweetpoll_switch_id) references tweetpoll_switch
alter table tweetpoll_switch add constraint FK89F7B0A3550299A foreign key (tweet_poll_id) references tweetPoll
alter table tweetpoll_switch add constraint FK89F7B0A3DDD118B5 foreign key (q_answer_id) references questions_answers
alter table userAccount add constraint FKA7D56BE25ECE45A2 foreign key (account_uid) references account
alter table userAccount add constraint FKA7D56BE2B8EB1450 foreign key (groupId) references groups
alter table userAccount_followers add constraint FK7F1957F8F44558E9 foreign key (uid) references userAccount
alter table userAccount_followers add constraint FK7F1957F8E53FBC6 foreign key (uid_follower) references userAccount
alter table userAccount_permission add constraint FKBE01CE4C43ADB63D foreign key (sec_id_permission) references permission
alter table userAccount_permission add constraint FKBE01CE4C5F77A117 foreign key (sec_id_secondary) references userAccount
alter table userAccount_project add constraint FKFBC45BBC84536452 foreign key (cat_id_project) references project
alter table userAccount_project add constraint FKFBC45BBC5F77A117 foreign key (sec_id_secondary) references userAccount
alter table survey_temporal_result add constraint FK7867CF546BF7A1C foreign key (question_qid) references questions
alter table survey_temporal_result add constraint FK7867CF5496009B4 foreign key (answer_q_answer_id) references questions_answers
alter table survey_temporal_result add constraint FK7867CF551153812 foreign key (survey_sid) references surveys
alter table question_preferences add constraint FKD540D01F46BF7A1C foreign key (question_qid) references questions
alter table hash_tags_ranking add constraint FK71DECDA119AA125 foreign key (hashTag_hash_tag_id) references hash_tags