package mapper;

import domain.SysCounty;
import domain.SysCountyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCountyMapper {
    long countByExample(SysCountyExample example);

    int deleteByExample(SysCountyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCounty record);

    int insertSelective(SysCounty record);

    List<SysCounty> selectByExample(SysCountyExample example);

    SysCounty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCounty record, @Param("example") SysCountyExample example);

    int updateByExample(@Param("record") SysCounty record, @Param("example") SysCountyExample example);

    int updateByPrimaryKeySelective(SysCounty record);

    int updateByPrimaryKey(SysCounty record);
}