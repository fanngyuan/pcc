package org.archnotes.pcc.dao;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fanngyuan on 12/22/16.
 */
@Component
public class FollowDao extends BaseDao{

    public List<Integer> getFollowing(int userId){
        return this.template.queryForList("select to_uid from follow where from_uid=?",Integer.class,userId);
    }
}
