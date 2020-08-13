package com.example.elasticsearch;


import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author wangpeil
 */
@Service
public class EsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EsService.class);
    private final RestHighLevelClient restHighLevelClient;

    @Autowired
    public EsService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


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
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    public boolean deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("first_index");
        request.timeout(TimeValue.timeValueMinutes(2));
        try {
            AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("索引不存在");
            }
        }
        return false;
    }

    public boolean existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("first_index");
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public boolean openIndex() throws IOException {
        OpenIndexRequest request = new OpenIndexRequest("first_index");
        OpenIndexResponse response = restHighLevelClient.indices().open(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    public boolean closeIndex() throws IOException {
        CloseIndexRequest request = new CloseIndexRequest("first_index");
        CloseIndexResponse response = restHighLevelClient.indices().close(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * 更换索引名称
     *
     * @throws IOException io异常
     */
    public boolean shrinkIndex() throws IOException {
        ResizeRequest request = new ResizeRequest("other_index", "first_index");
        ResizeResponse response = restHighLevelClient.indices().shrink(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    public String search() throws IOException {
        GetRequest request = new GetRequest("first_index","1");
        try {
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        if (response.isExists()) {
            return response.getSourceAsString();
        }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("索引不存在",e);
            }
        }
        return null;
    }
}
