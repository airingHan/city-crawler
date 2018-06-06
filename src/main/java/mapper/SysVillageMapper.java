package mapper;

import domain.SysVillage;
import domain.SysVillageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysVillageMapper {
    long countByExample(SysVillageExample example);

    int deleteByExample(SysVillageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysVillage record);

    int insertSelective(SysVillage record);

    List<SysVillage> selectByExample(SysVillageExample example);

    SysVillage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysVillage record, @Param("example") SysVillageExample example);

    int updateByExample(@Param("record") SysVillage record, @Param("example") SysVillageExample example);

    int updateByPrimaryKeySelective(SysVillage record);

    int updateByPrimaryKey(SysVillage record);
}