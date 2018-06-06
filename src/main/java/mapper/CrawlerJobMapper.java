package mapper;

import domain.CrawlerJob;
import domain.CrawlerJobExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrawlerJobMapper {
    long countByExample(CrawlerJobExample example);

    int deleteByExample(CrawlerJobExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerJob record);

    int insertSelective(CrawlerJob record);

    List<CrawlerJob> selectByExample(CrawlerJobExample example);

    CrawlerJob selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrawlerJob record, @Param("example") CrawlerJobExample example);

    int updateByExample(@Param("record") CrawlerJob record, @Param("example") CrawlerJobExample example);

    int updateByPrimaryKeySelective(CrawlerJob record);

    int updateByPrimaryKey(CrawlerJob record);
}