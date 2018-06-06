package mapper;

import domain.SysTown;
import domain.SysTownExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysTownMapper {
    long countByExample(SysTownExample example);

    int deleteByExample(SysTownExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysTown record);

    int insertSelective(SysTown record);

    List<SysTown> selectByExample(SysTownExample example);

    SysTown selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysTown record, @Param("example") SysTownExample example);

    int updateByExample(@Param("record") SysTown record, @Param("example") SysTownExample example);

    int updateByPrimaryKeySelective(SysTown record);

    int updateByPrimaryKey(SysTown record);
}