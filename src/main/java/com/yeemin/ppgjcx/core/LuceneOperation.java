package com.yeemin.ppgjcx.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Service
public class LuceneOperation implements SmartLifecycle {

    private Directory directory;

    private IKAnalyzer ikAnalyzer;

    public void index(Object object) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        try (IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(ikAnalyzer))) {
            Document document = new Document();
            jsonObject.forEach((k, v) -> {
                document.add(new TextField(k, String.valueOf(v), Store.YES));
            });
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> search(String key, String value) {
        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Query query = new TermQuery(new Term(key, value));

            TopDocs topDocs = searcher.search(query, 10);
            System.out.println("totalHits: " + topDocs.totalHits);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<Map<String, String>> list = new ArrayList<>();
            for (ScoreDoc scoreDoc : scoreDocs) {
                int doc = scoreDoc.doc;
                Document document = searcher.doc(doc);
                List<IndexableField> fields = document.getFields();
                Map<String, String> map = new HashMap<>();
                for (IndexableField field : fields) {
                    System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
                    map.put(field.name(), field.stringValue());
                }
                list.add(map);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(0);
    }

    @Override
    public void start() {
        try {
            directory = FSDirectory.open(Path.of("lucene/demo"));
            ikAnalyzer = new IKAnalyzer();
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
