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
package org.encuestame.core.util;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 * Commons utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 10, 2011
 */
public class EnMeUtils {

    /** Front End Service Log. **/
    private static Logger log = Logger.getLogger(EnMeUtils.class);


    private static final int BASE = 2;
    private static final int MIN_SIZE = 12;

    /**
     * Calculate percent.
     * @param total
     * @param value
     * @return
     */
    public static String calculatePercent(double total, double value){
        double myAprValue = (value / total);
        if(myAprValue != 0){
            final DecimalFormat percent = new DecimalFormat("#0.00%");
            return percent.format(myAprValue);
        } else {
            return "0.00%";
        }
    }

    /**
     * Description.
     * <p>
     * The frequency is calculated based on the hits (visits) that receives the hashtag
     * and use has on tweetPolls, survey, etc.. Frequency is the use of hashtag.
     * </p>
     * @param frecuency Number of times the label has been used in polls, survey or tweetPolls
     * @param frecMax : Maximum number of frequency.
     * @param frecMin : Minimum number of frecuency.
     * @return
     */
    public static long calculateSizeTag(long frecuency, long  frecMax, long frecMin) {
        float frec = Float.valueOf(frecuency);
        float maxiFrec = Float.valueOf(frecMax);
        float miniFrec = Float.valueOf(frecMin);
        double minValue = Double.valueOf(EnMeUtils.MIN_SIZE);
        final float frecDiff = frecMax - miniFrec;
        double perRelative = ((frec - miniFrec) / (frecDiff == 0 ? 1 : frecDiff)) * maxiFrec;
        double perLog;
        if (perRelative == 0) {
            perLog = minValue;
        } else {
            perLog = (Math.log(perRelative) / Math.log(EnMeUtils.BASE))
             + minValue;
        }
        log.debug("Size tag Value ---------------> " + Math.round(perLog) );
        return Math.round(perLog);
    }
}