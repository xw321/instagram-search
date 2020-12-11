package com.example.indexing;

import com.google.gson.stream.JsonReader;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class IndexJson {

    public static void main(String[] args) throws IOException {
        // connect
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        // read json file
        File folder = new File("src/data");
        File[] listOfFiles = folder.listFiles();


        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (!filename.endsWith(".json")) {
                continue;
            }
            String jsonFilePath = folder + "/" + filename;
            // initialize jsonReader class by passing reader
            JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(jsonFilePath), StandardCharsets.UTF_8));
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray(); //start of json array

            while (jsonReader.hasNext()) { //next json array element

                Post post = gson.fromJson(jsonReader, Post.class);
                // build obj
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.field("shortcode", post.getShortcode());
                    builder.field("type", post.getType());
                    builder.field("display_url", post.getDisplay_url());
                    builder.field("thumbnail_src", post.getThumbnail_src());
                    builder.field("description", post.getDescription());

                    builder.field("owner_id", post.getOwner().getId());
                    builder.field("owner_username", post.getOwner().getUsername());
                    builder.field("is_video", post.isIs_video());

                    builder.field("comments", post.getComments());
                    builder.field("likes", post.getLikes());
                    builder.field("views", post.getViews());
                    builder.field("taken_at_timestamp", post.getTaken_at_timestamp());
                    builder.field("hashtags", post.getHashtags());
                    builder.field("mentions", post.getMentions());
                }
                builder.endObject();
                IndexRequest indexRequest = new IndexRequest("instapost")
                        .id(post.getId()).source(builder);


                client.index(indexRequest, RequestOptions.DEFAULT);
            }
        }

        client.close();
    }
}
