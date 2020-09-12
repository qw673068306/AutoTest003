package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DateBaseUtiil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserInfoListTest {

    @Test(dependsOnGroups = "LoginTrue" ,description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {

        SqlSession session = DateBaseUtiil.getSqlSession();
        GetUserListCase getUserInfoListCase = session.selectOne("getUserInfoListCase",1);
        System.out.println(getUserInfoListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        JSONArray resultjson = getJsonResult(getUserInfoListCase);
        List<User> userlist= session.selectList(getUserInfoListCase.getExpected(),getUserInfoListCase);
//        for(User u:userlist){
//            System.out.println("获取的user："+u.toString());
//        }
        JSONArray userlistjson = new JSONArray(userlist);
        Assert.assertEquals(userlistjson.length(),resultjson.length());
        for(int i=0;i<resultjson.length();i++){
            JSONObject expect = (JSONObject) resultjson.get(i);
            JSONObject actual = (JSONObject) userlistjson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }

    }

    public JSONArray getJsonResult(GetUserListCase getUserInfoListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("UserName",getUserInfoListCase.getUserName());
        param.put("age",getUserInfoListCase.getAge());
        param.put("sex",getUserInfoListCase.getSex());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        post.setHeader("content-type","application/json");
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;

    }
}
