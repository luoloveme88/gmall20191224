package com.atguigu.gmall.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.beans.PmsSearchSkuInfo;
import com.atguigu.gmall.beans.PmsSearchSkuInfoParam;
import com.atguigu.gmall.beans.PmsSkuAttrValue;
import com.atguigu.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchSkuInfoParam searchSkuInfoParam) {

        //query   QuerySourceBuilder
        //bool  BoolQueryBuilder
        //filter {term}  TermQueryBuilder
        //must {match}  MatchQueryBuilder
        // from
        // size
        // sort
        // jest的dsl工具

        String dsl = putDSL(searchSkuInfoParam);
        System.out.println(dsl);
        List<PmsSearchSkuInfo> list = new ArrayList<>();
        Search search = new Search.Builder(dsl).addIndex("gmall_pms").addType("PmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
        }
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;

            Map<String, List<String>> highlight = hit.highlight;
            if(highlight!=null){
                String skuName = highlight.get("skuName").get(0);
                source.setSkuName(skuName);
            }
            list.add(source);
        }

        return list;
    }

    private String putDSL(PmsSearchSkuInfoParam searchSkuInfoParam){
        List<PmsSkuAttrValue> skuAttrValueList = searchSkuInfoParam.getSkuAttrValueList();
        String catalog3Id = searchSkuInfoParam.getCatalog3Id();
        //组装DSL语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (skuAttrValueList != null) {
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                boolQueryBuilder.filter(new TermQueryBuilder("skuAttrValueList.valueId",
                        pmsSkuAttrValue.getValueId()));
            }
        }
        if(StringUtils.isNotBlank(catalog3Id)){
            boolQueryBuilder.filter(new TermQueryBuilder("catalog3Id",
                    catalog3Id));
        }
        if(StringUtils.isNotBlank(searchSkuInfoParam.getKeyword())){
            boolQueryBuilder.must(new MatchQueryBuilder("skuName", searchSkuInfoParam.getKeyword()));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("skuName");
        searchSourceBuilder.highlight(highlightBuilder);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.sort("_id", SortOrder.ASC);
        return searchSourceBuilder.toString();



    }

}
