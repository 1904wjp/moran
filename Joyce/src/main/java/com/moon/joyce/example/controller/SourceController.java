package com.moon.joyce.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.commons.utils.UUIDUtils;
import com.moon.joyce.example.entity.Album;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.entity.vo.MainSource;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.entity.tundish.AlbumSource;
import com.moon.joyce.example.functionality.service.AlbumSourceService;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.service.AlbumService;
import com.moon.joyce.example.service.SourceService;
import com.moon.joyce.example.service.serviceControllerDetails.SourceServiceControllerDetailService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private SourceServiceControllerDetailService sourceServiceControllerDetailService;
    //默认位置
    private String baseSite = "front,back,left,right,top,bottom";

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 资源页面
     * @return
     */
    @RequestMapping("/sourcePage")
    public String addSourcePage() {
        return pagePrefix + "sourcePage";
    }
    /**
     * 资源列表页面
     *
     * @return
     */
    @RequestMapping("/sourceListPage")
    public String sourceListPage() {
        return pagePrefix + "sourceListPage";
    }
    /**
     * 相册列表页面
     *
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
    @ResponseBody
    @GetMapping("/getAlbum/{id}")
    public Result getAlbumById(@PathVariable String id){
        Album album = albumService.getById(id);
        Map<String, Source> map = albumService.analyAlbumConfig(album.getSourceConfig());
        album.setMap(map);
        return dataResult(!map.isEmpty(),album);
    }

    /**
     * 保存资源
     * @param album
     * @return
     */
    @ResponseBody
    @PostMapping("/saveAlbum")
    public Result saveAlbum(Album album) {
        int size = album.getSources().size();
        if ("0".equals(album.getType())){
            if (size != 12){
                return error("当前类型只能是12张");
            }
        }
        List<Source> sources = new ArrayList<>();
        album.getSources().forEach(x->{
                    setBaseField(x);
                    x.setType("4");
                    sources.add(x);
                });
        boolean saveSourceRs = sourceService.saveBatch(sources);
        String ids = StringsUtils.listToStr(sources.stream().map(Source::getId).collect(Collectors.toList()));
        JSONObject jo = (JSONObject)JSON.parse(album.getSourceConfig());
        jo.put("ids",ids);
        album.setSourceConfig(jo.toString());
        boolean rs = albumService.saveOrUpdate(album);
        List<AlbumSource> albumSources = new ArrayList<>();
        album.getSources().forEach(x->albumSources.add(new AlbumSource(album.getId(),x.getId())));
        boolean asRs = albumSourceService.saveBatch(albumSources);
        album.setTotal(size);
        album.setUserId(getSessionUserId());
        return dataResult(rs && saveSourceRs && asRs);
    }

    /**
     * 获取资源列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getList")
    public PageVo getList(Source source) {
        source.setUserId(getSessionUser().getId());
        List<Source> list = sourceService.getList(source);
        long total = sourceService.getCount(source);
        return new PageVo(list, total);
    }


    /**
     * 保存资源
     * @return
     */
    @ResponseBody
    @PostMapping("/saveSource")
    public Result addSource(Source source) {
        if (StringUtils.isNoneBlank(source.getSourceName())){
            source.setSourceName(UUIDUtils.getUUID(8));
        }
        if (!sourceServiceControllerDetailService.check(source)) {
            return error("该资源名已被使用");
        }
        if (source.getApplyStatus().equals(Constant.APPLY_STATUS)) {
            boolean b = sourceServiceControllerDetailService.retireApplyStatus(source.getType(),getSessionUserId());
            if (!b) {
                return R.error();
            }
        }

        if (Objects.isNull(source.getId())) {
            source.setCreateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setCreateTime(new Date());
            source.setDeleteFlag(Constant.UNDELETE_STATUS);
            if (StringUtils.isNoneBlank(source.getVUrl())){
                Source tempSource = new Source();
                tempSource.setSourceName("v_"+source.getSourceName());
                tempSource.setUrl(source.getVUrl());
                tempSource.setType("2");
                tempSource.setCreateBy(getSessionUser().getUsername());
                tempSource.setUserId(getSessionUser().getId());
                tempSource.setCreateTime(new Date());
                tempSource.setDeleteFlag(Constant.UNDELETE_STATUS);
                boolean b = sourceService.saveOrUpdate(tempSource);
                if (b){
                    source.setType("3");
                    source.setVId(tempSource.getId());
                }
            }
        } else {
            source.setUpdateBy(getSessionUser().getUsername());
            source.setUserId(getSessionUser().getId());
            source.setUpdateTime(new Date());
        }
        boolean rs = sourceService.saveOrUpdate(source);
        return R.dataResult(rs);
    }

    /**
     * 删除某个相册
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
            return R.error(Constant.NULL_CODE);
        }
        source.setUpdateBy(getSessionUser().getUsername());
        source.setUpdateTime(new Date());
        boolean b = sourceServiceControllerDetailService.retireApplyStatus(source.getType(),getSessionUserId());
        if (!b) {
            return R.error();
        }
        source.setApplyStatus(Constant.APPLY_STATUS);
        boolean rs = sourceService.saveOrUpdate(source);
        return R.dataResult(rs, "修改失败", "修改成功", source);
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
            filePath = fileService.uploadImg(file);
        } catch (Exception e) {
            return error("上传异常");
        }
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
        source.setDeleteFlag(Constant.UNDELETE_STATUS);
        source.setApplyStatus(Constant.APPLY_STATUS);
        source.setUserId(getSessionUserId());
        Source dbSource = sourceService.getOne(source);
        source.setApplyStatus(Constant.SPARE_STATUS);
        List<Source> list = sourceService.getList(source);
        if (Objects.nonNull(dbSource) && !list.isEmpty()) {
            MainSource mainSource = new MainSource(1L, dbSource, list);
            return success(mainSource);
        }
        return R.error("主页背景默认配置");
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
        } catch (Exception e) {
            return error("上传异常,视频上传失败！");
        }
        if (code==200){
            return R.success("视频上传完成，进行解析！");
        }
        if (code==201){
            return R.success("正在上传视频！");
        }
         return R.error("视频上传失败！");
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
            map.remove(FileUtils.vrp);
        } catch (Exception e) {
            return error("视频解析失败");
        }
        return R.dataResult(!map.isEmpty(),"视频解析失败","视频解析成功",map);
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
        return R.success(dbSource);
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

