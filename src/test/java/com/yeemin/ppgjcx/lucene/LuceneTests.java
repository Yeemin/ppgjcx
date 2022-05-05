package com.yeemin.ppgjcx.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTests {

    // https://www.cnblogs.com/lqmblog/p/15192571.html

    @Test
    public void createIndex() throws Exception {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Document document = new Document();
        Field field = new TextField("name", "生一鸣", Store.YES);
        document.add(field);
        document.add(new TextField("address", "江苏省无锡市惠山区阳光壹佰国际新城", Store.YES));
        document.add(new IntPoint("age", 28));
        document.add(new TextField("email", "yeeminshon@outlook.com", Store.YES));

        indexWriter.addDocument(document);
        indexWriter.close();
        directory.close();
    }

    @Test
    public void matchAllDocsQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Query query = new MatchAllDocsQuery();
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void termQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("address", "阳光"));

        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void booleanQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        BooleanQuery booleanQuery = new BooleanQuery.Builder()
                .add(new TermQuery(new Term("name", "生")), Occur.MUST)
                .add(new TermQuery(new Term("address", "无锡")), Occur.MUST)
                .build();
        TopDocs topDocs = searcher.search(booleanQuery, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void queryParser() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        QueryParser queryParser = new QueryParser("address", new IKAnalyzer());
        Query query = queryParser.parse("国际");

        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void multiFieldQueryParser() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[] { "name", "address" },
                new IKAnalyzer());
        Query query = multiFieldQueryParser.parse("新城");
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void numbericRangeQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Query query = IntPoint.newRangeQuery("age", 20, 40);
        TopDocs topDocs = searcher.search(query, 10);
        System.out.println("totalHits: " + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            System.out.println("doc: " + doc);
            Document document = searcher.doc(doc);
            List<IndexableField> fields = document.getFields();
            for (IndexableField field : fields) {
                System.out.printf("key: %s, value: %s\n", field.name(), field.stringValue());
            }
        }

        reader.close();
        directory.close();
    }

    @Test
    public void deleteAll() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        indexWriter.deleteAll();
        indexWriter.close();
        directory.close();
    }

    @Test
    public void deleteByQuery() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Query query = new TermQuery(new Term("address", "无锡"));
        indexWriter.deleteDocuments(query);
        indexWriter.close();
        directory.close();
    }

    @Test
    public void update() throws IOException {
        Directory directory = FSDirectory.open(Path.of("lucene/demo"));
        IKAnalyzer ikAnalyzer = new IKAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(ikAnalyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        Document document = new Document();
        document.add(new TextField("address", "江苏省扬州市宝应县", Store.YES));
        indexWriter.updateDocument(new Term("address", "阳光"), document);

        indexWriter.close();
        directory.close();
    }
}
