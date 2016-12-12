package com.cb.qiangqiang2.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cb on 2016/12/10.
 */

public class EmojiUtils {
    private static EmojiUtils emojiUtils;

    private static LinkedHashMap<String, Integer> allDZHashMap = new LinkedHashMap();
    private static LinkedHashMap<String, Integer> allMHashMap = new LinkedHashMap();
    private static LinkedHashMap<String, Integer> allQQHashMap = new LinkedHashMap();
    private static LinkedHashMap<String, Integer> allHashMap = new LinkedHashMap();

    private ResourceUtils resource;

    public static EmojiUtils getInstance(Context context) {
        if (emojiUtils == null) {
            synchronized (EmojiUtils.class) {
                emojiUtils = new EmojiUtils(context);
            }
        }
        return emojiUtils;
    }

    public EmojiUtils(Context context) {
        initRes(context);
    }

    private void initRes(Context context) {
        resource = ResourceUtils.getInstance(context);
        allDZHashMap.clear();
        allDZHashMap.put(resource.getString("mc_forum_face_tear"), resource.getDrawableId("mc_forum_face198"));
        allDZHashMap.put(resource.getString("mc_forum_face_haha"), resource.getDrawableId("mc_forum_face234"));
        allDZHashMap.put(resource.getString("mc_forum_face_crazy"), Integer.valueOf(resource.getDrawableId("mc_forum_face239")));
        allDZHashMap.put(resource.getString("mc_forum_face_xixi"), Integer.valueOf(resource.getDrawableId("mc_forum_face233")));
        allDZHashMap.put(resource.getString("mc_forum_face_titter"), Integer.valueOf(resource.getDrawableId("mc_forum_face247")));
        allDZHashMap.put(resource.getString("mc_forum_face_clap"), Integer.valueOf(resource.getDrawableId("mc_forum_face255")));
        allDZHashMap.put(resource.getString("mc_forum_face_fury"), Integer.valueOf(resource.getDrawableId("mc_forum_face242")));
        allDZHashMap.put(resource.getString("mc_forum_face_heart"), Integer.valueOf(resource.getDrawableId("mc_forum_face279")));
        allDZHashMap.put(resource.getString("mc_forum_face_heart_broken"), Integer.valueOf(resource.getDrawableId("mc_forum_face280")));
        allDZHashMap.put(resource.getString("mc_forum_face_sick"), Integer.valueOf(resource.getDrawableId("mc_forum_face258")));
        allDZHashMap.put(resource.getString("mc_forum_face_love"), Integer.valueOf(resource.getDrawableId("mc_forum_face17")));
        allDZHashMap.put(resource.getString("mc_forum_face_shy"), Integer.valueOf(resource.getDrawableId("mc_forum_face201")));
        allDZHashMap.put(resource.getString("mc_forum_face_poor"), Integer.valueOf(resource.getDrawableId("mc_forum_face268")));
        allDZHashMap.put(resource.getString("mc_forum_face_greedy"), Integer.valueOf(resource.getDrawableId("mc_forum_face238")));
        allDZHashMap.put(resource.getString("mc_forum_face_faint"), Integer.valueOf(resource.getDrawableId("mc_forum_face7")));
        allDZHashMap.put(resource.getString("mc_forum_face_hana"), Integer.valueOf(resource.getDrawableId("mc_forum_face254")));
        allDZHashMap.put(resource.getString("mc_forum_face_too_happy"), Integer.valueOf(resource.getDrawableId("mc_forum_face261")));
        allDZHashMap.put(resource.getString("mc_forum_face_kiss"), Integer.valueOf(resource.getDrawableId("mc_forum_face259")));
        allDZHashMap.put(resource.getString("mc_forum_face_disdain"), Integer.valueOf(resource.getDrawableId("mc_forum_face252")));
        allDZHashMap.put(resource.getString("mc_forum_face_hehe"), Integer.valueOf(resource.getDrawableId("mc_forum_face25")));
        allDZHashMap.put(resource.getString("mc_forum_face_booger"), Integer.valueOf(resource.getDrawableId("mc_forum_face253")));
        allDZHashMap.put(resource.getString("mc_forum_face_decline"), Integer.valueOf(resource.getDrawableId("mc_forum_face6")));
        allDZHashMap.put(resource.getString("mc_forum_face_rabbit"), Integer.valueOf(resource.getDrawableId("mc_forum_rabbit_thumb")));
        allDZHashMap.put(resource.getString("mc_forum_face_good"), Integer.valueOf(resource.getDrawableId("mc_forum_face100")));
        allDZHashMap.put(resource.getString("mc_forum_face_come"), Integer.valueOf(resource.getDrawableId("mc_forum_face277")));
        allDZHashMap.put(resource.getString("mc_forum_face_force"), Integer.valueOf(resource.getDrawableId("mc_forum_face219")));
        allDZHashMap.put(resource.getString("mc_forum_face_circusee"), Integer.valueOf(resource.getDrawableId("mc_forum_face218")));
        allDZHashMap.put(resource.getString("mc_forum_face_sprout"), Integer.valueOf(resource.getDrawableId("mc_forum_kawayi_thumb")));
        allDZHashMap.put(resource.getString("mc_forum_face_flowers"), Integer.valueOf(resource.getDrawableId("mc_forum_face120")));
        allDZHashMap.put(resource.getString("mc_forum_face_confused"), Integer.valueOf(resource.getDrawableId("mc_forum_face121")));
        allQQHashMap.clear();
        allQQHashMap.put(resource.getString("mc_forum_face_cruel"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_01")));
        allQQHashMap.put(resource.getString("mc_forum_face_solid_food"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_02")));
        allQQHashMap.put(resource.getString("mc_forum_face_mouth"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_03")));
        allQQHashMap.put(resource.getString("mc_forum_face_asleep"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_04")));
        allQQHashMap.put(resource.getString("mc_forum_face_sweat"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_05")));
        allQQHashMap.put(resource.getString("mc_forum_face_sleep"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_06")));
        allQQHashMap.put(resource.getString("mc_forum_face_surprise"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_11")));
        allQQHashMap.put(resource.getString("mc_forum_face_white_eye"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_12")));
        allQQHashMap.put(resource.getString("mc_forum_face_white_query"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_13")));
        allQQHashMap.put(resource.getString("mc_forum_face_cattiness"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_14")));
        allQQHashMap.put(resource.getString("mc_forum_face_left_humph"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_15")));
        allQQHashMap.put(resource.getString("mc_forum_face_right_humph"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_16")));
        allQQHashMap.put(resource.getString("mc_forum_face_knock"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_21")));
        allQQHashMap.put(resource.getString("mc_forum_face_chagrin"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_22")));
        allQQHashMap.put(resource.getString("mc_forum_face_hush"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_23")));
        allQQHashMap.put(resource.getString("mc_forum_face_spit"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_24")));
        allQQHashMap.put(resource.getString("mc_forum_face_grimace"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_25")));
        allQQHashMap.put(resource.getString("mc_forum_face_bye"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_26")));
        allQQHashMap.put(resource.getString("mc_forum_face_cry"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_31")));
        allQQHashMap.put(resource.getString("mc_forum_face_arrogance"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_32")));
        allQQHashMap.put(resource.getString("mc_forum_face_moon"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_33")));
        allQQHashMap.put(resource.getString("mc_forum_face_sun"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_34")));
        allQQHashMap.put(resource.getString("mc_forum_face_ye"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_35")));
        allQQHashMap.put(resource.getString("mc_forum_face_handshake"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_36")));
        allQQHashMap.put(resource.getString("mc_forum_face_ok"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_41")));
        allQQHashMap.put(resource.getString("mc_forum_face_coffee"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_42")));
        allQQHashMap.put(resource.getString("mc_forum_face_meal"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_43")));
        allQQHashMap.put(resource.getString("mc_forum_face_gift"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_44")));
        allQQHashMap.put(resource.getString("mc_forum_face_pig_head"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_45")));
        allQQHashMap.put(resource.getString("mc_forum_face_hug"), Integer.valueOf(resource.getDrawableId("mc_forum_qq_46")));
        allMHashMap.clear();
        allMHashMap.put(resource.getString("mc_forum_face_praise"), Integer.valueOf(resource.getDrawableId("mc_forum_m_01")));
        allMHashMap.put(resource.getString("mc_forum_face_hold"), Integer.valueOf(resource.getDrawableId("mc_forum_m_02")));
        allMHashMap.put(resource.getString("mc_forum_face_sleipnir"), Integer.valueOf(resource.getDrawableId("mc_forum_m_03")));
        allMHashMap.put(resource.getString("mc_forum_face_cheating"), Integer.valueOf(resource.getDrawableId("mc_forum_m_04")));
        allMHashMap.put(resource.getString("mc_forum_face_have"), Integer.valueOf(resource.getDrawableId("mc_forum_m_05")));
        allMHashMap.put(resource.getString("mc_forum_face_thanks"), Integer.valueOf(resource.getDrawableId("mc_forum_m_06")));
        allMHashMap.put(resource.getString("mc_forum_face_demon"), Integer.valueOf(resource.getDrawableId("mc_forum_m_11")));
        allMHashMap.put(resource.getString("mc_forum_face_saucer_man"), Integer.valueOf(resource.getDrawableId("mc_forum_m_12")));
        allMHashMap.put(resource.getString("mc_forum_face_blue_heart"), Integer.valueOf(resource.getDrawableId("mc_forum_m_13")));
        allMHashMap.put(resource.getString("mc_forum_face_purple_heart"), Integer.valueOf(resource.getDrawableId("mc_forum_m_14")));
        allMHashMap.put(resource.getString("mc_forum_face_yellow_heart"), Integer.valueOf(resource.getDrawableId("mc_forum_m_15")));
        allMHashMap.put(resource.getString("mc_forum_face_green_heart"), Integer.valueOf(resource.getDrawableId("mc_forum_m_16")));
        allMHashMap.put(resource.getString("mc_forum_face_music"), Integer.valueOf(resource.getDrawableId("mc_forum_m_21")));
        allMHashMap.put(resource.getString("mc_forum_face_flicker"), Integer.valueOf(resource.getDrawableId("mc_forum_m_22")));
        allMHashMap.put(resource.getString("mc_forum_face_star"), Integer.valueOf(resource.getDrawableId("mc_forum_m_23")));
        allMHashMap.put(resource.getString("mc_forum_face_drip"), Integer.valueOf(resource.getDrawableId("mc_forum_m_24")));
        allMHashMap.put(resource.getString("mc_forum_face_flame"), Integer.valueOf(resource.getDrawableId("mc_forum_m_25")));
        allMHashMap.put(resource.getString("mc_forum_face_shit"), Integer.valueOf(resource.getDrawableId("mc_forum_m_26")));
        allMHashMap.put(resource.getString("mc_forum_face_foot"), Integer.valueOf(resource.getDrawableId("mc_forum_m_31")));
        allMHashMap.put(resource.getString("mc_forum_face_rain"), Integer.valueOf(resource.getDrawableId("mc_forum_m_32")));
        allMHashMap.put(resource.getString("mc_forum_face_cloudy"), Integer.valueOf(resource.getDrawableId("mc_forum_m_33")));
        allMHashMap.put(resource.getString("mc_forum_face_lightning"), Integer.valueOf(resource.getDrawableId("mc_forum_m_34")));
        allMHashMap.put(resource.getString("mc_forum_face_snowflake"), Integer.valueOf(resource.getDrawableId("mc_forum_m_35")));
        allMHashMap.put(resource.getString("mc_forum_face_cyclone"), Integer.valueOf(resource.getDrawableId("mc_forum_m_36")));
        allMHashMap.put(resource.getString("mc_forum_face_bag"), Integer.valueOf(resource.getDrawableId("mc_forum_m_41")));
        allMHashMap.put(resource.getString("mc_forum_face_house"), Integer.valueOf(resource.getDrawableId("mc_forum_m_42")));
        allMHashMap.put(resource.getString("mc_forum_face_fireworks"), Integer.valueOf(resource.getDrawableId("mc_forum_m_43")));

        allHashMap.clear();
        allHashMap.putAll(allDZHashMap);
        allHashMap.putAll(allQQHashMap);
        allHashMap.putAll(allMHashMap);
    }

    public LinkedHashMap<String, Integer> getAllEmojiMap() {
        return allHashMap;
    }

    public SpannableString parseText(String text, TextView textView, Context context) {
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile("\\[[^\\]]+\\]");
        LinkedHashMap<String, Integer> map = getAllEmojiMap();
        float emojiSize = textView.getTextSize();
        Matcher matcher = pattern.matcher(text);
        int imageSpanTop = 0;
        while (matcher.find()) {
            String group = matcher.group();
            ImageSpan imageSpan;
            if (group.contains("mobcent_phiz=")) {
                //从网络加载图片
                String url = group.substring(1, group.length() -1).replace("mobcent_phiz=", "");
                Drawable drawableFromNet = new URLImageParser(textView, context, (int) (emojiSize * 1.2)).getDrawable(url);
                imageSpan = new ImageSpan(drawableFromNet, ImageSpan.ALIGN_BASELINE);
                spannableString.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                //匹配本地emoji图片
                if (map.get(group) != null) {
                    Drawable drawable = context.getResources().getDrawable(map.get(group));
                    //获取图片宽高比
                    float ratio = drawable.getIntrinsicWidth() * 1.0f / drawable.getIntrinsicHeight();
                    drawable.setBounds(0, imageSpanTop, (int) (emojiSize * ratio), (int)(emojiSize + imageSpanTop));
                    imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    spannableString.setSpan(imageSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spannableString;
    }

}
