package com.moon.joyce.example.controller;


import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.entity.doma.Album;
import com.moon.joyce.example.entity.doma.Source;
import com.moon.joyce.example.entity.vo.MainSource;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.tundish.AlbumSource;
import com.moon.joyce.example.functionality.enums.AlbumEnum;
import com.moon.joyce.example.functionality.service.AlbumSourceService;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.AlbumService;
import com.moon.joyce.example.service.SourceService;
import com.moon.joyce.example.service.serviceControllerDetails.SourceControllerDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-09-25
 */
@Controller
@RequestMapping("/example/source")
public class SourceController extends BaseController {

    private final static String urlPrefix = "/example/source";

    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "source/";
    @Autowired
    private SourceService sourceService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumSourceService albumSourceService;
    @Autowired
    private SourceControllerDetailService sourceServiceControllerDetailService;
    //默认位置
    private String baseSite = "front,back,left,right,top,bottom";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //用户视频
    private static final String VIDEO_NAME = "user_video";
    /**
     * 资源页面
     * @return
     */
    @RequestMapping("/sourcePage")
    public String addSourcePage() {
        return pagePrefix + "sourcePage";
    }

    /**
     * 编辑sql页面
     * @return
     */
    @RequestMapping("/editSqlPage")
    public String editSqlPage() {
        return pagePrefix + "editSqlPage";
    }
    /**
     * 资源列表页面
     * @return
     */
    @RequestMapping("/sourceListPage")
    public String sourceListPage() {
        return pagePrefix + "sourceListPage";
    }

    /**
     * sql关键字
     * @throws IOException
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/getEditSqlData")
    public Result getEditSqlData() {
      //  String[] strings = {"SELECT","FROM","WHERE","DELETE","UPDATE","SET","AND","LEFT JOIN","RIGHT JOIN","ON","GROUP BY"};
        String keyWord = "ADD\tALL\tALTER\n" +
                "ANALYZE\tAND\tAS\n" +
                "ASC\tASENSITIVE\tBEFORE\n" +
                "BETWEEN\tBIGINT\tBINARY\n" +
                "BLOB\tBOTH\tBY\n" +
                "CALL\tCASCADE\tCASE\n" +
                "CHANGE\tCHAR\tCHARACTER\n" +
                "CHECK\tCOLLATE\tCOLUMN\n" +
                "CONDITION\tCONNECTION\tCONSTRAINT\n" +
                "CONTINUE\tCONVERT\tCREATE\n" +
                "CROSS\tCURRENT_DATE\tCURRENT_TIME\n" +
                "CURRENT_TIMESTAMP\tCURRENT_USER\tCURSOR\n" +
                "DATABASE\tDATABASES\tDAY_HOUR\n" +
                "DAY_MICROSECOND\tDAY_MINUTE\tDAY_SECOND\n" +
                "DEC\tDECIMAL\tDECLARE\n" +
                "DEFAULT\tDELAYED\tDELETE\n" +
                "DESC\tDESCRIBE\tDETERMINISTIC\n" +
                "DISTINCT\tDISTINCTROW\tp\n" +
                "DOUBLE\tDROP\tDUAL\n" +
                "EACH\tELSE\tELSEIF\n" +
                "ENCLOSED\tESCAPED\tEXISTS\n" +
                "EXIT\tEXPLAIN\tFALSE\n" +
                "FETCH\tFLOAT\tFLOAT4\n" +
                "FLOAT8\tFOR\tFORCE\n" +
                "FOREIGN\tFROM\tFULLTEXT\n" +
                "GOTO\tGRANT\tGROUP\n" +
                "HAVING\tHIGH_PRIORITY\tHOUR_MICROSECOND\n" +
                "HOUR_MINUTE\tHOUR_SECOND\tIF\n" +
                "IGNORE\tIN\tINDEX\n" +
                "INFILE\tINNER\tINOUT\n" +
                "INSENSITIVE\tINSERT\tINT\n" +
                "INT1\tINT2\tINT3\n" +
                "INT4\tINT8\tINTEGER\n" +
                "INTERVAL\tINTO\tIS\n" +
                "ITERATE\tJOIN\tKEY\n" +
                "KEYS\tKILL\tLABEL\n" +
                "LEADING\tLEAVE\tLEFT\n" +
                "LIKE\tLIMIT\tLINEAR\n" +
                "LINES\tLOAD\tLOCALTIME\n" +
                "LOCALTIMESTAMP\tLOCK\tLONG\n" +
                "LONGBLOB\tLONGTEXT\tLOOP\n" +
                "LOW_PRIORITY\tMATCH\tMEDIUMBLOB\n" +
                "MEDIUMINT\tMEDIUMTEXT\tMIDDLEINT\n" +
                "MINUTE_MICROSECOND\tMINUTE_SECOND\tMOD\n" +
                "MODIFIES\tNATURAL\tNOT\n" +
                "NO_WRITE_TO_BINLOG\tNULL\tNUMERIC\n" +
                "ON\tOPTIMIZE\tOPTION\n" +
                "OPTIONALLY\tOR\tORDER\n" +
                "OUT\tOUTER\tOUTFILE\n" +
                "PRECISION\tPRIMARY\tPROCEDURE\n" +
                "PURGE\tRAID0\tRANGE\n" +
                "READ\tREADS\tREAL\n" +
                "REFERENCES\tREGEXP\tRELEASE\n" +
                "RENAME\tREPEAT\tREPLACE\n" +
                "REQUIRE\tRESTRICT\tRETURN\n" +
                "REVOKE\tRIGHT\tRLIKE\n" +
                "SCHEMA\tSCHEMAS\tSECOND_MICROSECOND\n" +
                "SELECT\tSENSITIVE\tSEPARATOR\n" +
                "SET\tSHOW\tSMALLINT\n" +
                "SPATIAL\tSPECIFIC\tSQL\n" +
                "SQLEXCEPTION\tSQLSTATE\tSQLWARNING\n" +
                "SQL_BIG_RESULT\tSQL_CALC_FOUND_ROWS\tSQL_SMALL_RESULT\n" +
                "SSL\tSTARTING\tSTRAIGHT_JOIN\n" +
                "TABLE\tTERMINATED\tTHEN\n" +
                "TINYBLOB\tTINYINT\tTINYTEXT\n" +
                "TO\tTRAILING\tTRIGGER\n" +
                "TRUE\tUNDO\tUNION\n" +
                "UNIQUE\tUNLOCK\tUNSIGNED\n" +
                "UPDATE\tUSAGE\tUSE\n" +
                "USING\tUTC_DATE\tUTC_TIME\n" +
                "UTC_TIMESTAMP\tVALUES\tVARBINARY\n" +
                "VARCHAR\tVARCHARACTER\tVARYING\n" +
                "WHEN\tWHERE\tWHILE\n" +
                "WITH\tWRITE\tX509\n" +
                "XOR\tYEAR_MONTH\tZEROFILL" ;
        String[] strings = keyWord.split("\t|\n");
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        List<String> list2 = new ArrayList<>();
        for (String s : list) {
            String str = s.toLowerCase();
            list2.add(str);
        }
        list.addAll(list2);
        list = new ArrayList<>(new HashSet<>(list));
        return dataResult(true, list);
    }

    /**
     * 播放资源
     * @return
     */
    @RequestMapping("/playSourcePage/{id}/{sourceName}/{searchWord}/{lable}/{type}")
    public String playVideoPage(@PathVariable Long id,@PathVariable String sourceName,@PathVariable String searchWord,@PathVariable String lable,@PathVariable String type,ModelMap map){
        map.addAttribute("id",id);
        map.addAttribute("sourceName",sourceName);
        map.addAttribute("searchWord",searchWord);
        map.addAttribute("type",type);
        map.addAttribute("lable",lable);
        Source source = sourceService.getVideoInfo(id);
        File file = new File(source.getRealUrl());
        if (!file.exists()){
            map.addAttribute("exist",1);
        }else {
            map.addAttribute("exist",0);
        }
        MultimediaObject multimediaObject = new MultimediaObject(file);
        try {
            MultimediaInfo info = multimediaObject.getInfo();
            long duration = info.getDuration()/1000;
            String sumTime= DateUtils.getChargeTimechargeTime(duration);
            map.addAttribute("sumTime",sumTime);
          //  System.out.println("------>>>>>>>"+duration);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return pagePrefix + "playSourcePage";
    }


    /**
     * 下载文件
     * @param response
     * @throws IOException
     */
    @RequestMapping("/downloadFile/{id}")
    public void downloadFile(HttpServletResponse response,@PathVariable String id) throws IOException {
        Source source = sourceService.getById(id);
        if (source==null){
            return;
        }
        if (source.getType().equals("3")){
            Source vSource = sourceService.getById(source.getVId());
            source.setRealUrl(vSource.getRealUrl());
        }
        FileUtils.download(source.getRealUrl(),response);
        loggingService.save(getLogging("下载文件", StringsUtils.paramFormat("id",id),urlPrefix+"/downloadFile/"+id));
    }

    /**
     * 删除资源
     * @throws IOException
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/deleteSource")
    public Result deleteSource(@RequestParam String ids) {
        if (StringUtils.isBlank(ids)) {
            return error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.strToList(ids);
        List<Source> sources= sourceService.getByIds(list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        String msg1 ="";
        String msg2 ="";
        for (Source source : sources) {
            if (source.getVId()!=null){
                arrayList.add(source.getVId()+"");
            }
            if (source.getApplyStatus().equals(1)){
                msg1+="主页应用:"+source.getSourceName()+",";
            }
            if (source.getApplyStatus().equals(4)){
                msg1+="相册应用:"+source.getSourceName()+",";
            }
        }
        String msg ="";
        if (!msg1.equals("")){
            msg+=msg1;
        }
        if (!msg2.equals("")){
            msg+=msg2;
        }
        if (!msg.equals("")){
            msg+="不能删除，请解除应用后删除！";
            return error(msg);
        }
        boolean del = sourceService.removeByIds(arrayList);
        loggingService.save(getLogging(del,"删除资源", StringsUtils.paramFormat("ids",ids),urlPrefix+"/deleteSource"));
        return dataResult(del, Constant.ERROR_CODE);
    }

    /**
     * 上传多个文件
      * @param files
     * @return
     */
    @ResponseBody
    //@MethodUrl(name = "多文件上传", url = "/files")
    @PostMapping("/files")
    public Result uploadAlbum(@RequestParam("files") MultipartFile[] files) {
        String paths = null;
        try {
            paths = fileService.uploadImgs(files);
        } catch (Exception e) {
            e.printStackTrace();
            return error("上传失败");
        }
       // logger.info("上传相册的路径----》" + paths);
        loggingService.save(getLogging(Objects.nonNull(paths),"多文件上传", StringsUtils.paramFormat("files",paths),urlPrefix+"/files"));
        return dataResult(Objects.nonNull(paths), "上传失败", "上传成功", paths);
    }
    /**
     * 相册列表页面
     * @return
     */
    @RequestMapping("/albumListPage")
    public String albumListPage() {
        return pagePrefix + "albumListPage";
    }

    /**
     * 相册页面
     * @return
     */
    @RequestMapping("/albumPage/{id}")
    public String albumPage(@PathVariable Long id, ModelMap map) {
        map.addAttribute("id",id);
        return pagePrefix + "albumPage";
    }

    /**
     * 相册页面
     * @return
     */
    @RequestMapping("/addAlbumPage")
    public String addAlbumPage() {
        return pagePrefix + "addAlbumPage";
    }

    /**
     * 获取资源列表
     * @param album
     * @return
     */
    @ResponseBody
    @GetMapping("/getAlbumList")
    public PageVo getAlbumList(Album album) {
        album.setUserId(getSessionUser().getId());
        return albumService.getPage(album);
    }

    /**
     * 删除相册
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/delAlbum")
    public Result delAlbum(@RequestParam("ids") String ids) {
        List<String> list = StringsUtils.strToList(ids);
        List<AlbumSource> albumSources = new ArrayList<>();
        for (String s : list) {
            AlbumSource albumSource = new AlbumSource();
            albumSource.setAlbumId(Long.valueOf(s));
            List<AlbumSource> albumSourceList = albumSourceService.getList(albumSource);
            albumSources.addAll(albumSourceList);
        }
        List<String> collect = albumSources.stream().map(AlbumSource::getId).map(String::valueOf).collect(Collectors.toList());
        boolean conRs = albumSourceService.removeByIds(collect);
        boolean rs = albumService.removeByIds(list);
        loggingService.save(getLogging((rs && conRs),"删除相册", StringsUtils.paramFormat("ids",ids),urlPrefix+"/delAlbum"));
        return dataResult(rs && conRs,RE.DELETE.getCode());
    }

    /**
     * 查询某个相册资源
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/getAlbum/{id}")
    public Result getAlbumById(@PathVariable String id){
        Album album = albumService.getById(id);
        album = albumService.analyAlbumConfig(album);
        return dataResult(!album.getMap().isEmpty(),RE.SELECT.getCode(),album);
    }

    /**
     * 保存相册
     * @param album
     * @return
     */
    @ResponseBody
    @PostMapping("/saveAlbum")
    public Result saveAlbum(Album album) {
        String[] sourceUrls = album.getSourceUrls().split(",");
        if (AlbumEnum.BOX.getCode().toString().equals(album.getType())){
            if (!album.getTotal().toString().equals(AlbumEnum.BOX.getLength().toString())){
                return error("当前类型只能是"+AlbumEnum.BOX.getLength()+"张");
            }
        }
        List<Source> sources = new ArrayList<>();
        Arrays.stream(sourceUrls).forEach(x->{
            Source source = new Source();
            source.setUrl(x);
            source.setSourceName(UUIDUtils.getUUIDName());
            setBaseField(source);
            source.setApplyStatus(4);
            source.setType("0");
            source.setDescContent("盒型相册");
            source.setUserId(getSessionUserId());
            sources.add(source);
                });
        boolean saveSourceRs = sourceService.saveBatch(sources);
        List<Long> collect = sources.stream().map(Source::getId).collect(Collectors.toList());
        String ids = StringsUtils.listToStr(collect);
        JSONObject jo = new JSONObject();
        jo.put("ids",ids);
        jo.put("site",getDefSite());
        setBaseField(album);
        album.setSourceConfig(jo.toString());
        boolean rs = albumService.saveOrUpdate(album);
        List<AlbumSource> albumSources = new ArrayList<>();
        sources.forEach(x->{
            AlbumSource albumSource = new AlbumSource(album.getId(), x.getId());
            setBaseField(albumSource);
            albumSources.add(albumSource);});
        boolean asRs = albumSourceService.saveBatch(albumSources);
        album.setUserId(getSessionUserId());
        loggingService.save(getLogging((rs && saveSourceRs && asRs),"保存相册", JSONObject.toJSONString(album),urlPrefix+"/saveAlbum"));
        return dataResult(rs && saveSourceRs && asRs,RE.ADDORUPDATE.getCode(),album);
    }

    /**
     * 获取资源列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getList")
    public PageVo getList(Source source) {
        source.setSType(0);
        source.setCreateIds(getSessionUser().getId());
        source.setUserId(getSessionUser().getId());
        List<Source> list = sourceService.getList(source);
        List<Source> sources = new ArrayList<>();
        sources.addAll(list);
        for (Source s : list) {
            if (s.getCreateIds().equals(getSessionUserId())) {
                s.setPowerLevel(1);
            } else {
                if (s.getIsPub() != null) {
                    if (s.getIsPub().equals(0)) {
                        if (s.getPowerLevel() == null) {
                            sources.remove(s);
                        }
                    }
                }
            }
            s.setVUrl("https://www.baidu.com");
        }
        long total = sourceService.getCount(source);
        return new PageVo(list, total);
    }

    /**
     * 获取资源列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getLists/l")
    public Result getList2(Source source) {
        if (source.getSearchWord().equals("-1")){
            source.setSearchWord(null);
        }
        if (source.getLable().equals("-1")){
            source.setLable(null);
        }
        if (source.getType().equals("-1")){
            source.setType(null);
        }
        source.setType("3");
        PageVo list = getList(source);
        return dataResult(!list.getRows().isEmpty(),list.getRows());
    }
    /**
     * 保存资源
     * @return
     */
    @ResponseBody
    @PostMapping("/saveSource")
    public Result addSource(Source source) {
        setBaseField(source);
        source.setUserId(getSessionUserId());
        if (StringUtils.isBlank(source.getSourceName())){
            source.setSourceName(UUIDUtils.getUUIDName());
        }
        if (!sourceServiceControllerDetailService.check(source)) {
            return error("该资源名已被使用");
        }
        if (source.getApplyStatus().equals(Constant.APPLY_STATUS)) {
            boolean b = sourceServiceControllerDetailService.retireApplyStatus(source);
            if (!b) {
                loggingService.save(getLogging(!b,"保存资源", JSONObject.toJSONString(source),urlPrefix+"/saveSource"));
                return error();
            }
        }
        if (source.getType().equals("0")||StringUtils.isBlank(source.getType())){
            Object sessionValue = getSessionValue(getSessionUserId() + "pic");
            if (sessionValue!=null){
                source.setRealUrl(sessionValue.toString());
            }

        }
        if (Objects.isNull(source.getId())) {
            source.setUserId(getSessionUser().getId());
            if (StringUtils.isNoneBlank(source.getVUrl())){
                Source tempSource = new Source();
                setBaseField(tempSource);
                tempSource.setSourceName("v_"+source.getSourceName());
                tempSource.setUrl(source.getVUrl());
                tempSource.setType("2");
                tempSource.setUserId(getSessionUser().getId());
                if (Objects.nonNull(getSessionValue(getSessionUserId()+VIDEO_NAME).toString())){
                    tempSource.setRealUrl(getSessionValue(getSessionUserId()+VIDEO_NAME).toString());
                }
                boolean b = sourceService.saveOrUpdate(tempSource);
                if (b){
                    source.setType("3");
                    source.setApplyStatus(0);
                    source.setVId(tempSource.getId());
                }
            }
        } else {
            source.setUserId(getSessionUser().getId());
        }
        boolean rs = sourceService.saveOrUpdate(source);
        loggingService.save(getLogging(rs,"保存资源", JSONObject.toJSONString(source),urlPrefix+"/saveSource"));
        return dataResult(rs);
    }

    /**
     * 删除相册
     * @return
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/deleteAlbum")
    public Result deleteUser(@RequestParam String ids){
        if (StringUtils.isBlank(ids)){
            return error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.strToList(ids);
        boolean del = albumService.removeByIds(list);
        loggingService.save(getLogging(del,"删除相册", StringsUtils.paramFormat("ids",ids),urlPrefix+"/deleteAlbum"));
        return dataResult(del,Constant.ERROR_CODE);
    }

    /**
     * 保存资源
     * @return
     */
    @ResponseBody
    @PostMapping("/setMainSource")
    public Result addSource(@RequestParam("id") Long id) {
        Source source = sourceService.getById(id);
        if (Objects.isNull(source)) {
            return error(Constant.NULL_CODE);
        }
        source.setUpdateBy(getSessionUser().getUsername());
        source.setUpdateTime(new Date());
        boolean b = sourceServiceControllerDetailService.retireApplyStatus(source);
        if (!b) {
            loggingService.save(getLogging(false,"保存资源", StringsUtils.paramFormat("id",id),urlPrefix+"/setMainSource"));
            return error();
        }
        source.setApplyStatus(Constant.APPLY_STATUS);
        boolean rs = sourceService.saveOrUpdate(source);
        loggingService.save(getLogging(rs,"保存资源", StringsUtils.paramFormat("id",id),urlPrefix+"/setMainSource"));
        return dataResult(rs, "修改失败", "修改成功", source);
    }

    /**
     * 上传资源
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadSource")
    public Result uploadSource(@RequestParam(value = "file", required = true) MultipartFile file) {
        String filePath = null;
        try {
            Map<String, String> map = fileService.uploadImg(file);
            filePath = map.get("v");
            setSession(getSessionUserId()+"pic",map.get("r"));
        } catch (Exception e) {
            loggingService.save(getLogging("上传资源异常", JSONObject.toJSONString(file),urlPrefix+"/uploadSource"));
            return error("上传异常");
        }
        loggingService.save(getLogging("上传资源成功", JSONObject.toJSONString(file),urlPrefix+"/uploadSource"));
        return success("上传成功", filePath);
    }

    /**
     * 主页配置
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSourceImage")
    public Result doProjectMainPage() {
        Source source = new Source();
        source.setType("0");
        source.setUserId(getSessionUserId());
        source.setCreateIds(getSessionUserId());
        source.setApplyStatus(Constant.APPLY_STATUS);
        Source dbSource = sourceService.getOne(source);
        source.setApplyStatus(Constant.SPARE_STATUS);
        source.setSType(1);
        List<Source> list = sourceService.getList(source);
        if (Objects.nonNull(dbSource) && !list.isEmpty()) {
            MainSource mainSource = new MainSource(1L, dbSource, list);
            return success(mainSource);
        }
        return error("主页背景默认配置");
    }

    /**
     * 上传临时文件分片
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @PostMapping("/uploadVideoSource")
    public Result uploadVideo(HttpServletRequest req, HttpServletResponse resp) {
        int code = 0;
        try {
            code = fileService.uploadVideo(req, resp);
            logger.info("---->code:"+code);
        } catch (Exception e) {
            loggingService.save(getLogging(false,"上传临时文件分片", "",urlPrefix+"/uploadVideoSource"));
            return error("上传异常,视频上传失败！");
        }
        if (code==200){
            loggingService.save(getLogging(true,"上传临时文件分片", "",urlPrefix+"/uploadVideoSource"));
            return success(code,"视频上传完成，进行解析！");
        }
        if (code==201){
            return success(code,"正在上传视频！");
        }
         return error("视频上传失败！");
    }

    /**
     * 解析视频
     * @param uuid
     * @param newFileName
     * @return
     */
    @ResponseBody
    @GetMapping("/mergeVideoSource")
    public Result mergeVideo( String uuid, String newFileName){
        Map<String, Object> map = null;
        try {
            map = fileService.mergeTempFile(uuid, newFileName);
            logger.info("{}上传的视频map信息",map.toString());
            setSession(getSessionUserId()+VIDEO_NAME,map.get(FileUtils.vrp));
            map.remove(FileUtils.vrp);
        } catch (Exception e) {
            logger.info("{}",e.toString());
            return error("视频解析失败");
        }
        if (map.isEmpty()){
            logger.info("{}上传的视频map信息1",map.toString());
        }
        return dataResult(!map.isEmpty(),"视频解析失败","视频解析成功",map);
    }

    /**
     *获取封面视频
     * @return
     */
    @ResponseBody
    @RequestMapping("/getIndexVideo")
    public Result getIndexVideo(){
        Source source = new Source();
        source.setApplyStatus(1);
        source.setType("2");
        source.setDeleteFlag(0);
        Source dbSource = sourceService.getOne(source);
        if (Objects.isNull(dbSource)|| StringUtils.isBlank(dbSource.getUrl())){
            source.setUrl(Constant.VIDEO_DEFAULT_NAME);
            return success(source);
        }
        return success(dbSource);
    }


    /**
     * 视频播放
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @GetMapping("/playVideo/{id}")
    public void play(HttpServletRequest request, HttpServletResponse response,@PathVariable Long id) throws IOException {
        response.reset();
        Source source = sourceService.getVideoInfo(id);
        File file = new File(source.getRealUrl());
        long fileLength = file.length();
        // 随机读文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        //获取从那个字节开始读取文件
        String rangeString = request.getHeader("Range");
        long range = 0;
        if (StringUtils.isNotBlank(rangeString)) {
            range = Long.parseLong(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        }
        //获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        //设置内容类型
        response.setHeader("Content-Type", "video/mp4");
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 移动访问指针到指定位置
        randomAccessFile.seek(range);
        // 每次请求只返回1MB的视频流
        byte[] bytes = new byte[1024 * 512];
        int len = randomAccessFile.read(bytes);
        //设置此次相应返回的数据长度
        response.setContentLength(len);
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
        // 将这1MB的视频流响应给客户端
        outputStream.write(bytes, 0, len);
        outputStream.close();
        randomAccessFile.close();
       // System.out.println("返回数据区间:【" + range + "-" + (range + len) + "】");
    }

    /**
     * 获取默认位置信息
     * @return
     */
    private String getDefSite(){
        String[] pres = {"out","in"};
        StringBuilder sb = new StringBuilder();
        String[] sites = baseSite.split(",");
        for (String pre : pres) {
            for (String site : sites) {
                sb.append(pre).append("_").append(site).append(",");
            }
        }
        return  sb.substring(0, sb.toString().length()-1);
    }




}

