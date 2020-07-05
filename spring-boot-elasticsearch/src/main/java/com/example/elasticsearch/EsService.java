package com.example.elasticsearch;

import com.example.elasticsearch.dao.UserRepository;
import com.example.elasticsearch.pojo.User;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wangpeil
 */
@Service
public class EsService {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final UserRepository userRepository;

    @Autowired
    public EsService(ElasticsearchRestTemplate elasticsearchRestTemplate, UserRepository userRepository) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.userRepository = userRepository;
    }


    public void addIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(User.class);
        indexOperations.create();
        indexOperations.putMapping(indexOperations.createMapping());
    }

    public void deleteIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(User.class);
        indexOperations.delete();
    }

    public void create() {
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        userRepository.saveAll(list);
    }

    public List<User> find() {
        Iterable<User> all = userRepository.findAll();
        return new ArrayList<>((Collection<? extends User>) all);
    }


    public List<StringTerms.Bucket> agg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms("genderAgg").field("gender").subAggregation(AggregationBuilders.terms("subAge").field("age")));
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        AggregatedPage<User> search = (AggregatedPage<User>) userRepository.search(queryBuilder.build());
        StringTerms genderAgg = (StringTerms) search.getAggregation("genderAgg");
        assert genderAgg != null;
        return genderAgg.getBuckets();
    }
}
