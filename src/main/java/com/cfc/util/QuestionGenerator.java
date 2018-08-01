package com.cfc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author fangchen
 * date  2018/7/30 上午10:56
 */
public class QuestionGenerator {
    public static final int CET4 = 1;
    public static final int CET6 = 2;
    public static final int KAOYAN = 3;
    public static final int OTHER = 4;
    public static final int IELTS = 5;
    public static final int TOEFL = 6;
    public static final int TEM4 = 7;
    public static final int TEM8 = 8;
    public static final int SKILLS = 9;
    public static final int GONGKAO = 10;

//     polytechnic = 理工;
//     letter = 文学;
//     finance = 金融;
//     manage = 管理;
//     economy = 经济;
//     art = 艺术;
//     other = 其他;

//    TYPE_TAG_MAP.put(CET4, "四级");
//    TYPE_TAG_MAP.put(CET6, "六级");
//    TYPE_TAG_MAP.put(KAOYAN, "考研");
//    TYPE_TAG_MAP.put(OTHER, "其他");
//    TYPE_TAG_MAP.put(IELTS, "雅思");
//    TYPE_TAG_MAP.put(TOEFL, "托福");
//    TYPE_TAG_MAP.put(TEM4, "专四");
//    TYPE_TAG_MAP.put(TEM8, "专八");
//    TYPE_TAG_MAP.put(SKILLS, "实用英语");
//    TYPE_TAG_MAP.put(GONGKAO, "公考");

    public static void main(String[] args) {
        Map<Integer, Object> quest = new LinkedHashMap<>();
        Map<String, Object> cet4 = new LinkedHashMap<>();
        Map<String, Object> cq1 = new LinkedHashMap<>();
        Map<String, Object> cq2 = new LinkedHashMap<>();
        Map<String, Object> answer1 = new LinkedHashMap<>();
        answer1.put("0", "初次参加");
        answer1.put("1", "1次");
        answer1.put("2", "2次");
        answer1.put("3", "3次及以上");
        Map<String, Object> answer2 = new LinkedHashMap<>();
        answer2.put("349", "350分以下");
        answer2.put("350-424", "350～425");
        answer2.put("425-549", "425（含）～ 550");
        answer2.put("550", "550（含）以上 ");

        cq1.put("之前参加过四级考试吗? ", answer1);
        cq2.put("在此项考试中所取得最好成绩是? ", answer2);
        cet4.put("q1",cq1);
        cet4.put("q2",cq2);
        quest.put(CET4, cet4);

        Map<String, Object> cet6 = new LinkedHashMap<>();
        Map<String, Object> ccq1 = new LinkedHashMap<>();
        Map<String, Object> ccq2 = new LinkedHashMap<>();
        Map<String, Object> answer3 = new LinkedHashMap<>();
        answer3.put("349", "350分以下");
        answer3.put("350-424", "350～425");
        answer3.put("425-519", "425（含）～ 520");
        answer3.put("520", "520（含）以上 ");
        ccq1.put("之前参加过六级考试吗? ", answer1);
        ccq2.put("在此项考试中所取得最好成绩是? ", answer3);
        cet6.put("q1", ccq1);
        cet6.put("q2", ccq2);
        quest.put(CET6, cet6);

        Map<String, Object> kaoyan = new LinkedHashMap<>();
        Map<String, Object> kq1 = new LinkedHashMap<>();
        Map<String, Object> kq2 = new LinkedHashMap<>();
        Map<String, Object> kq3 = new LinkedHashMap<>();
        Map<String, Object> kq4 = new LinkedHashMap<>();
        Map<String, Object> answer4 = new LinkedHashMap<>();
        answer4.put("985", "985");
        answer4.put("211", "211");
        answer4.put("!211", "非211");
        answer4.put("other", "其他");
        Map<String, Object> answer5 = new LinkedHashMap<>();
        answer5.put("polytechnic", "理工");
        answer5.put("letter", "文学");
        answer5.put("finance", "金融");
        answer5.put("manage", "管理");
        answer5.put("economy", "经济");
        answer5.put("art", "艺术");
        answer5.put("other", "其他");
        kq1.put("你准备报考的院校属于? ", answer4);
        kq2.put("你准备报考的专业属于? ", answer5);
        kq3.put("此前有过考研经历吗? ", answer1);
        // TODO: 2018/7/30 待补充
        kq4.put("你在这项考试中最好成绩是?", "");
        kaoyan.put("q1", kq1);
        kaoyan.put("q2", kq2);
        kaoyan.put("q3", kq3);
        kaoyan.put("q4", kq4);
        quest.put(KAOYAN, kaoyan);

        Map<String, Object> ielts = new LinkedHashMap<>();
        Map<String, Object> iq1 = new LinkedHashMap<>();
        Map<String, Object> iq2 = new LinkedHashMap<>();
        Map<String, Object> iq3 = new LinkedHashMap<>();
        Map<String, Object> iq4 = new LinkedHashMap<>();
        Map<String, Object> answer6 = new LinkedHashMap<>();
        answer6.put("9-10", "9～10");
        answer6.put("7-9", "7～9");
        answer6.put("5-6.5", "5～6.5");
        answer6.put("4", "5分以下");
        Map<String, Object> answer8 = new LinkedHashMap<>();
        answer8.put("usa", "美国");
        answer8.put("uk", "英国");
        answer8.put("au", "澳大利亚");
        answer8.put("ca", "加拿大");
        answer8.put("other", "其他");
        iq1.put("你准备报考的院校属于? ", answer8);
        iq2.put("你准备报考的专业属于? ", answer5);
        iq3.put("此前有过备考雅思的经验吗? ", answer1);
        iq4.put("你在这项考试中最好成绩是?", answer6);
        ielts.put("q1", iq1);
        ielts.put("q2", iq2);
        ielts.put("q3", iq3);
        ielts.put("q4", iq4);
        quest.put(IELTS, ielts);

        Map<String, Object> toefl = new LinkedHashMap<>();
        Map<String, Object> tq1 = new LinkedHashMap<>();
        Map<String, Object> tq2 = new LinkedHashMap<>();
        Map<String, Object> tq3 = new LinkedHashMap<>();
        Map<String, Object> tq4 = new LinkedHashMap<>();
        Map<String, Object> answer7 = new LinkedHashMap<>();
        answer7.put("100-120", "100~120");
        answer7.put("80-99", "80~99");
        answer7.put("79", "80分以下");
        answer7.put("other", "其他");
        tq1.put("你准备报考的院校属于? ", answer8);
        tq2.put("你准备报考的专业属于? ", answer5);
        tq3.put("此前有过备考雅思的经验吗? ", answer1);
        tq4.put("你在这项考试中最好成绩是?", answer7);
        toefl.put("q1", tq1);
        toefl.put("q2", tq2);
        toefl.put("q3", tq3);
        toefl.put("q4", tq4);
        quest.put(TOEFL, toefl);

        Map<String, Object> gongkao = new LinkedHashMap<>();
        Map<String, Object> gq1 = new LinkedHashMap<>();
        Map<String, Object> gq2 = new LinkedHashMap<>();
        gq1.put("你准备报考的院校属于? ", answer1);
        gq2.put("你在这项考试中的最好成绩是? ", "");
        gongkao.put("q1", gq1);
        gongkao.put("q2", gq2);
        quest.put(GONGKAO, gongkao);

        System.out.println(JSON.toJSONString(quest,SerializerFeature.BrowserCompatible
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.DisableCircularReferenceDetect));

    }

}
