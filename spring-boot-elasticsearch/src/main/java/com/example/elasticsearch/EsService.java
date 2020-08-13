package com.example.elasticsearch;


import com.example.elasticsearch.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wangpeil
 */
@Service
public class EsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EsService.class);
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Qualifier("restHighLevelClient")
    private final RestHighLevelClient client;

    @Autowired
    public EsService(RestHighLevelClient restHighLevelClient) {
        this.client = restHighLevelClient;
    }

    /**
     * 添加索引
     *
     * @return
     * @throws IOException
     */
    public boolean addIndex() throws IOException {
        // 添加一个索引名称
        CreateIndexRequest request = new CreateIndexRequest("first_index");
        // 添加设置
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
        // 添加mapping
        request.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        // 过时方式 更换对应的package的类
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @return
     * @throws IOException
     */
    public boolean deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("first_index");
        request.timeout(TimeValue.timeValueMinutes(2));
        try {
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("索引不存在");
            }
        }
        return false;
    }

    /**
     * 查看索引是否存在
     *
     * @return
     * @throws IOException
     */
    public boolean existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("first_index");
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    public boolean openIndex() throws IOException {
        OpenIndexRequest request = new OpenIndexRequest("first_index");
        OpenIndexResponse response = client.indices().open(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    public boolean closeIndex() throws IOException {
        CloseIndexRequest request = new CloseIndexRequest("first_index");
        CloseIndexResponse response = client.indices().close(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 更换索引名称
     *
     * @throws IOException io异常
     */
    public boolean shrinkIndex() throws IOException {
        ResizeRequest request = new ResizeRequest("other_index", "first_index");
        ResizeResponse response = client.indices().shrink(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 添加文档
     *
     * @throws IOException
     */
    public void add() throws IOException {
        User user = new User(3, "male");
        IndexRequest request = new IndexRequest("first_index");
        request.timeout("1s");
        request.id("1").source(OBJECT_MAPPER.writeValueAsString(user), XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 查询文档
     *
     * @return
     * @throws IOException
     */
    public String search() throws IOException {
        GetRequest request = new GetRequest("first_index", "1");
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                return response.getSourceAsString();
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("索引不存在", e);
            }
        }
        return null;
    }

    /**
     * 更新文档
     * @throws IOException
     */
    public void update() throws IOException {
        User user = new User(3, "female");
        UpdateRequest request = new UpdateRequest("first_index", "1");
        request.timeout("1s");
        request.doc(OBJECT_MAPPER.writeValueAsString(user), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 删除文档
     * @throws IOException
     */
    public void delete() throws IOException {
        DeleteRequest request = new DeleteRequest("first_index", "1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }
}
