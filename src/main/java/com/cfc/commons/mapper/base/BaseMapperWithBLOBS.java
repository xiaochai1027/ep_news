package com.cfc.commons.mapper.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * MyBatisBaseMapper
 *
 * @param <ST>              simpleClass
 * @param <T>classWithBLOBS
 * @param <PK>
 * @param <E>
 * @author CallMeXYZ
 */
public interface BaseMapperWithBLOBS<ST, T, PK extends Serializable, E> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(PK id);

    int insert(T record);

    int insertSelective(T record);

    List<ST> selectByExample(E example);

    T selectByPrimaryKey(PK id);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") ST record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> updateByExampleWithBLOBs(@Param("record") T record, @Param("example") E example);

    List<T> selectByExampleWithBLOBs(E example);

}
