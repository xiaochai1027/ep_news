package com.cfc.util.exception;

import java.util.HashMap;
import java.util.Map;

public class Errors {
    public static final int ERROR_UNKNOWN = 30000;

    public static final int ERROR_PARAMS = 30001;

    public static final int ERROR_NO_SUCH_PATTERN = 40000;

    /**
     * passport
     **/
    public static final int ERROR_PASSWORD_NOT_EQUAL = 30002;

    public static final int ERROR_LOGIN_NAME_OR_PASSWORD_ERROR = 30003;

    public static final int ERROR_TOKEN_FORMAT = 30004;

    public static final int ERROR_TOKEN_EXPIRED = 30005;

    public static final int ERROR_LOGINNAME_EXISTS = 30006;

    public static final int ERROR_VERIFYCODE_INVALIDATE = 30007;

    public static final int ERROR_SMS_HAS_LIMITED = 30008;

    public static final int ERROR_LOGINNAME_NOT_EXISTS = 30009;

    public static final int ERROR_LOGINNAME_INVALIDATE = 30010;

    public static final int ERROR_PIC_CODE_FAIL = 30011;

    public static final int ERROR_PIC_CODE_INVALIDATE = 30012;

    public static final int ERROR_NICKNAME_EXISTS = 30013;

    public static final int ERROR_SIGN = 30014;

    public static final int ERROR_RESET_PHONE = 30015; // 重置手机号的时候发生了错误

    public static final int ERROR_RESET_PHONE_NOT_SAME_ACCOUNT = 30016; // 重置的验证码和登录的用户不一致

    public static final int ERROR_TOKEN_KICK_OFF = 30017; // 被踢掉了

    public static final int ERROR_WEIXIN_ALREADY_BIND = 30018; // 微信已经绑定提示错误

    public static final int ERROR_WEIXIN_ALREADY_PHONE_BIND = 30019; // 手机号已经绑定过

    /**
     * order
     **/
    public static final int ERROR_COURSE_NOT_ON_SALE = 40001;

    public static final int ERROR_COURSE_HAS_STOPPING_SELLING = 40002;

    public static final int ERROR_COURSE_HAS_SOLD_OUT = 40003;

    public static final int ERROR_ORDER_PAID_FAIL = 40004;

    public static final int ERROR_COURSE_HAS_PAID = 40005;

    public static final int ERROR_HAS_NO_ADDRESS = 40006;

    public static final int ERROR_COURSE_HAS_NOT_PAID = 40007;

    public static final int ERROR_OPEN_ID_NOT_EXISTS = 40008;

    public static final int ERROR_ACTIVE_CODE_NOT_EXISTS = 40009;

    public static final int ERROR_ACTIVE_CODE_HAS_USED = 40010;

    public static final int ERROR_ACTIVE_CODE_ORDER_NOT_EXISTS = 40011;

    public static final int ERROR_NO_SUCH_ORDER = 40012;

    public static final int ERROR_NO_SUCH_JDPAID_FAIL = 40013;

    public static final int ERROR_ORDER_REFUND_FAIL = 40014;

    public static final int ERROR_ORDER_NOT_FOUND = 40015;

    public static final int ERROR_ORDER_IAP_VERIFIED_FAILURE = 40016;

    public static final int ERROR_ORDER_IAP_LIMIT = 40017;

    public static final int ERROR_ORDER_IAP_CANNOT_GET_PRODUCT_INFO = 40018;

    /**
     * course
     **/
    public static final int ERROR_NO_SUCH_COURSE = 40101;

    public static final int ERROR_HAS_COMMENTED = 40102;

    public static final int ERROR_NOT_RESERVE_COURSE = 40103;

    public static final int ERROR_COURSE_HAS_STOPPING_RESERVING = 40104;

    public static final int ERROR_LESSON_HAS_COMMENT = 40105;

    public static final int ERROR_COURSE_NOT_START_SALE = 40106;

    public static final int ERROR_COURSE_NOT_BUY = 40107;

    public static final int ERROR_COURSE_SAME_COURSE = 40108;

    public static final int ERROR_COURSE_NOT_REG_FREE = 40109;

    public static final int ERROR_LESSON_NOT_REG_FREE = 40110;

    public static final int ERROR_COURSE_ALREADY_ASSOCIATED = 40111;

    public static final int ERROR_COURSE_NOT_NEED_DELAY = 40112;

    public static final int ERROR_CLASS_NOT_HAS_COURSE = 40113;

    public static final int ERROR_VIP_COURSE_ILLEGAL_ACCESS = 40114;

    public static final int ERROR_COURSE_ILLEGAL_ACCESS_TO_VIP = 40115;

    public static final int ERROR_COURSE_TYPE_TAG_EXISTS = 40116;

    /**
     * 课程介绍错误
     */
    public static final int ERROR_COURSE_INTRO_DETAIL = 40117;

    /**
     * exam
     **/
    public static final int ERROR_NO_SUCH_EXAM = 40201;

    public static final int ERROR_EXAM_HAS_STARTED = 40203;

    public static final int ERROR_EXAM_HAS_ENDED = 40204;

    public static final int ERROR_NO_SUCH_SUBJECT = 40205;

    public static final int ERROR_EXAM_NOT_STARTED = 40206;

    public static final int ERROR_EXAM_NOT_ENDED = 40207;

    public static final int ERROR_EXAM_NOT_MATCH = 40208;

    /**
     * reservation
     **/
    public static final int ERROR_MY_COURSE_RESERVE_EXISTS = 40301;

    /**
     * card
     **/
    public static final int ERROR_CARD_EXISTS = 40401;

    public static final int ERROR_CARD_GENERATE_FAIL = 40402;

    public static final int ERROR_CARD_GENERATE_FAIL_NO_EXISTS = 40403;

    public static final int ERROR_CARD_GENERATE_FAIL_COURSE_TAG_IS_NULL = 40404;

    public static final int ERROR_CARD_MODIFY_FAIL = 40405;

    public static final int ERROR_CARD_MODIFY_FAIL_CARDNO_IS_NULL = 40406;

    /**
     * coupon
     **/
    public static final int ERROR_COUPON_DUPLICATE_GEN = 40501;

    public static final int ERROR_COUPON_NO_SUCH = 40502;

    public static final int ERROR_COUPON_HAS_BEEN_USED = 40503;

    public static final int ERROR_COUPON_EXPIRED = 40504;

    public static final int ERROR_COUPON_UNAVAILABLE = 40505;

    public static final int ERROR_COUPON_ACT_NOT_FOUND = 40506;

    public static final int ERROR_COUPON_ACT_ALREADY_OVER = 40507;

    public static final int ERROR_COUPON_ACT_NOT_START = 40508;

    /**
     * word game
     **/
    public static final int ERROR_WORDARY_GENERATE_FAIL = 40601;
    public static final int ERROR_WORDARY_MATCH_FAIL = 40602;
    public static final int ERROR_WORDARY_MATCH_HAS_QUIT = 40603;
    public static final int ERROR_WORDARY_ROOM_NOT_EXISTS = 40604;
    public static final int ERROR_WORDARY_ROOM_ACCESS_INVALIDATE = 40605;
    public static final int ERROR_WORDARY_ANSWER_FAIL = 40606;
    public static final int ERROR_WORDARY_GAME_OVER = 40607;
    public static final int ERROR_WORDARY_ROOM_QUESTION_EMPTY = 40608;
    public static final int ERROR_WORDARY_GENERATE_ROOM_FAIL = 40609;

    /**
     * live
     */
    public static final int ERROR_NO_BEST_TRANSLATOR = 40701;
    public static final int ERROR_SCAN_RESULT_FORMAT = 40702;


    /**
     * classcard
     **/
    public static final int ERROR_CLASSCARD_EXISTS = 40801;

    public static final int ERROR_CLASSCARD_INSERT_FAIL = 40802;

    public static final int ERROR_CLASSCARD_GENERATE_CARDNO_FAIL = 40803;

    public static final int ERROR_CLASSCARD_INSERT_FAIL_ORDER_IS_NULL = 40804;

    public static final int ERROR_CLASSCARD_MODIFY_FAIL = 40805;

    public static final int ERROR_CLASSCARD_MODIFY_FAIL_ORDER_IS_NULL = 40806;

    public static final int ERROR_CLASSCARD_CLASS_NOT_NEED = 40807;

    /**
     * question
     **/

    public static final int ERROR_QUESTION_INSERT_FAIL = 40901;

    public static final int ERROR_QUESTION_OVER_ADD_TIME = 40902;

    public static final int ERROR_QUESTION_WAITING_NEW_ANSWER = 40903;

    public static final int ERROR_QUESTION_COUNT_OVER_LIMIT = 40904;


    /***
     * act
     */
    public static final int ERROR_ACT_ALREADY_HELP = 401001;  //  已经帮他领取过
    public static final int ERROR_ACT_ALREADY_HELP_OTHER = 401002;  //  已经帮别人领取过
    public static final int ERROR_ACT_ALREADY_DONE = 401003; // 他已经领取成功
    public static final int ERROR_ACT_MIS_FISSION_NAME = 401004; // 找不到活动名称
    public static final int ERROR_ACT_MIS_UID = 401005; // 无正确的uid
    public static final int ERROR_ACT_ALREADY_FETCH = 401006; // 已经领取了该活动

    public static final int ERROR_ACT_ERROR_IN_TRANSACTION = 401007; // 活动赠课发生错误
    public static final int ERROR_ACT_ERROR_NEW_USER = 401008; // 仅限新用户
    /***
     * task
     */
    public static final int ERROR_TASK_REPLY_OVER = 401101;


    protected static Map<Integer, String> errorMap;

    public static final int ERROR_PAY_TRADE_NUM_NOT_MATCH = 401201; // 流水号不匹配
    public static final int ERROR_PAY_IN_TRANSACTION = 401202; // 充值数据提交错误
    public static final int ERROR_PAY_REMAIN_NOT_ENOUGH = 401203; // 余额不足
    public static final int ERROR_PAY_REPEAT_TRANSACTION_ID = 401204; // 苹果的流水号重复校验


    /**
     * app 版本过低
     */
    public static final int ERROR_APP_VERSION_LOW = 401300;


    /**
     *  admin 数据已存在
     */

    public static final int ERROR_ADMIN_DATA_EXIST = 401400;




    static {
        errorMap = new HashMap<>();
        errorMap.put(ERROR_UNKNOWN, "internal unknown error");
        errorMap.put(ERROR_PARAMS, "params error");
        errorMap.put(ERROR_NO_SUCH_PATTERN, "no such pattern");

        /* passport **/
        errorMap.put(ERROR_PASSWORD_NOT_EQUAL, "password is not equal");
        errorMap.put(ERROR_LOGIN_NAME_OR_PASSWORD_ERROR, "loginName or password error");
        errorMap.put(ERROR_TOKEN_FORMAT, "token format error");
        errorMap.put(ERROR_TOKEN_EXPIRED, "token expired");
        errorMap.put(ERROR_LOGINNAME_EXISTS, "loginName has existed");
        errorMap.put(ERROR_VERIFYCODE_INVALIDATE, "verifycode has not existsed or invalidate");
        errorMap.put(ERROR_SMS_HAS_LIMITED, "sms has limited");
        errorMap.put(ERROR_LOGINNAME_NOT_EXISTS, "loginName has not existsed");
        errorMap.put(ERROR_LOGINNAME_INVALIDATE, "loginName invalidate");
        errorMap.put(ERROR_PIC_CODE_FAIL, "pic code fail");
        errorMap.put(ERROR_PIC_CODE_INVALIDATE, "pic code invalidate");
        errorMap.put(ERROR_NICKNAME_EXISTS, "nickname has existsed");
        errorMap.put(ERROR_SIGN, "sign is not correct");

        errorMap.put(ERROR_RESET_PHONE, "error in reset phone");
        errorMap.put(ERROR_RESET_PHONE_NOT_SAME_ACCOUNT, "not a same phone num");
        errorMap.put(ERROR_TOKEN_KICK_OFF, "another device has logged in");

        /* order **/
        errorMap.put(ERROR_COURSE_NOT_ON_SALE, "course is not on sale");
        errorMap.put(ERROR_COURSE_HAS_STOPPING_SELLING, "course has stopping selling");
        errorMap.put(ERROR_COURSE_HAS_SOLD_OUT, "course has sold out");
        errorMap.put(ERROR_COURSE_HAS_PAID, "course has paid");
        errorMap.put(ERROR_HAS_NO_ADDRESS, "has no address");
        errorMap.put(ERROR_COURSE_HAS_NOT_PAID, "course has not paid");
        errorMap.put(ERROR_OPEN_ID_NOT_EXISTS, "open id is not exists");
        errorMap.put(ERROR_ACTIVE_CODE_NOT_EXISTS, "active code not exists");
        errorMap.put(ERROR_ACTIVE_CODE_HAS_USED, "active code has used");
        errorMap.put(ERROR_ACTIVE_CODE_ORDER_NOT_EXISTS, "active code has not take effect");
        errorMap.put(ERROR_NO_SUCH_ORDER, "no such order");
        errorMap.put(ERROR_NO_SUCH_JDPAID_FAIL, "order jdpay fail");
        errorMap.put(ERROR_ORDER_REFUND_FAIL, "order refund fail");
        errorMap.put(ERROR_ORDER_NOT_FOUND, "order not found");
        errorMap.put(ERROR_ORDER_IAP_VERIFIED_FAILURE, "order iap fail");
        errorMap.put(ERROR_ORDER_IAP_LIMIT, "order iap limit");

        /* course **/
        errorMap.put(ERROR_NO_SUCH_COURSE, "no such course");
        errorMap.put(ERROR_HAS_COMMENTED, "the lesson has commented");
        errorMap.put(ERROR_NOT_RESERVE_COURSE, "the course is non-reserved");
        errorMap.put(ERROR_COURSE_HAS_STOPPING_RESERVING, "the course has stopping reserving");
        errorMap.put(ERROR_LESSON_HAS_COMMENT, "the lesson has commented");
        errorMap.put(ERROR_COURSE_NOT_START_SALE, "the course not start sale");
        errorMap.put(ERROR_COURSE_NOT_BUY, "the course not buy");
        errorMap.put(ERROR_COURSE_SAME_COURSE, "the same course");
        errorMap.put(ERROR_COURSE_NOT_REG_FREE, "the course not allow Reg-Free access");
        errorMap.put(ERROR_LESSON_NOT_REG_FREE, "the lesson not allow Reg-Free access");
        errorMap.put(ERROR_COURSE_ALREADY_ASSOCIATED, "the course has already associated");
        errorMap.put(ERROR_COURSE_NOT_NEED_DELAY, "the course not allow delay delivery");
        errorMap.put(ERROR_CLASS_NOT_HAS_COURSE, "the class not have this course");
        errorMap.put(ERROR_VIP_COURSE_ILLEGAL_ACCESS, "vip course illegal access error");
        errorMap.put(ERROR_COURSE_ILLEGAL_ACCESS_TO_VIP, "vip course illegal access redirect to vip");
        errorMap.put(ERROR_COURSE_TYPE_TAG_EXISTS, "the tag exists in course");
        errorMap.put(ERROR_COURSE_INTRO_DETAIL, "vip course detail intro error");

        /* exam **/
        errorMap.put(ERROR_NO_SUCH_EXAM, "start exam is not exists");
        errorMap.put(ERROR_EXAM_HAS_STARTED, "exam has started");
        errorMap.put(ERROR_EXAM_HAS_ENDED, "exam has ended");
        errorMap.put(ERROR_NO_SUCH_SUBJECT, "set answer subject is not exists");
        errorMap.put(ERROR_EXAM_NOT_STARTED, "exam has not started");
        errorMap.put(ERROR_EXAM_NOT_ENDED, "exam has not ended");
        errorMap.put(ERROR_EXAM_NOT_MATCH, "exam analytics not match");

        /* reserve **/
        errorMap.put(ERROR_MY_COURSE_RESERVE_EXISTS, "the course had been reserved");

        /* verify code **/
        errorMap.put(ERROR_PIC_CODE_INVALIDATE, "pic code invalidate");

        /* card **/
        errorMap.put(ERROR_CARD_EXISTS, "the course had been created school ID card");
        errorMap.put(ERROR_CARD_GENERATE_FAIL, "generate school ID card fail");
        errorMap.put(ERROR_CARD_GENERATE_FAIL_NO_EXISTS, "generate school ID card fail, generate cardNo has existsed");
        errorMap.put(ERROR_CARD_GENERATE_FAIL_COURSE_TAG_IS_NULL, "generate school ID card fail, course's tags is empty");
        errorMap.put(ERROR_CARD_MODIFY_FAIL, "modify school ID card fail");
        errorMap.put(ERROR_CARD_MODIFY_FAIL_CARDNO_IS_NULL, "modify school ID card fail, cardNo. is empty");

        /* coupon **/
        errorMap.put(ERROR_COUPON_DUPLICATE_GEN, "the duplicate generate coupon code");
        errorMap.put(ERROR_COUPON_NO_SUCH, "no such coupon code");
        errorMap.put(ERROR_COUPON_HAS_BEEN_USED, "the coupon code has been used");
        errorMap.put(ERROR_COUPON_EXPIRED, "the coupon code has been expired");
        errorMap.put(ERROR_COUPON_UNAVAILABLE, "unavailable coupon code");
        errorMap.put(ERROR_COUPON_ACT_NOT_FOUND, "the coupon act is not found");
        errorMap.put(ERROR_COUPON_ACT_NOT_START, "the coupon act is not start");
        errorMap.put(ERROR_COUPON_ACT_ALREADY_OVER, "the coupon act already over");

        /* wordary **/
        errorMap.put(ERROR_WORDARY_GENERATE_FAIL, "generate wordary game fail");
        errorMap.put(ERROR_WORDARY_MATCH_FAIL, "match wordary game fail");
        errorMap.put(ERROR_WORDARY_MATCH_HAS_QUIT, "user has quit the match");
        errorMap.put(ERROR_WORDARY_ROOM_NOT_EXISTS, "room has not existsed");
        errorMap.put(ERROR_WORDARY_ROOM_ACCESS_INVALIDATE, "enter room invalidate");
        errorMap.put(ERROR_WORDARY_ANSWER_FAIL, "answer fail");
        errorMap.put(ERROR_WORDARY_GAME_OVER, "the game has ended");
        errorMap.put(ERROR_WORDARY_ROOM_QUESTION_EMPTY, "the room questions has empty");
        errorMap.put(ERROR_WORDARY_GENERATE_ROOM_FAIL, "generate room fail");

        /* live **/
        errorMap.put(ERROR_NO_BEST_TRANSLATOR, "no best translator");
        errorMap.put(ERROR_SCAN_RESULT_FORMAT, "scan result format error");

        /* classcard **/
        errorMap.put(ERROR_CLASSCARD_EXISTS, "the classcard had been created");
        errorMap.put(ERROR_CLASSCARD_INSERT_FAIL, "insert classcard fail");
        errorMap.put(ERROR_CLASSCARD_GENERATE_CARDNO_FAIL, "generate cardNo fail");
        errorMap.put(ERROR_CLASSCARD_INSERT_FAIL_ORDER_IS_NULL, "insert classcard fail, order not existed");
        errorMap.put(ERROR_CLASSCARD_MODIFY_FAIL, "modify classcard fail");
        errorMap.put(ERROR_CLASSCARD_MODIFY_FAIL_ORDER_IS_NULL, "modify classcard fail, order not existed");
        errorMap.put(ERROR_CLASSCARD_CLASS_NOT_NEED, "class not need classcard");

        /* question **/
        errorMap.put(ERROR_QUESTION_INSERT_FAIL, "insert class_student_question fail");
        errorMap.put(ERROR_QUESTION_OVER_ADD_TIME, "your addition questions over two times");
        errorMap.put(ERROR_QUESTION_WAITING_NEW_ANSWER, "assistances no new answer,can not add new question");
        errorMap.put(ERROR_QUESTION_COUNT_OVER_LIMIT, "add questions over limit number");

        /* fission */
        errorMap.put(ERROR_ACT_ALREADY_HELP, "act already help");
        errorMap.put(ERROR_ACT_ALREADY_HELP_OTHER, "act already help other");
        errorMap.put(ERROR_ACT_ALREADY_DONE, "act already done");
        errorMap.put(ERROR_ACT_MIS_FISSION_NAME, "act mis fission name");
        errorMap.put(ERROR_ACT_MIS_UID, "act mis uid");

        errorMap.put(ERROR_TASK_REPLY_OVER, "the task has been answered");

        /* pay */
        errorMap.put(ERROR_PAY_TRADE_NUM_NOT_MATCH, "trade no not match");
        errorMap.put(ERROR_PAY_IN_TRANSACTION, "pay in transaction no");
        errorMap.put(ERROR_PAY_REMAIN_NOT_ENOUGH, "pay remain not enough");
        errorMap.put(ERROR_PAY_REPEAT_TRANSACTION_ID, "pay transaction repeated");

        /* act */
        errorMap.put(ERROR_ACT_ERROR_IN_TRANSACTION, "act give course error in transaction");

        /* app version low */
        errorMap.put(ERROR_APP_VERSION_LOW, "app version is too low");

        errorMap.put(ERROR_ADMIN_DATA_EXIST, "data is exist");


    }

    public static String getErrorMessage(int errorCode) {
        return errorMap.get(errorCode);
    }
}
