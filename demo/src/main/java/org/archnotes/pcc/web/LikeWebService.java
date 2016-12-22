package org.archnotes.pcc.web;

import com.alibaba.fastjson.JSON;
import com.wordnik.swagger.annotations.ApiOperation;
import org.archnotes.pcc.dao.LikeDao;
import org.archnotes.pcc.dao.UserDao;
import org.archnotes.pcc.entity.Response;
import org.archnotes.pcc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fanngyuan on 12/22/16.
 */
@Controller
@Service
@RequestMapping(value = "/v1/like")
public class LikeWebService {

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private UserDao userDao;

    public LikeDao getLikeDao() {
        return likeDao;
    }

    public void setLikeDao(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "点赞")
    public String like(@RequestParam(value = "target_id") int targetId,@RequestParam(value = "type") int type,@RequestParam("user_id") int userId){
        likeDao.like(targetId,userId,type);
        Response response = new Response();
        response.setCode(Response.SUCC);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/is_liked", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "是否点赞")
    public String isLiked(@RequestParam(value = "target_id") int targetId,@RequestParam(value = "type") int type,@RequestParam("user_id") int userId){
        boolean isLiked=likeDao.isLiked(targetId,userId,type);
        Response response = new Response();
        response.setCode(Response.SUCC);
        response.setResult(isLiked);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "点赞数")
    public String likeCount(@RequestParam(value = "target_id") int targetId,@RequestParam(value = "type") int type){
        int count=likeDao.likeCount(targetId,type);
        Response response = new Response();
        response.setCode(Response.SUCC);
        response.setResult(count);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/user_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "点赞用户列表")
    public String userList(@RequestParam(value = "target_id") int targetId,@RequestParam(value = "type") int type,
                           @RequestParam(value = "page") int page,@RequestParam(value = "count") int count){
        List<Integer> userIds=likeDao.likedUserIds(targetId,type,page,count);
        Map<Integer,User> userMap = userDao.multiGet(userIds);
        List<User> userList = new ArrayList<>();
        for(Integer userId:userIds){
            userList.add(userMap.get(userId));
        }
        Response response = new Response();
        response.setCode(Response.SUCC);
        response.setResult(userList);
        return JSON.toJSONString(response);
    }
}
