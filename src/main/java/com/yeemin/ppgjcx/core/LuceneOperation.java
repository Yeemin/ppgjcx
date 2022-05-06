package com.yeemin.ppgjcx.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Service
public class LuceneOperation implements SmartLifecycle {

    private Directory directory;

    private IKAnalyzer ikAnalyzer;

    private IndexWriterConfig indexWriterConfig;

    public void index(String id, Object object) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            Document document = new Document();
            jsonObject.forEach((k, v) -> {
                document.add(new TextField(k, String.valueOf(v), Store.YES));
            });
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try {
            directory = FSDirectory.open(Path.of("lucene/demo"));
            ikAnalyzer = new IKAnalyzer();
            indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        Optional.ofNullable(directory).ifPresent(directory -> {
            try {
                directory.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean isRunning() {
        return false;
    }

}
