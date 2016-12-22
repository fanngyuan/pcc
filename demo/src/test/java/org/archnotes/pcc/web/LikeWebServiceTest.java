package org.archnotes.pcc.web;

import com.alibaba.fastjson.JSON;
import org.archnotes.pcc.entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * Created by fanngyuan on 12/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"classpath:context.xml","classpath:ds-context.xml" })
public class LikeWebServiceTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    public WebApplicationContext getWac() {
        return wac;
    }

    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters().build();
    }

    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Before
    public void cleanData() {
        String script = "classpath:ds-cleanup.sql";
        Resource resource = wac.getResource(script);
        JdbcTestUtils.executeSqlScript(template, resource, true);
    }

    @Test
    public void testLike() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/like/new").param("target_id","1")
                .param("type", "1").param("user_id", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Response response=JSON.parseObject(result.getResponse().getContentAsString(), Response.class);
        assertEquals(response.getCode(),Response.SUCC);

        result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/like/is_liked").param("target_id","1")
                .param("type", "1").param("user_id", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        response=JSON.parseObject(result.getResponse().getContentAsString(), Response.class);
        assertEquals(response.getCode(),Response.SUCC);
        assertEquals(response.getResult(),true);

        result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/like/count").param("target_id","1")
                .param("type", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        response=JSON.parseObject(result.getResponse().getContentAsString(), Response.class);
        assertEquals(response.getCode(),Response.SUCC);
        assertEquals(response.getResult(),1);

        result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/like/user_list").param("target_id","1")
                .param("type", "1").param("page","1").param("count","20"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        response=JSON.parseObject(result.getResponse().getContentAsString(), Response.class);
        assertEquals(response.getCode(),Response.SUCC);

    }
}
