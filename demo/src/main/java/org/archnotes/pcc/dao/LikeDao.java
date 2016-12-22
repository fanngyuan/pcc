package org.archnotes.pcc.dao;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fanngyuan on 12/22/16.
 */
@Component
public class LikeDao extends BaseDao{

    public void like(int targetId,int userId,int type){
        this.template.update("insert into target_like(target_id,type,user_id) values(?,?,?)",targetId,type,userId);
    }

    public boolean isLiked(int targetId,int userId,int type){
        int count=this.template.queryForObject("select count(*) from target_like where target_id=? and type=? and user_id=?",Integer.class,targetId,type,userId);
        if(count==0){
            return false;
        }else{
            return true;
        }
    }

    public int likeCount(int targetId,int type){
        return this.template.queryForObject("select count(*) from target_like where target_id=? and type=?",Integer.class,targetId,type);
    }

    public List<Integer> likedUserIds(int targetId,int type,int page,int count){
        return this.template.queryForList("select user_id from target_like where target_id=? and type=? order by id desc limit ?,?",Integer.class,targetId,type,(page-1)*count,count);
    }
}
