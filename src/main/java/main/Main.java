package main;

import domain.*;
import enums.JobStateEnums;
import enums.JobTypeEnums;
import mapper.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.Parser;
import util.JsoupUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 启动类
 */
public class Main {

    private Parser parser = new Parser();

    private String relativePath = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";

    public static void main(String[] args) throws IOException {
        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/index.html";

//        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/13.html";
//        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/13/1301.html";
//        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/13/01/130102.html";
//        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/13/01/02/130102001.html";
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            Main main = new Main();
            Document document = JsoupUtil.getDocumentRetry(url, 0);
            CrawlerJobExample example = null;
            CrawlerJobMapper mapper = session.getMapper(CrawlerJobMapper.class);

            //省级
            main.parseAndSaveProvinceAndSaveNextLevel(document, session);

            //地级
            example = new CrawlerJobExample();
            example.createCriteria().andTypeEqualTo(JobTypeEnums.CITY.code)
            .andStateEqualTo(JobStateEnums.NOT_PARSING.value);
            List<CrawlerJob> provinceList = mapper.selectByExample(example);
            for (CrawlerJob job : provinceList) {
                document = JsoupUtil.getDocumentRetry(job.getUrl(), 0);
                main.parseAndSaveCityAndSaveNextLevel(document, session);
                job.setState(JobStateEnums.PARSING.value);
                mapper.updateByPrimaryKey(job);
            }


            //县级
            example = new CrawlerJobExample();
            example.createCriteria().andTypeEqualTo(JobTypeEnums.COUNTY.code)
                    .andStateEqualTo(JobStateEnums.NOT_PARSING.value);
            List<CrawlerJob> cityList = mapper.selectByExample(example);
            for (CrawlerJob job : cityList) {
                document = JsoupUtil.getDocumentRetry(job.getUrl(),0);
                main.parseAndSaveAreaAndSaveNextLevel(document, job.getUrl(), session);
                job.setState(JobStateEnums.PARSING.value);
                mapper.updateByPrimaryKey(job);
            }

            //乡级
            example = new CrawlerJobExample();
            example.createCriteria().andTypeEqualTo(JobTypeEnums.TOWN.code)
                    .andStateEqualTo(JobStateEnums.NOT_PARSING.value);
            List<CrawlerJob> countyList = mapper.selectByExample(example);
            for (CrawlerJob job : countyList) {
                document = JsoupUtil.getDocumentRetry(job.getUrl(), 0);
                main.parseTown(document, session);
                job.setState(JobStateEnums.PARSING.value);
                mapper.updateByPrimaryKey(job);
            }

            //村级
            example = new CrawlerJobExample();
            example.createCriteria().andTypeEqualTo(JobTypeEnums.VILLAGE.code)
                    .andStateEqualTo(JobStateEnums.NOT_PARSING.value);
            List<CrawlerJob> townList = mapper.selectByExample(example);
            for (CrawlerJob job : townList) {
                document = JsoupUtil.getDocumentRetry(job.getUrl(), 0);
                main.parseCommunity(document, session);
                job.setState(JobStateEnums.PARSING.value);
                mapper.updateByPrimaryKey(job);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO异常");
        } finally {
            session.close();
        }
    }

    public static String getUrl(String relativePath, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(relativePath);
        sb.append(url);
        return sb.toString();
    }

    /**
     * 解析省页面，保存省数据，保存下级任务
     * @param document
     * @param session
     */
    public void parseAndSaveProvinceAndSaveNextLevel(Document document, SqlSession session) {
        //解析省页面，返回省数据
        List<BaseModel> list = parser.parseProvince(document);
        //保存省数据
        SysProvinceMapper mapper = session.getMapper(SysProvinceMapper.class);
        for (BaseModel baseModel : list) {
            SysProvince province = new SysProvince();
            province.setCode(Integer.valueOf(baseModel.getId().substring(0,2)));
            province.setName(baseModel.getName());
            mapper.insertSelective(province);
        }
        //保存下级任务
        CrawlerJobMapper jobMapper = session.getMapper(CrawlerJobMapper.class);
        for (BaseModel baseModel : list) {
            CrawlerJob job = new CrawlerJob();
            job.setType(JobTypeEnums.CITY.code);
            String url = getUrl(relativePath, baseModel.getId());
            job.setUrl(url);
            job.setState(JobStateEnums.NOT_PARSING.value);
            jobMapper.insertSelective(job);
        }
    }

    /**
     * 解析地级页面，保存地级数据，保存下级任务
     */
    public void parseAndSaveCityAndSaveNextLevel(Document document, SqlSession session) {
        //解析地级页面，返回数据
        List<BaseModel> list = parser.parseCity(document);
        //保存地级数据
        SysCityMapper mapper = session.getMapper(SysCityMapper.class);
        for (BaseModel baseModel : list) {
            SysCity city = new SysCity();
            city.setCode(Integer.valueOf(baseModel.getId().substring(3,7)));
            city.setName(baseModel.getName());
            city.setProvinceCode(Integer.valueOf(baseModel.getId().substring(0,2)));
            mapper.insertSelective(city);
        }
        //保存下级任务
        CrawlerJobMapper jobMapper = session.getMapper(CrawlerJobMapper.class);
        for (BaseModel baseModel : list) {
            CrawlerJob job = new CrawlerJob();
            job.setType(JobTypeEnums.COUNTY.code);
            String url = getUrl(relativePath, baseModel.getId());
            job.setUrl(url);
            job.setState(JobStateEnums.NOT_PARSING.value);
            jobMapper.insertSelective(job);
        }
    }

    /**
     * 解析县级页面，保存县级数据，保存下级任务
     */
    public void parseAndSaveAreaAndSaveNextLevel(Document document, String url, SqlSession session) {
        //解析县级页面,返回数据
        List<BaseModel> list = parser.parseCounty(document);
        CrawlerJobMapper jobMapper = session.getMapper(CrawlerJobMapper.class);
        //下一级为乡级，而不为县级
        //保存到数据库一条县级
        SysCountyMapper mapper = session.getMapper(SysCountyMapper.class);
        if (list.isEmpty()) {
            SysCounty sysCounty = new SysCounty();
            int i = url.lastIndexOf("/");
            sysCounty.setCode(Integer.valueOf(url.substring(i+1, i + 5)+"00"));
            sysCounty.setCityCode(Integer.valueOf(url.substring(i+1, i + 5)));
            sysCounty.setName(url.substring(i+1, i + 5)+"00");
            mapper.insertSelective(sysCounty);
            CrawlerJob job = new CrawlerJob();
            job.setType(JobTypeEnums.TOWN.code);
            job.setUrl(url);
            job.setState(JobStateEnums.NOT_PARSING.value);
            jobMapper.insertSelective(job);
            return;
        }
        //保存县级数据
        for (BaseModel baseModel : list) {
            SysCounty sysCounty = new SysCounty();
            sysCounty.setCode(Integer.valueOf(baseModel.getId().substring(0,6)));
            sysCounty.setCityCode(Integer.valueOf(baseModel.getId().substring(0,4)));
            sysCounty.setName(baseModel.getName());
            mapper.insertSelective(sysCounty);
        }
        //保存下级任务
        for (BaseModel baseModel : list) {
            if (baseModel.getHref() != null) {
                CrawlerJob job = new CrawlerJob();
                job.setType(JobTypeEnums.TOWN.code);
                String parenturl = baseModel.getHref().substring(3,5);
                parenturl = parenturl + "/" + baseModel.getHref();
                parenturl = getUrl(relativePath, parenturl);
                job.setUrl(parenturl);
                job.setState(JobStateEnums.NOT_PARSING.value);
                jobMapper.insertSelective(job);
            }
        }
    }

    public void parseTown(Document document, SqlSession session) {
        //解析乡级页面，保存乡级数据
        List<BaseModel> list = parser.parseTown(document);
        //保存乡级数据
        SysTownMapper mapper = session.getMapper(SysTownMapper.class);
        for (BaseModel baseModel : list) {
            SysTown town = new SysTown();
            town.setCode(Integer.valueOf(baseModel.getId().substring(0,9)));
            town.setCountyCode(Integer.valueOf(baseModel.getId().substring(0,6)));
            town.setName(baseModel.getName());
            mapper.insertSelective(town);
        }
        //保存下级任务
        CrawlerJobMapper jobMapper = session.getMapper(CrawlerJobMapper.class);
        for (BaseModel baseModel : list) {
            if (baseModel.getHref() != null) {
                String url = null;
                if ("00".equals(baseModel.getHref().substring(7,9))) {
                    String parenturl = baseModel.getHref().substring(3,5);
                    url = parenturl + "/" + baseModel.getHref();
                } else {
                    String parenturl = baseModel.getHref().substring(3,5) + "/" + baseModel.getHref().substring(5,7);
                    url = parenturl + "/" + baseModel.getHref();
                }
                CrawlerJob job = new CrawlerJob();
                job.setType(JobTypeEnums.VILLAGE.code);
                url = getUrl(relativePath, url);
                job.setUrl(url);
                job.setState(JobStateEnums.NOT_PARSING.value);
                jobMapper.insertSelective(job);
            }
        }
    }

    /**
     * 解析村级页面，保存村级数据
     */
    public void parseCommunity(Document document, SqlSession session) {
        //解析村级页面,返回数据
        List<BaseModel> list = parser.parseVillage(document);
        //保存村级数据
        SysVillageMapper sysVillageMapper = session.getMapper(SysVillageMapper.class);
        for (BaseModel baseModel : list) {
            SysVillage sysVillage = new SysVillage();
            sysVillage.setCode(Long.valueOf(baseModel.getId()));
            sysVillage.setTownCode(Integer.valueOf(baseModel.getId().substring(0, 9)));
            sysVillage.setName(baseModel.getName());
            sysVillageMapper.insertSelective(sysVillage);
        }
    }

}
