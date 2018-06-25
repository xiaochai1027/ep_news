//package com.cfc.task;
//
//import com.cfc.dao.mapper.CheckUuidMapper;
//import com.cfc.dao.model.CheckUuid;
//import com.cfc.util.basedao.BaseDao;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
///**
// * @auther fangchen.chai ON 2017/11/22
// */
//@Component
//public class Task {
//
//    private Logger logger = LoggerFactory.getLogger(Task.class);
//
////    @Autowired
////    private CheckUuidMapper checkUuidMapper;
//
////    @Scheduled(cron = "0/1 * * * * ?")
//    public void checkUuid(){
//        CheckUuid checkUuid = new CheckUuid();
//        checkUuid.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
//        checkUuid.setNum(1);
////        checkUuidMapper.insertOrUpdateCheckUuid(checkUuid);
//        BaseDao.insert(checkUuid, "check_uuid");
//        logger.info("task --- checkUuid = {}",checkUuid.getId());
//    }
//}
