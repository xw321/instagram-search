package com.example.indexing;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * This class handles the API call from front-end.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RecordController {
    @PostMapping("/search")
    public String[] search(@RequestParam("query") String query) throws IOException {
        // parse the user input query text
        // List<String> queryTerms = RecordService.getQueryTerms(query);
        // rank all records based on parsed query
        //PriorityQueue<RecordBM25> heap = RecordService.buildRank(queryTerms);
        // get the top 20 ranked results and send back to front-end
        //int[] res = RecordService.getTopK(20, heap);


        // connect
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        SearchRequest searchRequest = new SearchRequest("instapost");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query, "description", "hashtags")
                .fuzziness(Fuzziness.AUTO)).size(50);
        searchRequest.source(searchSourceBuilder);
        // issue request
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        float maxScore = hits.getMaxScore();
        String[] responseString = new String[searchHits.length];
        PriorityQueue<NewHit> maxHeap = new PriorityQueue<>((a, b) -> (a.getNewScore() >= b.getNewScore() ? -1 : 1));

        for (int i = 0; i < searchHits.length; i++) {
            // do something with the SearchHit
            float newScore = getNewScore(searchHits[i], maxScore);
            maxHeap.offer(new NewHit(searchHits[i], newScore));
        }
        client.close();
        int k = 0;
        while (!maxHeap.isEmpty()) {
            responseString[k++] = maxHeap.poll().getHit().getSourceAsString();
        }
        return responseString;
    }

    private float getNewScore(SearchHit searchHit, float maxScore) {
        float raw = searchHit.getScore();
        Map<String, Object> map = searchHit.getSourceAsMap();
        int likes = (int) map.get("likes");
        int comments = (int) map.get("comments");
        float weight = (float) (likes * 0.8 + comments * 0.2);
        int scale = (int) (weight / raw) + 1;
        float newScore = (float) (1.0 + raw + weight * 1.0 / scale);
        newScore = (float) (raw - 1 + (Math.log(newScore) / Math.log(maxScore)));
        System.out.println("old : " + raw + "    new : " + newScore);
        return newScore;

    }
}
