package org.archnotes.pcc.dao;

import org.apache.log4j.Logger;
import org.archnotes.pcc.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fanngyuan on 12/22/16.
 */
@Component
public class UserDao extends BaseDao{

    private static final Logger logger = Logger.getLogger(UserDao.class);

    public User get(int userId){
        try{
            return this.template.queryForObject("select user_id,user_name from account where user_id=?",new UserRowMapper(),userId);
        }catch (EmptyResultDataAccessException ex ){
            logger.warn(ex.getLocalizedMessage());
        }
        return null;
    }

    public Map<Integer,User> multiGet(Collection<Integer> ids){
        String insql = getInStmt(ids);
        String sql = String.format("select user_id,user_name from account where user_id in %s",insql);
        List<User> users=this.template.query(sql,new UserRowMapper());
        if(users==null||users.size()==0){
            return null;
        }
        Map<Integer,User> userMap = new HashMap<>();
        for(User user:users){
            userMap.put(user.getUserId(),user);
        }
        return userMap;
    }

    public static String getInStmt(Collection<Integer> pkList) {
        if (pkList==null||pkList.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        int i=0;
        for(Integer key :pkList){
            if (key==null)
                continue;
            stringBuilder.append(key);
            if (i < pkList.size() - 1) {
                stringBuilder.append(",");
            }
            i++;
        }
        stringBuilder.append(")");
        if(stringBuilder.toString().length()==2){
            return null;
        }
        return stringBuilder.toString();
    }

    class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            return user;
        }
    }
}
